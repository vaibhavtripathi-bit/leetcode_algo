package com.leetcode.solutions.tree.problems.medium

import com.leetcode.solutions.common.TreeNode

/**
 * LeetCode 230: Kth Smallest Element in a BST
 * https://leetcode.com/problems/kth-smallest-element-in-a-bst/
 *
 * Difficulty: Medium
 * Companies: Google, Amazon, Microsoft, Meta
 *
 * Given the root of a BST and an integer k, return the kth smallest value.
 * BST inorder traversal gives sorted order — kth element is the answer.
 *
 * Example: root = [3,1,4,null,2], k = 1 → 1
 */

/** Solution 1: Inorder Recursive - Time: O(n), Space: O(h) */
class KthSmallest1 {

    private var count = 0
    private var result = 0

    fun kthSmallest(root: TreeNode?, k: Int): Int {
        count = k
        inorder(root)
        return result
    }

    private fun inorder(node: TreeNode?) {
        if (node == null || count == 0) return

        inorder(node.left)

        count--
        if (count == 0) {
            result = node.`val`
            return
        }

        inorder(node.right)
    }
}

/** Solution 2: Inorder Iterative - Time: O(h + k), Space: O(h) */
class KthSmallest2 {

    fun kthSmallest(root: TreeNode?, k: Int): Int {
        val stack = ArrayDeque<TreeNode>()
        var current = root
        var remaining = k

        while (current != null || stack.isNotEmpty()) {
            while (current != null) {
                stack.addLast(current)
                current = current.left
            }

            current = stack.removeLast()
            remaining--

            if (remaining == 0) return current.`val`

            current = current.right
        }

        return -1
    }
}

/** Solution 3: Collect Inorder then Index - Time: O(n), Space: O(n) */
class KthSmallest3 {

    fun kthSmallest(root: TreeNode?, k: Int): Int {
        val sorted = collectInorder(root)
        return sorted[k - 1]
    }

    private fun collectInorder(node: TreeNode?): List<Int> {
        if (node == null) return emptyList()
        return collectInorder(node.left) + listOf(node.`val`) + collectInorder(node.right)
    }
}
