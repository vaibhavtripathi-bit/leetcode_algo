package com.leetcode.solutions.tree.problems.hard

import com.leetcode.solutions.common.TreeNode

/**
 * LeetCode 297: Serialize and Deserialize Binary Tree
 * https://leetcode.com/problems/serialize-and-deserialize-binary-tree/
 * 
 * Difficulty: Hard
 * Companies: Google, Amazon, Microsoft, Meta
 */

/** Solution 1: Preorder DFS - Time: O(n), Space: O(n) */
class Codec1 {
    
    fun serialize(root: TreeNode?): String {
        val sb = StringBuilder()
        serializeHelper(root, sb)
        return sb.toString()
    }
    
    private fun serializeHelper(node: TreeNode?, sb: StringBuilder) {
        if (node == null) {
            sb.append("null,")
            return
        }
        sb.append("${node.`val`},")
        serializeHelper(node.left, sb)
        serializeHelper(node.right, sb)
    }
    
    fun deserialize(data: String): TreeNode? {
        val nodes = data.split(",").toMutableList()
        return deserializeHelper(nodes.iterator())
    }
    
    private fun deserializeHelper(iter: Iterator<String>): TreeNode? {
        if (!iter.hasNext()) return null
        
        val value = iter.next()
        if (value == "null" || value.isEmpty()) return null
        
        val node = TreeNode(value.toInt())
        node.left = deserializeHelper(iter)
        node.right = deserializeHelper(iter)
        
        return node
    }
}

/** Solution 2: Level Order BFS - Time: O(n), Space: O(n) */
class Codec2 {
    
    fun serialize(root: TreeNode?): String {
        if (root == null) return ""
        
        val result = StringBuilder()
        val queue = ArrayDeque<TreeNode?>()
        queue.addLast(root)
        
        while (queue.isNotEmpty()) {
            val node = queue.removeFirst()
            
            if (node == null) {
                result.append("null,")
            } else {
                result.append("${node.`val`},")
                queue.addLast(node.left)
                queue.addLast(node.right)
            }
        }
        
        return result.toString()
    }
    
    fun deserialize(data: String): TreeNode? {
        if (data.isEmpty()) return null
        
        val values = data.split(",").filter { it.isNotEmpty() }
        if (values.isEmpty() || values[0] == "null") return null
        
        val root = TreeNode(values[0].toInt())
        val queue = ArrayDeque<TreeNode>()
        queue.addLast(root)
        
        var i = 1
        while (queue.isNotEmpty() && i < values.size) {
            val node = queue.removeFirst()
            
            if (i < values.size && values[i] != "null") {
                node.left = TreeNode(values[i].toInt())
                queue.addLast(node.left!!)
            }
            i++
            
            if (i < values.size && values[i] != "null") {
                node.right = TreeNode(values[i].toInt())
                queue.addLast(node.right!!)
            }
            i++
        }
        
        return root
    }
}

/** Solution 3: Parentheses Encoding - Time: O(n), Space: O(n) */
class Codec3 {
    
    fun serialize(root: TreeNode?): String {
        if (root == null) return "()"
        return "(${root.`val`}${serialize(root.left)}${serialize(root.right)})"
    }
    
    fun deserialize(data: String): TreeNode? {
        val index = intArrayOf(0)
        return parse(data, index)
    }
    
    private fun parse(data: String, index: IntArray): TreeNode? {
        if (index[0] >= data.length || data[index[0]] != '(') return null
        index[0]++
        
        if (data[index[0]] == ')') {
            index[0]++
            return null
        }
        
        var numEnd = index[0]
        while (numEnd < data.length && (data[numEnd].isDigit() || data[numEnd] == '-')) {
            numEnd++
        }
        
        val value = data.substring(index[0], numEnd).toInt()
        index[0] = numEnd
        
        val node = TreeNode(value)
        node.left = parse(data, index)
        node.right = parse(data, index)
        
        index[0]++
        return node
    }
}
