package com.leetcode.solutions.linkedlist.traversals

import com.leetcode.solutions.common.ListNode

/**
 * Two Pointer / Fast-Slow Pointer Patterns for Linked Lists
 */
object TwoPointerPatterns {
    
    fun findMiddle(head: ListNode?): ListNode? {
        var slow = head
        var fast = head
        
        while (fast?.next != null) {
            slow = slow?.next
            fast = fast.next?.next
        }
        
        return slow
    }
    
    fun findFirstMiddle(head: ListNode?): ListNode? {
        var slow = head
        var fast = head?.next
        
        while (fast?.next != null) {
            slow = slow?.next
            fast = fast.next?.next
        }
        
        return slow
    }
    
    fun hasCycle(head: ListNode?): Boolean {
        var slow = head
        var fast = head
        
        while (fast?.next != null) {
            slow = slow?.next
            fast = fast.next?.next
            if (slow == fast) return true
        }
        
        return false
    }
    
    fun findCycleStart(head: ListNode?): ListNode? {
        val meetingPoint = findMeetingPoint(head) ?: return null
        return findEntryPoint(head, meetingPoint)
    }
    
    private fun findMeetingPoint(head: ListNode?): ListNode? {
        var slow = head
        var fast = head
        
        while (fast?.next != null) {
            slow = slow?.next
            fast = fast.next?.next
            if (slow == fast) return slow
        }
        
        return null
    }
    
    private fun findEntryPoint(head: ListNode?, meeting: ListNode): ListNode? {
        var p1 = head
        var p2: ListNode? = meeting
        
        while (p1 != p2) {
            p1 = p1?.next
            p2 = p2?.next
        }
        
        return p1
    }
    
    fun findNthFromEnd(head: ListNode?, n: Int): ListNode? {
        var fast = head
        repeat(n) { fast = fast?.next }
        
        var slow = head
        while (fast != null) {
            slow = slow?.next
            fast = fast.next
        }
        
        return slow
    }
}
