package com.leetcode.solutions.tree.problems.easy

import com.leetcode.solutions.common.TreeNode

/**
 * LeetCode 101: Symmetric Tree
 * https://leetcode.com/problems/symmetric-tree/
 * 
 * Difficulty: Easy
 * Companies: Google, Amazon, Microsoft, Meta
 */

/** Solution 1: Recursive - Time: O(n), Space: O(h) */
class IsSymmetric1 {
    fun isSymmetric(root: TreeNode?): Boolean {
        return isMirror(root?.left, root?.right)
    }
    
    private fun isMirror(left: TreeNode?, right: TreeNode?): Boolean {
        if (left == null && right == null) return true
        if (left == null || right == null) return false
        if (left.`val` != right.`val`) return false
        
        return isMirror(left.left, right.right) && isMirror(left.right, right.left)
    }
}

/** Solution 2: Iterative with Queue - Time: O(n), Space: O(n) */
class IsSymmetric2 {
    fun isSymmetric(root: TreeNode?): Boolean {
        if (root == null) return true
        
        val queue = ArrayDeque<TreeNode?>()
        queue.addLast(root.left)
        queue.addLast(root.right)
        
        while (queue.isNotEmpty()) {
            val left = queue.removeFirst()
            val right = queue.removeFirst()
            
            if (left == null && right == null) continue
            if (left == null || right == null) return false
            if (left.`val` != right.`val`) return false
            
            queue.addLast(left.left)
            queue.addLast(right.right)
            queue.addLast(left.right)
            queue.addLast(right.left)
        }
        
        return true
    }
}

/** Solution 3: Iterative with Stack - Time: O(n), Space: O(n) */
class IsSymmetric3 {
    fun isSymmetric(root: TreeNode?): Boolean {
        if (root == null) return true
        
        val stack = ArrayDeque<TreeNode?>()
        stack.addLast(root.left)
        stack.addLast(root.right)
        
        while (stack.isNotEmpty()) {
            val right = stack.removeLast()
            val left = stack.removeLast()
            
            if (left == null && right == null) continue
            if (left == null || right == null) return false
            if (left.`val` != right.`val`) return false
            
            stack.addLast(left.left)
            stack.addLast(right.right)
            stack.addLast(left.right)
            stack.addLast(right.left)
        }
        
        return true
    }
}
