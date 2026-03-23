package com.leetcode.solutions.heap.problems.hard

import java.util.PriorityQueue

/**
 * 23. Merge k Sorted Lists
 * https://leetcode.com/problems/merge-k-sorted-lists/
 *
 * You are given an array of k linked-lists lists, each linked-list is sorted in ascending order.
 * Merge all the linked-lists into one sorted linked-list and return it.
 *
 * Example 1:
 * Input: lists = [[1,4,5],[1,3,4],[2,6]]
 * Output: [1,1,2,3,4,4,5,6]
 * Explanation: The linked-lists are:
 * [1->4->5, 1->3->4, 2->6]
 * merging them into one sorted list: 1->1->2->3->4->4->5->6
 *
 * Example 2:
 * Input: lists = []
 * Output: []
 *
 * Example 3:
 * Input: lists = [[]]
 * Output: []
 *
 * Constraints:
 * - k == lists.length
 * - 0 <= k <= 10^4
 * - 0 <= lists[i].length <= 500
 * - -10^4 <= lists[i][j] <= 10^4
 * - lists[i] is sorted in ascending order.
 * - The sum of lists[i].length will not exceed 10^4.
 *
 * Companies: Amazon, Facebook, Google, Microsoft, Apple, Uber, Bloomberg, Oracle, ByteDance
 */

class ListNode(var `val`: Int) {
    var next: ListNode? = null
}

/**
 * Solution 1: Min Heap
 * Use a min-heap to always get the smallest node among all list heads.
 *
 * Time Complexity: O(N log k) where N is total nodes, k is number of lists
 * Space Complexity: O(k) for the heap
 */
fun mergeKLists(lists: Array<ListNode?>): ListNode? {
    if (lists.isEmpty()) return null

    val minHeap = PriorityQueue<ListNode>(compareBy { it.`val` })

    for (list in lists) {
        list?.let { minHeap.offer(it) }
    }

    val dummy = ListNode(0)
    var current = dummy

    while (minHeap.isNotEmpty()) {
        val smallest = minHeap.poll()
        current.next = smallest
        current = smallest

        smallest.next?.let { minHeap.offer(it) }
    }

    return dummy.next
}

/**
 * Solution 2: Min Heap with Index Tracking
 * Alternative implementation using pairs to track nodes.
 *
 * Time Complexity: O(N log k)
 * Space Complexity: O(k)
 */
fun mergeKListsWithIndex(lists: Array<ListNode?>): ListNode? {
    if (lists.isEmpty()) return null

    val minHeap = PriorityQueue<Pair<Int, ListNode>>(compareBy { it.second.`val` })

    for ((index, list) in lists.withIndex()) {
        list?.let { minHeap.offer(Pair(index, it)) }
    }

    val dummy = ListNode(0)
    var tail = dummy
    val heads = lists.copyOf()

    while (minHeap.isNotEmpty()) {
        val (index, node) = minHeap.poll()
        tail.next = node
        tail = node

        heads[index] = node.next
        heads[index]?.let { minHeap.offer(Pair(index, it)) }
    }

    return dummy.next
}

/**
 * Solution 3: Divide and Conquer
 * Recursively merge lists in pairs, similar to merge sort.
 *
 * Time Complexity: O(N log k)
 * Space Complexity: O(log k) for recursion stack
 */
fun mergeKListsDivideConquer(lists: Array<ListNode?>): ListNode? {
    if (lists.isEmpty()) return null

    fun mergeTwoLists(l1: ListNode?, l2: ListNode?): ListNode? {
        val dummy = ListNode(0)
        var current = dummy
        var p1 = l1
        var p2 = l2

        while (p1 != null && p2 != null) {
            if (p1.`val` <= p2.`val`) {
                current.next = p1
                p1 = p1.next
            } else {
                current.next = p2
                p2 = p2.next
            }
            current = current.next!!
        }

        current.next = p1 ?: p2
        return dummy.next
    }

    fun merge(lists: Array<ListNode?>, start: Int, end: Int): ListNode? {
        if (start > end) return null
        if (start == end) return lists[start]

        val mid = start + (end - start) / 2
        val left = merge(lists, start, mid)
        val right = merge(lists, mid + 1, end)
        return mergeTwoLists(left, right)
    }

    return merge(lists, 0, lists.size - 1)
}

/**
 * Solution 4: Sequential Merge
 * Merge lists one by one, simpler but less efficient.
 *
 * Time Complexity: O(kN) where N is total nodes
 * Space Complexity: O(1)
 */
fun mergeKListsSequential(lists: Array<ListNode?>): ListNode? {
    if (lists.isEmpty()) return null

    fun mergeTwoLists(l1: ListNode?, l2: ListNode?): ListNode? {
        val dummy = ListNode(0)
        var current = dummy
        var p1 = l1
        var p2 = l2

        while (p1 != null && p2 != null) {
            if (p1.`val` <= p2.`val`) {
                current.next = p1
                p1 = p1.next
            } else {
                current.next = p2
                p2 = p2.next
            }
            current = current.next!!
        }

        current.next = p1 ?: p2
        return dummy.next
    }

    var result: ListNode? = null
    for (list in lists) {
        result = mergeTwoLists(result, list)
    }
    return result
}
