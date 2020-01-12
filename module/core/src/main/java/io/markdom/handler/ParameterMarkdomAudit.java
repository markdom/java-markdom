package io.markdom.handler;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.function.Consumer;

import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomHeadingLevel;
import io.markdom.util.ObjectHelper;

public final class ParameterMarkdomAudit extends AbstractMarkdomAudit {

	private final Consumer<String> violationConsumer;

	public ParameterMarkdomAudit(Consumer<String> violationConsumer) {
		this.violationConsumer = ObjectHelper.notNull("violation consumer", violationConsumer);
	}

	@Override
	public void onCodeBlock(String code, Optional<String> hint) {
		isNotNull("code", code);
		isNotNull("hint optional ", hint);
		hasNoLineBreak("hint", hint);
	}

	@Override
	public void onHeadingBlock(MarkdomHeadingLevel level) {
		isNotNull("level", level);
	}

	@Override
	public void onOrderedListBlock(Integer startIndex) {
		isNotNull("start index", startIndex);
		isNotNegative("start index", startIndex);
	}

	@Override
	public void onCodeContent(String code) {
		isNotNull("code", code);
		hasNoLineBreak("code", code);
	}

	@Override
	public void onEmphasisContent(MarkdomEmphasisLevel level) {
		isNotNull("level", level);
	}

	@Override
	public void onImageContent(String uri, Optional<String> title, Optional<String> alternative) {
		isNotNull("uri", uri);
		isValidUri("uri", uri);
		isNotNull("title optional", title);
		hasNoLineBreak("title", title);
		isNotNull("alternative optional", alternative);
		hasNoLineBreak("alternative", alternative);
	}

	@Override
	public void onLineBreakContent(Boolean hard) {
		isNotNull("hard", hard);
	}

	@Override
	public void onLinkContent(String uri, Optional<String> title) {
		isNotNull("uri", uri);
		isValidUri("uri", uri);
		isNotNull("title optional", title);
		hasNoLineBreak("title", title);
	}

	@Override
	public void onTextContent(String text) {
		isNotNull("text", text);
		hasNoLineBreak("text", text);
	}

	private void isNotNull(String name, Object value) {
		if (null == value) {
			violationConsumer.accept("The given " + name + " is null");
		}
	}

	private void isNotNegative(String name, Integer value) {
		if (value < 0) {
			violationConsumer.accept("The given " + name + " is negative");
		}
	}

	private void hasNoLineBreak(String name, Optional<String> optional) {
		optional.ifPresent(value -> hasNoLineBreak(name, value));
	}

	private void hasNoLineBreak(String name, String value) {
		if (-1 != value.indexOf('\n')) {
			violationConsumer.accept("The given " + name + " contains a line break: " + value);
		}
	}

	private void isValidUri(String name, String uri) {
		try {
			new URI(uri);
		} catch (URISyntaxException e) {
			violationConsumer.accept("The given " + name + " is not a valid URI: " + uri);
		}
	}

}
