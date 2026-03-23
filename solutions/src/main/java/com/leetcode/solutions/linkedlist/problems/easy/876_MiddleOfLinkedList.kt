package com.leetcode.solutions.linkedlist.problems.easy

import com.leetcode.solutions.common.ListNode

/**
 * LeetCode 876: Middle of the Linked List
 * https://leetcode.com/problems/middle-of-the-linked-list/
 * 
 * Difficulty: Easy
 * Companies: Google, Amazon, Microsoft, Meta
 * 
 * Given head, return the middle node. If two middle nodes, return the second one.
 * 
 * Example: 1->2->3->4->5 → Return node 3
 * Example: 1->2->3->4->5->6 → Return node 4 (second middle)
 */

/** Solution 1: Fast-Slow Pointer - Time: O(n), Space: O(1) */
class MiddleNode1 {
    
    fun middleNode(head: ListNode?): ListNode? {
        var slow = head
        var fast = head
        
        while (hasMoreNodes(fast)) {
            slow = slow?.next
            fast = fast?.next?.next
        }
        
        return slow
    }
    
    private fun hasMoreNodes(fast: ListNode?) = fast?.next != null
}

/** Solution 2: Count Then Traverse - Time: O(n), Space: O(1) */
class MiddleNode2 {
    
    fun middleNode(head: ListNode?): ListNode? {
        val length = countNodes(head)
        return getNodeAt(head, length / 2)
    }
    
    private fun countNodes(head: ListNode?): Int {
        var count = 0
        var current = head
        
        while (current != null) {
            count++
            current = current.next
        }
        
        return count
    }
    
    private fun getNodeAt(head: ListNode?, index: Int): ListNode? {
        var current = head
        repeat(index) { current = current?.next }
        return current
    }
}

/** Solution 3: Store in Array - Time: O(n), Space: O(n) */
class MiddleNode3 {
    
    fun middleNode(head: ListNode?): ListNode? {
        val nodes = collectNodes(head)
        return nodes.getOrNull(nodes.size / 2)
    }
    
    private fun collectNodes(head: ListNode?): List<ListNode> {
        val nodes = mutableListOf<ListNode>()
        var current = head
        
        while (current != null) {
            nodes.add(current)
            current = current.next
        }
        
        return nodes
    }
}

/** Solution 4: Using Kotlin Sequence - Time: O(n), Space: O(n) */
class MiddleNode4 {
    
    fun middleNode(head: ListNode?): ListNode? {
        val nodes = generateSequence(head) { it.next }.toList()
        return nodes.getOrNull(nodes.size / 2)
    }
}
