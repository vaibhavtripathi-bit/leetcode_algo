package com.leetcode.solutions.tree.problems.medium

import com.leetcode.solutions.common.TreeNode

/**
 * LeetCode 236: Lowest Common Ancestor of a Binary Tree
 * https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/
 * 
 * Difficulty: Medium
 * Companies: Google, Amazon, Microsoft, Meta, Apple
 */

/** Solution 1: Recursive - Time: O(n), Space: O(h) */
class LowestCommonAncestor1 {
    fun lowestCommonAncestor(root: TreeNode?, p: TreeNode?, q: TreeNode?): TreeNode? {
        if (root == null || root == p || root == q) return root
        
        val left = lowestCommonAncestor(root.left, p, q)
        val right = lowestCommonAncestor(root.right, p, q)
        
        return when {
            left != null && right != null -> root
            left != null -> left
            else -> right
        }
    }
}

/** Solution 2: Parent Pointer with Path - Time: O(n), Space: O(n) */
class LowestCommonAncestor2 {
    fun lowestCommonAncestor(root: TreeNode?, p: TreeNode?, q: TreeNode?): TreeNode? {
        val parentMap = buildParentMap(root)
        val ancestorsOfP = collectAncestors(p, parentMap)
        return findFirstCommon(q, parentMap, ancestorsOfP)
    }
    
    private fun buildParentMap(root: TreeNode?): Map<TreeNode, TreeNode?> {
        val map = HashMap<TreeNode, TreeNode?>()
        val queue = ArrayDeque<TreeNode>()
        
        root?.let {
            map[it] = null
            queue.addLast(it)
        }
        
        while (queue.isNotEmpty()) {
            val node = queue.removeFirst()
            node.left?.let {
                map[it] = node
                queue.addLast(it)
            }
            node.right?.let {
                map[it] = node
                queue.addLast(it)
            }
        }
        
        return map
    }
    
    private fun collectAncestors(node: TreeNode?, parents: Map<TreeNode, TreeNode?>): Set<TreeNode> {
        val ancestors = HashSet<TreeNode>()
        var current = node
        
        while (current != null) {
            ancestors.add(current)
            current = parents[current]
        }
        
        return ancestors
    }
    
    private fun findFirstCommon(
        node: TreeNode?, 
        parents: Map<TreeNode, TreeNode?>, 
        ancestors: Set<TreeNode>
    ): TreeNode? {
        var current = node
        
        while (current != null) {
            if (current in ancestors) return current
            current = parents[current]
        }
        
        return null
    }
}

/** Solution 3: Iterative with Stack - Time: O(n), Space: O(n) */
class LowestCommonAncestor3 {
    fun lowestCommonAncestor(root: TreeNode?, p: TreeNode?, q: TreeNode?): TreeNode? {
        val stack = ArrayDeque<TreeNode?>()
        val parent = HashMap<TreeNode?, TreeNode?>()
        
        parent[root] = null
        stack.addLast(root)
        
        while (!parent.containsKey(p) || !parent.containsKey(q)) {
            val node = stack.removeLast() ?: continue
            
            node.left?.let {
                parent[it] = node
                stack.addLast(it)
            }
            node.right?.let {
                parent[it] = node
                stack.addLast(it)
            }
        }
        
        val ancestors = HashSet<TreeNode?>()
        var curr: TreeNode? = p
        while (curr != null) {
            ancestors.add(curr)
            curr = parent[curr]
        }
        
        curr = q
        while (curr !in ancestors) {
            curr = parent[curr]
        }
        
        return curr
    }
}
