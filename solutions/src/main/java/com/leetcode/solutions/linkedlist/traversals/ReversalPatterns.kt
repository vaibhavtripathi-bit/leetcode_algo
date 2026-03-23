package com.leetcode.solutions.linkedlist.traversals

import com.leetcode.solutions.common.ListNode

/**
 * Linked List Reversal Patterns
 */
object ReversalPatterns {
    
    fun reverseIterative(head: ListNode?): ListNode? {
        var prev: ListNode? = null
        var current = head
        
        while (current != null) {
            val next = current.next
            current.next = prev
            prev = current
            current = next
        }
        
        return prev
    }
    
    fun reverseRecursive(head: ListNode?): ListNode? {
        if (head?.next == null) return head
        
        val newHead = reverseRecursive(head.next)
        head.next?.next = head
        head.next = null
        
        return newHead
    }
    
    fun reverseBetween(head: ListNode?, left: Int, right: Int): ListNode? {
        if (head == null || left == right) return head
        
        val dummy = ListNode(0).apply { next = head }
        var prev: ListNode = dummy
        
        repeat(left - 1) { prev = prev.next!! }
        
        val start = prev.next
        var then = start?.next
        
        repeat(right - left) {
            start?.next = then?.next
            then?.next = prev.next
            prev.next = then
            then = start?.next
        }
        
        return dummy.next
    }
    
    fun reverseKGroup(head: ListNode?, k: Int): ListNode? {
        if (head == null || k == 1) return head
        
        val dummy = ListNode(0).apply { next = head }
        var prevGroupEnd: ListNode = dummy
        
        while (hasKNodes(prevGroupEnd.next, k)) {
            val groupStart = prevGroupEnd.next!!
            val groupEnd = getKthNode(prevGroupEnd, k)!!
            val nextGroupStart = groupEnd.next
            
            groupEnd.next = null
            prevGroupEnd.next = reverseIterative(groupStart)
            groupStart.next = nextGroupStart
            
            prevGroupEnd = groupStart
        }
        
        return dummy.next
    }
    
    private fun hasKNodes(head: ListNode?, k: Int): Boolean {
        var count = 0
        var current = head
        while (current != null && count < k) {
            count++
            current = current.next
        }
        return count == k
    }
    
    private fun getKthNode(start: ListNode?, k: Int): ListNode? {
        var current = start
        repeat(k) { current = current?.next }
        return current
    }
}
