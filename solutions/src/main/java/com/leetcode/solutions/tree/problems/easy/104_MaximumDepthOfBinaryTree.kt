package com.leetcode.solutions.tree.problems.easy

import com.leetcode.solutions.common.TreeNode
import kotlin.math.max

/**
 * LeetCode 104: Maximum Depth of Binary Tree
 * https://leetcode.com/problems/maximum-depth-of-binary-tree/
 * 
 * Difficulty: Easy
 * Companies: Google, Amazon, Microsoft, Meta
 */

/** Solution 1: Recursive DFS - Time: O(n), Space: O(h) */
class MaxDepth1 {
    fun maxDepth(root: TreeNode?): Int {
        if (root == null) return 0
        return 1 + max(maxDepth(root.left), maxDepth(root.right))
    }
}

/** Solution 2: Iterative BFS - Time: O(n), Space: O(n) */
class MaxDepth2 {
    fun maxDepth(root: TreeNode?): Int {
        if (root == null) return 0
        
        val queue = ArrayDeque<TreeNode>()
        queue.addLast(root)
        var depth = 0
        
        while (queue.isNotEmpty()) {
            depth++
            repeat(queue.size) {
                val node = queue.removeFirst()
                node.left?.let { queue.addLast(it) }
                node.right?.let { queue.addLast(it) }
            }
        }
        
        return depth
    }
}

/** Solution 3: Iterative DFS with Stack - Time: O(n), Space: O(n) */
class MaxDepth3 {
    fun maxDepth(root: TreeNode?): Int {
        if (root == null) return 0
        
        val stack = ArrayDeque<Pair<TreeNode, Int>>()
        stack.addLast(root to 1)
        var maxDepth = 0
        
        while (stack.isNotEmpty()) {
            val (node, depth) = stack.removeLast()
            maxDepth = max(maxDepth, depth)
            
            node.left?.let { stack.addLast(it to depth + 1) }
            node.right?.let { stack.addLast(it to depth + 1) }
        }
        
        return maxDepth
    }
}
