package io.markdom.common;

/**
 * A {@link MarkdomBlockParentType} indicates the node type of a <a href="http://markdom.io/#domain-blockparent"><i>Markdom
 * block parent</i></a>.
 */
public enum MarkdomBlockParentType {

	/**
	 * Indication for a <a href="http://markdom.io/#domain-document"><i>Markdom document</i></a> block parent.
	 */
	DOCUMENT,

	/**
	 * Indication for a <a href="http://markdom.io/#domain-quoteblock"><i>Markdom quote block</i></a> block parent.
	 */
	QUOTE_BLOCK,

	/**
	 * Indication for a <a href="http://markdom.io/#domain-listitem"><i>Markdom list item</i></a> block parent.
	 */
	LIST_ITEM;

}