package com.leetcode.solutions.linkedlist.problems.hard

import com.leetcode.solutions.common.ListNode
import java.util.PriorityQueue

/**
 * LeetCode 23: Merge k Sorted Lists
 * https://leetcode.com/problems/merge-k-sorted-lists/
 * 
 * Difficulty: Hard
 * Companies: Google, Amazon, Microsoft, Meta, Apple
 * 
 * Merge k sorted linked lists into one sorted linked list.
 * 
 * Example: lists = [[1,4,5],[1,3,4],[2,6]] → [1,1,2,3,4,4,5,6]
 */

/** Solution 1: Min Heap / Priority Queue - Time: O(N log k), Space: O(k) */
class MergeKLists1 {
    
    fun mergeKLists(lists: Array<ListNode?>): ListNode? {
        val minHeap = createMinHeap()
        addAllHeadsToHeap(lists, minHeap)
        return buildMergedList(minHeap)
    }
    
    private fun createMinHeap() = PriorityQueue<ListNode> { a, b -> a.`val` - b.`val` }
    
    private fun addAllHeadsToHeap(lists: Array<ListNode?>, heap: PriorityQueue<ListNode>) {
        lists.forEach { node ->
            node?.let { heap.offer(it) }
        }
    }
    
    private fun buildMergedList(heap: PriorityQueue<ListNode>): ListNode? {
        val dummy = ListNode(0)
        var tail = dummy
        
        while (heap.isNotEmpty()) {
            val smallest = heap.poll()
            tail.next = smallest
            tail = smallest
            
            smallest.next?.let { heap.offer(it) }
        }
        
        return dummy.next
    }
}

/** Solution 2: Divide and Conquer - Time: O(N log k), Space: O(log k) */
class MergeKLists2 {
    
    fun mergeKLists(lists: Array<ListNode?>): ListNode? {
        if (lists.isEmpty()) return null
        return divideAndMerge(lists, 0, lists.size - 1)
    }
    
    private fun divideAndMerge(lists: Array<ListNode?>, left: Int, right: Int): ListNode? {
        if (left == right) return lists[left]
        
        val mid = left + (right - left) / 2
        val leftMerged = divideAndMerge(lists, left, mid)
        val rightMerged = divideAndMerge(lists, mid + 1, right)
        
        return mergeTwoLists(leftMerged, rightMerged)
    }
    
    private fun mergeTwoLists(l1: ListNode?, l2: ListNode?): ListNode? {
        val dummy = ListNode(0)
        var tail = dummy
        var p1 = l1
        var p2 = l2
        
        while (p1 != null && p2 != null) {
            if (p1.`val` <= p2.`val`) {
                tail.next = p1
                p1 = p1.next
            } else {
                tail.next = p2
                p2 = p2.next
            }
            tail = tail.next!!
        }
        
        tail.next = p1 ?: p2
        return dummy.next
    }
}

/** Solution 3: Merge One by One - Time: O(kN), Space: O(1) */
class MergeKLists3 {
    
    fun mergeKLists(lists: Array<ListNode?>): ListNode? {
        if (lists.isEmpty()) return null
        
        var result = lists[0]
        for (i in 1 until lists.size) {
            result = mergeTwoLists(result, lists[i])
        }
        
        return result
    }
    
    private fun mergeTwoLists(l1: ListNode?, l2: ListNode?): ListNode? {
        val dummy = ListNode(0)
        var tail = dummy
        var p1 = l1
        var p2 = l2
        
        while (p1 != null && p2 != null) {
            if (p1.`val` <= p2.`val`) {
                tail.next = p1
                p1 = p1.next
            } else {
                tail.next = p2
                p2 = p2.next
            }
            tail = tail.next!!
        }
        
        tail.next = p1 ?: p2
        return dummy.next
    }
}

/** Solution 4: Collect All, Sort, Rebuild - Time: O(N log N), Space: O(N) */
class MergeKLists4 {
    
    fun mergeKLists(lists: Array<ListNode?>): ListNode? {
        val allValues = collectAllValues(lists)
        allValues.sort()
        return buildList(allValues)
    }
    
    private fun collectAllValues(lists: Array<ListNode?>): MutableList<Int> {
        val values = mutableListOf<Int>()
        
        lists.forEach { head ->
            var curr = head
            while (curr != null) {
                values.add(curr.`val`)
                curr = curr.next
            }
        }
        
        return values
    }
    
    private fun buildList(values: List<Int>): ListNode? {
        if (values.isEmpty()) return null
        
        val head = ListNode(values[0])
        var curr = head
        
        for (i in 1 until values.size) {
            curr.next = ListNode(values[i])
            curr = curr.next!!
        }
        
        return head
    }
}
