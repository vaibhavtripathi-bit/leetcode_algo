package com.leetcode.solutions.utils.general

/**
 * TypeConversions — common type casting and conversion helpers.
 *
 * Eliminates the boilerplate of converting between Kotlin/Java collection
 * types that you constantly need while writing algorithm solutions.
 */

// ─────────────────────────────────────────────
// IntArray ↔ List<Int>
// This conversion comes up in almost every problem
// where you want to use list utilities on an array or vice versa.
// ─────────────────────────────────────────────

/** IntArray → List<Int>. intArrayOf(1,2,3) → [1,2,3] */
fun IntArray.toIntList(): List<Int> = this.toList()

/** IntArray → MutableList<Int> */
fun IntArray.toMutableIntList(): MutableList<Int> = this.toMutableList()

/** List<Int> → IntArray */
fun List<Int>.toIntArray(): IntArray = this.toIntArray()

// ─────────────────────────────────────────────
// Array<Int> ↔ IntArray
// Kotlin has both; boxing matters for performance.
// ─────────────────────────────────────────────

/** Array<Int> (boxed) → IntArray (primitive) */
fun Array<Int>.toPrimitiveIntArray(): IntArray = this.toIntArray()

/** IntArray (primitive) → Array<Int> (boxed) */
fun IntArray.toBoxedArray(): Array<Int> = this.toTypedArray()

// ─────────────────────────────────────────────
// Array<IntArray> ↔ List<List<Int>>
// Very common when converting between LeetCode input
// format and what your algorithm needs.
// ─────────────────────────────────────────────

/** Array<IntArray> → List<List<Int>> */
fun Array<IntArray>.toListOfLists(): List<List<Int>> = this.map { it.toList() }

/** List<List<Int>> → Array<IntArray> */
fun List<List<Int>>.toArrayOfIntArrays(): Array<IntArray> =
    this.map { it.toIntArray() }.toTypedArray()

// ─────────────────────────────────────────────
// String ↔ various types
// ─────────────────────────────────────────────

/** String → IntArray of each character's ASCII code. "abc" → [97,98,99] */
fun String.toAsciiArray(): IntArray = this.map { it.code }.toIntArray()

/** IntArray of ASCII codes → String. [97,98,99] → "abc" */
fun IntArray.asciiToString(): String = String(this.map { it.toChar() }.toCharArray())

/** String → List<Int> digits. "123" → [1,2,3]. Non-digit chars return -1. */
fun String.toDigitList(): List<Int> = this.map { if (it.isDigit()) it - '0' else -1 }

/** String → List<Char>. "hello" → ['h','e','l','l','o'] */
fun String.toCharList(): List<Char> = this.toList()

/** CharArray → String. */
fun CharArray.toStr(): String = String(this)

/** List<Char> → String. */
fun List<Char>.toStr(): String = this.toCharArray().let { String(it) }

// ─────────────────────────────────────────────
// Char ↔ Int (ASCII)
// ─────────────────────────────────────────────

/** Char → ASCII Int code. 'A' → 65 */
fun Char.toAscii(): Int = this.code

/** ASCII Int code → Char. 65 → 'A' */
fun Int.toAsciiChar(): Char = this.toChar()

// ─────────────────────────────────────────────
// Boolean ↔ Int (0/1)
// Used in bitmask problems, counting truths.
// ─────────────────────────────────────────────

/** Boolean → Int. true → 1, false → 0 */
fun Boolean.toInt(): Int = if (this) 1 else 0

/** Int → Boolean. 0 → false, anything else → true */
fun Int.toBool(): Boolean = this != 0

// ─────────────────────────────────────────────
// Long ↔ Int (safe conversion)
// ─────────────────────────────────────────────

/** Long → Int safely, clamped to Int range. */
fun Long.toIntSafe(): Int = this.coerceIn(Int.MIN_VALUE.toLong(), Int.MAX_VALUE.toLong()).toInt()

/** Checks if a Long fits within Int range. */
fun Long.fitsInInt(): Boolean = this in Int.MIN_VALUE.toLong()..Int.MAX_VALUE.toLong()

// ─────────────────────────────────────────────
// Pair and Triple
// ─────────────────────────────────────────────

/** Destructures IntArray of size 2 into a Pair. intArrayOf(3,5).toPair() → (3,5) */
fun IntArray.toPair(): Pair<Int, Int> {
    require(this.size >= 2) { "Array must have at least 2 elements" }
    return this[0] to this[1]
}

/** Destructures IntArray of size 3 into a Triple. */
fun IntArray.toTriple(): Triple<Int, Int, Int> {
    require(this.size >= 3) { "Array must have at least 3 elements" }
    return Triple(this[0], this[1], this[2])
}

/** Pair<Int,Int> → IntArray. (3,5) → [3,5] */
fun Pair<Int, Int>.toIntArray(): IntArray = intArrayOf(first, second)

/** Triple<Int,Int,Int> → IntArray. */
fun Triple<Int, Int, Int>.toIntArray(): IntArray = intArrayOf(first, second, third)
