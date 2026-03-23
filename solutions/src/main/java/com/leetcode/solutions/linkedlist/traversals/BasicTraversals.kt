package com.leetcode.solutions.linkedlist.traversals

import com.leetcode.solutions.common.ListNode

/**
 * Basic Linked List Traversal Patterns
 * Fundamental operations that form building blocks for complex problems.
 */
object BasicTraversals {
    
    fun collectValues(head: ListNode?): List<Int> {
        val result = mutableListOf<Int>()
        var current = head
        
        while (current != null) {
            result.add(current.`val`)
            current = current.next
        }
        
        return result
    }
    
    fun length(head: ListNode?): Int {
        var count = 0
        var current = head
        
        while (current != null) {
            count++
            current = current.next
        }
        
        return count
    }
    
    fun getNthNode(head: ListNode?, n: Int): ListNode? {
        var current = head
        var index = 0
        
        while (current != null && index < n) {
            current = current.next
            index++
        }
        
        return current
    }
    
    fun getTail(head: ListNode?): ListNode? {
        if (head == null) return null
        
        var current = head
        while (current?.next != null) {
            current = current.next
        }
        
        return current
    }
    
    fun contains(head: ListNode?, target: Int): Boolean {
        var current = head
        
        while (current != null) {
            if (current.`val` == target) return true
            current = current.next
        }
        
        return false
    }
    
    fun indexOf(head: ListNode?, target: Int): Int {
        var current = head
        var index = 0
        
        while (current != null) {
            if (current.`val` == target) return index
            current = current.next
            index++
        }
        
        return -1
    }
}
