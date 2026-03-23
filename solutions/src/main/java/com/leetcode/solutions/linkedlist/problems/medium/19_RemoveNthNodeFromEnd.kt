package com.leetcode.solutions.linkedlist.problems.medium

import com.leetcode.solutions.common.ListNode

/**
 * LeetCode 19: Remove Nth Node From End of List
 * https://leetcode.com/problems/remove-nth-node-from-end-of-list/
 * 
 * Difficulty: Medium
 * Companies: Google, Amazon, Microsoft, Meta, Apple
 * 
 * Given head, remove the nth node from the end and return its head.
 * 
 * Example: 1->2->3->4->5, n=2 → 1->2->3->5
 */

/** Solution 1: Two Pointer (One Pass) - Time: O(n), Space: O(1) */
class RemoveNthFromEnd1 {
    
    fun removeNthFromEnd(head: ListNode?, n: Int): ListNode? {
        val dummy = ListNode(0).apply { next = head }
        
        val fast = advanceNSteps(dummy, n + 1)
        val slow = findNodeBeforeTarget(dummy, fast)
        
        removeNext(slow)
        
        return dummy.next
    }
    
    private fun advanceNSteps(start: ListNode, n: Int): ListNode? {
        var current: ListNode? = start
        repeat(n) { current = current?.next }
        return current
    }
    
    private fun findNodeBeforeTarget(slow: ListNode, fast: ListNode?): ListNode {
        var s: ListNode? = slow
        var f = fast
        
        while (f != null) {
            s = s?.next
            f = f.next
        }
        
        return s!!
    }
    
    private fun removeNext(node: ListNode) {
        node.next = node.next?.next
    }
}

/** Solution 2: Calculate Length - Time: O(n), Space: O(1) */
class RemoveNthFromEnd2 {
    
    fun removeNthFromEnd(head: ListNode?, n: Int): ListNode? {
        val length = getLength(head)
        val targetIndex = length - n
        
        if (targetIndex == 0) return head?.next
        
        val prev = getNodeAt(head, targetIndex - 1)
        prev?.next = prev?.next?.next
        
        return head
    }
    
    private fun getLength(head: ListNode?): Int {
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

/** Solution 3: Store in List - Time: O(n), Space: O(n) */
class RemoveNthFromEnd3 {
    
    fun removeNthFromEnd(head: ListNode?, n: Int): ListNode? {
        val nodes = collectNodes(head)
        val targetIndex = nodes.size - n
        
        if (targetIndex == 0) return head?.next
        
        nodes[targetIndex - 1].next = nodes.getOrNull(targetIndex + 1)
        return head
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

/** Solution 4: Recursive with Counter - Time: O(n), Space: O(n) */
class RemoveNthFromEnd4 {
    
    private var count = 0
    
    fun removeNthFromEnd(head: ListNode?, n: Int): ListNode? {
        count = 0
        return remove(head, n)
    }
    
    private fun remove(node: ListNode?, n: Int): ListNode? {
        if (node == null) return null
        
        node.next = remove(node.next, n)
        count++
        
        return if (count == n) node.next else node
    }
}
