package com.leetcode.solutions.tree.problems.medium

import com.leetcode.solutions.common.TreeNode

/**
 * LeetCode 105: Construct Binary Tree from Preorder and Inorder Traversal
 * https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/
 * 
 * Difficulty: Medium
 * Companies: Google, Amazon, Microsoft, Meta
 */

/** Solution 1: Recursive with HashMap - Time: O(n), Space: O(n) */
class BuildTree1 {
    private var preIndex = 0
    private lateinit var inorderMap: Map<Int, Int>
    
    fun buildTree(preorder: IntArray, inorder: IntArray): TreeNode? {
        preIndex = 0
        inorderMap = inorder.withIndex().associate { it.value to it.index }
        return build(preorder, 0, inorder.size - 1)
    }
    
    private fun build(preorder: IntArray, left: Int, right: Int): TreeNode? {
        if (left > right) return null
        
        val rootVal = preorder[preIndex++]
        val root = TreeNode(rootVal)
        val mid = inorderMap[rootVal]!!
        
        root.left = build(preorder, left, mid - 1)
        root.right = build(preorder, mid + 1, right)
        
        return root
    }
}

/** Solution 2: Recursive without HashMap - Time: O(n²), Space: O(n) */
class BuildTree2 {
    fun buildTree(preorder: IntArray, inorder: IntArray): TreeNode? {
        return build(preorder, 0, preorder.size - 1, inorder, 0, inorder.size - 1)
    }
    
    private fun build(
        preorder: IntArray, preStart: Int, preEnd: Int,
        inorder: IntArray, inStart: Int, inEnd: Int
    ): TreeNode? {
        if (preStart > preEnd || inStart > inEnd) return null
        
        val rootVal = preorder[preStart]
        val root = TreeNode(rootVal)
        
        val rootIndex = findIndex(inorder, inStart, inEnd, rootVal)
        val leftSize = rootIndex - inStart
        
        root.left = build(preorder, preStart + 1, preStart + leftSize, 
                         inorder, inStart, rootIndex - 1)
        root.right = build(preorder, preStart + leftSize + 1, preEnd, 
                          inorder, rootIndex + 1, inEnd)
        
        return root
    }
    
    private fun findIndex(arr: IntArray, start: Int, end: Int, target: Int): Int {
        for (i in start..end) {
            if (arr[i] == target) return i
        }
        return -1
    }
}

/** Solution 3: Iterative with Stack - Time: O(n), Space: O(n) */
class BuildTree3 {
    fun buildTree(preorder: IntArray, inorder: IntArray): TreeNode? {
        if (preorder.isEmpty()) return null
        
        val root = TreeNode(preorder[0])
        val stack = ArrayDeque<TreeNode>()
        stack.addLast(root)
        
        var inorderIndex = 0
        
        for (i in 1 until preorder.size) {
            var node = stack.last()
            
            if (node.`val` != inorder[inorderIndex]) {
                node.left = TreeNode(preorder[i])
                stack.addLast(node.left!!)
            } else {
                while (stack.isNotEmpty() && stack.last().`val` == inorder[inorderIndex]) {
                    node = stack.removeLast()
                    inorderIndex++
                }
                node.right = TreeNode(preorder[i])
                stack.addLast(node.right!!)
            }
        }
        
        return root
    }
}
