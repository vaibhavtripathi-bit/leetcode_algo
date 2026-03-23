package com.leetcode.solutions.tree.problems.medium

import com.leetcode.solutions.common.TreeNode

/**
 * LeetCode 102: Binary Tree Level Order Traversal
 * https://leetcode.com/problems/binary-tree-level-order-traversal/
 * 
 * Difficulty: Medium
 * Companies: Google, Amazon, Microsoft, Meta
 */

/** Solution 1: BFS with Queue - Time: O(n), Space: O(n) */
class LevelOrder1 {
    fun levelOrder(root: TreeNode?): List<List<Int>> {
        if (root == null) return emptyList()
        
        val result = mutableListOf<List<Int>>()
        val queue = ArrayDeque<TreeNode>()
        queue.addLast(root)
        
        while (queue.isNotEmpty()) {
            val level = processLevel(queue)
            result.add(level)
        }
        
        return result
    }
    
    private fun processLevel(queue: ArrayDeque<TreeNode>): List<Int> {
        val level = mutableListOf<Int>()
        val size = queue.size
        
        repeat(size) {
            val node = queue.removeFirst()
            level.add(node.`val`)
            node.left?.let { queue.addLast(it) }
            node.right?.let { queue.addLast(it) }
        }
        
        return level
    }
}

/** Solution 2: DFS with Level Tracking - Time: O(n), Space: O(n) */
class LevelOrder2 {
    fun levelOrder(root: TreeNode?): List<List<Int>> {
        val result = mutableListOf<MutableList<Int>>()
        dfs(root, 0, result)
        return result
    }
    
    private fun dfs(node: TreeNode?, level: Int, result: MutableList<MutableList<Int>>) {
        if (node == null) return
        
        if (level >= result.size) {
            result.add(mutableListOf())
        }
        
        result[level].add(node.`val`)
        dfs(node.left, level + 1, result)
        dfs(node.right, level + 1, result)
    }
}

/** Solution 3: Iterative DFS with Stack - Time: O(n), Space: O(n) */
class LevelOrder3 {
    fun levelOrder(root: TreeNode?): List<List<Int>> {
        if (root == null) return emptyList()
        
        val result = mutableListOf<MutableList<Int>>()
        val stack = ArrayDeque<Pair<TreeNode, Int>>()
        stack.addLast(root to 0)
        
        while (stack.isNotEmpty()) {
            val (node, level) = stack.removeLast()
            
            if (level >= result.size) {
                result.add(mutableListOf())
            }
            result[level].add(node.`val`)
            
            node.right?.let { stack.addLast(it to level + 1) }
            node.left?.let { stack.addLast(it to level + 1) }
        }
        
        return result
    }
}
