package com.leetcode.solutions.linkedlist.problems.easy

import com.leetcode.solutions.common.ListNode

/**
 * LeetCode 83: Remove Duplicates from Sorted List
 * https://leetcode.com/problems/remove-duplicates-from-sorted-list/
 * 
 * Difficulty: Easy
 * Companies: Google, Amazon, Microsoft
 * 
 * Given head of a sorted linked list, delete all duplicates.
 * Return the linked list sorted with only distinct elements.
 * 
 * Example: 1->1->2 → 1->2
 */

/** Solution 1: Iterative - Time: O(n), Space: O(1) */
class DeleteDuplicates1 {
    
    fun deleteDuplicates(head: ListNode?): ListNode? {
        var current = head
        
        while (current?.next != null) {
            if (isDuplicate(current, current.next!!)) {
                skipDuplicate(current)
            } else {
                current = current.next
            }
        }
        
        return head
    }
    
    private fun isDuplicate(node: ListNode, next: ListNode) = node.`val` == next.`val`
    
    private fun skipDuplicate(node: ListNode) {
        node.next = node.next?.next
    }
}

/** Solution 2: Recursive - Time: O(n), Space: O(n) */
class DeleteDuplicates2 {
    
    fun deleteDuplicates(head: ListNode?): ListNode? {
        if (head?.next == null) return head
        
        head.next = deleteDuplicates(head.next)
        
        return if (head.`val` == head.next?.`val`) head.next else head
    }
}

/** Solution 3: Using Distinct Set - Time: O(n), Space: O(n) */
class DeleteDuplicates3 {
    
    fun deleteDuplicates(head: ListNode?): ListNode? {
        val distinctValues = collectDistinct(head)
        return buildList(distinctValues)
    }
    
    private fun collectDistinct(head: ListNode?): List<Int> {
        val seen = linkedSetOf<Int>()
        var current = head
        
        while (current != null) {
            seen.add(current.`val`)
            current = current.next
        }
        
        return seen.toList()
    }
    
    private fun buildList(values: List<Int>): ListNode? {
        if (values.isEmpty()) return null
        
        val head = ListNode(values[0])
        var current = head
        
        for (i in 1 until values.size) {
            current.next = ListNode(values[i])
            current = current.next!!
        }
        
        return head
    }
}
