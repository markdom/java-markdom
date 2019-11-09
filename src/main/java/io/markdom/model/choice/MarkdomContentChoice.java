package io.markdom.model.choice;

import io.markdom.model.MarkdomCodeContent;
import io.markdom.model.MarkdomEmphasisContent;
import io.markdom.model.MarkdomImageContent;
import io.markdom.model.MarkdomLineBreakContent;
import io.markdom.model.MarkdomLinkContent;
import io.markdom.model.MarkdomTextContent;

public interface MarkdomContentChoice {

	public void onCodeContent(MarkdomCodeContent codeContent);

	public void onEmphasisContent(MarkdomEmphasisContent emphasisContent);

	public void onImageContent(MarkdomImageContent imageContent);

	public void onLineBreakContent(MarkdomLineBreakContent lineBreakContent);

	public void onLinkContent(MarkdomLinkContent linkContent);

	public void onTextContent(MarkdomTextContent textContent);

}
