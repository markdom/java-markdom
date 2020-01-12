package io.markdom.util;

import java.util.LinkedList;
import java.util.List;

import lombok.experimental.UtilityClass;

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

	public static List<String> splitLines(String string) {
		List<String> lines = new LinkedList<>();
		while (!string.isEmpty()) {
			int index = string.indexOf('\n');
			if (-1 == index) {
				lines.add(string);
				string = "";
			} else {
				if (0 != index && '\r' == string.charAt(index - 1)) {
					lines.add(string.substring(0, index - 1));
					string = string.substring(index + 1);
				} else {
					lines.add(string.substring(0, index));
					string = string.substring(index + 1);
				}
			}
		}
		return lines;
	}

}
