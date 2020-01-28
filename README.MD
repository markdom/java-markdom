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

The opinionated meta-module `suite` comprises a selection of commonly used modules, namely `util`, `core`, `handler-commonmark-atlassian`, `handler-html-jsoup`, `handler-html-w3c`, `handler-json-org`, `handler-text-commonmark`, `handler-xml-w3c` and `model-basic`. The meta-module `full` comprises all modules.

## Maven


This library is hosted in the [Maven Central Repository](https://search.maven.org/artifact/io.markdom/suite). You can use it with the following coordinates:

```xml
<dependency>
  <groupId>io.markdom</groupId>
  <artifactId>suite</artifactId>
  <version>0.0.7</version>
</dependency>
```

To resolve another module or meta-module, simply replace the artifact ID `suite` with the name of the desired module or meta-module.

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
      onDocument(document);
    }

    @Override
    public void onBlock(MarkdomBlock block) {
      onBlock(block);
    }

    @Override
    public void onListItem(MarkdomListItem listItem) {
      onListItem(listItem);
    }

    @Override
    public void onContent(MarkdomContent content) {
      onContent(content);
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
public void onBlockparent(MarkdomBlockParent parent) {
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
  block.Parent().ifPresent(parent -> onBlockParent(parent));
}
```

### Working with Commonmark documents

#### Using the Atlassian Commonmark library

Using the Atlassian Commonmark library requires the module `handler-commonmark-atlassian`. This module contains a dispatcher and a handler that consume and produce Commonmark documents.

##### Consuming a Commonmark document

The class `AtlassianCommonmarkDocumentMarkdomDispatcher` is a `MarkdomDispatcher` that consumes a `Document`.

```java
Parser parser = Parser.builder().build();
Document commonmarkDocument = (Document) parser.parseReader(new FileReader("test.md"));

MarkdomDispatcher dispatcher = new AtlassianCommonmarkDocumentMarkdomDispatcher(commonmarkDocument);
MarkdomDocumentMarkdomHandler handler = new MarkdomDocumentMarkdomHandler(new BasicMarkdomFactory());

MarkdomDocument document = dispatcher.handle(handler);
```

##### Producing a Commonamrk document

The class `AtlassianCommonmarkDocumentMarkdomHandler` is a `MarkdomHandler` that produces a `Document`.

```java
MarkdomDispatcher dispatcher = getMarkdomDocument();
AtlassianCommonmarkDocumentMarkdomHandler handler = new AtlassianCommonmarkDocumentMarkdomHandler();

Document commonmarkDocument = dispatcher.handle(handler);
```

*Note: The Atlassian Comonmark library doesn't provide a mechanism to convert a Commonmark document into Commonmark text.*

### Working with Commonmark text

#### Using the Commonmark text module

Using the Commonmark text module requires the module `handler-text-commonmark`. This module contains a dispatcher and a handler that consume and produce Commonmark documents.

##### Producing Commonmark text

The class `CommonmarkDocumentMarkdomHandler` is a `MarkdomHandler` that produces Commonmark text. The handler takes an `Appendable` (i.e. `System.out` or a `StringWriter`) as a constructor argument and return that `Appendable` as es result.  It appends the produced Commonmark text to the `Appendable` as a side-effect.

```java
Appendable appendable = getAppendable();
MarkdomDispatcher dispatcher = getMarkdomDocument();

dispatcher.handle(new CommonmarkTextMarkdomHandler<>(appendable));
```

### Working with Markdom JSON

All handlers that create JSON objects wrap their result as a `JsonObjectResult`. A `JsonObjectResult` has a getter for the actual result and convenience methods that convert the wrapped result into text.

#### Using the JSON reference library

Using the JSON reference library requires the module `handler-json-org`. This module contains a dispatchers and a handler that consume and produce Markdom JSON objects.

##### Consuming a JSON document

The class `JsonObjectMarkdomDispatcher` is a `MarkdomDispatcher` that consumes a `JSONObject`.

```java
JSONObject jsonObject = new JSONObject(new JSONTokener(openJsonInputStream()));

MarkdomDispatcher dispatcher = new JsonObjectMarkdomDispatcher(jsonObject);
MarkdomDocumentMarkdomHandler handler = new MarkdomDocumentMarkdomHandler(new BasicMarkdomFactory());

MarkdomDocument document = dispatcher.handle(handler);
```

##### Producing a JSON document

The class `JsonObjectMarkdomHandler` is a `MarkdomHandler` that produces a wrapped `JSONObject`.

```java
MarkdomDispatcher dispatcher = getMarkdomDocument();
JsonObjectMarkdomHandler handler = new JsonObjectMarkdomHandler();

JSONObject jsonObject = dispatcher.handle(handler).asObject();
```

### Working with Markdom XML

All handlers that create XML documents wrap their result in a `XmlDocumentResult`. A `XmlDocumentResult` has a getter for the actual result and convenience methods that convert the wrapped result into text or as the root element of the wrapped  XML document.

#### Using the default XML implementation

Using the default XML implementation requires the module `handler-xml-w3c`. This module contains a dispatcher and  a handler that consume and produce Markdom XML documents.

The components provided by this module need a `DocumentBuilder`. The `DocumentBuilder` should be aware of namespaces.

```java
DocumentBuilderFactory xmlFactory = DocumentBuilderFactory.newInstance();
xmlFactory.setNamespaceAware(true);
DocumentBuilder xmlBuilder = xmlFactory.newDocumentBuilder();
```

##### Consuming a XML document

```java
Document xmlDocument = xmlBuilder.parse(new InputSource(TestHelper.openExampleXml()));

MarkdomDispatcher dispatcher = new XmlDocumentMarkdomDispatcher(xmlDocument);
MarkdomDocumentMarkdomHandler handler = new MarkdomDocumentMarkdomHandler(new BasicMarkdomFactory());

MarkdomDocument document = dispatcher.handle(handler);
```

##### Producing a XML document

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