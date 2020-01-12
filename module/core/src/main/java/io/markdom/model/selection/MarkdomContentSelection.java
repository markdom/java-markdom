package io.markdom.model.selection;

import io.markdom.model.MarkdomCodeContent;
import io.markdom.model.MarkdomEmphasisContent;
import io.markdom.model.MarkdomImageContent;
import io.markdom.model.MarkdomLineBreakContent;
import io.markdom.model.MarkdomLinkContent;
import io.markdom.model.MarkdomTextContent;

public interface MarkdomContentSelection<Result> {

	public Result onCodeContent(MarkdomCodeContent codeContent);

	public Result onEmphasisContent(MarkdomEmphasisContent emphasisContent);

	public Result onImageContent(MarkdomImageContent imageContent);

	public Result onLineBreakContent(MarkdomLineBreakContent lineBreakContent);

	public Result onLinkContent(MarkdomLinkContent linkContent);

	public Result onTextContent(MarkdomTextContent textContent);

}
