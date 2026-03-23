package com.leetcode.solutions.tree.traversals

import com.leetcode.solutions.common.TreeNode

/**
 * DFS Tree Traversal Patterns - Inorder, Preorder, Postorder
 */
object DFSTraversals {
    
    // ============== Inorder (Left -> Root -> Right) ==============
    
    fun inorderRecursive(root: TreeNode?): List<Int> {
        val result = mutableListOf<Int>()
        inorderHelper(root, result)
        return result
    }
    
    private fun inorderHelper(node: TreeNode?, result: MutableList<Int>) {
        if (node == null) return
        inorderHelper(node.left, result)
        result.add(node.`val`)
        inorderHelper(node.right, result)
    }
    
    fun inorderIterative(root: TreeNode?): List<Int> {
        val result = mutableListOf<Int>()
        val stack = ArrayDeque<TreeNode>()
        var current = root
        
        while (current != null || stack.isNotEmpty()) {
            while (current != null) {
                stack.addLast(current)
                current = current.left
            }
            current = stack.removeLast()
            result.add(current.`val`)
            current = current.right
        }
        
        return result
    }
    
    // ============== Preorder (Root -> Left -> Right) ==============
    
    fun preorderRecursive(root: TreeNode?): List<Int> {
        val result = mutableListOf<Int>()
        preorderHelper(root, result)
        return result
    }
    
    private fun preorderHelper(node: TreeNode?, result: MutableList<Int>) {
        if (node == null) return
        result.add(node.`val`)
        preorderHelper(node.left, result)
        preorderHelper(node.right, result)
    }
    
    fun preorderIterative(root: TreeNode?): List<Int> {
        if (root == null) return emptyList()
        
        val result = mutableListOf<Int>()
        val stack = ArrayDeque<TreeNode>()
        stack.addLast(root)
        
        while (stack.isNotEmpty()) {
            val node = stack.removeLast()
            result.add(node.`val`)
            
            node.right?.let { stack.addLast(it) }
            node.left?.let { stack.addLast(it) }
        }
        
        return result
    }
    
    // ============== Postorder (Left -> Right -> Root) ==============
    
    fun postorderRecursive(root: TreeNode?): List<Int> {
        val result = mutableListOf<Int>()
        postorderHelper(root, result)
        return result
    }
    
    private fun postorderHelper(node: TreeNode?, result: MutableList<Int>) {
        if (node == null) return
        postorderHelper(node.left, result)
        postorderHelper(node.right, result)
        result.add(node.`val`)
    }
    
    fun postorderIterative(root: TreeNode?): List<Int> {
        if (root == null) return emptyList()
        
        val result = mutableListOf<Int>()
        val stack = ArrayDeque<TreeNode>()
        stack.addLast(root)
        
        while (stack.isNotEmpty()) {
            val node = stack.removeLast()
            result.add(0, node.`val`)
            
            node.left?.let { stack.addLast(it) }
            node.right?.let { stack.addLast(it) }
        }
        
        return result
    }
}
