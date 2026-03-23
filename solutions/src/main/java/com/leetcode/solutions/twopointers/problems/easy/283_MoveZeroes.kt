package com.leetcode.solutions.twopointers.problems.easy

/**
 * 283. Move Zeroes
 * https://leetcode.com/problems/move-zeroes/
 *
 * Given an integer array nums, move all 0's to the end of it while maintaining
 * the relative order of the non-zero elements.
 *
 * Companies: Facebook, Amazon, Apple, Microsoft, Bloomberg
 */

/**
 * Solution 1: Two Pointers - Swap Approach (Optimal)
 * Time Complexity: O(n) - single pass
 * Space Complexity: O(1) - in-place modification
 */
class MoveZeroesTwoPointers {
    fun moveZeroes(nums: IntArray) {
        var insertPos = 0

        for (i in nums.indices) {
            if (nums[i] != 0) {
                if (i != insertPos) {
                    nums[insertPos] = nums[i].also { nums[i] = nums[insertPos] }
                }
                insertPos++
            }
        }
    }
}

/**
 * Solution 2: Two Pass - Fill Non-Zeros First
 * Time Complexity: O(n) - two passes through array
 * Space Complexity: O(1) - in-place modification
 */
class MoveZeroesTwoPass {
    fun moveZeroes(nums: IntArray) {
        var insertPos = 0

        for (num in nums) {
            if (num != 0) {
                nums[insertPos++] = num
            }
        }

        while (insertPos < nums.size) {
            nums[insertPos++] = 0
        }
    }
}

/**
 * Solution 3: Snowball Approach
 * Time Complexity: O(n) - single pass
 * Space Complexity: O(1) - in-place
 *
 * The "snowball" is the count of zeros that accumulates as we traverse.
 * When we find a non-zero, we swap it with the position before the snowball.
 */
class MoveZeroesSnowball {
    fun moveZeroes(nums: IntArray) {
        var snowballSize = 0

        for (i in nums.indices) {
            if (nums[i] == 0) {
                snowballSize++
            } else if (snowballSize > 0) {
                nums[i - snowballSize] = nums[i]
                nums[i] = 0
            }
        }
    }
}

/**
 * Solution 4: Using partition logic (similar to quicksort partition)
 * Time Complexity: O(n) - single pass
 * Space Complexity: O(1) - in-place
 */
class MoveZeroesPartition {
    fun moveZeroes(nums: IntArray) {
        var boundary = 0

        for (current in nums.indices) {
            if (nums[current] != 0) {
                val temp = nums[boundary]
                nums[boundary] = nums[current]
                nums[current] = temp
                boundary++
            }
        }
    }

    fun moveZeroesKotlinStyle(nums: IntArray) {
        var nonZeroIndex = 0
        nums.forEachIndexed { index, value ->
            if (value != 0) {
                nums[nonZeroIndex] = nums[index].also { nums[index] = nums[nonZeroIndex] }
                nonZeroIndex++
            }
        }
    }
}

fun main() {
    val testCases = listOf(
        intArrayOf(0, 1, 0, 3, 12),
        intArrayOf(0),
        intArrayOf(1, 0, 0, 0, 2, 3),
        intArrayOf(1, 2, 3, 4, 5)
    )

    testCases.forEach { input ->
        val original = input.toList()
        val arr1 = input.copyOf()
        val arr2 = input.copyOf()
        val arr3 = input.copyOf()

        MoveZeroesTwoPointers().moveZeroes(arr1)
        MoveZeroesTwoPass().moveZeroes(arr2)
        MoveZeroesSnowball().moveZeroes(arr3)

        println("Original: $original")
        println("Two Pointers: ${arr1.toList()}")
        println("Two Pass: ${arr2.toList()}")
        println("Snowball: ${arr3.toList()}")
        println()
    }
}
