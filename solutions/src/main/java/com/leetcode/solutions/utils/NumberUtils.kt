package com.leetcode.solutions.utils

/**
 * NumberUtils — high-frequency number helpers for FAANG interviews.
 *
 * All functions are top-level or extension functions for clean call-sites:
 *   123.digits()        → [1, 2, 3]
 *   123.reverse()       → 321
 *   42.toBinaryString() → "101010"
 */

// ─────────────────────────────────────────────
// Digit extraction
// ─────────────────────────────────────────────

/** Returns digits from most significant to least significant. 123 → [1, 2, 3] */
fun Int.digits(): List<Int> {
    if (this == 0) return listOf(0)
    val result = mutableListOf<Int>()
    var n = Math.abs(this)
    while (n > 0) {
        result.add(0, n % 10)
        n /= 10
    }
    return result
}

/** Sum of all digits. 123 → 6 */
fun Int.digitSum(): Int = Math.abs(this).digits().sum()

/** Number of digits. 1234 → 4, 0 → 1 */
fun Int.digitCount(): Int = if (this == 0) 1 else Math.log10(Math.abs(this).toDouble()).toInt() + 1

// ─────────────────────────────────────────────
// Reverse / Palindrome
// ─────────────────────────────────────────────

/**
 * Reverses digits of a number. Returns 0 on overflow (as LeetCode expects).
 * 123 → 321, -123 → -321, 120 → 21
 */
fun Int.reverseDigits(): Int {
    var n = this
    var reversed = 0L
    while (n != 0) {
        reversed = reversed * 10 + n % 10
        n /= 10
    }
    return if (reversed > Int.MAX_VALUE || reversed < Int.MIN_VALUE) 0 else reversed.toInt()
}

/** True if integer reads the same forwards and backwards. Negatives are never palindromes. */
fun Int.isPalindromeNumber(): Boolean {
    if (this < 0) return false
    if (this != 0 && this % 10 == 0) return false   // trailing zero can't be palindrome
    var original = this
    var reversed = 0
    while (original > reversed) {
        reversed = reversed * 10 + original % 10
        original /= 10
    }
    return original == reversed || original == reversed / 10
}

// ─────────────────────────────────────────────
// Base conversions  (binary is the only one
// that appears frequently in FAANG problems)
// ─────────────────────────────────────────────

/** Int → binary string without "0b" prefix. 42 → "101010" */
fun Int.toBinaryString(): String = Integer.toBinaryString(this)

/** Binary string → Int. "101010" → 42 */
fun String.binaryToInt(): Int = Integer.parseInt(this, 2)

/** Int → binary string with fixed width, zero-padded. 5, width=8 → "00000101" */
fun Int.toBinaryStringPadded(width: Int): String = Integer.toBinaryString(this).padStart(width, '0')

// ─────────────────────────────────────────────
// Digit reconstruction
// ─────────────────────────────────────────────

/** List of digits → Int. [1, 2, 3] → 123 */
fun List<Int>.digitsToInt(): Int = fold(0) { acc, d -> acc * 10 + d }

/** IntArray of digits → Int. intArrayOf(1,2,3) → 123 */
fun IntArray.digitsToInt(): Int = fold(0) { acc, d -> acc * 10 + d }
