package com.leetcode.solutions.linkedlist.problems.easy

import com.leetcode.solutions.common.ListNode

/**
 * LeetCode 141: Linked List Cycle
 * https://leetcode.com/problems/linked-list-cycle/
 * 
 * Difficulty: Easy
 * Companies: Google, Amazon, Microsoft, Meta, Apple
 * 
 * Given head, determine if the linked list has a cycle.
 * Return true if cycle exists, false otherwise.
 */

/** Solution 1: Floyd's Fast-Slow Pointer - Time: O(n), Space: O(1) */
class HasCycle1 {
    
    fun hasCycle(head: ListNode?): Boolean {
        var slow = head
        var fast = head
        
        while (canContinue(fast)) {
            slow = slow?.next
            fast = fast?.next?.next
            
            if (pointersCollide(slow, fast)) return true
        }
        
        return false
    }
    
    private fun canContinue(fast: ListNode?) = fast?.next != null
    
    private fun pointersCollide(slow: ListNode?, fast: ListNode?) = slow == fast
}

/** Solution 2: HashSet - Time: O(n), Space: O(n) */
class HasCycle2 {
    
    fun hasCycle(head: ListNode?): Boolean {
        val visited = HashSet<ListNode>()
        var current = head
        
        while (current != null) {
            if (alreadyVisited(current, visited)) return true
            visited.add(current)
            current = current.next
        }
        
        return false
    }
    
    private fun alreadyVisited(node: ListNode, visited: Set<ListNode>) = node in visited
}

/** Solution 3: Modify Node Values (Marking) - Time: O(n), Space: O(1) */
class HasCycle3 {
    
    private val marker = Int.MIN_VALUE
    
    fun hasCycle(head: ListNode?): Boolean {
        var current = head
        
        while (current != null) {
            if (isMarked(current)) return true
            mark(current)
            current = current.next
        }
        
        return false
    }
    
    private fun isMarked(node: ListNode) = node.`val` == marker
    
    private fun mark(node: ListNode) {
        node.`val` = marker
    }
}

/** Solution 4: Step Limit (When Max Size Known) - Time: O(n), Space: O(1) */
class HasCycle4 {
    
    fun hasCycle(head: ListNode?, maxNodes: Int = 10_000): Boolean {
        var current = head
        var steps = 0
        
        while (current != null) {
            if (steps > maxNodes) return true
            current = current.next
            steps++
        }
        
        return false
    }
}
