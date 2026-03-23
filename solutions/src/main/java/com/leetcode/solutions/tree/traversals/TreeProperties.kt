package com.leetcode.solutions.tree.traversals

import com.leetcode.solutions.common.TreeNode
import kotlin.math.abs
import kotlin.math.max

/**
 * Common Tree Property Calculations
 */
object TreeProperties {
    
    fun height(root: TreeNode?): Int {
        if (root == null) return 0
        return 1 + max(height(root.left), height(root.right))
    }
    
    fun depth(root: TreeNode?, target: Int, currentDepth: Int = 0): Int {
        if (root == null) return -1
        if (root.`val` == target) return currentDepth
        
        val leftResult = depth(root.left, target, currentDepth + 1)
        if (leftResult != -1) return leftResult
        
        return depth(root.right, target, currentDepth + 1)
    }
    
    fun countNodes(root: TreeNode?): Int {
        if (root == null) return 0
        return 1 + countNodes(root.left) + countNodes(root.right)
    }
    
    fun countLeaves(root: TreeNode?): Int {
        if (root == null) return 0
        if (root.left == null && root.right == null) return 1
        return countLeaves(root.left) + countLeaves(root.right)
    }
    
    fun isBalanced(root: TreeNode?): Boolean {
        return checkBalance(root) != -1
    }
    
    private fun checkBalance(node: TreeNode?): Int {
        if (node == null) return 0
        
        val left = checkBalance(node.left)
        if (left == -1) return -1
        
        val right = checkBalance(node.right)
        if (right == -1) return -1
        
        if (abs(left - right) > 1) return -1
        
        return 1 + max(left, right)
    }
    
    fun isSameTree(p: TreeNode?, q: TreeNode?): Boolean {
        if (p == null && q == null) return true
        if (p == null || q == null) return false
        if (p.`val` != q.`val`) return false
        
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right)
    }
    
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
