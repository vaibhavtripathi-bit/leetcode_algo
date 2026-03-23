package com.leetcode.solutions.tree.problems.easy

import com.leetcode.solutions.common.TreeNode

/**
 * LeetCode 572: Subtree of Another Tree
 * https://leetcode.com/problems/subtree-of-another-tree/
 * 
 * Difficulty: Easy
 * Companies: Google, Amazon, Microsoft, Meta
 */

/** Solution 1: Recursive - Time: O(m*n), Space: O(h) */
class IsSubtree1 {
    fun isSubtree(root: TreeNode?, subRoot: TreeNode?): Boolean {
        if (subRoot == null) return true
        if (root == null) return false
        
        if (isSameTree(root, subRoot)) return true
        
        return isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot)
    }
    
    private fun isSameTree(p: TreeNode?, q: TreeNode?): Boolean {
        if (p == null && q == null) return true
        if (p == null || q == null) return false
        if (p.`val` != q.`val`) return false
        
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right)
    }
}

/** Solution 2: Serialize and String Match - Time: O(m+n), Space: O(m+n) */
class IsSubtree2 {
    fun isSubtree(root: TreeNode?, subRoot: TreeNode?): Boolean {
        val rootStr = serialize(root)
        val subStr = serialize(subRoot)
        return rootStr.contains(subStr)
    }
    
    private fun serialize(node: TreeNode?): String {
        if (node == null) return "#"
        return ",${node.`val`},${serialize(node.left)},${serialize(node.right)}"
    }
}

/** Solution 3: Iterative with Queue - Time: O(m*n), Space: O(m+n) */
class IsSubtree3 {
    fun isSubtree(root: TreeNode?, subRoot: TreeNode?): Boolean {
        if (subRoot == null) return true
        if (root == null) return false
        
        val queue = ArrayDeque<TreeNode>()
        queue.addLast(root)
        
        while (queue.isNotEmpty()) {
            val node = queue.removeFirst()
            
            if (isSameTree(node, subRoot)) return true
            
            node.left?.let { queue.addLast(it) }
            node.right?.let { queue.addLast(it) }
        }
        
        return false
    }
    
    private fun isSameTree(p: TreeNode?, q: TreeNode?): Boolean {
        if (p == null && q == null) return true
        if (p == null || q == null) return false
        if (p.`val` != q.`val`) return false
        
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right)
    }
}
