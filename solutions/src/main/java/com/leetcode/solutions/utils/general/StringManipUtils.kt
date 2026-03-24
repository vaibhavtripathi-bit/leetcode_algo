package com.leetcode.solutions.utils.general

/**
 * StringManipUtils — common string manipulation helpers.
 *
 * Covers the operations you keep rewriting inline:
 * reversing, word splitting, trimming, counting, replacing.
 */

// ─────────────────────────────────────────────
// Reverse
// ─────────────────────────────────────────────

/** Reverses the entire string. "hello" → "olleh" */
fun String.reversed(): String = this.reversed()   // Kotlin built-in, aliased for discoverability

/** Reverses each word in the string, preserving word order.
 *  "hello world" → "olleh dlrow" */
fun String.reverseEachWord(): String =
    this.split(" ").joinToString(" ") { it.reversed() }

/**
 * Reverses the order of words. Handles multiple spaces.
 * "  the sky is blue  " → "blue is sky the"
 * Used in: LC 151 — Reverse Words in a String
 */
fun String.reverseWords(): String =
    this.trim().split("\\s+".toRegex()).reversed().joinToString(" ")

// ─────────────────────────────────────────────
// Case manipulation
// ─────────────────────────────────────────────

/** Capitalizes the first letter, lowercases the rest. "hELLO" → "Hello" */
fun String.capitalize(): String =
    if (isEmpty()) this else this[0].uppercaseChar() + substring(1).lowercase()

/** Capitalizes the first letter of each word. "hello world" → "Hello World" */
fun String.titleCase(): String =
    split(" ").joinToString(" ") { it.capitalize() }

/** Converts camelCase to snake_case. "helloWorld" → "hello_world" */
fun String.camelToSnake(): String =
    replace(Regex("([A-Z])")) { "_${it.value.lowercase()}" }.trimStart('_')

/** Converts snake_case to camelCase. "hello_world" → "helloWorld" */
fun String.snakeToCamel(): String =
    split("_").mapIndexed { i, s -> if (i == 0) s else s.capitalize() }.joinToString("")

// ─────────────────────────────────────────────
// Counting / finding
// ─────────────────────────────────────────────

/** Counts occurrences of a character. "hello".countChar('l') → 2 */
fun String.countChar(c: Char): Int = this.count { it == c }

/** Counts occurrences of a substring (non-overlapping). "abab".countSubstring("ab") → 2 */
fun String.countSubstring(sub: String): Int {
    if (sub.isEmpty()) return 0
    var count = 0; var start = 0
    while (true) {
        val idx = indexOf(sub, start)
        if (idx == -1) break
        count++; start = idx + sub.length
    }
    return count
}

/** Returns the index of the first non-repeating character, or -1.
 *  "leetcode" → 0 ('l') */
fun String.firstUniqueCharIndex(): Int {
    val freq = IntArray(26)
    for (c in this) freq[c - 'a']++
    for (i in indices) if (freq[this[i] - 'a'] == 1) return i
    return -1
}

/** Returns the most frequent character. "aabbc" → 'a' (or 'b' — first max). */
fun String.mostFrequentChar(): Char {
    val freq = IntArray(26)
    for (c in this) if (c in 'a'..'z') freq[c - 'a']++
    return ('a' + freq.indices.maxByOrNull { freq[it] }!!)
}

// ─────────────────────────────────────────────
// Splitting / tokenizing
// ─────────────────────────────────────────────

/** Splits string into words, ignoring extra whitespace. "  hi  there  " → ["hi","there"] */
fun String.words(): List<String> = this.trim().split("\\s+".toRegex()).filter { it.isNotEmpty() }

/** Splits string into individual characters as a List<String>. "abc" → ["a","b","c"] */
fun String.toCharStrings(): List<String> = this.map { it.toString() }

// ─────────────────────────────────────────────
// Padding / trimming
// ─────────────────────────────────────────────

/** Left-pads string to given width with a pad character. "42".padLeftWith(5,'0') → "00042" */
fun String.padLeftWith(width: Int, padChar: Char = ' '): String = this.padStart(width, padChar)

/** Right-pads string to given width with a pad character. */
fun String.padRightWith(width: Int, padChar: Char = ' '): String = this.padEnd(width, padChar)

/** Removes all whitespace from the string. "h e l l o" → "hello" */
fun String.removeAllWhitespace(): String = this.replace("\\s".toRegex(), "")

/** Removes all non-alphanumeric characters. "a!b@c#" → "abc" */
fun String.keepAlphanumeric(): String = this.filter { it.isLetterOrDigit() }

// ─────────────────────────────────────────────
// Compression / encoding
// ─────────────────────────────────────────────

/**
 * Run-length encoding: compresses consecutive characters.
 * "aaabbc" → "a3b2c1"
 * Used in: LC 443 — String Compression
 */
fun String.runLengthEncode(): String {
    if (isEmpty()) return ""
    val sb = StringBuilder()
    var i = 0
    while (i < length) {
        val c = this[i]; var count = 0
        while (i < length && this[i] == c) { count++; i++ }
        sb.append(c).append(count)
    }
    return sb.toString()
}

/**
 * Decodes a run-length encoded string.
 * "a3b2c1" → "aaabbc"
 */
fun String.runLengthDecode(): String {
    val sb = StringBuilder(); var i = 0
    while (i < length) {
        val c = this[i++]
        var numStr = ""
        while (i < length && this[i].isDigit()) { numStr += this[i++] }
        repeat(numStr.toIntOrNull() ?: 1) { sb.append(c) }
    }
    return sb.toString()
}

// ─────────────────────────────────────────────
// Check helpers
// ─────────────────────────────────────────────

/** True if string contains only digits. "123" → true, "12a" → false */
fun String.isNumeric(): Boolean = this.isNotEmpty() && this.all { it.isDigit() }

/** True if string contains only letters. */
fun String.isAlpha(): Boolean = this.isNotEmpty() && this.all { it.isLetter() }

/** True if string contains only letters and digits. */
fun String.isAlphanumeric(): Boolean = this.isNotEmpty() && this.all { it.isLetterOrDigit() }
