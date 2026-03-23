package com.leetcode.solutions.linkedlist.problems.easy

import com.leetcode.solutions.common.ListNode

/**
 * LeetCode 234: Palindrome Linked List
 * https://leetcode.com/problems/palindrome-linked-list/
 * 
 * Difficulty: Easy
 * Companies: Google, Amazon, Microsoft, Meta, Apple
 * 
 * Given head, return true if it is a palindrome.
 * 
 * Example: 1->2->2->1 → true
 * Example: 1->2 → false
 */

/** Solution 1: Reverse Second Half - Time: O(n), Space: O(1) */
class IsPalindrome1 {
    
    fun isPalindrome(head: ListNode?): Boolean {
        if (head?.next == null) return true
        
        val middle = findMiddle(head)
        val secondHalf = reverse(middle)
        
        return compareHalves(head, secondHalf)
    }
    
    private fun findMiddle(head: ListNode?): ListNode? {
        var slow = head
        var fast = head
        
        while (fast?.next != null) {
            slow = slow?.next
            fast = fast.next?.next
        }
        
        return slow
    }
    
    private fun reverse(head: ListNode?): ListNode? {
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
    
    private fun compareHalves(first: ListNode?, second: ListNode?): Boolean {
        var p1 = first
        var p2 = second
        
        while (p2 != null) {
            if (p1?.`val` != p2.`val`) return false
            p1 = p1.next
            p2 = p2.next
        }
        
        return true
    }
}

/** Solution 2: Stack for First Half - Time: O(n), Space: O(n/2) */
class IsPalindrome2 {
    
    fun isPalindrome(head: ListNode?): Boolean {
        if (head?.next == null) return true
        
        val stack = ArrayDeque<Int>()
        var slow = head
        var fast = head
        
        while (fast?.next != null) {
            stack.addLast(slow!!.`val`)
            slow = slow.next
            fast = fast.next?.next
        }
        
        if (fast != null) slow = slow?.next
        
        return compareWithStack(slow, stack)
    }
    
    private fun compareWithStack(head: ListNode?, stack: ArrayDeque<Int>): Boolean {
        var current = head
        
        while (current != null) {
            if (current.`val` != stack.removeLast()) return false
            current = current.next
        }
        
        return true
    }
}

/** Solution 3: Convert to Array - Time: O(n), Space: O(n) */
class IsPalindrome3 {
    
    fun isPalindrome(head: ListNode?): Boolean {
        val values = collectValues(head)
        return checkPalindrome(values)
    }
    
    private fun collectValues(head: ListNode?): List<Int> {
        val values = mutableListOf<Int>()
        var current = head
        
        while (current != null) {
            values.add(current.`val`)
            current = current.next
        }
        
        return values
    }
    
    private fun checkPalindrome(values: List<Int>): Boolean {
        var left = 0
        var right = values.size - 1
        
        while (left < right) {
            if (values[left] != values[right]) return false
            left++
            right--
        }
        
        return true
    }
}

/** Solution 4: Recursive - Time: O(n), Space: O(n) */
class IsPalindrome4 {
    
    private var frontPointer: ListNode? = null
    
    fun isPalindrome(head: ListNode?): Boolean {
        frontPointer = head
        return check(head)
    }
    
    private fun check(current: ListNode?): Boolean {
        if (current == null) return true
        if (!check(current.next)) return false
        if (frontPointer?.`val` != current.`val`) return false
        
        frontPointer = frontPointer?.next
        return true
    }
}
