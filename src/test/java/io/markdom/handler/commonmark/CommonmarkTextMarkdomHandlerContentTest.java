package io.markdom.handler.commonmark;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.StringWriter;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.handler.text.commonmark.CommonmarkTextMarkdomHandler;
import io.markdom.handler.text.commonmark.CommonmarkTextOptions;
import io.markdom.handler.text.commonmark.EmphasisOption;
import io.markdom.handler.text.commonmark.LineBreakOption;
import io.markdom.handler.text.commonmark.LineEndOption;
import io.markdom.model.MarkdomDocument;
import io.markdom.model.MarkdomFactory;
import io.markdom.model.MarkdomParagraphBlock;
import io.markdom.model.basic.BasicMarkdomFactory;

public class CommonmarkTextMarkdomHandlerContentTest {

	private static MarkdomFactory factory;

	private MarkdomDocument document;

	private MarkdomParagraphBlock paragraph;

	@BeforeAll
	public static void setupFactory() {
		factory = new BasicMarkdomFactory();
	}

	@BeforeEach
	public void setupDocument() {
		paragraph = factory.paragraphBlock();
		document = factory.document(paragraph);
	}

	@Test
	public void singleTextLiteralWithoutSpecialCharactersIsPassedThrough() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foobar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foobar", commonmarkText);

	}

	@Test
	public void multipleTextLiteralWithoutSpecialCharactersArePassedThrough() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo"),
			factory.textContent("bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foobar", commonmarkText);

	}

	@Test
	public void leadingSpacesInSingleInTextLiteralsAreRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("  foobar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foobar", commonmarkText);

	}

	@Test
	public void leadingTabsInSingleTextLiteralsAreRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("\t\tfoobar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foobar", commonmarkText);

	}

	@Test
	public void leadingSpacesInMultipleTextLiteralsAreRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent(" "),
			factory.textContent(" foobar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foobar", commonmarkText);

	}

	@Test
	public void leadingTabsInMultipleTextLiteralsAreRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("\t"),
			factory.textContent("\tfoobar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foobar", commonmarkText);

	}

	@Test
	public void trailingSpacesInSingleTextLiteralsAreRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foobar  ")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foobar", commonmarkText);

	}

	@Test
	public void trailingTabsInSingleTextLiteralsAreRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foobar\t\t")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foobar", commonmarkText);

	}

	@Test
	public void leadingSpaceInSingleTextLiteralsBehindOtherContentAreNotRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.codeContent("foo"),
			factory.textContent(" bar") 
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("`foo` bar", commonmarkText);

	}

	@Test
	public void leadingTabInSingleTextLiteralsBehindOtherContentAreNotRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.codeContent("foo"),
			factory.textContent("\tbar") 
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("`foo` bar", commonmarkText);

	}

	@Test
	public void leadingSpacesInSingleTextLiteralsBehindOtherContentProduceOnlyOneSpace() {

		// @formatter:off
		paragraph.addContents(
			factory.codeContent("foo"),
			factory.textContent("  bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("`foo` bar", commonmarkText);

	}

	@Test
	public void leadingTabsInSingleTextLiteralsBehindOtherContentProduceOnlyOneSpace() {

		// @formatter:off
		paragraph.addContents(
			factory.codeContent("foo"),
			factory.textContent("\t\tbar") 
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("`foo` bar", commonmarkText);

	}

	@Test
	public void trailingSpaceInSingleTextLiteralsInFrontOfSoftLineBreaksAreRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo "),
			factory.lineBreakContent(false),
			factory.textContent("bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo\nbar", commonmarkText);

	}

	@Test
	public void trailingTabInSingleTextLiteralsInFrontOfHardLineBreaksAreRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo\t"),
			factory.lineBreakContent(true),
			factory.textContent("bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo\\\nbar", commonmarkText);

	}

	@Test
	public void trailingSpaceInSingleTextLiteralsInFrontOfOtherContentAreNotRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo "), 
			factory.codeContent("bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo `bar`", commonmarkText);

	}

	@Test
	public void trailingTabInSingleTextLiteralsInFrontOfOtherContentAreNotRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo\t"), 
			factory.codeContent("bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo `bar`", commonmarkText);

	}

	@Test
	public void trailingSpacesInSingleTextLiteralsInFrontOfOtherContentProduceOnlyOneSpace() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo  "), 
			factory.codeContent("bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo `bar`", commonmarkText);

	}

	@Test
	public void trailingTabsInSingleTextLiteralsInFrontOfOtherContentProduceOnlyOneSpace() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo\t\t"), 
			factory.codeContent("bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo `bar`", commonmarkText);

	}

	@Test
	public void trailingSpacesInMultipleTextLiteralsAreRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foobar "),
			factory.textContent(" ")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foobar", commonmarkText);

	}

	@Test
	public void trailingTabsInMultipleTextLiteralsAreRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foobar\t"),
			factory.textContent("\t")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foobar", commonmarkText);

	}

	@Test
	public void internalSpacesInSingleTextLiteralProduceOnlyOneSpace() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo   bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo bar", commonmarkText);

	}

	@Test
	public void internalTabsInSingleTextLiteralProduceOnlyOneSpace() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo\t\t\tbar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo bar", commonmarkText);

	}

	@Test
	public void internalSpacesInMultipleTextLiteralProduceOnlyOneSpace() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo "),
			factory.textContent(" "),
			factory.textContent(" bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo bar", commonmarkText);

	}

	@Test
	public void internalTabsInMultipleTextLiteralProduceOnlyOneSpace() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo\t"),
			factory.textContent("\t"),
			factory.textContent("\tbar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo bar", commonmarkText);

	}

	@Test
	public void singleSoftLineBreakProducesLineBreakString() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo"),
			factory.lineBreakContent(false),
			factory.textContent("bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		options.setLineEndOption(LineEndOption.UNIX);
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo\nbar", commonmarkText);

	}

	@Test
	public void singleSoftLineBreakProducesLineBreakString2() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo"),
			factory.lineBreakContent(false),
			factory.textContent("bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		options.setLineEndOption(LineEndOption.WINDOWS);
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo\r\nbar", commonmarkText);

	}

	@Test
	public void multipleSoftLineBreaksProduceOnlyOneLineBreakString() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo"),
			factory.lineBreakContent(false),
			factory.lineBreakContent(false),
			factory.textContent("bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		options.setLineEndOption(LineEndOption.UNIX);
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo\nbar", commonmarkText);

	}

	@Test
	public void singleHardLineBreakProducesLineEndAndLineBreakStrings() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo"),
			factory.lineBreakContent(true),
			factory.textContent("bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		options.setLineBreakOption(LineBreakOption.BACKSLASH);
		options.setLineEndOption(LineEndOption.UNIX);
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo\\\nbar", commonmarkText);

	}

	@Test
	public void singleHardLineBreakProducesLineEndAndLineBreakStrings2() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo"),
			factory.lineBreakContent(true),
			factory.textContent("bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		options.setLineBreakOption(LineBreakOption.DOUBLE_SPACE);
		options.setLineEndOption(LineEndOption.WINDOWS);
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo  \r\nbar", commonmarkText);

	}

	@Test
	public void multipleHardLineBreakProduceOnlyOneLineEndAndLineBreakStrings() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo"),
			factory.lineBreakContent(true),
			factory.lineBreakContent(true),
			factory.textContent("bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		options.setLineBreakOption(LineBreakOption.BACKSLASH);
		options.setLineEndOption(LineEndOption.UNIX);
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo\\\nbar", commonmarkText);

	}

	@Test
	public void muptipleHardLineBreakProduceOnlyOneLineEndAndLineBreakStrings2() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo"),
			factory.lineBreakContent(true),
			factory.lineBreakContent(true),
			factory.textContent("bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		options.setLineBreakOption(LineBreakOption.DOUBLE_SPACE);
		options.setLineEndOption(LineEndOption.WINDOWS);
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo  \r\nbar", commonmarkText);

	}

	@Test
	public void mixedSoftAndHardLineBreakProduceExactlyOneLineEndAndLineBreakStrings() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo"),
			factory.lineBreakContent(false),
			factory.lineBreakContent(true),
			factory.lineBreakContent(false),
			factory.textContent("bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		options.setLineBreakOption(LineBreakOption.BACKSLASH);
		options.setLineEndOption(LineEndOption.UNIX);
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo\\\nbar", commonmarkText);

	}

	@Test
	public void mixedSoftAndHardLineBreakProduceExactlyOneLineEndAndLineBreakStrings2() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo"),
			factory.lineBreakContent(true),
			factory.lineBreakContent(false),
			factory.lineBreakContent(true),
			factory.textContent("bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		options.setLineBreakOption(LineBreakOption.DOUBLE_SPACE);
		options.setLineEndOption(LineEndOption.WINDOWS);
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo  \r\nbar", commonmarkText);

	}

	@Test
	public void leadingSoftLineBreaksAreRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.lineBreakContent(false),
			factory.textContent("foobar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foobar", commonmarkText);

	}

	@Test
	public void leadingHardLineBreaksAreRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.lineBreakContent(true),
			factory.textContent("foobar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foobar", commonmarkText);

	}

	@Test
	public void leadingHardLineBreaksAreRemoved2() {

		// @formatter:off
		paragraph.addContents(
			factory.lineBreakContent(true),
			factory.lineBreakContent(true),
			factory.textContent("foobar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foobar", commonmarkText);

	}

	@Test
	public void leadingSoftAndHardLineBreaksAreRemoved2() {

		// @formatter:off
		paragraph.addContents(
			factory.lineBreakContent(false),
			factory.lineBreakContent(true),
			factory.textContent("foobar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foobar", commonmarkText);

	}

	@Test
	public void leadingHardAndSoftLineBreaksAreRemoved2() {

		// @formatter:off
		paragraph.addContents(
			factory.lineBreakContent(true),
			factory.lineBreakContent(false),
			factory.textContent("foobar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foobar", commonmarkText);

	}

	@Test
	public void trailingSoftLineBreaksAreRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foobar"),
			factory.lineBreakContent(false)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foobar", commonmarkText);

	}

	@Test
	public void trailingHardLineBreaksAreRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foobar"),
			factory.lineBreakContent(true)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foobar", commonmarkText);

	}

	@Test
	public void trailingHardLineBreaksAreRemoved2() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foobar"),
			factory.lineBreakContent(true),
			factory.lineBreakContent(true)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foobar", commonmarkText);

	}

	@Test
	public void trailingHardAndSoftLineBreaksAreRemoved2() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foobar"),
			factory.lineBreakContent(true),
			factory.lineBreakContent(false)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foobar", commonmarkText);

	}

	@Test
	public void trailingSoftAndHardLineBreaksAreRemoved2() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foobar"),
			factory.lineBreakContent(false),
			factory.lineBreakContent(true)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foobar", commonmarkText);

	}

	@Test
	public void singleCodeLiteralWithoutSpecialCharactersIsSurroundedByBackticks() {

		// @formatter:off
		paragraph.addContents(
			factory.codeContent("foobar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("`foobar`", commonmarkText);

	}

	@Test
	public void singleCodeLiteralWithInternalBacktickIsSurroundedByDoubleBackticks() {

		// @formatter:off
		paragraph.addContents(
			factory.codeContent("foo`bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("``foo`bar``", commonmarkText);

	}

	@Test
	public void singleCodeLiteralWithMultipleInternalBacktickIsSurroundedByDoubleBackticks() {

		// @formatter:off
		paragraph.addContents(
			factory.codeContent("foo`bar`baz")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("``foo`bar`baz``", commonmarkText);

	}

	@Test
	public void singleCodeLiteralWithDoubleInternalBacktickIsSurroundedBytripleBackticks() {

		// @formatter:off
		paragraph.addContents(
			factory.codeContent("foo``bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("```foo``bar```", commonmarkText);

	}

	@Test
	public void singleCodeLiteralWithLeadingBacktickIsSurroundedWithSpacesAndDoubleBackticks() {

		// @formatter:off
		paragraph.addContents(
			factory.codeContent("`foobar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("`` `foobar ``", commonmarkText);

	}

	@Test
	public void singleCodeLiteralWithLeadingSpaceIsSurroundedWithSpacesAndBackticks() {

		// @formatter:off
		paragraph.addContents(
			factory.codeContent(" foobar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("`  foobar `", commonmarkText);

	}

	@Test
	public void singleCodeLiteralWithTrailingBacktickIsSurroundedWithSpacesAndDoubleBackticks() {

		// @formatter:off
		paragraph.addContents(
			factory.codeContent("foobar`")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("`` foobar` ``", commonmarkText);

	}

	@Test
	public void singleCodeLiteralWithTrailingSpaceIsSurroundedWithSpacesAndBackticks() {

		// @formatter:off
		paragraph.addContents(
			factory.codeContent("foobar ")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("` foobar  `", commonmarkText);

	}

	@Test
	public void singleCodeLiteralWithOnlyABacktickIsSurroundedWithSpacesAndSoubleBackticks() {

		// @formatter:off
		paragraph.addContents(
			factory.codeContent("`")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("`` ` ``", commonmarkText);

	}

	@Test
	public void singleCodeLiteralWithOnlyABacktickSurroundedBySpacesIsSurroundedWithDoubleSpacesAndDoubleBackticks() {

		// @formatter:off
		paragraph.addContents(
			factory.codeContent(" ` ")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("``  `  ``", commonmarkText);

	}

	@Test
	public void singleCodeLiteralWithOnlySpacesIsSurroundedBySingleBackticks() {

		// @formatter:off
		paragraph.addContents(
			factory.codeContent("   ")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("`   `", commonmarkText);

	}

	@Test
	public void singleCodeLiteralWithTabIsRenderedWithSpace() {

		// @formatter:off
		paragraph.addContents(
			factory.codeContent("foo\tbar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("`foo bar`", commonmarkText);

	}

	@Test
	public void singleCodeLiteralWithNewlineIsRenderedWithSpace1() {

		// @formatter:off
		paragraph.addContents(
			factory.codeContent("foo\nbar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("`foo bar`", commonmarkText);

	}

	@Test
	public void singleCodeLiteralWithNewlineIsRenderedWithSpace2() {

		// @formatter:off
		paragraph.addContents(
			factory.codeContent("foo\r\nbar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("`foo bar`", commonmarkText);

	}

	@Test
	public void singleCodeLiteralWithMultipleNewlinesIsRenderedWithSingleSpace() {

		// @formatter:off
		paragraph.addContents(
			factory.codeContent("foo\n\nbar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("`foo bar`", commonmarkText);

	}

	@Test
	public void singleCodeLiteralWithMultipleNewlinesAtDiffrentPlacesIsRenderedWithSingleSpaces() {

		// @formatter:off
		paragraph.addContents(
			factory.codeContent("foo\nbar\nbaz")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("`foo bar baz`", commonmarkText);

	}

	@Test
	public void singleCodeLiteralWithLeadingNewlineIsRenderedWithoutLeadingSpace() {

		// @formatter:off
		paragraph.addContents(
			factory.codeContent("\nfoobar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("`foobar`", commonmarkText);

	}

	@Test
	public void singleCodeLiteralWithTrailingNewlineIsRenderedWithoutTraingSpace() {

		// @formatter:off
		paragraph.addContents(
			factory.codeContent("foobar\n")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("`foobar`", commonmarkText);

	}

	@Test
	public void singleCodeLiteralWithSpacesAroudNewlineIsRenderedWithSingleSpace() {

		// @formatter:off
		paragraph.addContents(
			factory.codeContent("foo \n bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("`foo bar`", commonmarkText);

	}

	@Test
	public void singleCodeLiteralWithTabsAroudNewlineIsRenderedWithSingleSpace() {

		// @formatter:off
		paragraph.addContents(
			factory.codeContent("foo\t\n\tbar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("`foo bar`", commonmarkText);

	}

	@Test
	public void multipleCodeLiteralsAreCombinedIntoAsingleCodeLiteral() {

		// @formatter:off
		paragraph.addContents(
			factory.codeContent("foo"),
			factory.codeContent("bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("`foobar`", commonmarkText);

	}

	@Test
	public void emptyCodeLiteralsAreRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo"),
			factory.codeContent(""),
			factory.textContent("bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foobar", commonmarkText);

	}

	@Test
	public void emptyCodeLiteralsInbetweenLinebreaksAreRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo"),
			factory.lineBreakContent(true),
			factory.codeContent(""),
			factory.lineBreakContent(true),
			factory.textContent("bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo\\\nbar", commonmarkText);

	}

	@Test
	public void emptyTextLiteralsInbetweenLinebreaksAreRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo"),
			factory.lineBreakContent(true),
			factory.textContent(""),
			factory.lineBreakContent(true),
			factory.textContent("bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo\\\nbar", commonmarkText);

	}

	@Test
	public void imagesWithOutTitleAndAlternativeIsRenderedWitUrlOnly() {

		// @formatter:off
		paragraph.addContents(
			factory.imageContent("/foobar.png")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("![](/foobar.png)", commonmarkText);

	}

	@Test
	public void imagesWithPresenButEmptyTitlesAreRenderedWithUrlOnly() {

		// @formatter:off
		paragraph.addContents(
			factory.imageContent("/foobar.png", Optional.of(""), Optional.empty())
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("![](/foobar.png)", commonmarkText);

	}

	@Test
	public void imagesWithPresenButEmptyAlternativesAreRenderedWithUrlOnly() {

		// @formatter:off
		paragraph.addContents(
			factory.imageContent("/foobar.png", Optional.empty(), Optional.of(""))
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("![](/foobar.png)", commonmarkText);

	}

	@Test
	public void imagesWithPresenAndNonemptyTitlesAreRenderedWithUrlAndTitle() {

		// @formatter:off
		paragraph.addContents(
			factory.imageContent("/foobar.png", Optional.of("title"), Optional.empty())
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("![](/foobar.png title)", commonmarkText);

	}

	@Test
	public void imagesWithPresenAndNonEmptyAlternativesAreRenderedWithUrlAndAlternative() {

		// @formatter:off
		paragraph.addContents(
			factory.imageContent("/foobar.png", Optional.empty(), Optional.of("alternative"))
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("![alternative](/foobar.png)", commonmarkText);

	}

	@Test
	public void imagesWithPresenAndNonEmptyTitlesAndAlternativesAreRenderedWithUrlTitleAndAlternative() {

		// @formatter:off
		paragraph.addContents(
			factory.imageContent("/foobar.png", Optional.of("title"), Optional.of("alternative"))
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("![alternative](/foobar.png title)", commonmarkText);

	}

	@Test
	public void imagesWithEmptyUrlsAreRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo"),
			factory.imageContent(""),
			factory.textContent("bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foobar", commonmarkText);

	}

	@Test
	public void imagesWithEmptyUrlsInbetweenLinebreaksAreRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo"),
			factory.lineBreakContent(true),
			factory.imageContent(""),
			factory.lineBreakContent(true),
			factory.textContent("bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo\\\nbar", commonmarkText);

	}

	@Test
	public void imagesWithEmptyUrlAndPresentAndNonemptyTitlesAreRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo"),
			factory.imageContent("", Optional.of("title"), Optional.empty()),
			factory.textContent("bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foobar", commonmarkText);

	}

	@Test
	public void imagesWithEmptyUrlsAndPresentAndNonemptyTitlesInbetweenLinebreaksAreRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo"),
			factory.lineBreakContent(true),
			factory.imageContent("", Optional.of("title"), Optional.empty()),
			factory.lineBreakContent(true),
			factory.textContent("bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo\\\nbar", commonmarkText);

	}

	@Test
	public void imagesWithEmptyUrlAndPresentAndNonemptyAlternativesAreRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo"),
			factory.imageContent("", Optional.empty(), Optional.of("alternative")),
			factory.textContent("bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foobar", commonmarkText);

	}

	@Test
	public void imagesWithEmptyUrlsAndPresentAndNonemptyAlternativesInbetweenLinebreaksAreRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo"),
			factory.lineBreakContent(true),
			factory.imageContent("", Optional.empty(), Optional.of("alternative")),
			factory.lineBreakContent(true),
			factory.textContent("bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo\\\nbar", commonmarkText);

	}

	@Test
	public void linksWithOutTitleIsRenderedWitUrlOnly() {

		// @formatter:off
		paragraph.addContents(
			factory.linkContent(
				"/foobar.png",
				factory.textContent("foobar")
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("[foobar](/foobar.png)", commonmarkText);

	}

	@Test
	public void linksWithPresenButEmptyTitlesAreRenderedWithUrlOnly() {

		// @formatter:off
		paragraph.addContents(
			factory.linkContent(
				"/foobar.png",
				Optional.empty(),
				factory.textContent("foobar")
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("[foobar](/foobar.png)", commonmarkText);

	}

	@Test
	public void linksWithPresenAndNonemptyTitlesAreRenderedWithUrlAndTitle() {

		// @formatter:off
		paragraph.addContents(
			factory.linkContent(
				"/foobar.png",
				Optional.of("title"),
				factory.textContent("foobar")
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("[foobar](/foobar.png title)", commonmarkText);
	}

	@Test
	public void linksWithEmptyUrlsAreRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.linkContent(
				"",
				Optional.of("title"),
				factory.textContent("foobar")
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foobar", commonmarkText);

	}

	@Test
	public void linksWithEmptyUrlsInbetweenLinebreaksAreRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo"),
			factory.lineBreakContent(true),
			factory.linkContent(
				"",
				Optional.empty(),
				factory.textContent("bar")
			),
			factory.lineBreakContent(true),
			factory.textContent("baz")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo\\\nbar\\\nbaz", commonmarkText);

	}

	@Test
	public void linksWithEmptyUrlAndPresentAndNonemptyTitlesAreRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.linkContent(
				"",
				Optional.of("title"),
				factory.textContent("foobar")
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foobar", commonmarkText);

	}

	@Test
	public void linksWithEmptyUrlsAndPresentAndNonemptyTitlesInbetweenLinebreaksAreRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo"),
			factory.lineBreakContent(true),
			factory.linkContent(
				"",
				Optional.of("title"),
				factory.textContent("bar")
			),
			factory.lineBreakContent(true),
			factory.textContent("baz")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo\\\nbar\\\nbaz", commonmarkText);

	}

	@Test
	public void nestedLinksAreRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.linkContent(
				"/foobar.png",
				factory.linkContent(
					"/foobar.png",
					factory.textContent("foobar")
				)
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("[foobar](/foobar.png)", commonmarkText);

	}

	@Test
	public void singleLevel1EmphasisIsSouroundedBySingleStars() {

		// @formatter:off
		paragraph.addContents(
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_1,
				factory.textContent("foobar")
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		options.setEmphasisLevel1Option(EmphasisOption.STAR);
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("*foobar*", commonmarkText);

	}

	@Test
	public void singleLevel1EmphasisIsSouroundedBySingleUnderscores() {

		// @formatter:off
		paragraph.addContents(
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_1,
				factory.textContent("foobar")
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		options.setEmphasisLevel1Option(EmphasisOption.UNDERSCORE);
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("_foobar_", commonmarkText);

	}

	@Test
	public void singleLevel2EmphasisIsSouroundedByDoubleStars() {

		// @formatter:off
		paragraph.addContents(
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_2,
				factory.textContent("foobar")
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		options.setEmphasisLevel2Option(EmphasisOption.STAR);
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("**foobar**", commonmarkText);

	}

	@Test
	public void singleLevel2EmphasisIsSouroundedByDoubleUnderscores() {

		// @formatter:off
		paragraph.addContents(
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_2,
				factory.textContent("foobar")
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		options.setEmphasisLevel2Option(EmphasisOption.UNDERSCORE);
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("__foobar__", commonmarkText);

	}

	@Test
	public void multipleLevel1EmphasisesAreCombinedIntoASingleEmphasis() {

		// @formatter:off
		paragraph.addContents(
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_1,
				factory.textContent("foo")
			),
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_1,
				factory.textContent("bar")
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("*foobar*", commonmarkText);

	}

	@Test
	public void multipleLevel2EmphasisesAreCombinedIntoASingleEmphasis() {

		// @formatter:off
		paragraph.addContents(
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_2,
				factory.textContent("foo")
			),
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_2,
				factory.textContent("bar")
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("**foobar**", commonmarkText);

	}

	@Test
	public void singleLevel1EmphasisWithoutConttentAreRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo"),
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_1
			),
			factory.textContent("bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foobar", commonmarkText);

	}

	@Test
	public void singleLevel2EmphasisWithoutConttentAreRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo"),
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_2
			),
			factory.textContent("bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foobar", commonmarkText);

	}

	@Test
	public void singleLevel1EmphasisWithoutConttentInbetweenLinebreaksAreRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo"),
			factory.lineBreakContent(true),
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_1
			),
			factory.lineBreakContent(true),
			factory.textContent("bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo\\\nbar", commonmarkText);

	}

	@Test
	public void singleLevel2EmphasisWithoutConttentInbetweenLinebreaksAreRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo"),
			factory.lineBreakContent(true),
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_2
			),
			factory.lineBreakContent(true),
			factory.textContent("bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo\\\nbar", commonmarkText);

	}

	@Test
	public void singleLevel1EmphasisWithoutActualConttentAreRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo"),
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_1,
				factory.textContent("")
			),
			factory.textContent("bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foobar", commonmarkText);

	}

	@Test
	public void singleLevel2EmphasisWithoutActualConttentAreRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo"),
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_2,
				factory.textContent("")
			),
			factory.textContent("bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foobar", commonmarkText);

	}

	@Test
	public void singleLevel1EmphasisWithoutActualRemainingConttentAreRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo"),
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_1,
				factory.textContent(" ")
			),
			factory.textContent("bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foobar", commonmarkText);

	}

	@Test
	public void singleLevel2EmphasisWithoutActualRemainingConttentAreRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo"),
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_2,
				factory.textContent(" ")
			),
			factory.textContent("bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foobar", commonmarkText);

	}

	@Test
	public void singleLevel1EmphasisWithoutActualConttentInbetweenLinebreaksAreRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo"),
			factory.lineBreakContent(true),
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_1,
				factory.textContent("")
			),
			factory.lineBreakContent(true),
			factory.textContent("bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo\\\nbar", commonmarkText);

	}

	@Test
	public void singleLevel2EmphasisWithoutActualConttentInbetweenLinebreaksAreRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo"),
			factory.lineBreakContent(true),
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_2,
				factory.textContent("")
			),
			factory.lineBreakContent(true),
			factory.textContent("bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo\\\nbar", commonmarkText);

	}

	@Test
	public void nestedEmphasisesAreSurroundedByMultipleStars() {

		// @formatter:off
		paragraph.addContents(
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_1,
				factory.emphasisContent(
					MarkdomEmphasisLevel.LEVEL_2,
					factory.textContent("foobar")
				)
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		options.setEmphasisLevel1Option(EmphasisOption.STAR);
		options.setEmphasisLevel2Option(EmphasisOption.STAR);
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("***foobar***", commonmarkText);

	}

	@Test
	public void nestedEmphasisesAreSurroundedByMultipleUnderscores() {

		// @formatter:off
		paragraph.addContents(
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_2,
				factory.emphasisContent(
					MarkdomEmphasisLevel.LEVEL_1,
					factory.textContent("foobar")
				)
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		options.setEmphasisLevel1Option(EmphasisOption.UNDERSCORE);
		options.setEmphasisLevel2Option(EmphasisOption.UNDERSCORE);
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("___foobar___", commonmarkText);

	}

	@Test
	public void nestedEmphasisesAreSurroundedByMultipleStarsAndUnderscores() {

		// @formatter:off
		paragraph.addContents(
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_1,
				factory.emphasisContent(
					MarkdomEmphasisLevel.LEVEL_2,
					factory.textContent("foobar")
				)
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		options.setEmphasisLevel1Option(EmphasisOption.UNDERSCORE);
		options.setEmphasisLevel2Option(EmphasisOption.STAR);
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("_**foobar**_", commonmarkText);

	}

	@Test
	public void nestedEmphasisesAreSurroundedByMultipleUnderscoresAndStars() {

		// @formatter:off
		paragraph.addContents(
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_2,
				factory.emphasisContent(
					MarkdomEmphasisLevel.LEVEL_1,
					factory.textContent("foobar")
				)
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		options.setEmphasisLevel1Option(EmphasisOption.STAR);
		options.setEmphasisLevel2Option(EmphasisOption.UNDERSCORE);
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("__*foobar*__", commonmarkText);

	}

	@Test
	public void nestedEmphasisesWithoutConttentsAreRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo"),
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_1,
				factory.emphasisContent(
					MarkdomEmphasisLevel.LEVEL_2
				)
			),
			factory.textContent("bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foobar", commonmarkText);

	}

	@Test
	public void nestedEmphasisesWithoutConttentsAreRemoved2() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo"),
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_2,
				factory.emphasisContent(
					MarkdomEmphasisLevel.LEVEL_1
				)
			),
			factory.textContent("bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foobar", commonmarkText);

	}

	@Test
	public void nestedEmphasisesWithoutConttentsInbetweenLinebreaksAreRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo"),
			factory.lineBreakContent(true),
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_1,
				factory.emphasisContent(
					MarkdomEmphasisLevel.LEVEL_2
				)
			),
			factory.lineBreakContent(true),
			factory.textContent("bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo\\\nbar", commonmarkText);

	}

	@Test
	public void nestedEmphasisesWithoutConttentsInbetweenLinebreaksAreRemoved2() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo"),
			factory.lineBreakContent(true),
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_2,
				factory.emphasisContent(
					MarkdomEmphasisLevel.LEVEL_1
				)
			),
			factory.lineBreakContent(true),
			factory.textContent("bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo\\\nbar", commonmarkText);

	}

	@Test
	public void adjacentEmphasisesAreRemovedSurroundedByMultipleStars() {

		// @formatter:off
		paragraph.addContents(
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_1,
				factory.textContent("foo")
			),
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_2,
				factory.textContent("bar")
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		options.setEmphasisLevel1Option(EmphasisOption.STAR);
		options.setEmphasisLevel2Option(EmphasisOption.STAR);
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("*foo***bar**", commonmarkText);

	}

	@Test
	public void adjacentEmphasisesAreRemovedSurroundedByMultipleUnderscores() {

		// @formatter:off
		paragraph.addContents(
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_2,
				factory.textContent("foo")
			),
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_1,
				factory.textContent("bar")
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		options.setEmphasisLevel1Option(EmphasisOption.UNDERSCORE);
		options.setEmphasisLevel2Option(EmphasisOption.UNDERSCORE);
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("__foo___bar_", commonmarkText);

	}

	@Test
	public void adjacentEmphasisesAreRemovedSurroundedByMultipleStarsAndUnderscores() {

		// @formatter:off
		paragraph.addContents(
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_1,
				factory.textContent("foo")
			),
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_2,
				factory.textContent("bar")
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		options.setEmphasisLevel1Option(EmphasisOption.UNDERSCORE);
		options.setEmphasisLevel2Option(EmphasisOption.STAR);
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("_foo_**bar**", commonmarkText);

	}

	@Test
	public void adjacentEmphasisesAreRemovedSurroundedByMultipleUnderscoresAndStars() {

		// @formatter:off
		paragraph.addContents(
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_2,
				factory.textContent("foo")
			),
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_1,
				factory.textContent("bar")
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		options.setEmphasisLevel1Option(EmphasisOption.STAR);
		options.setEmphasisLevel2Option(EmphasisOption.UNDERSCORE);
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("__foo__*bar*", commonmarkText);

	}

	@Test
	public void singleEmphasisesCanBeInterruptedBySoftLineBreaks() {

		// @formatter:off
		paragraph.addContents(
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_1,
				factory.textContent("foo"),
				factory.lineBreakContent(false),
				factory.textContent("bar")
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("*foo\nbar*", commonmarkText);

	}

	@Test
	public void singleEmphasisesCanBeInterruptedByHardLineBreaks() {

		// @formatter:off
		paragraph.addContents(
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_1,
				factory.textContent("foo"),
				factory.lineBreakContent(true),
				factory.textContent("bar")
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("*foo\\\nbar*", commonmarkText);

	}

	@Test
	public void nestedEmphasisesCanBeInterruptedBySoftLineBreaks() {

		// @formatter:off
		paragraph.addContents(
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_1,
				factory.emphasisContent(
					MarkdomEmphasisLevel.LEVEL_2,
					factory.textContent("foo"),
					factory.lineBreakContent(false),
					factory.textContent("bar")
				)
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("***foo\nbar***", commonmarkText);

	}

	@Test
	public void nestedEmphasisesCanBeInterruptedByHardLineBreaks() {

		// @formatter:off
		paragraph.addContents(
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_1,
				factory.emphasisContent(
					MarkdomEmphasisLevel.LEVEL_2,
					factory.textContent("foo"),
					factory.lineBreakContent(true),
					factory.textContent("bar")
				)
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("***foo\\\nbar***", commonmarkText);

	}

	@Test
	public void singleEmphasisesWithLeadingSoftLineBreakesAreBegunAfterTheLineBreak() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo"),
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_1,
				factory.lineBreakContent(false),
				factory.textContent("bar")
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo\n*bar*", commonmarkText);

	}

	@Test
	public void singleEmphasisesWithTrailingHardLineBreakesAreEndedBeforeTheLineBreak() {

		// @formatter:off
		paragraph.addContents(
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_1,
				factory.textContent("foo"),
				factory.lineBreakContent(true)
			),
			factory.textContent("bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("*foo*\\\nbar", commonmarkText);

	}

	@Test
	public void nestedEmphasisesWithLeadingSoftLineBreakesAreBegunAfterTheLineBreak() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo"),
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_1,
				factory.emphasisContent(
					MarkdomEmphasisLevel.LEVEL_2,
					factory.lineBreakContent(false),
					factory.textContent("bar")
				)
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo\n***bar***", commonmarkText);

	}

	@Test
	public void nestedEmphasisesWithTrailingHardLineBreakesAreEndedBeforeTheLineBreak() {

		// @formatter:off
		paragraph.addContents(
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_2,
				factory.emphasisContent(
					MarkdomEmphasisLevel.LEVEL_1,
					factory.textContent("foo"),
					factory.lineBreakContent(true)
				)
			),
			factory.textContent("bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("***foo***\\\nbar", commonmarkText);

	}

	@Test
	public void ledingLineBreaksInEmphasisesIsRemovedIfItEndsUpAtTheBeginningOfTheFirstLine() {

		// @formatter:off
		paragraph.addContents(
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_1,
				factory.lineBreakContent(false),
				factory.textContent("foobar")
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("*foobar*", commonmarkText);

	}

	@Test
	public void ledingLineBreaksInEmphasisesIsRemovedIfItEndsUpAtTheBeginningOfAFollowingLine() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo"),
			factory.lineBreakContent(false),
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_2,
				factory.lineBreakContent(false),
				factory.textContent("bar")
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo\n**bar**", commonmarkText);

	}

	@Test
	public void multipleLedingLineBreaksInNestedEmphasisesAreRemovedIfTheyEndUpAtTheBeginningOfTheFirstLine() {

		// @formatter:off
		paragraph.addContents(
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_1,
				factory.lineBreakContent(false),
				factory.emphasisContent(
					MarkdomEmphasisLevel.LEVEL_2,
					factory.lineBreakContent(false),
					factory.textContent("foobar")
				)
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("***foobar***", commonmarkText);

	}

	@Test
	public void multipleLedingLineBreaksInNestedEmphasisesAreRemovedIfTheyEndUpAtTheBeginningOfAFollowingLine() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo"),
			factory.lineBreakContent(false),
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_2,
				factory.lineBreakContent(false),
				factory.emphasisContent(
					MarkdomEmphasisLevel.LEVEL_1,
					factory.lineBreakContent(false),
					factory.textContent("bar")
				)
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo\n***bar***", commonmarkText);

	}

	@Test
	public void anyRemovedHardLineBreaksPrevailOverSoftLineBreaksIfTheyAreRemoved1() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo"),
			factory.lineBreakContent(true),
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_2,
				factory.lineBreakContent(false),
				factory.emphasisContent(
					MarkdomEmphasisLevel.LEVEL_1,
					factory.lineBreakContent(false),
					factory.textContent("bar")
				)
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo\\\n***bar***", commonmarkText);

	}

	@Test
	public void anyRemovedHardLineBreaksPrevailOverSoftLineBreaksIfTheyAreRemoved2() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo"),
			factory.lineBreakContent(false),
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_2,
				factory.lineBreakContent(true),
				factory.emphasisContent(
					MarkdomEmphasisLevel.LEVEL_1,
					factory.lineBreakContent(false),
					factory.textContent("bar")
				)
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo\\\n***bar***", commonmarkText);

	}

	@Test
	public void anyRemovedHardLineBreaksPrevailOverSoftLineBreaksIfTheyAreRemoved3() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo"),
			factory.lineBreakContent(false),
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_2,
				factory.lineBreakContent(false),
				factory.emphasisContent(
					MarkdomEmphasisLevel.LEVEL_1,
					factory.lineBreakContent(true),
					factory.textContent("bar")
				)
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo\\\n***bar***", commonmarkText);

	}

	@Test
	public void singleEmphasisesWithLeadingSoftLineBreakesAndWithoutAnyOtherActualContentAreRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foobar"),
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_1,
				factory.lineBreakContent(false),
				factory.textContent("")
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foobar", commonmarkText);

	}

	@Test
	public void singleEmphasisesWithTrailingHardLineBreakesAndWithoutAnyOtherActualContentAreRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foobar"),
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_1,
				factory.textContent(""),
				factory.lineBreakContent(true)
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foobar", commonmarkText);

	}

	@Test
	public void nestedEmphasisesWithLeadingSoftLineBreakesAndWithoutAnyOtherActualContentAreRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foobar"),
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_1,
				factory.emphasisContent(
					MarkdomEmphasisLevel.LEVEL_2,
					factory.lineBreakContent(false),
					factory.textContent("")
				)
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foobar", commonmarkText);

	}

	@Test
	public void nestedEmphasisesWithTrailingHardLineBreakesAndWithoutAnyOtherActualContentAreRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foobar"),
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_2,
				factory.emphasisContent(
					MarkdomEmphasisLevel.LEVEL_1,
					factory.textContent(""),
					factory.lineBreakContent(true)
				)
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foobar", commonmarkText);

	}

	@Test
	public void nestedEmphasisesWithSameLevelAreRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_1,
				factory.emphasisContent(
					MarkdomEmphasisLevel.LEVEL_1,
					factory.textContent("foobar")
				)
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("*foobar*", commonmarkText);

	}

	@Test
	public void multipleNestedEmphasisesWithSameLevelAreRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_1,
				factory.emphasisContent(
					MarkdomEmphasisLevel.LEVEL_1,
					factory.emphasisContent(
						MarkdomEmphasisLevel.LEVEL_1,
						factory.textContent("foobar")
					)
				)
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("*foobar*", commonmarkText);

	}

	@Test
	public void indirectlyNestedEmphasisesWithSameLevelAreRemoved() {

		// @formatter:off
		paragraph.addContents(
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_1,
				factory.emphasisContent(
					MarkdomEmphasisLevel.LEVEL_2,
					factory.emphasisContent(
						MarkdomEmphasisLevel.LEVEL_1,
						factory.textContent("foobar")
					)
				)
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("***foobar***", commonmarkText);

	}

	@Test
	public void leadingSpacesInEmphasisesAreShiftedToTheLeft() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo"),
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_1,
				factory.emphasisContent(
					MarkdomEmphasisLevel.LEVEL_2,
					factory.textContent(" bar")
				)
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo ***bar***", commonmarkText);

	}

	@Test
	public void leadingSpacesInEmphasisesAreShiftedToTheLeftAndOutOfTheLine() {

		// @formatter:off
		paragraph.addContents(
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_1,
				factory.textContent(" foo"),
				factory.emphasisContent(
					MarkdomEmphasisLevel.LEVEL_2,
					factory.textContent(" bar")
				)
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("*foo **bar***", commonmarkText);

	}

	@Test
	public void trailingSpacesInEmphasisesAreShiftedToTheRight() {

		// @formatter:off
		paragraph.addContents(
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_1,
				factory.emphasisContent(
					MarkdomEmphasisLevel.LEVEL_2,
					factory.textContent("foo ")
				)
			),
			factory.textContent("bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("***foo*** bar", commonmarkText);

	}

	@Test
	public void trailingSpacesInEmphasisesAreShiftedToTheRightAndOutOfTheLine() {

		// @formatter:off
		paragraph.addContents(
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_1,
				factory.emphasisContent(
					MarkdomEmphasisLevel.LEVEL_2,
					factory.textContent("foo ")
				),
				factory.textContent("bar ")
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("***foo** bar*", commonmarkText);

	}

	@Test
	public void leadingSpacesInLinksAndEmphasisesAreShiftedToTheLeft() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo"),
			factory.linkContent(
				"/foobar.png",
				factory.emphasisContent(
					MarkdomEmphasisLevel.LEVEL_2,
					factory.textContent(" bar")
				)
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo [**bar**](/foobar.png)", commonmarkText);

	}

	@Test
	public void trailingSpacesInLinksAndEmphasisesAreShiftedToTheRight() {

		// @formatter:off
		paragraph.addContents(
			factory.linkContent(
				"/foobar.png",
				factory.emphasisContent(
					MarkdomEmphasisLevel.LEVEL_2,
					factory.textContent("foo ")
				)
			),
			factory.textContent("bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("[**foo**](/foobar.png) bar", commonmarkText);

	}

	@Test
	public void backslashesInTextLiteralsAreEscaped() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo\\bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo\\\\bar", commonmarkText);

	}

	@Test
	public void backticksInTextLiteralsAreEscaped() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo`bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo\\`bar", commonmarkText);

	}

	@Test
	public void starsInTextLiteralsAreEscaped() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo*bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo\\*bar", commonmarkText);

	}

	@Test
	public void underscoresInTextLiteralsAreEscaped() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo_bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo\\_bar", commonmarkText);

	}

	@Test
	public void lessThansInTextLiteralsAreEscaped() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo<bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo\\<bar", commonmarkText);

	}

	@Test
	public void openingSquareBracketsInTextLiteralsAreEscaped() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo[bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo\\[bar", commonmarkText);

	}

	@Test
	public void trailingBangsInTextLiteralsInFrontOfLinksAreEscaped() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo!"),
			factory.linkContent(
				"/foobar.png",
				factory.textContent("bar")
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo\\![bar](/foobar.png)", commonmarkText);

	}

	@Test
	public void nonTrailingBangsInTextLiteralsInFrontOfLinksAreEscaped() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("!foo"),
			factory.linkContent(
				"/foobar.png",
				factory.textContent("bar")
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("!foo[bar](/foobar.png)", commonmarkText);

	}

	@Test
	public void trailingBangsInTextLiteralsInFrontOfOtheContentAreNotEscaped() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo!"),
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_1,
				factory.textContent("bar")
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo!*bar*", commonmarkText);

	}

	@Test
	public void linksWithLeadingSoftLineBreakesAreBegunAfterTheLineBreak() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("foo"),
			factory.linkContent(
				"/foobar.png",
				factory.lineBreakContent(false),
				factory.textContent("bar")
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("foo\n[bar](/foobar.png)", commonmarkText);

	}

	@Test
	public void linksWithTrailingHardLineBreakesAreEndedBeforeTheLineBreak() {

		// @formatter:off
		paragraph.addContents(
			factory.linkContent(
				"/foobar.png",
				factory.textContent("foo"),
				factory.lineBreakContent(true)
			),
			factory.textContent("bar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("[foo](/foobar.png)\\\nbar", commonmarkText);

	}

	@Test
	public void singleTextContentThatContainsOnlyOneDashIsEscapedAtTheBeginning() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("-")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("\\-", commonmarkText);

	}

	@Test
	public void singleTextContentThatStartsWithDashAndSpaceIsEscapedAtTheBeginning() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("- foobar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("\\- foobar", commonmarkText);

	}

	@Test
	public void leadingTextContentThatStartsWithDashAndSpaceFromOtherContentIsEscapedAtTheBeginning() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("-"),
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_1,
				factory.textContent(" foobar")
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("\\- *foobar*", commonmarkText);

	}

	@Test
	public void singleTextContentThatContainsOnlyOnePlusIsEscapedAtTheBeginning() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("+")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("\\+", commonmarkText);

	}

	@Test
	public void singleTextContentThatStartsWithPlusAndSpaceIsEscapedAtTheBeginning() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("+ foobar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("\\+ foobar", commonmarkText);

	}

	@Test
	public void leadingTextContentThatStartsWithPlusAndSpaceFromOtherContentIsEscapedAtTheBeginning() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("+"),
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_1,
				factory.textContent(" foobar")
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("\\+ *foobar*", commonmarkText);

	}

	@Test
	public void singleTextContentThatContainsOnlyOneStarIsEscapedAtTheBeginning() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("*")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("\\*", commonmarkText);

	}

	@Test
	public void singleTextContentThatStartsWithStarAndSpaceIsEscapedAtTheBeginning() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("* foobar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("\\* foobar", commonmarkText);

	}

	@Test
	public void leadingTextContentThatStartsWithStarAndSpaceFromOtherContentIsEscapedAtTheBeginning() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("*"),
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_1,
				factory.textContent(" foobar")
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("\\* *foobar*", commonmarkText);

	}

	@ParameterizedTest
	@ValueSource(strings = { "#", "##", "###", "####", "#####", "######" })
	public void singleTextContentThatStartsWithUpToSixHashesAndSpaceIsEscapedAtTheBeginning(String hashes) {

		// @formatter:off
		paragraph.addContents(
			factory.textContent(hashes + " foobar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("\\" + hashes + " foobar", commonmarkText);

	}

	@Test
	public void singleTextContentThatStartsWithMoreThanSixHashesAndSpaceIsNotEscapedAtTheBeginning() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("####### foobar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("####### foobar", commonmarkText);

	}

	@ParameterizedTest
	@ValueSource(strings = { "#", "##", "###", "####", "#####", "######" })
	public void leadingTextContentThatStartsWithUpToSixHashesAndSpaceFromOtherContentIsEscapedAtTheBeginning(String hashes) {

		// @formatter:off
		paragraph.addContents(
			factory.textContent(hashes),
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_1,
				factory.textContent(" foobar")
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("\\" + hashes + " *foobar*", commonmarkText);

	}

	@Test
	public void leadingTextContentThatStartsWithMoreThanSixHashesAndSpaceFromOtherContentIsNotEscapedAtTheBeginning() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("#######"),
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_1,
				factory.textContent(" foobar")
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("####### *foobar*", commonmarkText);

	}

	@Test
	public void singleTextContentThatXContainsOnlyDigitsAndDotIsEscapedAtTheDot() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("42.")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("42\\.", commonmarkText);

	}

	@Test
	public void singleTextContentThatStartsWithDigitsDotAndSpaceIsEscapedAtTheDot() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("42. foobar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("42\\. foobar", commonmarkText);

	}

	@Test
	public void leadingTextContentThatStartsWithDigitsDotAndSpaceFromOtherContentIsEscapedAtTheDot() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("42."),
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_1,
				factory.textContent(" foobar")
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("42\\. *foobar*", commonmarkText);

	}

	@Test
	public void singleTextContentThatContainsOnlyDigitsAndClosingParenthesisIsEscapedAtTheParenthesis() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("42)")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("42\\)", commonmarkText);

	}

	@Test
	public void singleTextContentThatStartsWithDigitsClosingParenthesisAndSpaceIsEscapedAtTheParenthesis() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("42) foobar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("42\\) foobar", commonmarkText);

	}

	@Test
	public void leadingTextContentThatStartsWithDigitsClosingParenthesisAndSpaceFromOtherContentIsEscapedAtTheParenthesis() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("42)"),
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_1,
				factory.textContent(" foobar")
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("42\\) *foobar*", commonmarkText);

	}

	@ParameterizedTest
	@ValueSource(strings = { "~~~", "~~~~", "~~~~~", "~~~~~~" })
	public void singleTextContentContainsOnlyThreeOrMoreTildesIsEscapedAtTheBeginning(String tildes) {

		// @formatter:off
		paragraph.addContents(
			factory.textContent(tildes)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("\\" + tildes, commonmarkText);

	}

	@ParameterizedTest
	@ValueSource(strings = { "~", "~~" })
	public void singleTextContentContainsOnlyOneOrTwoTildesIsNotEscapedAtTheBeginning(String tildes) {

		// @formatter:off
		paragraph.addContents(
			factory.textContent(tildes)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals(tildes, commonmarkText);

	}

	@ParameterizedTest
	@ValueSource(strings = { "~~~", "~~~~", "~~~~~", "~~~~~~" })
	public void singleTextContentThatStartsWithThreeOrMoreTildesIsEscapedAtTheBeginning(String tildes) {

		// @formatter:off
		paragraph.addContents(
			factory.textContent(tildes + "foobar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("\\" + tildes + "foobar", commonmarkText);

	}

	@ParameterizedTest
	@ValueSource(strings = { "~", "~~" })
	public void singleTextContentThatStartsWithOneOrTwoTildesIsNotEscapedAtTheBeginning(String tildes) {

		// @formatter:off
		paragraph.addContents(
			factory.textContent(tildes + "foobar")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals(tildes + "foobar", commonmarkText);

	}

	@ParameterizedTest
	@ValueSource(strings = { "~~~", "~~~~", "~~~~~", "~~~~~~" })
	public void leadingTextContentThatStartsWithThreeOrMoreTildesIsEscapedAtTheBeginning(String tildes) {

		// @formatter:off
		paragraph.addContents(
			factory.textContent(tildes), 
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_1,
				factory.textContent("foobar")
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("\\" + tildes + "*foobar*", commonmarkText);

	}

	@ParameterizedTest
	@ValueSource(strings = { "~", "~~" })
	public void leadingTextContentThatStartsWithOneOrTwoTildesIsNotEscapedAtTheBeginning(String tildes) {

		// @formatter:off
		paragraph.addContents(
			factory.textContent(tildes), 
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_1,
				factory.textContent("foobar")
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals(tildes + "*foobar*", commonmarkText);

	}

	@ParameterizedTest
	@ValueSource(strings = { "~~~", "~~~~", "~~~~~", "~~~~~~" })
	public void leadingTextContentThatStartsWithThreeOrMoreTildesAndSpaceIsEscapedAtTheBeginning(String tildes) {

		// @formatter:off
		paragraph.addContents(
			factory.textContent(tildes + " "), 
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_1,
				factory.textContent("foobar")
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("\\" + tildes + " *foobar*", commonmarkText);

	}

	@ParameterizedTest
	@ValueSource(strings = { "~", "~~" })
	public void leadingTextContentThatStartsWithOneOrTwoTildesAndSpaceIsNotEscapedAtTheBeginning(String tildes) {

		// @formatter:off
		paragraph.addContents(
			factory.textContent(tildes + " "), 
			factory.emphasisContent(
				MarkdomEmphasisLevel.LEVEL_1,
				factory.textContent("foobar")
			)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals(tildes + " *foobar*", commonmarkText);

	}

	@ParameterizedTest
	@ValueSource(strings = { "=", "==", "===", "====" })
	public void singleTextContentThatContainsOneOrMoreOfEqualsIsEscapedTheBeginning(String equals) {

		// @formatter:off
		paragraph.addContents(
			factory.textContent(equals)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("\\" + equals, commonmarkText);

	}

	@ParameterizedTest
	@ValueSource(strings = { "---", "-- --", "- - -", "-- -- --" })
	public void singleTextContentThatContainsOnlyThreeOrMoreDashesWithOptionalSpacesIsEscapedTheBeginning(String dashes) {

		// @formatter:off
		paragraph.addContents(
			factory.textContent(dashes)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("\\" + dashes, commonmarkText);

	}

	@ParameterizedTest
	@ValueSource(strings = { "___", "__ __", "_ _ _", "__ __ __" })
	public void singleTextContentThatContainsOnlyThreeOrMoreUnderscoredWithOptionalSpacesIsEscapedTheBeginning(String underscores) {

		// @formatter:off
		paragraph.addContents(
			factory.textContent(underscores)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("\\" + underscores, commonmarkText);

	}

	@ParameterizedTest
	@ValueSource(strings = { "***", "** **", "* * *", "** ** **" })
	public void singleTextContentThatContainsOnlyThreeOrMoreStarsWithOptionalSpacesIsEscapedTheBeginning(String stars) {

		// @formatter:off
		paragraph.addContents(
			factory.textContent(stars)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("\\" + stars, commonmarkText);

	}

	@ParameterizedTest
	@ValueSource(strings = { "#", "##", "###", "####", "#####", "######" })
	public void singleTextContentThatContainsUpToSixHashesIsEscapedTheBeginning(String hashes) {

		// @formatter:off
		paragraph.addContents(
			factory.textContent(hashes)
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("\\" + hashes, commonmarkText);

	}

	@Test
	public void singleTextContentThatContainsMoreThanSixHashesIsNotEscapedTheBeginning() {

		// @formatter:off
		paragraph.addContents(
			factory.textContent("#######")
		);
		// @formatter:on

		CommonmarkTextOptions options = new CommonmarkTextOptions();
		String commonmarkText = document.handle(new CommonmarkTextMarkdomHandler<>(options, new StringWriter())).toString();

		assertEquals("#######", commonmarkText);

	}

}
