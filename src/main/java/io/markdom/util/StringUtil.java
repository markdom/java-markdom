package io.markdom.util;

import java.util.Iterator;
import java.util.StringTokenizer;

import lombok.experimental.UtilityClass;
import net.markenwerk.commons.iterators.StringTokenizerIterator;

@UtilityClass

public class StringUtil {

	public static int longestSequenceLength(CharSequence value, char character) {
		int maxLength = 0;
		int currentLength = 0;
		for (int i = 0, n = value.length(); i < n; i++) {
			if (character == value.charAt(i)) {
				currentLength++;
			} else {
				maxLength = Math.max(maxLength, currentLength);
				currentLength = 0;
			}
		}
		return Math.max(maxLength, currentLength);
	}

	public static int leadingSequenceLength(CharSequence value, char character) {
		int length = 0;
		for (int i = 0, n = value.length(); i < n; i++) {
			if (character == value.charAt(i)) {
				length++;
			} else {
				return length;
			}
		}
		return length;
	}

	public static boolean consistsOnlyOf(CharSequence value, char character) {
		for (int i = 0, n = value.length(); i < n; i++) {
			if (character != value.charAt(i)) {
				return false;
			}
		}
		return true;
	}

	public static boolean consistsOnlyOfAnyOf(CharSequence value, char[] characters) {
		for (int i = 0, n = value.length(); i < n; i++) {
			char character = value.charAt(i);
			if (!contains(characters, character)) {
				return false;
			}
		}
		return true;
	}

	private static boolean contains(char[] characters, char character) {
		for (int i = 0, n = characters.length; i < n; i++) {
			if (characters[i] == character) {
				return true;
			}
		}
		return false;
	}

	public static String repeat(char character, int length) {
		char[] buffer = new char[length];
		for (int i = 0; i < length; i++) {
			buffer[i] = character;
		}
		return new String(buffer);
	}

	public static Iterator<String> splitLines(String code) {
		return new StringTokenizerIterator(new StringTokenizer(code, "\r\n", false));
	}

}
