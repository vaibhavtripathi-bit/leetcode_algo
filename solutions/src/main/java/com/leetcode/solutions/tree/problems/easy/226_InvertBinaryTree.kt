package com.leetcode.solutions.tree.problems.easy

import com.leetcode.solutions.common.TreeNode

/**
 * LeetCode 226: Invert Binary Tree
 * https://leetcode.com/problems/invert-binary-tree/
 * 
 * Difficulty: Easy
 * Companies: Google, Amazon, Microsoft, Meta
 */

/** Solution 1: Recursive - Time: O(n), Space: O(h) */
class InvertTree1 {
    fun invertTree(root: TreeNode?): TreeNode? {
        if (root == null) return null
        
        val left = invertTree(root.left)
        val right = invertTree(root.right)
        
        root.left = right
        root.right = left
        
        return root
    }
}

/** Solution 2: Iterative BFS - Time: O(n), Space: O(n) */
class InvertTree2 {
    fun invertTree(root: TreeNode?): TreeNode? {
        if (root == null) return null
        
        val queue = ArrayDeque<TreeNode>()
        queue.addLast(root)
        
        while (queue.isNotEmpty()) {
            val node = queue.removeFirst()
            swap(node)
            
            node.left?.let { queue.addLast(it) }
            node.right?.let { queue.addLast(it) }
        }
        
        return root
    }
    
    private fun swap(node: TreeNode) {
        val temp = node.left
        node.left = node.right
        node.right = temp
    }
}

/** Solution 3: Iterative DFS - Time: O(n), Space: O(n) */
class InvertTree3 {
    fun invertTree(root: TreeNode?): TreeNode? {
        if (root == null) return null
        
        val stack = ArrayDeque<TreeNode>()
        stack.addLast(root)
        
        while (stack.isNotEmpty()) {
            val node = stack.removeLast()
            
            val temp = node.left
            node.left = node.right
            node.right = temp
            
            node.left?.let { stack.addLast(it) }
            node.right?.let { stack.addLast(it) }
        }
        
        return root
    }
}
