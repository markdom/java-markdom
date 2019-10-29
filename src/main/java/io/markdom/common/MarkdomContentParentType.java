package io.markdom.common;

/**
 * A {@link MarkdomContentParentType} indicates the node type of a
 * <a href="http://markdom.io/#domain-contentparent"><i>Markdom content parent</i></a>.
 */
public enum MarkdomContentParentType {

	/**
	 * Indication for a <a href="http://markdom.io/#domain-document"><i>Markdom heading block</i></a> content parent.
	 */
	HEADING_BLOCK,

	/**
	 * Indication for a <a href="http://markdom.io/#domain-quoteblock"><i>Markdom paragraph block</i></a> content parent.
	 */
	PARAGRAPH_BLOCK,

	/**
	 * Indication for a <a href="http://markdom.io/#domain-listitem"><i>Markdom emphasis content</i></a> content parent.
	 */
	EMPHASIS_CONTENT,

	/**
	 * Indication for a <a href="http://markdom.io/#domain-listitem"><i>Markdom link content</i></a> content parent.
	 */
	LINK_CONTENT;

}