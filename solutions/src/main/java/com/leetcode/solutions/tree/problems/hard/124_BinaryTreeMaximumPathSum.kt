package com.leetcode.solutions.tree.problems.hard

import com.leetcode.solutions.common.TreeNode
import kotlin.math.max

/**
 * LeetCode 124: Binary Tree Maximum Path Sum
 * https://leetcode.com/problems/binary-tree-maximum-path-sum/
 * 
 * Difficulty: Hard
 * Companies: Google, Amazon, Microsoft, Meta
 */

/** Solution 1: DFS with Global Max - Time: O(n), Space: O(h) */
class MaxPathSum1 {
    private var maxSum = Int.MIN_VALUE
    
    fun maxPathSum(root: TreeNode?): Int {
        maxSum = Int.MIN_VALUE
        maxGain(root)
        return maxSum
    }
    
    private fun maxGain(node: TreeNode?): Int {
        if (node == null) return 0
        
        val leftGain = max(0, maxGain(node.left))
        val rightGain = max(0, maxGain(node.right))
        
        val pathSum = node.`val` + leftGain + rightGain
        maxSum = max(maxSum, pathSum)
        
        return node.`val` + max(leftGain, rightGain)
    }
}

/** Solution 2: DFS Returning Pair - Time: O(n), Space: O(h) */
class MaxPathSum2 {
    fun maxPathSum(root: TreeNode?): Int {
        return dfs(root).first
    }
    
    private fun dfs(node: TreeNode?): Pair<Int, Int> {
        if (node == null) return Int.MIN_VALUE to 0
        
        val (leftMax, leftGain) = dfs(node.left)
        val (rightMax, rightGain) = dfs(node.right)
        
        val positiveLeftGain = max(0, leftGain)
        val positiveRightGain = max(0, rightGain)
        
        val pathThroughNode = node.`val` + positiveLeftGain + positiveRightGain
        val maxSoFar = maxOf(pathThroughNode, leftMax, rightMax)
        
        val maxGainFromNode = node.`val` + max(positiveLeftGain, positiveRightGain)
        
        return maxSoFar to maxGainFromNode
    }
}

/** Solution 3: Iterative Postorder - Time: O(n), Space: O(n) */
class MaxPathSum3 {
    fun maxPathSum(root: TreeNode?): Int {
        if (root == null) return 0
        
        val gains = HashMap<TreeNode?, Int>()
        gains[null] = 0
        
        var maxSum = Int.MIN_VALUE
        val stack = ArrayDeque<TreeNode>()
        val visited = HashSet<TreeNode>()
        stack.addLast(root)
        
        while (stack.isNotEmpty()) {
            val node = stack.last()
            
            if (visited.contains(node)) {
                stack.removeLast()
                
                val leftGain = max(0, gains[node.left] ?: 0)
                val rightGain = max(0, gains[node.right] ?: 0)
                
                val pathSum = node.`val` + leftGain + rightGain
                maxSum = max(maxSum, pathSum)
                
                gains[node] = node.`val` + max(leftGain, rightGain)
            } else {
                visited.add(node)
                node.right?.let { stack.addLast(it) }
                node.left?.let { stack.addLast(it) }
            }
        }
        
        return maxSum
    }
}
