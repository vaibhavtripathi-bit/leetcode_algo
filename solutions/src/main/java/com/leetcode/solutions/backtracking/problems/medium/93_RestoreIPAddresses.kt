package com.leetcode.solutions.backtracking.problems.medium

/**
 * LeetCode 93: Restore IP Addresses
 * https://leetcode.com/problems/restore-ip-addresses/
 *
 * Difficulty: Medium
 * Companies: Amazon, Google
 *
 * Return all valid IP addresses (4 parts, each 0-255, no leading zeros).
 *
 * Example: s = "25525511135" → ["255.255.11.135","255.255.111.35"]
 */

/** Solution 1: Backtracking - Time: O(3^4) = O(1), Space: O(1) */
class RestoreIPAddresses1 {
    fun restoreIpAddresses(s: String): List<String> {
        val result = mutableListOf<String>()
        backtrack(s, 0, mutableListOf(), result)
        return result
    }

    private fun backtrack(
        s: String, start: Int,
        parts: MutableList<String>, result: MutableList<String>
    ) {
        if (parts.size == 4 && start == s.length) {
            result.add(parts.joinToString("."))
            return
        }

        if (parts.size == 4 || start == s.length) return

        for (len in 1..3) {
            if (start + len > s.length) break

            val segment = s.substring(start, start + len)
            if (!isValidSegment(segment)) continue

            parts.add(segment)
            backtrack(s, start + len, parts, result)
            parts.removeLast()
        }
    }

    private fun isValidSegment(segment: String): Boolean {
        if (segment.length > 1 && segment[0] == '0') return false   // no leading zeros
        return segment.toInt() <= 255
    }
}

/** Solution 2: Iterative with 3 loops - Time: O(1), Space: O(1) */
class RestoreIPAddresses2 {
    fun restoreIpAddresses(s: String): List<String> {
        val result = mutableListOf<String>()

        for (i in 1..minOf(3, s.length - 3)) {
            for (j in i + 1..minOf(i + 3, s.length - 2)) {
                for (k in j + 1..minOf(j + 3, s.length - 1)) {
                    val p1 = s.substring(0, i)
                    val p2 = s.substring(i, j)
                    val p3 = s.substring(j, k)
                    val p4 = s.substring(k)

                    if (isValid(p1) && isValid(p2) && isValid(p3) && isValid(p4)) {
                        result.add("$p1.$p2.$p3.$p4")
                    }
                }
            }
        }

        return result
    }

    private fun isValid(s: String): Boolean {
        if (s.isEmpty() || s.length > 3) return false
        if (s.length > 1 && s[0] == '0') return false
        return s.toInt() <= 255
    }
}
