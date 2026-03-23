package com.leetcode.solutions.arrays.problems.medium

/**
 * LeetCode 238: Product of Array Except Self
 * https://leetcode.com/problems/product-of-array-except-self/
 *
 * Difficulty: Medium
 * Companies: All FAANG
 *
 * Return array where result[i] = product of all elements except nums[i].
 * Must be O(n) without division.
 *
 * Example: nums = [1,2,3,4] → [24,12,8,6]
 */

/** Solution 1: Prefix × Suffix (Two Pass) - Time: O(n), Space: O(1) extra */
class ProductExceptSelf1 {
    fun productExceptSelf(nums: IntArray): IntArray {
        val result = buildPrefixProducts(nums)
        multiplyWithSuffixProducts(nums, result)
        return result
    }

    private fun buildPrefixProducts(nums: IntArray): IntArray {
        val result = IntArray(nums.size) { 1 }
        for (i in 1 until nums.size) {
            result[i] = result[i - 1] * nums[i - 1]
        }
        return result
    }

    private fun multiplyWithSuffixProducts(nums: IntArray, result: IntArray) {
        var suffixProduct = 1
        for (i in nums.lastIndex downTo 0) {
            result[i] *= suffixProduct
            suffixProduct *= nums[i]
        }
    }
}

/** Solution 2: Explicit prefix and suffix arrays - Time: O(n), Space: O(n) */
class ProductExceptSelf2 {
    fun productExceptSelf(nums: IntArray): IntArray {
        val n = nums.size
        val prefix = IntArray(n) { 1 }
        val suffix = IntArray(n) { 1 }

        for (i in 1 until n) prefix[i] = prefix[i - 1] * nums[i - 1]
        for (i in n - 2 downTo 0) suffix[i] = suffix[i + 1] * nums[i + 1]

        return IntArray(n) { prefix[it] * suffix[it] }
    }
}
