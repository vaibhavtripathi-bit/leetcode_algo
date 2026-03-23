package com.leetcode.solutions.common

/**
 * Standard LeetCode ListNode definition for singly-linked list.
 */
class ListNode(var `val`: Int) {
    var next: ListNode? = null
    
    constructor(`val`: Int, next: ListNode?) : this(`val`) {
        this.next = next
    }
}

/**
 * Utility functions for ListNode - useful for testing solutions.
 */
object ListNodeUtil {
    
    fun create(vararg values: Int): ListNode? {
        if (values.isEmpty()) return null
        
        val head = ListNode(values[0])
        var current = head
        
        for (i in 1 until values.size) {
            current.next = ListNode(values[i])
            current = current.next!!
        }
        
        return head
    }
    
    fun toList(head: ListNode?): List<Int> {
        val result = mutableListOf<Int>()
        var current = head
        
        while (current != null) {
            result.add(current.`val`)
            current = current.next
        }
        
        return result
    }
    
    fun toString(head: ListNode?): String {
        return toList(head).joinToString(" -> ")
    }
}
