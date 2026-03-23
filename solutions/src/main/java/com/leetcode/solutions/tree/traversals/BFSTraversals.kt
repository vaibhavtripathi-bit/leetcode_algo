package com.leetcode.solutions.tree.traversals

import com.leetcode.solutions.common.TreeNode

/**
 * BFS Tree Traversal Patterns - Level Order
 */
object BFSTraversals {
    
    fun levelOrder(root: TreeNode?): List<List<Int>> {
        if (root == null) return emptyList()
        
        val result = mutableListOf<List<Int>>()
        val queue = ArrayDeque<TreeNode>()
        queue.addLast(root)
        
        while (queue.isNotEmpty()) {
            val levelSize = queue.size
            val level = mutableListOf<Int>()
            
            repeat(levelSize) {
                val node = queue.removeFirst()
                level.add(node.`val`)
                node.left?.let { queue.addLast(it) }
                node.right?.let { queue.addLast(it) }
            }
            
            result.add(level)
        }
        
        return result
    }
    
    fun levelOrderBottom(root: TreeNode?): List<List<Int>> {
        return levelOrder(root).reversed()
    }
    
    fun zigzagLevelOrder(root: TreeNode?): List<List<Int>> {
        if (root == null) return emptyList()
        
        val result = mutableListOf<List<Int>>()
        val queue = ArrayDeque<TreeNode>()
        queue.addLast(root)
        var leftToRight = true
        
        while (queue.isNotEmpty()) {
            val levelSize = queue.size
            val level = ArrayDeque<Int>()
            
            repeat(levelSize) {
                val node = queue.removeFirst()
                if (leftToRight) level.addLast(node.`val`)
                else level.addFirst(node.`val`)
                
                node.left?.let { queue.addLast(it) }
                node.right?.let { queue.addLast(it) }
            }
            
            result.add(level.toList())
            leftToRight = !leftToRight
        }
        
        return result
    }
    
    fun rightSideView(root: TreeNode?): List<Int> {
        if (root == null) return emptyList()
        
        val result = mutableListOf<Int>()
        val queue = ArrayDeque<TreeNode>()
        queue.addLast(root)
        
        while (queue.isNotEmpty()) {
            val levelSize = queue.size
            
            repeat(levelSize) { i ->
                val node = queue.removeFirst()
                if (i == levelSize - 1) result.add(node.`val`)
                node.left?.let { queue.addLast(it) }
                node.right?.let { queue.addLast(it) }
            }
        }
        
        return result
    }
}
