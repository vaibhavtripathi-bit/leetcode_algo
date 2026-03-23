package com.leetcode.solutions.linkedlist.problems.medium

import com.leetcode.solutions.common.ListNode

/**
 * LeetCode 142: Linked List Cycle II
 * https://leetcode.com/problems/linked-list-cycle-ii/
 * 
 * Difficulty: Medium
 * Companies: Google, Amazon, Microsoft, Meta
 * 
 * Given head, return the node where the cycle begins. Return null if no cycle.
 */

/** Solution 1: Floyd's Algorithm (Two Phase) - Time: O(n), Space: O(1) */
class DetectCycle1 {
    
    fun detectCycle(head: ListNode?): ListNode? {
        val meetingPoint = findMeetingPoint(head) ?: return null
        return findCycleStart(head, meetingPoint)
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
    
    private fun findCycleStart(head: ListNode?, meeting: ListNode): ListNode? {
        var p1 = head
        var p2: ListNode? = meeting
        
        while (p1 != p2) {
            p1 = p1?.next
            p2 = p2?.next
        }
        
        return p1
    }
}

/** Solution 2: HashSet - Time: O(n), Space: O(n) */
class DetectCycle2 {
    
    fun detectCycle(head: ListNode?): ListNode? {
        val visited = HashSet<ListNode>()
        var current = head
        
        while (current != null) {
            if (current in visited) return current
            visited.add(current)
            current = current.next
        }
        
        return null
    }
}

/** Solution 3: Two Pointer with Length Calculation - Time: O(n), Space: O(1) */
class DetectCycle3 {
    
    fun detectCycle(head: ListNode?): ListNode? {
        val meetingPoint = findMeetingPoint(head) ?: return null
        val cycleLength = calculateCycleLength(meetingPoint)
        return findStartWithLength(head, cycleLength)
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
    
    private fun calculateCycleLength(nodeInCycle: ListNode): Int {
        var count = 1
        var current = nodeInCycle.next
        
        while (current != nodeInCycle) {
            count++
            current = current?.next
        }
        
        return count
    }
    
    private fun findStartWithLength(head: ListNode?, cycleLen: Int): ListNode? {
        var fast = head
        repeat(cycleLen) { fast = fast?.next }
        
        var slow = head
        while (slow != fast) {
            slow = slow?.next
            fast = fast?.next
        }
        
        return slow
    }
}
