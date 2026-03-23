package com.leetcode.solutions.tree.problems.medium

import com.leetcode.solutions.common.TreeNode

/**
 * LeetCode 98: Validate Binary Search Tree
 * https://leetcode.com/problems/validate-binary-search-tree/
 * 
 * Difficulty: Medium
 * Companies: Google, Amazon, Microsoft, Meta, Apple
 */

/** Solution 1: Recursive with Range - Time: O(n), Space: O(h) */
class IsValidBST1 {
    fun isValidBST(root: TreeNode?): Boolean {
        return validate(root, null, null)
    }
    
    private fun validate(node: TreeNode?, min: Int?, max: Int?): Boolean {
        if (node == null) return true
        
        if (min != null && node.`val` <= min) return false
        if (max != null && node.`val` >= max) return false
        
        return validate(node.left, min, node.`val`) && 
               validate(node.right, node.`val`, max)
    }
}

/** Solution 2: Inorder Traversal Check - Time: O(n), Space: O(h) */
class IsValidBST2 {
    private var prev: Int? = null
    
    fun isValidBST(root: TreeNode?): Boolean {
        prev = null
        return inorder(root)
    }
    
    private fun inorder(node: TreeNode?): Boolean {
        if (node == null) return true
        
        if (!inorder(node.left)) return false
        
        if (prev != null && node.`val` <= prev!!) return false
        prev = node.`val`
        
        return inorder(node.right)
    }
}

/** Solution 3: Iterative Inorder - Time: O(n), Space: O(h) */
class IsValidBST3 {
    fun isValidBST(root: TreeNode?): Boolean {
        val stack = ArrayDeque<TreeNode>()
        var prev: Int? = null
        var current = root
        
        while (current != null || stack.isNotEmpty()) {
            while (current != null) {
                stack.addLast(current)
                current = current.left
            }
            
            current = stack.removeLast()
            
            if (prev != null && current.`val` <= prev) return false
            prev = current.`val`
            
            current = current.right
        }
        
        return true
    }
}

/** Solution 4: Morris Traversal - Time: O(n), Space: O(1) */
class IsValidBST4 {
    fun isValidBST(root: TreeNode?): Boolean {
        var current = root
        var prev: Int? = null
        
        while (current != null) {
            if (current.left == null) {
                if (prev != null && current.`val` <= prev) return false
                prev = current.`val`
                current = current.right
            } else {
                var predecessor = current.left
                while (predecessor?.right != null && predecessor.right != current) {
                    predecessor = predecessor.right
                }
                
                if (predecessor?.right == null) {
                    predecessor?.right = current
                    current = current.left
                } else {
                    predecessor.right = null
                    if (prev != null && current.`val` <= prev) return false
                    prev = current.`val`
                    current = current.right
                }
            }
        }
        
        return true
    }
}
