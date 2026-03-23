package com.leetcode.solutions.tree.problems.medium

import com.leetcode.solutions.common.TreeNode

/**
 * LeetCode 199: Binary Tree Right Side View
 * https://leetcode.com/problems/binary-tree-right-side-view/
 *
 * Difficulty: Medium
 * Companies: Amazon, Meta, Google, Microsoft
 *
 * Given the root of a binary tree, return the values of the nodes you can see
 * ordered from top to bottom when looking from the right side.
 *
 * Example: [1,2,3,null,5,null,4] → [1, 3, 4]
 */

/** Solution 1: BFS - Last Node of Each Level - Time: O(n), Space: O(n) */
class RightSideView1 {

    fun rightSideView(root: TreeNode?): List<Int> {
        if (root == null) return emptyList()

        val result = mutableListOf<Int>()
        val queue = ArrayDeque<TreeNode>()
        queue.addLast(root)

        while (queue.isNotEmpty()) {
            val rightmost = processLevel(queue)
            result.add(rightmost)
        }

        return result
    }

    private fun processLevel(queue: ArrayDeque<TreeNode>): Int {
        val size = queue.size
        var lastVal = 0

        repeat(size) { i ->
            val node = queue.removeFirst()
            lastVal = node.`val`
            node.left?.let { queue.addLast(it) }
            node.right?.let { queue.addLast(it) }
        }

        return lastVal
    }
}

/** Solution 2: DFS - Right Child First - Time: O(n), Space: O(h) */
class RightSideView2 {

    fun rightSideView(root: TreeNode?): List<Int> {
        val result = mutableListOf<Int>()
        dfs(root, 0, result)
        return result
    }

    private fun dfs(node: TreeNode?, depth: Int, result: MutableList<Int>) {
        if (node == null) return

        if (depth == result.size) {
            result.add(node.`val`)
        }

        dfs(node.right, depth + 1, result)
        dfs(node.left, depth + 1, result)
    }
}

/** Solution 3: BFS with Depth Tracking - Time: O(n), Space: O(n) */
class RightSideView3 {

    fun rightSideView(root: TreeNode?): List<Int> {
        if (root == null) return emptyList()

        val depthMap = mutableMapOf<Int, Int>()
        val queue = ArrayDeque<Pair<TreeNode, Int>>()
        queue.addLast(root to 0)

        while (queue.isNotEmpty()) {
            val (node, depth) = queue.removeFirst()
            depthMap[depth] = node.`val`
            node.left?.let { queue.addLast(it to depth + 1) }
            node.right?.let { queue.addLast(it to depth + 1) }
        }

        return depthMap.keys.sorted().map { depthMap[it]!! }
    }
}
