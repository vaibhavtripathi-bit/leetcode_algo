package com.leetcode.solutions.common

/**
 * Standard LeetCode TreeNode definition for binary tree.
 */
class TreeNode(var `val`: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
    
    constructor(`val`: Int, left: TreeNode?, right: TreeNode?) : this(`val`) {
        this.left = left
        this.right = right
    }
}

/**
 * Utility functions for TreeNode - useful for testing solutions.
 */
object TreeNodeUtil {
    
    fun create(vararg values: Int?): TreeNode? {
        if (values.isEmpty() || values[0] == null) return null
        
        val root = TreeNode(values[0]!!)
        val queue = ArrayDeque<TreeNode>()
        queue.addLast(root)
        
        var i = 1
        while (i < values.size && queue.isNotEmpty()) {
            val node = queue.removeFirst()
            
            if (i < values.size && values[i] != null) {
                node.left = TreeNode(values[i]!!)
                queue.addLast(node.left!!)
            }
            i++
            
            if (i < values.size && values[i] != null) {
                node.right = TreeNode(values[i]!!)
                queue.addLast(node.right!!)
            }
            i++
        }
        
        return root
    }
    
    fun toList(root: TreeNode?): List<Int?> {
        if (root == null) return emptyList()
        
        val result = mutableListOf<Int?>()
        val queue = ArrayDeque<TreeNode?>()
        queue.addLast(root)
        
        while (queue.isNotEmpty()) {
            val node = queue.removeFirst()
            if (node != null) {
                result.add(node.`val`)
                queue.addLast(node.left)
                queue.addLast(node.right)
            } else {
                result.add(null)
            }
        }
        
        while (result.isNotEmpty() && result.last() == null) {
            result.removeAt(result.size - 1)
        }
        
        return result
    }
}
