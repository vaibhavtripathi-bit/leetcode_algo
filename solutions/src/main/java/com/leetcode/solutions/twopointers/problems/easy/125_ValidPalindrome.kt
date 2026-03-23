package com.leetcode.solutions.twopointers.problems.easy

/**
 * 125. Valid Palindrome
 * https://leetcode.com/problems/valid-palindrome/
 *
 * A phrase is a palindrome if, after converting all uppercase letters into lowercase letters
 * and removing all non-alphanumeric characters, it reads the same forward and backward.
 *
 * Companies: Facebook, Microsoft, Apple, Amazon, Google
 */

/**
 * Solution 1: Two Pointers (Optimal)
 * Time Complexity: O(n) - single pass through the string
 * Space Complexity: O(1) - only using two pointers
 */
class ValidPalindromeTwoPointers {
    fun isPalindrome(s: String): Boolean {
        var left = 0
        var right = s.length - 1

        while (left < right) {
            while (left < right && !s[left].isLetterOrDigit()) {
                left++
            }
            while (left < right && !s[right].isLetterOrDigit()) {
                right--
            }
            if (s[left].lowercaseChar() != s[right].lowercaseChar()) {
                return false
            }
            left++
            right--
        }
        return true
    }
}

/**
 * Solution 2: Clean String and Compare
 * Time Complexity: O(n) - filtering and comparing
 * Space Complexity: O(n) - storing cleaned string
 */
class ValidPalindromeCleanString {
    fun isPalindrome(s: String): Boolean {
        val cleaned = s.filter { it.isLetterOrDigit() }.lowercase()
        return cleaned == cleaned.reversed()
    }

    fun isPalindromeManual(s: String): Boolean {
        val cleaned = StringBuilder()
        for (c in s) {
            if (c.isLetterOrDigit()) {
                cleaned.append(c.lowercaseChar())
            }
        }
        val str = cleaned.toString()
        return str == str.reversed()
    }
}

/**
 * Solution 3: Recursive Approach
 * Time Complexity: O(n) - processing each character once
 * Space Complexity: O(n) - recursive call stack
 */
class ValidPalindromeRecursive {
    fun isPalindrome(s: String): Boolean {
        val cleaned = s.filter { it.isLetterOrDigit() }.lowercase()
        return isPalindromeHelper(cleaned, 0, cleaned.length - 1)
    }

    private fun isPalindromeHelper(s: String, left: Int, right: Int): Boolean {
        if (left >= right) return true
        if (s[left] != s[right]) return false
        return isPalindromeHelper(s, left + 1, right - 1)
    }
}

/**
 * Solution 4: Character Array with Two Pointers
 * Time Complexity: O(n)
 * Space Complexity: O(n) - for character array
 */
class ValidPalindromeCharArray {
    fun isPalindrome(s: String): Boolean {
        val chars = s.filter { it.isLetterOrDigit() }
            .lowercase()
            .toCharArray()

        var left = 0
        var right = chars.size - 1

        while (left < right) {
            if (chars[left++] != chars[right--]) {
                return false
            }
        }
        return true
    }
}

fun main() {
    val solutions = listOf(
        ValidPalindromeTwoPointers(),
        ValidPalindromeCleanString(),
        ValidPalindromeRecursive(),
        ValidPalindromeCharArray()
    )

    val testCases = listOf(
        "A man, a plan, a canal: Panama" to true,
        "race a car" to false,
        " " to true
    )

    testCases.forEach { (input, expected) ->
        println("Input: \"$input\"")
        println("Expected: $expected")
        println("Results: ${solutions[0].isPalindrome(input)}")
        println()
    }
}
