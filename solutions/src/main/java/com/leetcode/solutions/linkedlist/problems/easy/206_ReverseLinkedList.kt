package com.leetcode.solutions.linkedlist.problems.easy

import com.leetcode.solutions.common.ListNode

/**
 * LeetCode 206: Reverse Linked List
 * https://leetcode.com/problems/reverse-linked-list/
 * 
 * Difficulty: Easy
 * Companies: Google, Amazon, Microsoft, Meta, Apple
 * 
 * Given the head of a singly linked list, reverse the list and return the reversed list.
 * 
 * Example: 1->2->3->4->5 becomes 5->4->3->2->1
 */

/** Solution 1: Iterative - Time: O(n), Space: O(1) */
class ReverseList1 {
    
    fun reverseList(head: ListNode?): ListNode? {
        var prev: ListNode? = null
        var current = head
        
        while (current != null) {
            val next = saveNext(current)
            linkToPrev(current, prev)
            prev = current
            current = next
        }
        
        return prev
    }
    
    private fun saveNext(node: ListNode) = node.next
    
    private fun linkToPrev(node: ListNode, prev: ListNode?) {
        node.next = prev
    }
}

/** Solution 2: Recursive - Time: O(n), Space: O(n) */
class ReverseList2 {
    
    fun reverseList(head: ListNode?): ListNode? {
        if (isBaseCase(head)) return head
        
        val newHead = reverseList(head?.next)
        reverseLink(head)
        
        return newHead
    }
    
    private fun isBaseCase(head: ListNode?) = head?.next == null
    
    private fun reverseLink(node: ListNode?) {
        node?.next?.next = node
        node?.next = null
    }
}

/** Solution 3: Tail Recursive (Kotlin optimized) - Time: O(n), Space: O(1) */
class ReverseList3 {
    
    fun reverseList(head: ListNode?): ListNode? = reverse(head, null)
    
    private tailrec fun reverse(current: ListNode?, prev: ListNode?): ListNode? {
        if (current == null) return prev
        
        val next = current.next
        current.next = prev
        
        return reverse(next, current)
    }
}

/** Solution 4: Using Stack - Time: O(n), Space: O(n) */
class ReverseList4 {
    
    fun reverseList(head: ListNode?): ListNode? {
        if (head == null) return null
        
        val stack = pushAllToStack(head)
        return buildFromStack(stack)
    }
    
    private fun pushAllToStack(head: ListNode): ArrayDeque<ListNode> {
        val stack = ArrayDeque<ListNode>()
        var current: ListNode? = head
        
        while (current != null) {
            stack.addLast(current)
            current = current.next
        }
        
        return stack
    }
    
    private fun buildFromStack(stack: ArrayDeque<ListNode>): ListNode {
        val newHead = stack.removeLast()
        var current = newHead
        
        while (stack.isNotEmpty()) {
            current.next = stack.removeLast()
            current = current.next!!
        }
        current.next = null
        
        return newHead
    }
}
