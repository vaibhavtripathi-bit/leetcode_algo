package com.leetcode.solutions.bitmanipulation.problems.medium

/**
 * LeetCode 371: Sum of Two Integers
 * https://leetcode.com/problems/sum-of-two-integers/
 *
 * Difficulty: Medium
 * Companies: Meta, Amazon, Apple
 *
 * Add two integers WITHOUT using + or - operators.
 *
 * Example: a = 1, b = 2 → 3
 */

/**
 * Solution 1: Bit manipulation (simulates half adder)
 * XOR = sum without carry
 * AND shifted left = carry
 * Repeat until no carry
 * Time: O(1), Space: O(1)
 */
class SumOfTwoIntegers1 {
    fun getSum(a: Int, b: Int): Int {
        var x = a
        var carry = b

        while (carry != 0) {
            val sum = x xor carry
            carry = (x and carry) shl 1
            x = sum
        }

        return x
    }
}

/** Solution 2: Recursive */
class SumOfTwoIntegers2 {
    fun getSum(a: Int, b: Int): Int {
        return if (b == 0) a
               else getSum(a xor b, (a and b) shl 1)
    }
}
