package io.markdom.handler.html.cleaner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.htmlcleaner.CData;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.ContentNode;
import org.htmlcleaner.DoctypeToken;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.HtmlNode;
import org.htmlcleaner.PrettyHtmlSerializer;
import org.htmlcleaner.Serializer;
import org.htmlcleaner.SimpleHtmlSerializer;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.TagNodeVisitor;

import io.markdom.handler.html.HtmlDocumentResult;
import io.markdom.util.Attribute;
import io.markdom.util.Attributes;
import io.markdom.util.ObjectHelper;

public final class HtmlCleanerDocumentResult implements HtmlDocumentResult<TagNode, TagNode, List<HtmlNode>> {

	private final HtmlCleaner cleaner;

	private final Serializer prettySerializer;

	private final Serializer serializer;

	private final TagNode document;

	public HtmlCleanerDocumentResult(TagNode document) {
		this.document = ObjectHelper.notNull("document", document);

		cleaner = new HtmlCleaner();

		CleanerProperties properties = cleaner.getProperties();
		properties.setHtmlVersion(HtmlCleaner.HTML_5);
		properties.setOmitDoctypeDeclaration(true);
		properties.setOmitXmlDeclaration(true);

		prettySerializer = new PrettyHtmlSerializer(properties, " ");

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
	public TagNode asElement(String tagName, Attributes attributes) {
		TagNode tagNode = new TagNode(tagName);
		for (Attribute attribute : attributes) {
			tagNode.addAttribute(attribute.getKey(), attribute.getValue());
		}
		tagNode.addChildren(asNodes());
		return tagNode;
	}

	@Override
	public String asElementText(String tagName, Attributes attributes, boolean pretty) {
		return asText(asElement(tagName, attributes), pretty);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<HtmlNode> asNodes() {
		return (List<HtmlNode>) asDocument().findElementByName("body", true).getAllChildren();
	}

	@Override
	public String asNodesText(boolean pretty) {
		String elementText = asElementText("foo", pretty);
		if (pretty) {
			return elementText.substring("<foo>\n".length(), elementText.length() - "\n</foo>".length());
		} else {
			return elementText.substring("<foo>".length(), elementText.length() - "</foo>".length());
		}
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

		Map<TagNode, TagNode> copyMap = new HashMap<>();

		tagNode.traverse(new TagNodeVisitor() {

			@Override
			public boolean visit(TagNode parentNode, HtmlNode htmlNode) {

				if (htmlNode instanceof TagNode) {

					TagNode tagNode = (TagNode) htmlNode;
					TagNode copyNode = tagNode.makeCopy();
					copyMap.put(tagNode, copyNode);
					if (null != parentNode) {
						copyMap.get(parentNode).addChild(copyNode);
					}

				} else if (htmlNode instanceof CData) {

					CData cdata = (CData) htmlNode;
					if (null != parentNode) {
						copyMap.get(parentNode).addChild(new CData(cdata.getContent()));
					}

				} else if (htmlNode instanceof ContentNode) {

					ContentNode contentNode = (ContentNode) htmlNode;
					if (null != parentNode) {
						copyMap.get(parentNode).addChild(new ContentNode(contentNode.getContent()));
					}

				}

				return true;

			}
		});

		return copyMap.get(tagNode);
	}

}
