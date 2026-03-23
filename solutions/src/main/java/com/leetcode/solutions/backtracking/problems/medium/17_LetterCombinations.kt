package com.leetcode.solutions.backtracking.problems.medium

/**
 * LeetCode 17: Letter Combinations of a Phone Number
 * https://leetcode.com/problems/letter-combinations-of-a-phone-number/
 *
 * Difficulty: Medium
 * Companies: Google, Amazon, Microsoft, Meta, Apple
 *
 * Return all possible letter combinations a phone number digits could represent.
 *
 * Example: digits = "23" → ["ad","ae","af","bd","be","bf","cd","ce","cf"]
 */

/** Solution 1: Backtracking - Time: O(4^n * n), Space: O(n) */
class LetterCombinations1 {
    private val phoneMap = mapOf(
        '2' to "abc", '3' to "def", '4' to "ghi", '5' to "jkl",
        '6' to "mno", '7' to "pqrs", '8' to "tuv", '9' to "wxyz"
    )

    fun letterCombinations(digits: String): List<String> {
        if (digits.isEmpty()) return emptyList()

        val result = mutableListOf<String>()
        backtrack(digits, 0, StringBuilder(), result)
        return result
    }

    private fun backtrack(
        digits: String, index: Int,
        current: StringBuilder, result: MutableList<String>
    ) {
        if (index == digits.length) {
            result.add(current.toString())
            return
        }

        val letters = phoneMap[digits[index]] ?: return
        for (letter in letters) {
            current.append(letter)
            backtrack(digits, index + 1, current, result)
            current.deleteCharAt(current.lastIndex)
        }
    }
}

/** Solution 2: BFS/Queue approach - Time: O(4^n), Space: O(4^n) */
class LetterCombinations2 {
    private val phoneMap = mapOf(
        '2' to "abc", '3' to "def", '4' to "ghi", '5' to "jkl",
        '6' to "mno", '7' to "pqrs", '8' to "tuv", '9' to "wxyz"
    )

    fun letterCombinations(digits: String): List<String> {
        if (digits.isEmpty()) return emptyList()

        var combinations = listOf("")

        for (digit in digits) {
            val letters = phoneMap[digit] ?: continue
            combinations = combinations.flatMap { prev ->
                letters.map { letter -> prev + letter }
            }
        }

        return combinations
    }
}
