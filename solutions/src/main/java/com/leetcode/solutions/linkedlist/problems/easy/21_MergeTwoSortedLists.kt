package com.leetcode.solutions.linkedlist.problems.easy

import com.leetcode.solutions.common.ListNode

/**
 * LeetCode 21: Merge Two Sorted Lists
 * https://leetcode.com/problems/merge-two-sorted-lists/
 * 
 * Difficulty: Easy
 * Companies: Google, Amazon, Microsoft, Meta, Apple
 * 
 * Merge two sorted linked lists and return it as a sorted list.
 * 
 * Example: l1 = 1->2->4, l2 = 1->3->4 → Output: 1->1->2->3->4->4
 */

/** Solution 1: Iterative with Dummy Node - Time: O(n+m), Space: O(1) */
class MergeLists1 {
    
    fun mergeTwoLists(list1: ListNode?, list2: ListNode?): ListNode? {
        val dummy = ListNode(0)
        var tail = dummy
        var l1 = list1
        var l2 = list2
        
        while (bothListsHaveNodes(l1, l2)) {
            if (l1!!.`val` <= l2!!.`val`) {
                tail.next = l1
                l1 = l1.next
            } else {
                tail.next = l2
                l2 = l2.next
            }
            tail = tail.next!!
        }
        
        tail.next = getRemainingList(l1, l2)
        return dummy.next
    }
    
    private fun bothListsHaveNodes(l1: ListNode?, l2: ListNode?) = l1 != null && l2 != null
    
    private fun getRemainingList(l1: ListNode?, l2: ListNode?) = l1 ?: l2
}

/** Solution 2: Recursive - Time: O(n+m), Space: O(n+m) */
class MergeLists2 {
    
    fun mergeTwoLists(list1: ListNode?, list2: ListNode?): ListNode? {
        if (list1 == null) return list2
        if (list2 == null) return list1
        
        return if (list1.`val` <= list2.`val`) {
            list1.apply { next = mergeTwoLists(next, list2) }
        } else {
            list2.apply { next = mergeTwoLists(list1, next) }
        }
    }
}

/** Solution 3: Iterative without Dummy - Time: O(n+m), Space: O(1) */
class MergeLists3 {
    
    fun mergeTwoLists(list1: ListNode?, list2: ListNode?): ListNode? {
        if (list1 == null) return list2
        if (list2 == null) return list1
        
        val (head, startL1, startL2) = pickSmallerAsHead(list1, list2)
        
        var tail = head
        var l1 = startL1
        var l2 = startL2
        
        while (l1 != null && l2 != null) {
            val (nextNode, newL1, newL2) = pickSmaller(l1, l2)
            tail.next = nextNode
            tail = nextNode
            l1 = newL1
            l2 = newL2
        }
        
        tail.next = l1 ?: l2
        return head
    }
    
    private fun pickSmallerAsHead(l1: ListNode, l2: ListNode): Triple<ListNode, ListNode?, ListNode?> {
        return if (l1.`val` <= l2.`val`) {
            Triple(l1, l1.next, l2)
        } else {
            Triple(l2, l1, l2.next)
        }
    }
    
    private fun pickSmaller(l1: ListNode, l2: ListNode): Triple<ListNode, ListNode?, ListNode?> {
        return if (l1.`val` <= l2.`val`) {
            Triple(l1, l1.next, l2)
        } else {
            Triple(l2, l1, l2.next)
        }
    }
}

/** Solution 4: Convert to Array, Sort, Rebuild - Time: O((n+m)log(n+m)), Space: O(n+m) */
class MergeLists4 {
    
    fun mergeTwoLists(list1: ListNode?, list2: ListNode?): ListNode? {
        val values = collectAndSort(list1, list2)
        return buildList(values)
    }
    
    private fun collectAndSort(l1: ListNode?, l2: ListNode?): List<Int> {
        return (toList(l1) + toList(l2)).sorted()
    }
    
    private fun toList(head: ListNode?): List<Int> {
        val result = mutableListOf<Int>()
        var current = head
        while (current != null) {
            result.add(current.`val`)
            current = current.next
        }
        return result
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
