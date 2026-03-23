package com.leetcode.solutions.bitmanipulation.problems.easy

/**
 * LeetCode 191: Number of 1 Bits (Hamming Weight)
 * https://leetcode.com/problems/number-of-1-bits/
 *
 * Difficulty: Easy
 * Companies: Amazon, Apple, Google
 *
 * Return number of set bits (1s) in the binary representation.
 *
 * Example: n = 11 (0b1011) → 3
 */

/** Solution 1: Brian Kernighan - Time: O(k) where k = set bits, Space: O(1) */
class HammingWeight1 {
    fun hammingWeight(n: Int): Int {
        var count = 0
        var num = n
        while (num != 0) {
            num = num and (num - 1)    // removes lowest set bit
            count++
        }
        return count
    }
}

/** Solution 2: Check each bit - Time: O(32), Space: O(1) */
class HammingWeight2 {
    fun hammingWeight(n: Int): Int {
        return (0 until 32).count { i -> (n ushr i) and 1 == 1 }
    }
}

/** Solution 3: Built-in - Time: O(1) */
class HammingWeight3 {
    fun hammingWeight(n: Int): Int = Integer.bitCount(n)
}
