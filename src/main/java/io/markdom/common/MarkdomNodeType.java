package io.markdom.common;

/**
 * A {@link MarkdomNodeType} indicates the node type of a <a href="http://markdom.io/#domain-node"><i>Markdom
 * node</i></a>.
 */
public enum MarkdomNodeType {

	/**
	 * Indication for a <a href="http://markdom.io/#domain-document"><i>Markdom document</i></a> node.
	 */
	DOCUMENT,

	/**
	 * Indication for a <a href="http://markdom.io/#domain-block"><i>Markdom block</i></a> node.
	 */
	BLOCK,

	/**
	 * Indication for a <a href="http://markdom.io/#domain-listitem"><i>Markdom list item</i></a> node.
	 */
	LIST_ITEM,

	/**
	 * Indication for a <a href="http://markdom.io/#domain-content"><i>Markdom content</i></a> node.
	 */
	CONTENT;

}