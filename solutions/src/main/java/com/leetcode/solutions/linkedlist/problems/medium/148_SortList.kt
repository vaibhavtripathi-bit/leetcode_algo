package com.leetcode.solutions.linkedlist.problems.medium

import com.leetcode.solutions.common.ListNode

/**
 * LeetCode 148: Sort List
 * https://leetcode.com/problems/sort-list/
 * 
 * Difficulty: Medium
 * Companies: Google, Amazon, Microsoft, Meta
 * 
 * Sort a linked list in O(n log n) time and O(1) space.
 * 
 * Example: 4->2->1->3 → 1->2->3->4
 */

/** Solution 1: Merge Sort (Top Down) - Time: O(n log n), Space: O(log n) */
class SortList1 {
    
    fun sortList(head: ListNode?): ListNode? {
        if (head?.next == null) return head
        
        val middle = findMiddle(head)
        val rightHead = middle?.next
        middle?.next = null
        
        val left = sortList(head)
        val right = sortList(rightHead)
        
        return merge(left, right)
    }
    
    private fun findMiddle(head: ListNode?): ListNode? {
        var slow = head
        var fast = head?.next
        
        while (fast?.next != null) {
            slow = slow?.next
            fast = fast.next?.next
        }
        
        return slow
    }
    
    private fun merge(l1: ListNode?, l2: ListNode?): ListNode? {
        val dummy = ListNode(0)
        var tail = dummy
        var p1 = l1
        var p2 = l2
        
        while (p1 != null && p2 != null) {
            if (p1.`val` <= p2.`val`) {
                tail.next = p1
                p1 = p1.next
            } else {
                tail.next = p2
                p2 = p2.next
            }
            tail = tail.next!!
        }
        
        tail.next = p1 ?: p2
        return dummy.next
    }
}

/** Solution 2: Merge Sort (Bottom Up) - Time: O(n log n), Space: O(1) */
class SortList2 {
    
    fun sortList(head: ListNode?): ListNode? {
        if (head?.next == null) return head
        
        val length = getLength(head)
        val dummy = ListNode(0).apply { next = head }
        
        var size = 1
        while (size < length) {
            var prev = dummy
            var curr = dummy.next
            
            while (curr != null) {
                val left = curr
                val right = split(left, size)
                curr = split(right, size)
                prev = merge(prev, left, right)
            }
            
            size *= 2
        }
        
        return dummy.next
    }
    
    private fun getLength(head: ListNode?): Int {
        var len = 0
        var curr = head
        while (curr != null) {
            len++
            curr = curr.next
        }
        return len
    }
    
    private fun split(head: ListNode?, size: Int): ListNode? {
        var curr = head
        repeat(size - 1) {
            curr = curr?.next
        }
        
        val next = curr?.next
        curr?.next = null
        return next
    }
    
    private fun merge(prev: ListNode, l1: ListNode?, l2: ListNode?): ListNode {
        var p1 = l1
        var p2 = l2
        var curr = prev
        
        while (p1 != null && p2 != null) {
            if (p1.`val` <= p2.`val`) {
                curr.next = p1
                p1 = p1.next
            } else {
                curr.next = p2
                p2 = p2.next
            }
            curr = curr.next!!
        }
        
        curr.next = p1 ?: p2
        while (curr.next != null) curr = curr.next!!
        
        return curr
    }
}

/** Solution 3: Convert to Array, Sort, Rebuild - Time: O(n log n), Space: O(n) */
class SortList3 {
    
    fun sortList(head: ListNode?): ListNode? {
        val values = collectValues(head)
        values.sort()
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
