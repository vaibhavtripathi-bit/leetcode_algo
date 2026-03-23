package com.leetcode.solutions.linkedlist.problems.medium

import com.leetcode.solutions.common.ListNode

/**
 * LeetCode 92: Reverse Linked List II
 * https://leetcode.com/problems/reverse-linked-list-ii/
 * 
 * Difficulty: Medium
 * Companies: Google, Amazon, Microsoft, Meta
 * 
 * Reverse the nodes from position left to right. Return the head.
 * 
 * Example: 1->2->3->4->5, left=2, right=4 → 1->4->3->2->5
 */

/** Solution 1: One Pass with Pointers - Time: O(n), Space: O(1) */
class ReverseBetween1 {
    
    fun reverseBetween(head: ListNode?, left: Int, right: Int): ListNode? {
        if (head == null || left == right) return head
        
        val dummy = ListNode(0).apply { next = head }
        val prev = findNodeBefore(dummy, left)
        
        reverseSection(prev, right - left)
        
        return dummy.next
    }
    
    private fun findNodeBefore(dummy: ListNode, position: Int): ListNode {
        var curr: ListNode = dummy
        repeat(position - 1) { curr = curr.next!! }
        return curr
    }
    
    private fun reverseSection(beforeStart: ListNode, count: Int) {
        val start = beforeStart.next
        var then = start?.next
        
        repeat(count) {
            start?.next = then?.next
            then?.next = beforeStart.next
            beforeStart.next = then
            then = start?.next
        }
    }
}

/** Solution 2: Cut, Reverse, Reconnect - Time: O(n), Space: O(1) */
class ReverseBetween2 {
    
    fun reverseBetween(head: ListNode?, left: Int, right: Int): ListNode? {
        if (head == null || left == right) return head
        
        val dummy = ListNode(0).apply { next = head }
        
        val beforeLeft = getNode(dummy, left - 1)
        val leftNode = beforeLeft?.next
        val rightNode = getNode(dummy, right)
        val afterRight = rightNode?.next
        
        rightNode?.next = null
        
        val reversed = reverse(leftNode)
        beforeLeft?.next = reversed
        leftNode?.next = afterRight
        
        return dummy.next
    }
    
    private fun getNode(start: ListNode, position: Int): ListNode? {
        var curr: ListNode? = start
        repeat(position) { curr = curr?.next }
        return curr
    }
    
    private fun reverse(head: ListNode?): ListNode? {
        var prev: ListNode? = null
        var curr = head
        
        while (curr != null) {
            val next = curr.next
            curr.next = prev
            prev = curr
            curr = next
        }
        
        return prev
    }
}

/** Solution 3: Using Stack - Time: O(n), Space: O(n) */
class ReverseBetween3 {
    
    fun reverseBetween(head: ListNode?, left: Int, right: Int): ListNode? {
        if (head == null || left == right) return head
        
        val nodes = collectNodes(head)
        reverseInArray(nodes, left - 1, right - 1)
        return rebuildList(nodes)
    }
    
    private fun collectNodes(head: ListNode?): MutableList<Int> {
        val values = mutableListOf<Int>()
        var curr = head
        while (curr != null) {
            values.add(curr.`val`)
            curr = curr.next
        }
        return values
    }
    
    private fun reverseInArray(arr: MutableList<Int>, left: Int, right: Int) {
        var l = left
        var r = right
        while (l < r) {
            val temp = arr[l]
            arr[l] = arr[r]
            arr[r] = temp
            l++
            r--
        }
    }
    
    private fun rebuildList(values: List<Int>): ListNode? {
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
