package io.markdom.handler.html.cleaner;

import java.util.List;
import java.util.regex.Pattern;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.DoctypeToken;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.PrettyHtmlSerializer;
import org.htmlcleaner.Serializer;
import org.htmlcleaner.SimpleHtmlSerializer;
import org.htmlcleaner.TagNode;

import io.markdom.handler.html.HtmlDocumentResult;
import io.markdom.util.Attribute;

public final class HtmlCleanerDocumentResult implements HtmlDocumentResult<TagNode, TagNode, List<TagNode>> {

	private final HtmlCleaner cleaner;

	private final Serializer prettySerializer;

	private final Serializer serializer;

	private final TagNode document;

	public HtmlCleanerDocumentResult(TagNode document) {

		if (null == document) {
			throw new IllegalArgumentException("The given document is null");
		}
		this.document = document;

		cleaner = new HtmlCleaner();

		CleanerProperties properties = cleaner.getProperties();
		properties.setHtmlVersion(HtmlCleaner.HTML_5);
		properties.setOmitDoctypeDeclaration(true);
		properties.setOmitXmlDeclaration(true);

		prettySerializer = new PrettyHtmlSerializer(properties);

		serializer = new SimpleHtmlSerializer(properties);

	}

	@Override
	public TagNode asDocument() {
		return makeDeepCopy(document);
	}

	@Override
	public String asDocumentText(boolean pretty) {
		return asText(asDocument(), pretty);
	}

	@Override
	public TagNode asElement(String tagName, Iterable<Attribute> attributes) {
		TagNode tagNode = new TagNode(tagName);
		for (Attribute attribute : attributes) {
			tagNode.addAttribute(attribute.getKey(), attribute.getValue());
		}
		for (TagNode blockNode : asElements()) {
			tagNode.addChild(makeDeepCopy(blockNode));
		}
		return tagNode;
	}

	@Override
	public String asElementText(String tagName, Iterable<Attribute> attributes, boolean pretty) {
		return asText(asElement(tagName, attributes), pretty);
	}

	@Override
	public List<TagNode> asElements() {
		return asDocument().findElementByName("body", true).getChildTagList();
	}

	@Override
	public String asElementsText(boolean pretty) {
		StringBuilder builder = new StringBuilder();
		for (TagNode blockNode : asElements()) {
			builder.append(asText(blockNode, pretty));
		}
		return builder.toString();
	}

	private String asText(TagNode tagNode, boolean pretty) {
		DoctypeToken docType = tagNode.getDocType();
		if (null != docType) {
			return prepareDocType(docType) + selectSerializer(pretty).getAsString(tagNode);
		} else {
			return selectSerializer(pretty).getAsString(tagNode);
		}
	}

	private String prepareDocType(DoctypeToken docType) {
		return docType.toString().replaceFirst(Pattern.quote("!DOCTYPE"), "!doctype");
	}

	private Serializer selectSerializer(boolean pretty) {
		return pretty ? prettySerializer : serializer;
	}

	private TagNode makeDeepCopy(TagNode tagNode) {
		TagNode copyNode = tagNode.makeCopy();
		for (TagNode childNode : tagNode.getChildTagList()) {
			copyNode.addChild(makeDeepCopy(childNode));
		}
		return tagNode;
	}

}
