package com.leetcode.solutions.linkedlist.problems.hard

import com.leetcode.solutions.common.ListNode

/**
 * LeetCode 25: Reverse Nodes in k-Group
 * https://leetcode.com/problems/reverse-nodes-in-k-group/
 * 
 * Difficulty: Hard
 * Companies: Google, Amazon, Microsoft, Meta
 * 
 * Reverse the nodes in groups of k. Nodes remaining that are less than k
 * should remain in original order.
 * 
 * Example: 1->2->3->4->5, k=2 → 2->1->4->3->5
 * Example: 1->2->3->4->5, k=3 → 3->2->1->4->5
 */

/** Solution 1: Iterative - Time: O(n), Space: O(1) */
class ReverseKGroup1 {
    
    fun reverseKGroup(head: ListNode?, k: Int): ListNode? {
        if (head == null || k == 1) return head
        
        val dummy = ListNode(0).apply { next = head }
        var prevGroupEnd: ListNode = dummy
        
        while (hasKNodes(prevGroupEnd.next, k)) {
            val groupStart = prevGroupEnd.next!!
            val groupEnd = getKthNode(prevGroupEnd, k)!!
            val nextGroupStart = groupEnd.next
            
            groupEnd.next = null
            prevGroupEnd.next = reverse(groupStart)
            groupStart.next = nextGroupStart
            
            prevGroupEnd = groupStart
        }
        
        return dummy.next
    }
    
    private fun hasKNodes(head: ListNode?, k: Int): Boolean {
        var count = 0
        var curr = head
        while (curr != null && count < k) {
            count++
            curr = curr.next
        }
        return count == k
    }
    
    private fun getKthNode(start: ListNode?, k: Int): ListNode? {
        var curr = start
        repeat(k) { curr = curr?.next }
        return curr
    }
    
    private fun reverse(head: ListNode?): ListNode? {
        var prev: ListNode? = null
        var curr = head
        
        while (curr != null) {
            val next = curr.next
            curr.next = prev
            prev = curr
            curr = next
        }
        
        return prev
    }
}

/** Solution 2: Recursive - Time: O(n), Space: O(n/k) */
class ReverseKGroup2 {
    
    fun reverseKGroup(head: ListNode?, k: Int): ListNode? {
        if (!hasKNodes(head, k)) return head
        
        val (newHead, oldHead, nextGroup) = reverseFirstK(head!!, k)
        oldHead.next = reverseKGroup(nextGroup, k)
        
        return newHead
    }
    
    private fun hasKNodes(head: ListNode?, k: Int): Boolean {
        var count = 0
        var curr = head
        while (curr != null && count < k) {
            count++
            curr = curr.next
        }
        return count == k
    }
    
    private fun reverseFirstK(head: ListNode, k: Int): Triple<ListNode?, ListNode, ListNode?> {
        var prev: ListNode? = null
        var curr: ListNode? = head
        var remaining = k
        
        while (curr != null && remaining > 0) {
            val next = curr.next
            curr.next = prev
            prev = curr
            curr = next
            remaining--
        }
        
        return Triple(prev, head, curr)
    }
}

/** Solution 3: Using Stack - Time: O(n), Space: O(k) */
class ReverseKGroup3 {
    
    fun reverseKGroup(head: ListNode?, k: Int): ListNode? {
        val dummy = ListNode(0)
        var tail = dummy
        var curr = head
        
        while (curr != null) {
            val stack = ArrayDeque<ListNode>()
            var temp = curr
            
            repeat(k) {
                if (temp != null) {
                    stack.addLast(temp!!)
                    temp = temp!!.next
                }
            }
            
            if (stack.size == k) {
                while (stack.isNotEmpty()) {
                    tail.next = stack.removeLast()
                    tail = tail.next!!
                }
                curr = temp
            } else {
                tail.next = curr
                break
            }
        }
        
        tail.next = null
        return dummy.next
    }
}

/** Solution 4: Count then Process - Time: O(n), Space: O(1) */
class ReverseKGroup4 {
    
    fun reverseKGroup(head: ListNode?, k: Int): ListNode? {
        val totalNodes = countNodes(head)
        val groupsToReverse = totalNodes / k
        
        if (groupsToReverse == 0) return head
        
        val dummy = ListNode(0).apply { next = head }
        var prevGroupEnd: ListNode = dummy
        
        repeat(groupsToReverse) {
            prevGroupEnd = reverseNextKNodes(prevGroupEnd, k)
        }
        
        return dummy.next
    }
    
    private fun countNodes(head: ListNode?): Int {
        var count = 0
        var curr = head
        while (curr != null) {
            count++
            curr = curr.next
        }
        return count
    }
    
    private fun reverseNextKNodes(prevEnd: ListNode, k: Int): ListNode {
        val first = prevEnd.next!!
        var prev = first
        var curr = first.next
        
        repeat(k - 1) {
            val next = curr?.next
            curr?.next = prev
            prev = curr!!
            curr = next
        }
        
        val nextGroupStart = curr
        prevEnd.next = prev
        first.next = nextGroupStart
        
        return first
    }
}
