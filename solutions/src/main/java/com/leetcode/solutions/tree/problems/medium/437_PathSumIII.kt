package com.leetcode.solutions.tree.problems.medium

import com.leetcode.solutions.common.TreeNode

/**
 * LeetCode 437: Path Sum III
 * https://leetcode.com/problems/path-sum-iii/
 *
 * Difficulty: Medium
 * Companies: Amazon, Google, Meta
 *
 * Given root and targetSum, return the number of paths that sum to targetSum.
 * The path does not need to start or end at root/leaf, but must go downward.
 *
 * Example: root = [10,5,-3,3,2,null,11,3,-2,null,1], targetSum = 8 → 3
 */

/** Solution 1: Prefix Sum with HashMap - Time: O(n), Space: O(n) */
class PathSumIII1 {

    fun pathSum(root: TreeNode?, targetSum: Int): Int {
        val prefixSums = HashMap<Long, Int>()
        prefixSums[0L] = 1
        return dfs(root, 0L, targetSum.toLong(), prefixSums)
    }

    private fun dfs(
        node: TreeNode?,
        currentSum: Long,
        target: Long,
        prefixSums: HashMap<Long, Int>
    ): Int {
        if (node == null) return 0

        val newSum = currentSum + node.`val`
        val pathsEndingHere = prefixSums.getOrDefault(newSum - target, 0)

        prefixSums[newSum] = prefixSums.getOrDefault(newSum, 0) + 1

        val result = pathsEndingHere +
                dfs(node.left, newSum, target, prefixSums) +
                dfs(node.right, newSum, target, prefixSums)

        prefixSums[newSum] = prefixSums[newSum]!! - 1

        return result
    }
}

/** Solution 2: Brute Force DFS from Every Node - Time: O(n²), Space: O(n) */
class PathSumIII2 {

    fun pathSum(root: TreeNode?, targetSum: Int): Int {
        if (root == null) return 0

        return countPathsFrom(root, targetSum.toLong()) +
                pathSum(root.left, targetSum) +
                pathSum(root.right, targetSum)
    }

    private fun countPathsFrom(node: TreeNode?, remaining: Long): Int {
        if (node == null) return 0

        val found = if (remaining == node.`val`.toLong()) 1 else 0

        return found +
                countPathsFrom(node.left, remaining - node.`val`) +
                countPathsFrom(node.right, remaining - node.`val`)
    }
}
