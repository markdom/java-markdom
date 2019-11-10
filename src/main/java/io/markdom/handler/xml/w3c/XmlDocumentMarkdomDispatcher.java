package io.markdom.handler.xml.w3c;

import java.util.Iterator;
import java.util.Optional;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import io.markdom.common.MarkdomException;
import io.markdom.handler.xml.AbstractXmlDocumentMarkdomDispatcher;
import io.markdom.util.ObjectHelper;
import lombok.SneakyThrows;
import net.markenwerk.commons.interfaces.Predicate;
import net.markenwerk.commons.iterators.ConvertingIterator;
import net.markenwerk.commons.iterators.FilteredIterator;
import net.markenwerk.commons.iterators.NodeListIterator;

public final class XmlDocumentMarkdomDispatcher extends AbstractXmlDocumentMarkdomDispatcher<Element> {

	private static final Predicate<Node> ELEMENT_PREDICATE = new Predicate<Node>() {
		@Override
		public boolean test(Node node) {
			return Node.ELEMENT_NODE == node.getNodeType();
		}
	};

	private static final Predicate<Node> TEXT_PREDICATE = new Predicate<Node>() {
		@Override
		public boolean test(Node node) {
			return Node.TEXT_NODE == node.getNodeType() || Node.CDATA_SECTION_NODE == node.getNodeType();
		}
	};

	private final Element element;

	@SneakyThrows
	public XmlDocumentMarkdomDispatcher(Document document) {
		this.element = ObjectHelper.notNull("document", document).getDocumentElement();
	}

	public XmlDocumentMarkdomDispatcher(Element element) {
		this.element = ObjectHelper.notNull("element", element);
	}

	@Override
	protected Element getRootElement() {
		return element;
	}

	@Override
	protected String getName(Element xmlElement) {
		return xmlElement.getLocalName();
	}

	@Override
	protected Iterator<Element> getElements(Element xmlElement) {
		// @formatter:off
		return new ConvertingIterator<>(
			new FilteredIterator<>(
				new NodeListIterator(
					xmlElement.getChildNodes()
				),
				ELEMENT_PREDICATE
			),
			node -> (Element) node
		);
		// @formatter:on
	}

	private Iterator<String> getTexts(Element xmlElement) {
		// @formatter:off
		return new ConvertingIterator<>(
			new FilteredIterator<>(
				new NodeListIterator(
					xmlElement.getChildNodes()
				),
				TEXT_PREDICATE
			),
			Node::getTextContent
		);
		// @formatter:on
	}

	@Override
	protected String getText(Element xmlElement) {
		StringBuilder builder = new StringBuilder();
		Iterator<String> iterator = getTexts(xmlElement);
		while (iterator.hasNext()) {
			builder.append(iterator.next());
		}
		return builder.toString().trim();
	}

	@Override
	protected Optional<String> optString(Element xmlElement, String key) {
		String attribute = xmlElement.getAttribute(key);
		if (null == attribute || 0 == attribute.length()) {
			return Optional.empty();
		} else {
			return Optional.of(attribute);
		}
	}

	@Override
	protected String reqString(Element xmlElement, String key) {
		String attribute = xmlElement.getAttribute(key);
		if (null == attribute || 0 == attribute.length()) {
			throw new MarkdomException("Expected attribute for key " + key);
		} else {
			return attribute;
		}
	}

	@Override
	protected Boolean reqBoolean(Element xmlElement, String key) {
		return Boolean.parseBoolean(reqString(xmlElement, key));
	}

	@Override
	protected Integer reqInteger(Element xmlElement, String key) {
		return Integer.parseInt(reqString(xmlElement, key));
	}

}
