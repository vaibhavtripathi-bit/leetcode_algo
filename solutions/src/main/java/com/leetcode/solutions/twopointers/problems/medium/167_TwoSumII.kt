package com.leetcode.solutions.twopointers.problems.medium

/**
 * 167. Two Sum II - Input Array Is Sorted (Medium Version with Variations)
 * https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/
 *
 * This file contains advanced variations and optimizations of the Two Sum II problem,
 * including handling duplicates and finding all pairs.
 *
 * Companies: Amazon, Adobe, Google, Microsoft, Facebook, Apple
 */

/**
 * Solution 1: Standard Two Pointers
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 */
class TwoSumIIStandard {
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
 * Solution 2: Find All Pairs (Extension)
 * Time Complexity: O(n)
 * Space Complexity: O(k) where k is the number of pairs
 */
class TwoSumIIAllPairs {
    fun findAllPairs(numbers: IntArray, target: Int): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        var left = 0
        var right = numbers.size - 1

        while (left < right) {
            val sum = numbers[left] + numbers[right]
            when {
                sum == target -> {
                    result.add(Pair(numbers[left], numbers[right]))
                    val leftVal = numbers[left]
                    val rightVal = numbers[right]
                    while (left < right && numbers[left] == leftVal) left++
                    while (left < right && numbers[right] == rightVal) right--
                }
                sum < target -> left++
                else -> right--
            }
        }
        return result
    }

    fun countPairs(numbers: IntArray, target: Int): Int {
        var count = 0
        var left = 0
        var right = numbers.size - 1

        while (left < right) {
            val sum = numbers[left] + numbers[right]
            when {
                sum == target -> {
                    if (numbers[left] == numbers[right]) {
                        val n = right - left + 1
                        count += n * (n - 1) / 2
                        break
                    } else {
                        var leftCount = 1
                        var rightCount = 1
                        while (left + leftCount < right && numbers[left + leftCount] == numbers[left]) {
                            leftCount++
                        }
                        while (right - rightCount > left && numbers[right - rightCount] == numbers[right]) {
                            rightCount++
                        }
                        count += leftCount * rightCount
                        left += leftCount
                        right -= rightCount
                    }
                }
                sum < target -> left++
                else -> right--
            }
        }
        return count
    }
}

/**
 * Solution 3: Two Sum II with Binary Search Enhancement
 * Time Complexity: O(n) average, but can skip more elements
 * Space Complexity: O(1)
 */
class TwoSumIIBinarySearchEnhanced {
    fun twoSum(numbers: IntArray, target: Int): IntArray {
        var left = 0
        var right = numbers.size - 1

        right = findUpperBound(numbers, target - numbers[left], right)

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

    private fun findUpperBound(arr: IntArray, target: Int, end: Int): Int {
        var left = 0
        var right = end

        while (left < right) {
            val mid = left + (right - left + 1) / 2
            if (arr[mid] <= target) {
                left = mid
            } else {
                right = mid - 1
            }
        }
        return left
    }
}

/**
 * Solution 4: Two Sum Closest (Variation)
 * Find two numbers whose sum is closest to target
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 */
class TwoSumClosest {
    fun twoSumClosest(numbers: IntArray, target: Int): IntArray {
        var left = 0
        var right = numbers.size - 1
        var closestSum = Int.MAX_VALUE
        var result = intArrayOf(-1, -1)

        while (left < right) {
            val sum = numbers[left] + numbers[right]

            if (kotlin.math.abs(sum - target) < kotlin.math.abs(closestSum - target)) {
                closestSum = sum
                result = intArrayOf(left + 1, right + 1)
            }

            when {
                sum == target -> return intArrayOf(left + 1, right + 1)
                sum < target -> left++
                else -> right--
            }
        }
        return result
    }

    fun twoSumLessThanTarget(numbers: IntArray, target: Int): Int {
        var left = 0
        var right = numbers.size - 1
        var maxSum = Int.MIN_VALUE

        while (left < right) {
            val sum = numbers[left] + numbers[right]
            if (sum < target) {
                maxSum = maxOf(maxSum, sum)
                left++
            } else {
                right--
            }
        }
        return if (maxSum == Int.MIN_VALUE) -1 else maxSum
    }
}

fun main() {
    val standard = TwoSumIIStandard()
    val allPairs = TwoSumIIAllPairs()
    val closest = TwoSumClosest()

    println("=== Standard Two Sum II ===")
    val result = standard.twoSum(intArrayOf(2, 7, 11, 15), 9)
    println("Result: ${result.toList()}")

    println("\n=== Find All Pairs ===")
    val pairs = allPairs.findAllPairs(intArrayOf(1, 2, 3, 4, 5, 6), 7)
    println("Pairs that sum to 7: $pairs")

    println("\n=== Count Pairs ===")
    val count = allPairs.countPairs(intArrayOf(1, 1, 2, 2, 3, 3, 4, 4), 5)
    println("Count of pairs summing to 5: $count")

    println("\n=== Closest Sum ===")
    val closestResult = closest.twoSumClosest(intArrayOf(1, 3, 4, 7, 10), 15)
    println("Closest to 15: indices ${closestResult.toList()}")
}
