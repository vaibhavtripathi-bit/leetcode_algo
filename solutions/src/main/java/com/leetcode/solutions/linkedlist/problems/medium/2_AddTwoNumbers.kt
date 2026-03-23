package com.leetcode.solutions.linkedlist.problems.medium

import com.leetcode.solutions.common.ListNode

/**
 * LeetCode 2: Add Two Numbers
 * https://leetcode.com/problems/add-two-numbers/
 * 
 * Difficulty: Medium
 * Companies: Google, Amazon, Microsoft, Meta, Apple
 * 
 * Given two non-empty linked lists representing two non-negative integers
 * in reverse order, add them and return the sum as a linked list.
 * 
 * Example: l1 = 2->4->3 (342), l2 = 5->6->4 (465)
 * Output: 7->0->8 (807)
 */

/** Solution 1: Iterative with Carry - Time: O(max(n,m)), Space: O(max(n,m)) */
class AddTwoNumbers1 {
    
    fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {
        val dummy = ListNode(0)
        var current = dummy
        var carry = 0
        var p1 = l1
        var p2 = l2
        
        while (hasMoreDigits(p1, p2, carry)) {
            val sum = calculateSum(p1, p2, carry)
            carry = sum / 10
            current.next = ListNode(sum % 10)
            current = current.next!!
            
            p1 = p1?.next
            p2 = p2?.next
        }
        
        return dummy.next
    }
    
    private fun hasMoreDigits(p1: ListNode?, p2: ListNode?, carry: Int) =
        p1 != null || p2 != null || carry > 0
    
    private fun calculateSum(p1: ListNode?, p2: ListNode?, carry: Int) =
        (p1?.`val` ?: 0) + (p2?.`val` ?: 0) + carry
}

/** Solution 2: Recursive - Time: O(max(n,m)), Space: O(max(n,m)) */
class AddTwoNumbers2 {
    
    fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {
        return add(l1, l2, 0)
    }
    
    private fun add(l1: ListNode?, l2: ListNode?, carry: Int): ListNode? {
        if (l1 == null && l2 == null && carry == 0) return null
        
        val sum = (l1?.`val` ?: 0) + (l2?.`val` ?: 0) + carry
        val node = ListNode(sum % 10)
        node.next = add(l1?.next, l2?.next, sum / 10)
        
        return node
    }
}

/** Solution 3: Convert to Number (For Small Numbers) - Time: O(n+m), Space: O(1) */
class AddTwoNumbers3 {
    
    fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {
        val num1 = toNumber(l1)
        val num2 = toNumber(l2)
        return toList(num1 + num2)
    }
    
    private fun toNumber(head: ListNode?): Long {
        var num = 0L
        var multiplier = 1L
        var current = head
        
        while (current != null) {
            num += current.`val` * multiplier
            multiplier *= 10
            current = current.next
        }
        
        return num
    }
    
    private fun toList(num: Long): ListNode? {
        if (num == 0L) return ListNode(0)
        
        var n = num
        val dummy = ListNode(0)
        var current = dummy
        
        while (n > 0) {
            current.next = ListNode((n % 10).toInt())
            current = current.next!!
            n /= 10
        }
        
        return dummy.next
    }
}

/** Solution 4: Using StringBuilder - Time: O(n+m), Space: O(n+m) */
class AddTwoNumbers4 {
    
    fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {
        val digits1 = collectDigits(l1)
        val digits2 = collectDigits(l2)
        val resultDigits = addDigits(digits1, digits2)
        return buildList(resultDigits)
    }
    
    private fun collectDigits(head: ListNode?): List<Int> {
        val digits = mutableListOf<Int>()
        var current = head
        while (current != null) {
            digits.add(current.`val`)
            current = current.next
        }
        return digits
    }
    
    private fun addDigits(d1: List<Int>, d2: List<Int>): List<Int> {
        val result = mutableListOf<Int>()
        var carry = 0
        val maxLen = maxOf(d1.size, d2.size)
        
        for (i in 0 until maxLen) {
            val sum = (d1.getOrNull(i) ?: 0) + (d2.getOrNull(i) ?: 0) + carry
            result.add(sum % 10)
            carry = sum / 10
        }
        
        if (carry > 0) result.add(carry)
        return result
    }
    
    private fun buildList(digits: List<Int>): ListNode? {
        if (digits.isEmpty()) return null
        
        val head = ListNode(digits[0])
        var current = head
        
        for (i in 1 until digits.size) {
            current.next = ListNode(digits[i])
            current = current.next!!
        }
        
        return head
    }
}
