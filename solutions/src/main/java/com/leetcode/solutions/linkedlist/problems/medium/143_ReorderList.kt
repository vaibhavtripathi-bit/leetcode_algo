package com.leetcode.solutions.linkedlist.problems.medium

import com.leetcode.solutions.common.ListNode

/**
 * LeetCode 143: Reorder List
 * https://leetcode.com/problems/reorder-list/
 * 
 * Difficulty: Medium
 * Companies: Google, Amazon, Microsoft, Meta
 * 
 * Reorder list: L0 → L1 → ... → Ln-1 → Ln
 * To: L0 → Ln → L1 → Ln-1 → L2 → Ln-2 → ...
 * 
 * Example: 1->2->3->4 → 1->4->2->3
 */

/** Solution 1: Find Middle, Reverse, Merge - Time: O(n), Space: O(1) */
class ReorderList1 {
    
    fun reorderList(head: ListNode?) {
        if (head?.next == null) return
        
        val middle = findMiddle(head)
        val secondHalf = reverse(middle?.next)
        middle?.next = null
        
        merge(head, secondHalf)
    }
    
    private fun findMiddle(head: ListNode?): ListNode? {
        var slow = head
        var fast = head
        
        while (fast?.next?.next != null) {
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
    
    private fun merge(l1: ListNode?, l2: ListNode?) {
        var first = l1
        var second = l2
        
        while (second != null) {
            val temp1 = first?.next
            val temp2 = second.next
            
            first?.next = second
            second.next = temp1
            
            first = temp1
            second = temp2
        }
    }
}

/** Solution 2: Using Deque - Time: O(n), Space: O(n) */
class ReorderList2 {
    
    fun reorderList(head: ListNode?) {
        if (head?.next == null) return
        
        val deque = collectToDeque(head)
        rebuildList(head, deque)
    }
    
    private fun collectToDeque(head: ListNode?): ArrayDeque<ListNode> {
        val deque = ArrayDeque<ListNode>()
        var current = head?.next
        
        while (current != null) {
            deque.addLast(current)
            current = current.next
        }
        
        return deque
    }
    
    private fun rebuildList(head: ListNode?, deque: ArrayDeque<ListNode>) {
        var current = head
        var fromEnd = true
        
        while (deque.isNotEmpty()) {
            val next = if (fromEnd) deque.removeLast() else deque.removeFirst()
            current?.next = next
            current = next
            fromEnd = !fromEnd
        }
        
        current?.next = null
    }
}

/** Solution 3: Using ArrayList - Time: O(n), Space: O(n) */
class ReorderList3 {
    
    fun reorderList(head: ListNode?) {
        if (head?.next == null) return
        
        val nodes = collectNodes(head)
        relink(nodes)
    }
    
    private fun collectNodes(head: ListNode?): MutableList<ListNode> {
        val nodes = mutableListOf<ListNode>()
        var current = head
        
        while (current != null) {
            nodes.add(current)
            current = current.next
        }
        
        return nodes
    }
    
    private fun relink(nodes: MutableList<ListNode>) {
        var left = 0
        var right = nodes.size - 1
        
        while (left < right) {
            nodes[left].next = nodes[right]
            left++
            
            if (left == right) break
            
            nodes[right].next = nodes[left]
            right--
        }
        
        nodes[left].next = null
    }
}
