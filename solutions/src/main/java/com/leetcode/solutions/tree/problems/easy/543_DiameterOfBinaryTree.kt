package com.leetcode.solutions.tree.problems.easy

import com.leetcode.solutions.common.TreeNode
import kotlin.math.max

/**
 * LeetCode 543: Diameter of Binary Tree
 * https://leetcode.com/problems/diameter-of-binary-tree/
 * 
 * Difficulty: Easy
 * Companies: Google, Amazon, Microsoft, Meta
 */

/** Solution 1: DFS with Global Variable - Time: O(n), Space: O(h) */
class DiameterOfBinaryTree1 {
    private var diameter = 0
    
    fun diameterOfBinaryTree(root: TreeNode?): Int {
        diameter = 0
        height(root)
        return diameter
    }
    
    private fun height(node: TreeNode?): Int {
        if (node == null) return 0
        
        val left = height(node.left)
        val right = height(node.right)
        
        diameter = max(diameter, left + right)
        
        return 1 + max(left, right)
    }
}

/** Solution 2: DFS Returning Pair - Time: O(n), Space: O(h) */
class DiameterOfBinaryTree2 {
    fun diameterOfBinaryTree(root: TreeNode?): Int {
        return dfs(root).first
    }
    
    private fun dfs(node: TreeNode?): Pair<Int, Int> {
        if (node == null) return 0 to 0
        
        val (leftDiam, leftHeight) = dfs(node.left)
        val (rightDiam, rightHeight) = dfs(node.right)
        
        val currentDiameter = leftHeight + rightHeight
        val maxDiameter = maxOf(currentDiameter, leftDiam, rightDiam)
        val height = 1 + max(leftHeight, rightHeight)
        
        return maxDiameter to height
    }
}

/** Solution 3: Iterative Postorder - Time: O(n), Space: O(n) */
class DiameterOfBinaryTree3 {
    fun diameterOfBinaryTree(root: TreeNode?): Int {
        if (root == null) return 0
        
        val heights = HashMap<TreeNode?, Int>()
        heights[null] = 0
        
        val stack = ArrayDeque<TreeNode>()
        val visited = HashSet<TreeNode>()
        stack.addLast(root)
        var diameter = 0
        
        while (stack.isNotEmpty()) {
            val node = stack.last()
            
            if (visited.contains(node)) {
                stack.removeLast()
                val leftH = heights[node.left] ?: 0
                val rightH = heights[node.right] ?: 0
                heights[node] = 1 + max(leftH, rightH)
                diameter = max(diameter, leftH + rightH)
            } else {
                visited.add(node)
                node.right?.let { stack.addLast(it) }
                node.left?.let { stack.addLast(it) }
            }
        }
        
        return diameter
    }
}
