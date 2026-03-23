package com.leetcode.solutions.linkedlist.problems.easy

import com.leetcode.solutions.common.ListNode

/**
 * LeetCode 160: Intersection of Two Linked Lists
 * https://leetcode.com/problems/intersection-of-two-linked-lists/
 * 
 * Difficulty: Easy
 * Companies: Google, Amazon, Microsoft, Meta, Apple
 * 
 * Given heads of two linked lists, return the node where they intersect.
 * Return null if they don't intersect.
 */

/** Solution 1: Two Pointer Switch - Time: O(n+m), Space: O(1) */
class Intersection1 {
    
    fun getIntersectionNode(headA: ListNode?, headB: ListNode?): ListNode? {
        if (headA == null || headB == null) return null
        
        var ptrA = headA
        var ptrB = headB
        
        while (ptrA != ptrB) {
            ptrA = if (ptrA == null) headB else ptrA.next
            ptrB = if (ptrB == null) headA else ptrB.next
        }
        
        return ptrA
    }
}

/** Solution 2: Length Difference - Time: O(n+m), Space: O(1) */
class Intersection2 {
    
    fun getIntersectionNode(headA: ListNode?, headB: ListNode?): ListNode? {
        val lenA = getLength(headA)
        val lenB = getLength(headB)
        
        var ptrA = headA
        var ptrB = headB
        
        if (lenA > lenB) {
            ptrA = advanceBy(ptrA, lenA - lenB)
        } else {
            ptrB = advanceBy(ptrB, lenB - lenA)
        }
        
        return findIntersection(ptrA, ptrB)
    }
    
    private fun getLength(head: ListNode?): Int {
        var len = 0
        var current = head
        while (current != null) {
            len++
            current = current.next
        }
        return len
    }
    
    private fun advanceBy(head: ListNode?, steps: Int): ListNode? {
        var current = head
        repeat(steps) { current = current?.next }
        return current
    }
    
    private fun findIntersection(a: ListNode?, b: ListNode?): ListNode? {
        var ptrA = a
        var ptrB = b
        
        while (ptrA != ptrB) {
            ptrA = ptrA?.next
            ptrB = ptrB?.next
        }
        
        return ptrA
    }
}

/** Solution 3: HashSet - Time: O(n+m), Space: O(n) */
class Intersection3 {
    
    fun getIntersectionNode(headA: ListNode?, headB: ListNode?): ListNode? {
        val nodesInA = collectNodes(headA)
        return findFirstMatch(headB, nodesInA)
    }
    
    private fun collectNodes(head: ListNode?): Set<ListNode> {
        val nodes = HashSet<ListNode>()
        var current = head
        
        while (current != null) {
            nodes.add(current)
            current = current.next
        }
        
        return nodes
    }
    
    private fun findFirstMatch(head: ListNode?, nodeSet: Set<ListNode>): ListNode? {
        var current = head
        
        while (current != null) {
            if (current in nodeSet) return current
            current = current.next
        }
        
        return null
    }
}
