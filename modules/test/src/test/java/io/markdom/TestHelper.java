package io.markdom;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import net.markenwerk.utils.text.fetcher.BufferedTextFetcher;

@UtilityClass
public class TestHelper {

	public static Reader openExampleHtml() {
		return open("example.html");
	}

	public static Reader openExampleXhtml() {
		return open("example.xhtml");
	}

	public static Reader openExampleJson() {
		return open("example.json");
	}

	public static Reader openExampleXml() {
		return open("example.xml");
	}

	private static Reader open(String filename) {
		InputStream in = TestHelper.class.getResourceAsStream("/" + filename);
		return new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8")));
	}

	public static String readExampleHtml() {
		return read(openExampleHtml());
	}

	public static String readExampleXhtml() {
		return read(openExampleXhtml());
	}

	public static String readExampleJson() {
		return read(openExampleJson());
	}

	public static String readExampleXml() {
		return read(openExampleXml());
	}

	@SneakyThrows
	private static String read(Reader reader) {
		return new BufferedTextFetcher().read(reader, true);
	}

	@SneakyThrows
	public static DocumentBuilder getDocumentBuilder() {
		DocumentBuilderFactory xmlFactory = DocumentBuilderFactory.newInstance();
		xmlFactory.setNamespaceAware(true);
		return xmlFactory.newDocumentBuilder();
	}

}
