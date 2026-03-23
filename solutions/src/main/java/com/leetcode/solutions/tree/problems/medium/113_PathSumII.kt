package com.leetcode.solutions.tree.problems.medium

import com.leetcode.solutions.common.TreeNode

/**
 * LeetCode 113: Path Sum II
 * https://leetcode.com/problems/path-sum-ii/
 *
 * Difficulty: Medium
 * Companies: Amazon, Google, Microsoft
 *
 * Given the root of a binary tree and a target sum, return all root-to-leaf
 * paths where the sum of nodes equals targetSum.
 *
 * Example: root = [5,4,8,11,null,13,4,7,2,null,null,5,1], targetSum = 22
 * Output: [[5,4,11,2],[5,8,4,5]]
 */

/** Solution 1: DFS Backtracking - Time: O(n²), Space: O(n) */
class PathSum1 {

    fun pathSum(root: TreeNode?, targetSum: Int): List<List<Int>> {
        val result = mutableListOf<List<Int>>()
        dfs(root, targetSum, mutableListOf(), result)
        return result
    }

    private fun dfs(
        node: TreeNode?,
        remaining: Int,
        path: MutableList<Int>,
        result: MutableList<List<Int>>
    ) {
        if (node == null) return

        path.add(node.`val`)

        if (isLeaf(node) && remaining == node.`val`) {
            result.add(path.toList())
        } else {
            dfs(node.left, remaining - node.`val`, path, result)
            dfs(node.right, remaining - node.`val`, path, result)
        }

        path.removeLast()
    }

    private fun isLeaf(node: TreeNode) = node.left == null && node.right == null
}

/** Solution 2: Iterative with Stack - Time: O(n²), Space: O(n) */
class PathSum2 {

    fun pathSum(root: TreeNode?, targetSum: Int): List<List<Int>> {
        if (root == null) return emptyList()

        val result = mutableListOf<List<Int>>()
        val stack = ArrayDeque<Triple<TreeNode, Int, List<Int>>>()
        stack.addLast(Triple(root, targetSum, emptyList()))

        while (stack.isNotEmpty()) {
            val (node, remaining, path) = stack.removeLast()
            val currentPath = path + node.`val`

            if (isLeaf(node) && remaining == node.`val`) {
                result.add(currentPath)
            }

            node.right?.let { stack.addLast(Triple(it, remaining - node.`val`, currentPath)) }
            node.left?.let { stack.addLast(Triple(it, remaining - node.`val`, currentPath)) }
        }

        return result
    }

    private fun isLeaf(node: TreeNode) = node.left == null && node.right == null
}

/** Solution 3: DFS returning list - Time: O(n²), Space: O(n) */
class PathSum3 {

    fun pathSum(root: TreeNode?, targetSum: Int): List<List<Int>> {
        if (root == null) return emptyList()

        val remainingAfterRoot = targetSum - root.`val`

        if (isLeaf(root) && remainingAfterRoot == 0) {
            return listOf(listOf(root.`val`))
        }

        val leftPaths = pathSum(root.left, remainingAfterRoot)
        val rightPaths = pathSum(root.right, remainingAfterRoot)

        return (leftPaths + rightPaths).map { listOf(root.`val`) + it }
    }

    private fun isLeaf(node: TreeNode) = node.left == null && node.right == null
}
