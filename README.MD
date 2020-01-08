# Markdom reference implementation for Java

[![Build Status][BuildStatusBadge]][BuildStatusLink]
[![Maven Central][MavenCentralBadge]][MavenCentralLink]
[![Issues][IssuesBadge]][IssuesLink]
[![Java SE 8][JavaSE8Badge]][JavaSE8Link]
[![MIT License][MITLicenseBadge]][MITLicenseLink]

## Overview

This library provides the reference implementation of [Markdom](http://markdom.io).

## Modules

This library consists of multiple modules, namely:

- `util`: A support module that contains common utilities.
- `core`: A support module that contains datatypes, interfaces and abstract base implementations.
- `handler-commonmark-atlassian`: A handler that consumes CommonMark using the Atlassian [Commonmark](https://github.com/atlassian/commonmark-java) library.
- `handler-html-cleaner`: A handler that produces (X)HTML using the [HtmlCleaner](http://htmlcleaner.sourceforge.net) library.
- `handler-html-jsoup`: A handler that produces (X)HTML using the [jsoup](https://jsoup.org) HTML library.
- `handler-html-w3c`: A handler that produces XHTML using the Java default XML implementation.
- `handler-json-gson`: A handler that consumes and produces Markdom JSON using the [Gson](https://github.com/google/gson) library.
- `handler-json-jackson`: A handler that consumes and produces Markdom JSON using the [Jackson databind](https://github.com/FasterXML/jackson-databind) library.
- `handler-json-jakarta`: A handler that consumes and produces Markdom JSON using the [Jakarta JSON](https://github.com/eclipse-ee4j/jsonp) library.
- `handler-json-org`: A handler that consumes and produces Markdom JSON using the [JSON reference](https://github.com/stleary/JSON-java) library.
- `handler-json-simple`: A handler that consumes and produces Markdom JSON using the [JSON simple](https://cliftonlabs.github.io/json-simple/) library.
- `handler-text-commonmark`: A handler that produces Commonmark text.
- `handler-xml-w3c`: A handler that produces Markdom XML using the Java default XML implementation.
- `model-basic`: The reference Markdom model implementation.

The opinionated meta-module `suite` comprises a selection of commonly used modules, namely `util`, `core`, `handler-commonmark-atlassian`, `handler-html-jsoup`, `handler-html-w3c`, `handler-json-org`, `handler-text-commonmark`, `handler-xml-w3c` and `model-basic`.

## Maven


This library is hosted in the [Maven Central Repository](https://search.maven.org/artifact/io.markdom/suite). You can use it with the following coordinates:

```xml
<dependency>
  <groupId>io.markdom</groupId>
  <artifactId>suite</artifactId>
  <version>0.0.1</version>
</dependency>
```

To resolve another module, simply replace the artifact ID `suite` with the name of the desired module.

## Usage

### Working with Markdom documents

#### Creating a Markdom document from scratch.

Creating a `MarkdomDocument`requires a `MarkdomFactory`. The `model-basic` module contains the `BasicMarkdomFactory` implementation.

```java
MarkdomFactory factory = new BasicMarkdomFactory();
```

The `MarkdomFactory` comprises several methods to create different `MarkdomNode`s. There are usually two methods for each type of node. One method only takes the required attributes of that node; the other one also takes a list or an array of child nodes. It is therefore possible to create a document in two ways:

By passing the children to the parent, when the parent is created:

```java
MarkdomDocument document = factory.document(
  factory.headingBlock(
    MarkdomHeadingLevel.LEVEL_1,
    factory.textContent("Markdom")
  ),
  factory.unorderedListBlock(
    factory.listItem(
      factory.paragraphBlock(
        factory.textContent("Foo")
      )
    ),
    factory.listItem(
      factory.paragraphBlock(
        factory.textContent("Bar")
      )
    )
  )
);
```

By passing the children to the parent, after the parent has been created:

```java
MarkdomTextContent headingTextContent = factory.textContent("Markdom");

MarkdomHeadingBlock headingBlock = factory.headingBlock(MarkdomHeadingLevel.LEVEL_1);
headingBlock.addContent(headingTextContent);

MarkdomTextContent firstText = factory.textContent("Foo");
MarkdomParagraphBlock firstParagraph = factory.paragraphBlock(firstText);
MarkdomListItem firstListItem = factory.listItem(firstParagraph);

MarkdomTextContent secondText = factory.textContent("Bar");
MarkdomParagraphBlock secondParagraph = factory.paragraphBlock(secondText);
MarkdomListItem secondlistItem = factory.listItem(secondParagraph);

MarkdomUnorderedListBlock listBlock = factory.unorderedListBlock();
listBlock.addListItem(firstListItem);
listBlock.addListItem(secondlistItem);

MarkdomDocument document = factory.document();
document.addBlock(headingBlock);
document.addBlock(listBlock);
```

#### Resolving polymorphic types

Several types are polymorphic. An arbitrary `MarkdomNode` could either be a `MarkdomDocument`, a `MarkdomBlock`, a `MarkdomListItem` or a `MarkdomContent`. The model API provides two ways to resolve the actual type of a node.

By inspecting an enumeration that represent the actual type and casting to the indicated type:

```java
public void onNode(MarkdomNode node) {
  switch (node.getNodeType()) {
    case DOCUMENT:
      onDocument((MarkdomDocument) node);
      break;
    case BLOCK:
      onBlock((MarkdomBlock) node);
      break;
    case LIST_ITEM:
      onListItem((MarkdomListItem) node);
      break;
    case CONTENT:
      onContent((MarkdomContent) node);
      break;
  }
}
```

By passing a `MarkdomNodeChoice` that explicitly resolves the actual type:

```java
public void onNode(MarkdomNode node) {
  node.choose(new MarkdomNodeChoice() {

    @Override
    public void onDocument(MarkdomDocument document) {
      onDocument((MarkdomDocument) node);
    }

    @Override
    public void onBlock(MarkdomBlock block) {
      onBlock((MarkdomBlock) node);
    }

    @Override
    public void onListItem(MarkdomListItem listItem) {
      onListItem((MarkdomListItem) node);
    }

    @Override
    public void onContent(MarkdomContent content) {
      onContent((MarkdomContent) node);
    }

  });
}
```

Additionaly, there is `MarkdomNodeSelection`, which works similar to `MarkdomNodeChoice`, but has a generic return value.

Similar methods and interfaces exists for all polymorphic types, namely: `MarkdomNode`, `MarkdomBlock`, `MarkdomListBlock`, `MarkdomContent`, `MarkdomContentParent` and `MarkdomBlockParent`.

#### Moving down

Every `MarkdomNode` has a method that returns a list of its children. Specific types of nodes have a specific type of children. For example, while `MarkdomNode` will return a  List of `MarkdomNode`s as its children, a `MarkdomDocument`, which is a `MarkdomBlockParent` will return a List of `MarkdomBlock`s. It is therefore possible to inspect the children of a node in in two slightly different ways:

```java
public void onNode(MarkdomNode node) {
  node.getChildren().forEach(child -> onNode(child));
}
```

```java
public void onBlockparent(MarkdomPblockParent parent) {
  parent.getChildren().forEach(child -> onBlock(child));
}
```

#### Moving up

Every `MarkdomNode` has a method that returns the optional parent. Specific types of nodes have a specific type of parent. For example, while `MarkdomNode` will return `MarkdomNode` as its parent, a `MarkdomBlock` will return a `MarkdomBlockParent`. It is therefore possible to inspect the parent of a node in in two slightly different ways:

```java
public void onNode(MarkdomNode node) {
  node.getParent().ifPresent(parent -> onNode(parent));
}
```

```java
public void onBlock(MarkdomBlock block) {
  block.Parent().ifPresent(parent -> onBlockParent(child));
}
```

### Working with Markdom JSON

#### Using the JSON reference library

#### Consuming a JSON document

```java
JSONObject jsonObject = new JSONObject(new JSONTokener(openJsonInputStream()));

MarkdomDispatcher dispatcher = new JsonObjectMarkdomDispatcher(jsonObject);
MarkdomDocumentMarkdomHandler handler = new MarkdomDocumentMarkdomHandler(new BasicMarkdomFactory());

MarkdomDocument document = dispatcher.handle(handler);
```

#### Producing a JSON document

```java
MarkdomDispatcher dispatcher = getMarkdomDocument();
JsonObjectMarkdomHandler handler = new JsonObjectMarkdomHandler();

JSONObject jsonObject = document.handle(handler).asObject();
```

### Working with Markdom XML

#### Using the default XML implementation

```java
DocumentBuilderFactory xmlFactory = DocumentBuilderFactory.newInstance();
xmlFactory.setNamespaceAware(true);
DocumentBuilder xmlBuilder = xmlFactory.newDocumentBuilder();
```

#### Consuming a XML document

```java
Document xmlDocument = xmlBuilder.parse(new InputSource(TestHelper.openExampleXml()));

MarkdomDispatcher dispatcher = new XmlDocumentMarkdomDispatcher(xmlDocument);
MarkdomDocumentMarkdomHandler handler = new MarkdomDocumentMarkdomHandler(new BasicMarkdomFactory());

MarkdomDocument document = dispatcher.handle(handler);
```

#### Producing a XML document

```java
MarkdomDispatcher dispatcher = getMarkdomDocument();
XmlDocumentMarkdomHandler handler = handle(new XmlDocumentMarkdomHandler(xmlBuilder);

Document xmlDocument = dispatcher.handle(handler).asDocument();
```


[BuildStatusBadge]: https://travis-ci.com/markdom/java-markdom.svg?branch=master "Build Status"
[BuildStatusLink]: https://travis-ci.com/markdom/java-markdom

[MavenCentralBadge]: https://maven-badges.herokuapp.com/maven-central/io.markdom/suite/badge.svg "Maven Central"
[MavenCentralLink]: https://maven-badges.herokuapp.com/maven-central/io.markdom/suite

[IssuesBadge]: https://img.shields.io/github/issues/markdom/java-markdom.svg "Issues"
[IssuesLink]: https://github.com/markdom/java-markdom/issues

[JavaSE8Badge]: https://img.shields.io/badge/java-SE_8-brightgreen.svg "Java SE 8"
[JavaSE8Link]: http://docs.oracle.com/javase/8/docs/api/

[MITLicenseBadge]: https://img.shields.io/badge/license-MIT-brightgreen.svg "MIT License"
[MITLicenseLink]: https://github.com/markdom/java-markdom/blob/master/LICENSE
