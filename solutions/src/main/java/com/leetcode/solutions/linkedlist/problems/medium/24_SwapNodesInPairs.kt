package com.leetcode.solutions.linkedlist.problems.medium

import com.leetcode.solutions.common.ListNode

/**
 * LeetCode 24: Swap Nodes in Pairs
 * https://leetcode.com/problems/swap-nodes-in-pairs/
 * 
 * Difficulty: Medium
 * Companies: Google, Amazon, Microsoft, Meta
 * 
 * Swap every two adjacent nodes and return its head.
 * 
 * Example: 1->2->3->4 → 2->1->4->3
 */

/** Solution 1: Iterative - Time: O(n), Space: O(1) */
class SwapPairs1 {
    
    fun swapPairs(head: ListNode?): ListNode? {
        val dummy = ListNode(0).apply { next = head }
        var prev: ListNode = dummy
        
        while (hasPairToSwap(prev)) {
            val first = prev.next!!
            val second = first.next!!
            
            swapPair(prev, first, second)
            prev = first
        }
        
        return dummy.next
    }
    
    private fun hasPairToSwap(prev: ListNode) = prev.next?.next != null
    
    private fun swapPair(prev: ListNode, first: ListNode, second: ListNode) {
        first.next = second.next
        second.next = first
        prev.next = second
    }
}

/** Solution 2: Recursive - Time: O(n), Space: O(n) */
class SwapPairs2 {
    
    fun swapPairs(head: ListNode?): ListNode? {
        if (head?.next == null) return head
        
        val first = head
        val second = head.next!!
        
        first.next = swapPairs(second.next)
        second.next = first
        
        return second
    }
}

/** Solution 3: Value Swap - Time: O(n), Space: O(1) */
class SwapPairs3 {
    
    fun swapPairs(head: ListNode?): ListNode? {
        var curr = head
        
        while (curr?.next != null) {
            swapValues(curr, curr.next!!)
            curr = curr.next?.next
        }
        
        return head
    }
    
    private fun swapValues(a: ListNode, b: ListNode) {
        val temp = a.`val`
        a.`val` = b.`val`
        b.`val` = temp
    }
}

/** Solution 4: Using Stack - Time: O(n), Space: O(n) */
class SwapPairs4 {
    
    fun swapPairs(head: ListNode?): ListNode? {
        val values = collectValues(head)
        swapInArray(values)
        return buildList(values)
    }
    
    private fun collectValues(head: ListNode?): MutableList<Int> {
        val values = mutableListOf<Int>()
        var curr = head
        while (curr != null) {
            values.add(curr.`val`)
            curr = curr.next
        }
        return values
    }
    
    private fun swapInArray(values: MutableList<Int>) {
        var i = 0
        while (i + 1 < values.size) {
            val temp = values[i]
            values[i] = values[i + 1]
            values[i + 1] = temp
            i += 2
        }
    }
    
    private fun buildList(values: List<Int>): ListNode? {
        if (values.isEmpty()) return null
        
        val head = ListNode(values[0])
        var curr = head
        
        for (i in 1 until values.size) {
            curr.next = ListNode(values[i])
            curr = curr.next!!
        }
        
        return head
    }
}
