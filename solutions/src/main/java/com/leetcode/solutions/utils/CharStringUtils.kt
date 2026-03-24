package com.leetcode.solutions.utils

/**
 * CharStringUtils — high-frequency char/string helpers for FAANG interviews.
 *
 * The single most reused pattern in string problems:
 *   val freq = IntArray(26)
 *   freq['b' - 'a']++   ← 'b' → index 1
 *
 * These helpers make that pattern readable and mistake-free.
 */

// ─────────────────────────────────────────────
// Char ↔ Index conversions
// ─────────────────────────────────────────────

/** Lowercase char → 0-based index. 'a' → 0, 'z' → 25 */
fun Char.toLowercaseIndex(): Int = this - 'a'

/** Uppercase char → 0-based index. 'A' → 0, 'Z' → 25 */
fun Char.toUppercaseIndex(): Int = this - 'A'

/** 0-based index → lowercase char. 0 → 'a', 25 → 'z' */
fun Int.toAlphabetChar(): Char = ('a' + this)

/** Digit char → Int. '7' → 7 */
fun Char.toDigitInt(): Int = this - '0'

/** Int digit → Char. 7 → '7' */
fun Int.toDigitChar(): Char = '0' + this

// ─────────────────────────────────────────────
// Frequency arrays (IntArray of size 26)
// Used in: Anagram, Sliding Window, Permutation in String, etc.
// ─────────────────────────────────────────────

/**
 * Builds a frequency array of size 26 for lowercase letters.
 * "hello" → [0,0,0,0,1,0,1,0,0,0,0,2,0,0,1,0,0,0,0,0,0,0,0,0,0,0]
 */
fun String.charFrequency(): IntArray {
    val freq = IntArray(26)
    for (c in this) freq[c - 'a']++
    return freq
}

/**
 * Builds a frequency map for any characters (handles non-lowercase, digits, etc.)
 * "hello world" → {h:1, e:1, l:3, o:2, ' ':1, w:1, r:1, d:1}
 */
fun String.charFrequencyMap(): HashMap<Char, Int> {
    val freq = HashMap<Char, Int>()
    for (c in this) freq[c] = freq.getOrDefault(c, 0) + 1
    return freq
}

// ─────────────────────────────────────────────
// Anagram checks
// ─────────────────────────────────────────────

/** True if s and t are anagrams of each other. Both must be lowercase letters. */
fun String.isAnagramOf(other: String): Boolean {
    if (this.length != other.length) return false
    val diff = this.charFrequency()
    for (c in other) diff[c - 'a']--
    return diff.all { it == 0 }
}

/**
 * Canonical anagram key — use as HashMap key to group anagrams together.
 * "eat" → "aet", "tea" → "aet"
 */
fun String.anagramKey(): String = String(this.toCharArray().also { it.sort() })

// ─────────────────────────────────────────────
// Palindrome check
// ─────────────────────────────────────────────

/** True if string reads the same forwards and backwards (case-sensitive). */
fun String.isPalindrome(): Boolean {
    var left = 0; var right = lastIndex
    while (left < right) {
        if (this[left++] != this[right--]) return false
    }
    return true
}

/**
 * True if string is a palindrome ignoring non-alphanumeric characters and case.
 * Used in LeetCode 125: Valid Palindrome.
 */
fun String.isValidPalindrome(): Boolean {
    val cleaned = this.filter { it.isLetterOrDigit() }.lowercase()
    return cleaned.isPalindrome()
}

// ─────────────────────────────────────────────
// Char classification (readable wrappers)
// Kotlin already has these, but aliased here
// for discoverability and consistent call-style.
// ─────────────────────────────────────────────

fun Char.isLowercaseLetter(): Boolean = this in 'a'..'z'
fun Char.isUppercaseLetter(): Boolean = this in 'A'..'Z'
fun Char.isLetter(): Boolean          = isLowercaseLetter() || isUppercaseLetter()
fun Char.isDigitChar(): Boolean       = this in '0'..'9'
fun Char.isAlphanumeric(): Boolean    = isLetter() || isDigitChar()
