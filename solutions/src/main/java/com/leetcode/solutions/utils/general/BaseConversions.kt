package com.leetcode.solutions.utils.general

/**
 * BaseConversions — convert numbers between any bases, plus Roman numerals.
 *
 * Binary is already in NumberUtils; this file covers every other base
 * and the Roman numeral system (asked directly at Amazon/Google).
 */

// ─────────────────────────────────────────────
// Any base ↔ base 10
// ─────────────────────────────────────────────

/**
 * Converts a base-10 Int to a string representation in any base (2–36).
 * 255.toBaseString(16) → "ff"
 * 255.toBaseString(2)  → "11111111"
 * 255.toBaseString(8)  → "377"
 */
fun Int.toBaseString(base: Int): String {
    require(base in 2..36) { "Base must be between 2 and 36" }
    if (this == 0) return "0"
    val digits = "0123456789abcdefghijklmnopqrstuvwxyz"
    val sb = StringBuilder()
    var n = Math.abs(this)
    while (n > 0) {
        sb.append(digits[n % base])
        n /= base
    }
    if (this < 0) sb.append('-')
    return sb.reverse().toString()
}

/**
 * Converts a string in a given base to a base-10 Int.
 * "ff".fromBase(16) → 255
 * "11111111".fromBase(2) → 255
 * "377".fromBase(8) → 255
 */
fun String.fromBase(base: Int): Int = Integer.parseInt(this, base)

// ─────────────────────────────────────────────
// Hex and Octal shortcuts
// ─────────────────────────────────────────────

/** Int → hex string (lowercase, no prefix). 255 → "ff" */
fun Int.toHexString(): String = Integer.toHexString(this)

/** Int → octal string. 8 → "10" */
fun Int.toOctalString(): String = Integer.toOctalString(this)

/** Hex string → Int. "ff" → 255 */
fun String.hexToInt(): Int = Integer.parseInt(this, 16)

/** Octal string → Int. "10" → 8 */
fun String.octalToInt(): Int = Integer.parseInt(this, 8)

// ─────────────────────────────────────────────
// Roman Numerals ↔ Int
// Asked directly at Amazon (LC 12, LC 13)
// ─────────────────────────────────────────────

private val INT_TO_ROMAN = listOf(
    1000 to "M", 900 to "CM", 500 to "D", 400 to "CD",
    100  to "C", 90  to "XC", 50  to "L", 40  to "XL",
    10   to "X", 9   to "IX", 5   to "V", 4   to "IV",
    1    to "I"
)

private val ROMAN_TO_INT = mapOf(
    'I' to 1, 'V' to 5, 'X' to 10, 'L' to 50,
    'C' to 100, 'D' to 500, 'M' to 1000
)

/**
 * Converts an Int (1–3999) to a Roman numeral string.
 * 1994 → "MCMXCIV"
 */
fun Int.toRoman(): String {
    val sb = StringBuilder()
    var num = this
    for ((value, symbol) in INT_TO_ROMAN) {
        while (num >= value) { sb.append(symbol); num -= value }
    }
    return sb.toString()
}

/**
 * Converts a Roman numeral string to an Int.
 * "MCMXCIV" → 1994
 *
 * Rule: if a smaller value precedes a larger value, subtract it.
 */
fun String.romanToInt(): Int {
    var result = 0
    for (i in indices) {
        val current = ROMAN_TO_INT[this[i]]!!
        val next    = if (i + 1 < length) ROMAN_TO_INT[this[i + 1]]!! else 0
        result += if (current < next) -current else current
    }
    return result
}

// ─────────────────────────────────────────────
// Number ↔ English words
// (Amazon, Google — LC 273: Integer to English Words)
// ─────────────────────────────────────────────

private val BELOW_20 = listOf(
    "", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine",
    "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen",
    "Sixteen", "Seventeen", "Eighteen", "Nineteen"
)
private val TENS = listOf(
    "", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"
)
private val THOUSANDS = listOf("", "Thousand", "Million", "Billion")

/**
 * Converts an Int to its English words representation.
 * 1234567 → "One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven"
 * Used in: LC 273 — Integer to English Words
 */
fun Int.toEnglishWords(): String {
    if (this == 0) return "Zero"
    return buildEnglishWords(this).trim()
}

private fun buildEnglishWords(num: Int): String {
    return when {
        num == 0       -> ""
        num < 20       -> BELOW_20[num] + " "
        num < 100      -> TENS[num / 10] + " " + buildEnglishWords(num % 10)
        num < 1000     -> BELOW_20[num / 100] + " Hundred " + buildEnglishWords(num % 100)
        else -> {
            var result = ""
            var n = num
            for (i in THOUSANDS.indices) {
                if (n % 1000 != 0) {
                    result = buildEnglishWords(n % 1000) +
                             (if (THOUSANDS[i].isEmpty()) "" else THOUSANDS[i] + " ") +
                             result
                }
                n /= 1000
            }
            result
        }
    }
}
