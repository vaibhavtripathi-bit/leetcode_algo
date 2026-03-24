package com.leetcode.solutions.utils.general

/**
 * RangeUtils — clamp, bounds, range generation, and overlap helpers.
 *
 * Small but constantly useful — stops you from writing
 * max(lo, min(hi, value)) inline everywhere.
 */

// ─────────────────────────────────────────────
// Clamp
// ─────────────────────────────────────────────

/** Clamps an Int between lo and hi (inclusive). clamp(15, 0, 10) → 10 */
fun clamp(value: Int, lo: Int, hi: Int): Int = value.coerceIn(lo, hi)

fun clamp(value: Long, lo: Long, hi: Long): Long = value.coerceIn(lo, hi)

fun clamp(value: Double, lo: Double, hi: Double): Double = value.coerceIn(lo, hi)

/** Clamps this Int to [lo, hi]. 15.clampTo(0, 10) → 10 */
fun Int.clampTo(lo: Int, hi: Int): Int = this.coerceIn(lo, hi)

fun Long.clampTo(lo: Long, hi: Long): Long = this.coerceIn(lo, hi)

fun Double.clampTo(lo: Double, hi: Double): Double = this.coerceIn(lo, hi)

// ─────────────────────────────────────────────
// In-range checks
// ─────────────────────────────────────────────

/** True if value is within [lo, hi] inclusive. */
fun Int.isInRange(lo: Int, hi: Int): Boolean = this in lo..hi

/** True if value is strictly between lo and hi (exclusive). */
fun Int.isBetween(lo: Int, hi: Int): Boolean = this > lo && this < hi

/** True if value is within array bounds [0, size-1]. */
fun Int.isValidIndex(arraySize: Int): Boolean = this in 0 until arraySize

// ─────────────────────────────────────────────
// Range generation
// ─────────────────────────────────────────────

/** Generates a List<Int> from start to end (inclusive, step 1). rangeList(1,5) → [1,2,3,4,5] */
fun rangeList(start: Int, end: Int): List<Int> = (start..end).toList()

/** Generates a List<Int> with a given step. rangeList(0, 10, 2) → [0,2,4,6,8,10] */
fun rangeList(start: Int, end: Int, step: Int): List<Int> = (start..end step step).toList()

/** Generates an IntArray from 0 to n-1. indexRange(5) → [0,1,2,3,4] */
fun indexRange(n: Int): IntArray = IntArray(n) { it }

// ─────────────────────────────────────────────
// Interval overlap — beyond what CollectionUtils has
// ─────────────────────────────────────────────

/**
 * Returns the overlap length of two ranges [a0,a1] and [b0,b1].
 * Returns 0 if they don't overlap.
 * overlapLength(1,5, 3,8) → 2  ([3..5])
 */
fun overlapLength(a0: Int, a1: Int, b0: Int, b1: Int): Int =
    maxOf(0, minOf(a1, b1) - maxOf(a0, b0))

/**
 * Returns the merged range of two overlapping intervals, or null if they don't overlap.
 * merge(1,5, 3,8) → (1,8)
 */
fun mergeRange(a0: Int, a1: Int, b0: Int, b1: Int): Pair<Int, Int>? {
    if (a1 < b0 || b1 < a0) return null
    return minOf(a0, b0) to maxOf(a1, b1)
}

// ─────────────────────────────────────────────
// Min / Max of multiple values
// Kotlin has maxOf/minOf for two args;
// these handle variable-arg lists clearly.
// ─────────────────────────────────────────────

/** Returns the maximum of a list of integers. maxOfAll(3,1,4,1,5,9) → 9 */
fun maxOfAll(vararg values: Int): Int = values.max()!!

/** Returns the minimum of a list of integers. minOfAll(3,1,4,1,5,9) → 1 */
fun minOfAll(vararg values: Int): Int = values.min()!!

// ─────────────────────────────────────────────
// Circular index
// Used in: circular arrays, round-robin scheduling
// ─────────────────────────────────────────────

/**
 * Wraps an index to stay within [0, size-1] even if negative.
 * circularIndex(-1, 5) → 4
 * circularIndex(7, 5)  → 2
 */
fun circularIndex(index: Int, size: Int): Int = ((index % size) + size) % size

// ─────────────────────────────────────────────
// Ceiling / floor division (integer, no floats)
// ─────────────────────────────────────────────

/** Integer ceiling division. ceilDiv(7, 2) → 4 */
fun ceilDiv(a: Int, b: Int): Int = (a + b - 1) / b

/** Integer floor division (same as / for positives, handles negatives correctly). */
fun floorDiv(a: Int, b: Int): Int = Math.floorDiv(a, b)
