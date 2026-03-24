package com.leetcode.solutions.utils

/**
 * BitUtils — high-frequency bit manipulation helpers for FAANG interviews.
 *
 * These are the building blocks used across:
 * Single Number, Missing Number, Power of Two,
 * Counting Bits, Subset generation, etc.
 */

// ─────────────────────────────────────────────
// Count set bits
// ─────────────────────────────────────────────

/**
 * Count number of 1-bits (Brian Kernighan's algorithm).
 * Each iteration removes the lowest set bit. O(number of set bits).
 * 7 (0111) → 3
 */
fun Int.countSetBits(): Int {
    var n = this; var count = 0
    while (n != 0) { n = n and (n - 1); count++ }
    return count
}

/** Same as countSetBits but using built-in — both are acceptable in interview. */
fun Int.popCount(): Int = Integer.bitCount(this)

// ─────────────────────────────────────────────
// Bit check / set / clear / toggle
// Used in: bitmask DP, subset generation
// ─────────────────────────────────────────────

/** True if bit at position `pos` (0 = LSB) is set. */
fun Int.isBitSet(pos: Int): Boolean = (this shr pos) and 1 == 1

/** Returns number with bit at `pos` set to 1. */
fun Int.setBit(pos: Int): Int = this or (1 shl pos)

/** Returns number with bit at `pos` cleared to 0. */
fun Int.clearBit(pos: Int): Int = this and (1 shl pos).inv()

/** Returns number with bit at `pos` flipped. */
fun Int.toggleBit(pos: Int): Int = this xor (1 shl pos)

/** Returns value of the bit at position `pos` (0 or 1). */
fun Int.getBit(pos: Int): Int = (this shr pos) and 1

// ─────────────────────────────────────────────
// Power of 2 / lowest bit
// ─────────────────────────────────────────────

/**
 * True if n is a power of 2.
 * Trick: powers of 2 have exactly one set bit → n & (n-1) == 0
 */
fun Int.isPowerOfTwo(): Boolean = this > 0 && (this and (this - 1)) == 0

/**
 * Returns the lowest set bit value.
 * 12 (1100) → 4 (0100)
 * Used in Fenwick Tree update/query.
 */
fun Int.lowestSetBit(): Int = this and (-this)

/**
 * Returns the number with the lowest set bit removed.
 * 12 (1100) → 8 (1000)
 * Used in Brian Kernighan's bit count.
 */
fun Int.removeLowestSetBit(): Int = this and (this - 1)

// ─────────────────────────────────────────────
// XOR tricks
// ─────────────────────────────────────────────

/**
 * XOR of all integers from 1 to n follows a cycle of 4:
 *   n % 4 == 0 → n
 *   n % 4 == 1 → 1
 *   n % 4 == 2 → n + 1
 *   n % 4 == 3 → 0
 * Used in: Missing Number, Find the Difference
 */
fun xorUpTo(n: Int): Int = when (n % 4) {
    0    -> n
    1    -> 1
    2    -> n + 1
    else -> 0
}

// ─────────────────────────────────────────────
// Bitmask subset generation
// Used in: Subsets, bitmask DP (e.g. Travelling Salesman,
// Minimum Cost to Connect All Points)
// ─────────────────────────────────────────────

/**
 * Generates all subsets of a list using bitmask.
 * For n elements, there are 2^n subsets.
 *
 * Usage: listOf(1,2,3).allSubsets() → [[], [1], [2], [1,2], [3], ...]
 */
fun <T> List<T>.allSubsets(): List<List<T>> {
    val subsets = mutableListOf<List<T>>()
    val n = size
    for (mask in 0 until (1 shl n)) {
        val subset = mutableListOf<T>()
        for (i in 0 until n) {
            if (mask.isBitSet(i)) subset.add(this[i])
        }
        subsets.add(subset)
    }
    return subsets
}
