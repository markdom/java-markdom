package io.markdom.handler.html;

import java.util.Optional;
import java.util.Stack;

import io.markdom.common.MarkdomBlockType;
import io.markdom.common.MarkdomContentType;
import io.markdom.common.MarkdomEmphasisLevel;
import io.markdom.common.MarkdomException;
import io.markdom.common.MarkdomHeadingLevel;
import io.markdom.handler.MarkdomHandler;
import io.markdom.util.Attributes;
import io.markdom.util.CharacterData;
import io.markdom.util.Element;
import io.markdom.util.Gap;
import io.markdom.util.Node;
import io.markdom.util.NodeChoice;
import io.markdom.util.NodeSelection;
import io.markdom.util.Nodes;
import io.markdom.util.ObjectHelper;
import io.markdom.util.Text;

public abstract class AbstractHtmlDocumentMarkdomHandler<Result> implements MarkdomHandler<Result> {

	private static final NodeSelection<Integer> COUNT_GAP_SELECTION = new NodeSelection<Integer>() {

		@Override
		public Integer select(Element element) {
			return countGaps(element.getNodes());
		}

		@Override
		public Integer select(Text text) {
			return 0;
		}

		@Override
		public Integer select(CharacterData characterData) {
			return 0;
		}

		@Override
		public Integer select(Gap gap) {
			return 1;
		}

	};

	private final Stack<Nodes> nodes = new Stack<>();

	private final HtmlDelegate delegate;

	private final String title;

	public AbstractHtmlDocumentMarkdomHandler(HtmlDelegate delegate, String title) {
		this.delegate = ObjectHelper.notNull("delegate", delegate);
		this.title = ObjectHelper.notNull("title", title);
	}

	@Override
	public final void onDocumentBegin() {
		beginDocument("html", "html");
		pushElement("head");
		pushElement("title");
		setText(title);
		popElement();
		popElement();
		pushElement("body");
		addPreGapNodes(delegate.onDocument(new Gap()));
	}

	@Override
	public final void onBlocksBegin() {
	}

	@Override
	public final void onBlockBegin(MarkdomBlockType type) {
	}

	@Override
	public final void onCodeBlock(String code, Optional<String> hint) {
		addNodes(delegate.onCodeBlock(code, hint));
	}

	@Override
	public final void onCommentBlock(String comment) {
	}

	@Override
	public final void onDivisionBlock() {
		addNodes(delegate.onDivisionBlock());
	}

	@Override
	public final void onHeadingBlockBegin(MarkdomHeadingLevel level) {
		addPreGapNodes(delegate.onHeadingBlock(level, new Gap()));
	}

	@Override
	public final void onHeadingBlockEnd(MarkdomHeadingLevel level) {
		addPostGapNodes();
	}

	@Override
	public final void onOrderedListBlockBegin(Integer startIndex) {
		addPreGapNodes(delegate.onOrderdListBlock(startIndex, new Gap()));
	}

	@Override
	public final void onOrderedListBlockEnd(Integer startIndex) {
		addPostGapNodes();
	}

	@Override
	public final void onParagraphBlockBegin() {
		addPreGapNodes(delegate.onParagraphBlock(new Gap()));
	}

	@Override
	public final void onParagraphBlockEnd() {
		addPostGapNodes();
	}

	@Override
	public final void onQuoteBlockBegin() {
		addPreGapNodes(delegate.onQuoteBlock(new Gap()));
	}

	@Override
	public final void onQuoteBlockEnd() {
		addPostGapNodes();
	}

	@Override
	public final void onUnorderedListBlockBegin() {
		addPreGapNodes(delegate.onUnorderedListBlock(new Gap()));
	}

	@Override
	public final void onUnorderedListBlockEnd() {
		addPostGapNodes();
	}

	@Override
	public final void onBlockEnd(MarkdomBlockType type) {
	}

	@Override
	public final void onNextBlock() {
	}

	@Override
	public final void onBlocksEnd() {
	}

	@Override
	public final void onListItemsBegin() {
	}

	@Override
	public final void onListItemBegin() {
		addPreGapNodes(delegate.onListItem(new Gap()));
	}

	@Override
	public final void onListItemEnd() {
		addPostGapNodes();
	}

	@Override
	public final void onNextListItem() {
	}

	@Override
	public final void onListItemsEnd() {
	}

	@Override
	public final void onContentsBegin() {
	}

	@Override
	public final void onContentBegin(MarkdomContentType type) {
	}

	@Override
	public final void onCodeContent(String code) {
		addNodes(delegate.onCodeContent(code));
	}

	@Override
	public final void onEmphasisContentBegin(MarkdomEmphasisLevel level) {
		addPreGapNodes(delegate.onEmphasisContent(level, new Gap()));
	}

	@Override
	public final void onEmphasisContentEnd(MarkdomEmphasisLevel level) {
		addPostGapNodes();
	}

	@Override
	public final void onImageContent(String uri, Optional<String> title, Optional<String> alternative) {
		addNodes(delegate.onImageContent(uri, title, alternative));
	}

	@Override
	public final void onLineBreakContent(Boolean hard) {
		if (hard) {
			addNodes(delegate.onLineBreakContent());
		} else {
			addNodes(new Nodes().add(new Text(" ")));
		}
	}

	@Override
	public final void onLinkContentBegin(String uri, Optional<String> title) {
		addPreGapNodes(delegate.onLinkContent(uri, title, new Gap()));
	}

	@Override
	public final void onLinkContentEnd(String uri, Optional<String> title) {
		addPostGapNodes();
	}

	@Override
	public final void onTextContent(String text) {
		addNodes(delegate.onTextContent(text));
	}

	@Override
	public final void onContentEnd(MarkdomContentType type) {
	}

	@Override
	public final void onNextContent() {
	}

	@Override
	public final void onContentsEnd() {
	}

	@Override
	public final void onDocumentEnd() {
		addPostGapNodes();
		popElement();
		endDocument();
	}

	protected abstract void beginDocument(String dtdQualifiedName, String rootTagName);

	private void addNodes(Nodes nodes) {
		checkNodesHaveNoGaps(nodes);
		NodeChoice addNodesChoice = getAddNodesChoice();
		for (Node node : nodes) {
			node.choose(addNodesChoice);
		}
	}

	private void checkNodesHaveNoGaps(Nodes nodes) {
		if (0 != countGaps(nodes)) {
			throw new MarkdomException("Got nodes without exactly zero gaps: " + nodes);
		}
	}

	private NodeChoice getAddNodesChoice() {
		return new NodeChoice() {

			@Override
			public void choose(Element element) {
				pushElement(element.getTagName());
				setAttributes(element.getAttributes());
				for (Node node : element.getNodes()) {
					node.choose(this);
				}
				popElement();
			}

			@Override
			public void choose(CharacterData characterData) {
				setCharacterData(characterData.getText());
			}

			@Override
			public void choose(Text text) {
				setText(text.getText());
			}

			@Override
			public void choose(Gap gap) {
			}

		};
	}

	private void addPreGapNodes(Nodes nodes) {
		checkNodesHaveOneGap(nodes);
		NodeChoice choice = getAddPreGapNodesChoice();
		for (Node node : nodes) {
			node.choose(choice);
		}
		this.nodes.push(nodes);
	}

	private void checkNodesHaveOneGap(Nodes nodes) {
		if (1 != countGaps(nodes)) {
			throw new MarkdomException("Got nodes without exactly one gap: " + nodes);
		}
	}

	private NodeChoice getAddPreGapNodesChoice() {
		return new NodeChoice() {

			boolean gapSeen = false;

			@Override
			public void choose(Element element) {
				if (!gapSeen) {
					pushElement(element.getTagName());
					setAttributes(element.getAttributes());
				}
				for (Node node : element.getNodes()) {
					node.choose(this);
				}
				if (!gapSeen) {
					popElement();
				}
			}

			@Override
			public void choose(CharacterData characterData) {
				if (!gapSeen) {
					setCharacterData(characterData.getText());
				}
			}

			@Override
			public void choose(Text text) {
				if (!gapSeen) {
					setText(text.getText());
				}
			}

			@Override
			public void choose(Gap gap) {
				gapSeen = true;
			}

		};
	}

	private void addPostGapNodes() {
		NodeChoice choice = getAddPostGapNodesChoice();
		for (Node node : this.nodes.pop()) {
			node.choose(choice);
		}
	}

	private NodeChoice getAddPostGapNodesChoice() {
		return new NodeChoice() {

			boolean gapSeen = false;

			@Override
			public void choose(Element element) {
				if (gapSeen) {
					pushElement(element.getTagName());
					setAttributes(element.getAttributes());
				}
				for (Node node : element.getNodes()) {
					node.choose(this);
				}
				if (gapSeen) {
					popElement();
				}
			}

			@Override
			public void choose(CharacterData characterData) {
				if (gapSeen) {
					setCharacterData(characterData.getText());
				}
			}

			@Override
			public void choose(Text text) {
				if (gapSeen) {
					setText(text.getText());
				}
			}

			@Override
			public void choose(Gap gap) {
				gapSeen = true;
			}

		};
	}

	private static int countGaps(Nodes nodes) {
		int numberOfGaps = 0;
		for (Node node : nodes) {
			numberOfGaps += node.select(COUNT_GAP_SELECTION);
		}
		return numberOfGaps;
	}

	protected abstract void pushElement(String tagName);

	protected abstract void setAttributes(Attributes attributes);

	protected abstract void setCharacterData(String text);

	protected abstract void setText(String text);

	protected abstract void popElement();

	protected abstract void endDocument();

}
