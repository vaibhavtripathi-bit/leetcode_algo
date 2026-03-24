package com.leetcode.solutions.utils

/**
 * ArrayUtils — high-frequency array helpers for FAANG interviews.
 *
 * These are called repeatedly across sorting, two-pointer, prefix sum,
 * and partitioning problems.
 */

// ─────────────────────────────────────────────
// Swap
// ─────────────────────────────────────────────

/** Swap elements at indices i and j in place. */
fun IntArray.swap(i: Int, j: Int) {
    val temp = this[i]; this[i] = this[j]; this[j] = temp
}

/** Swap elements at indices i and j in a generic MutableList. */
fun <T> MutableList<T>.swap(i: Int, j: Int) {
    val temp = this[i]; this[i] = this[j]; this[j] = temp
}

// ─────────────────────────────────────────────
// Reverse subarray
// ─────────────────────────────────────────────

/** Reverse elements in [start, end] inclusive, in place. */
fun IntArray.reverseRange(start: Int, end: Int) {
    var l = start; var r = end
    while (l < r) { swap(l++, r--) }
}

/** Reverse the entire array in place. */
fun IntArray.reverseInPlace() = reverseRange(0, lastIndex)

// ─────────────────────────────────────────────
// Prefix / Suffix sums
// Used in: Subarray Sum, Product Except Self,
//          Trapping Rain Water, Equilibrium Index
// ─────────────────────────────────────────────

/**
 * Returns a 1-indexed prefix sum array of size n+1.
 * prefixSum[i] = sum of nums[0..i-1]
 * Range sum [l, r] = prefixSum[r+1] - prefixSum[l]
 */
fun IntArray.toPrefixSum(): IntArray {
    val prefix = IntArray(size + 1)
    for (i in indices) prefix[i + 1] = prefix[i] + this[i]
    return prefix
}

/** Range sum query using a prebuilt prefix sum array. O(1). */
fun IntArray.rangeSum(prefixSum: IntArray, left: Int, right: Int): Int =
    prefixSum[right + 1] - prefixSum[left]

/**
 * Returns suffix sum array of same size.
 * suffixSum[i] = sum of nums[i..n-1]
 */
fun IntArray.toSuffixSum(): IntArray {
    val suffix = IntArray(size)
    suffix[lastIndex] = last()
    for (i in lastIndex - 1 downTo 0) suffix[i] = suffix[i + 1] + this[i]
    return suffix
}

// ─────────────────────────────────────────────
// Partitioning
// ─────────────────────────────────────────────

/**
 * Move all zeros to the end while preserving relative order of non-zeros.
 * In-place. O(n) time, O(1) space.
 * Used in: LeetCode 283 — Move Zeroes
 */
fun IntArray.moveZerosToEnd() {
    var insertPos = 0
    for (num in this) { if (num != 0) this[insertPos++] = num }
    while (insertPos <= lastIndex) this[insertPos++] = 0
}

/**
 * Dutch National Flag — sort array of 0s, 1s, 2s in one pass.
 * In-place. O(n) time, O(1) space.
 * Used in: LeetCode 75 — Sort Colors
 */
fun IntArray.dutchNationalFlag() {
    var low = 0; var mid = 0; var high = lastIndex

    while (mid <= high) {
        when (this[mid]) {
            0    -> { swap(low++, mid++); }
            1    -> { mid++ }
            else -> { swap(mid, high--) }
        }
    }
}

// ─────────────────────────────────────────────
// Binary search variants
// These are called directly — cleaner than
// re-implementing at each problem site.
// ─────────────────────────────────────────────

/**
 * Standard binary search — returns index of target or -1.
 * Array must be sorted.
 */
fun IntArray.binarySearch(target: Int): Int {
    var lo = 0; var hi = lastIndex
    while (lo <= hi) {
        val mid = lo + (hi - lo) / 2
        when {
            this[mid] == target -> return mid
            this[mid] < target  -> lo = mid + 1
            else                -> hi = mid - 1
        }
    }
    return -1
}

/**
 * Leftmost binary search — first position where target could be inserted
 * to keep sort order (lower bound). Returns index in [0, size].
 */
fun IntArray.lowerBound(target: Int): Int {
    var lo = 0; var hi = size
    while (lo < hi) {
        val mid = lo + (hi - lo) / 2
        if (this[mid] < target) lo = mid + 1 else hi = mid
    }
    return lo
}

/**
 * Rightmost binary search — first position AFTER the last occurrence of target
 * (upper bound). Returns index in [0, size].
 */
fun IntArray.upperBound(target: Int): Int {
    var lo = 0; var hi = size
    while (lo < hi) {
        val mid = lo + (hi - lo) / 2
        if (this[mid] <= target) lo = mid + 1 else hi = mid
    }
    return lo
}

// ─────────────────────────────────────────────
// Misc
// ─────────────────────────────────────────────

/** Prints array in readable format for debugging: [1, 2, 3] */
fun IntArray.printFormatted() = println("[${joinToString(", ")}]")

/** Prints 2D matrix row by row for debugging. */
fun Array<IntArray>.printMatrix() {
    forEach { row -> println("[${row.joinToString(", ")}]") }
}
