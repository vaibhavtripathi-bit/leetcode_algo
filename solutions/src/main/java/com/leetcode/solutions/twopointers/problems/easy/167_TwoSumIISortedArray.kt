package com.leetcode.solutions.twopointers.problems.easy

/**
 * 167. Two Sum II - Input Array Is Sorted
 * https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/
 *
 * Given a 1-indexed array of integers that is already sorted in non-decreasing order,
 * find two numbers such that they add up to a specific target number.
 *
 * Companies: Amazon, Adobe, Google, Microsoft, Facebook
 */

/**
 * Solution 1: Two Pointers (Optimal)
 * Time Complexity: O(n) - single pass with two pointers
 * Space Complexity: O(1) - constant extra space
 */
class TwoSumIITwoPointers {
    fun twoSum(numbers: IntArray, target: Int): IntArray {
        var left = 0
        var right = numbers.size - 1

        while (left < right) {
            val sum = numbers[left] + numbers[right]
            when {
                sum == target -> return intArrayOf(left + 1, right + 1)
                sum < target -> left++
                else -> right--
            }
        }
        return intArrayOf(-1, -1)
    }
}

/**
 * Solution 2: Binary Search
 * Time Complexity: O(n log n) - binary search for each element
 * Space Complexity: O(1) - constant extra space
 */
class TwoSumIIBinarySearch {
    fun twoSum(numbers: IntArray, target: Int): IntArray {
        for (i in numbers.indices) {
            val complement = target - numbers[i]
            val index = binarySearch(numbers, complement, i + 1)
            if (index != -1) {
                return intArrayOf(i + 1, index + 1)
            }
        }
        return intArrayOf(-1, -1)
    }

    private fun binarySearch(arr: IntArray, target: Int, start: Int): Int {
        var left = start
        var right = arr.size - 1

        while (left <= right) {
            val mid = left + (right - left) / 2
            when {
                arr[mid] == target -> return mid
                arr[mid] < target -> left = mid + 1
                else -> right = mid - 1
            }
        }
        return -1
    }
}

/**
 * Solution 3: HashMap Approach (Not optimal for sorted array but works)
 * Time Complexity: O(n) - single pass
 * Space Complexity: O(n) - storing elements in map
 */
class TwoSumIIHashMap {
    fun twoSum(numbers: IntArray, target: Int): IntArray {
        val map = mutableMapOf<Int, Int>()

        for ((index, num) in numbers.withIndex()) {
            val complement = target - num
            if (map.containsKey(complement)) {
                return intArrayOf(map[complement]!! + 1, index + 1)
            }
            map[num] = index
        }
        return intArrayOf(-1, -1)
    }
}

/**
 * Solution 4: Two Pointers with Early Exit Optimization
 * Time Complexity: O(n) - but with potential early termination
 * Space Complexity: O(1) - constant extra space
 */
class TwoSumIIOptimized {
    fun twoSum(numbers: IntArray, target: Int): IntArray {
        var left = 0
        var right = numbers.size - 1

        while (left < right) {
            if (numbers[left] + numbers[left + 1] > target) {
                return intArrayOf(-1, -1)
            }
            if (numbers[right] + numbers[right - 1] < target) {
                return intArrayOf(-1, -1)
            }

            val sum = numbers[left] + numbers[right]
            when {
                sum == target -> return intArrayOf(left + 1, right + 1)
                sum < target -> left++
                else -> right--
            }
        }
        return intArrayOf(-1, -1)
    }
}

fun main() {
    val solution = TwoSumIITwoPointers()

    val testCases = listOf(
        Triple(intArrayOf(2, 7, 11, 15), 9, intArrayOf(1, 2)),
        Triple(intArrayOf(2, 3, 4), 6, intArrayOf(1, 3)),
        Triple(intArrayOf(-1, 0), -1, intArrayOf(1, 2))
    )

    testCases.forEach { (numbers, target, expected) ->
        val result = solution.twoSum(numbers, target)
        println("Numbers: ${numbers.toList()}, Target: $target")
        println("Expected: ${expected.toList()}, Got: ${result.toList()}")
        println()
    }
}
