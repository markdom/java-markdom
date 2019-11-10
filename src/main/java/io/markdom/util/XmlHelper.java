package io.markdom.util;

import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Node;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class XmlHelper {

	@SneakyThrows
	public static String asText(Node node, boolean pretty) {

		StreamResult result = new StreamResult(new StringWriter());

		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");

		if (pretty) {
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		}

		if (node instanceof Document) {
			DocumentType doctype = ((Document) node).getDoctype();
			if (null != doctype) {
				String systemId = doctype.getSystemId();
				if (null != systemId && !systemId.isEmpty()) {
					transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, systemId);
				}
				String publicId = doctype.getPublicId();
				if (null != publicId && !publicId.isEmpty()) {
					transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, publicId);
				}
			}
		} else {
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		}

		transformer.transform(new DOMSource(node), result);
		return result.getWriter().toString();

	}

}
