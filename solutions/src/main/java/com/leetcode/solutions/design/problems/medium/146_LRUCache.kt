package com.leetcode.solutions.design.problems.medium

/**
 * LeetCode 146: LRU Cache
 * https://leetcode.com/problems/lru-cache/
 *
 * Difficulty: Medium
 * Companies: All FAANG — one of the most frequently asked design problems
 *
 * Design a data structure that follows LRU (Least Recently Used) cache eviction policy.
 * All operations must be O(1).
 *
 * Key insight: HashMap for O(1) lookup + Doubly Linked List for O(1) insertion/deletion
 * - Most recently used: head of list
 * - Least recently used: tail of list (evict from here)
 */

/** Solution 1: HashMap + Doubly Linked List - Time: O(1), Space: O(capacity) */
class LRUCache(private val capacity: Int) {

    private val cache = HashMap<Int, Node>()
    private val head = Node(0, 0)  // dummy head (most recent end)
    private val tail = Node(0, 0)  // dummy tail (least recent end)

    init {
        head.next = tail
        tail.prev = head
    }

    fun get(key: Int): Int {
        val node = cache[key] ?: return -1
        moveToFront(node)
        return node.value
    }

    fun put(key: Int, value: Int) {
        if (key in cache) {
            val node = cache[key]!!
            node.value = value
            moveToFront(node)
        } else {
            if (cache.size == capacity) evictLeastRecent()
            val newNode = Node(key, value)
            insertAtFront(newNode)
            cache[key] = newNode
        }
    }

    private fun moveToFront(node: Node) {
        removeFromList(node)
        insertAtFront(node)
    }

    private fun removeFromList(node: Node) {
        node.prev!!.next = node.next
        node.next!!.prev = node.prev
    }

    private fun insertAtFront(node: Node) {
        node.next = head.next
        node.prev = head
        head.next!!.prev = node
        head.next = node
    }

    private fun evictLeastRecent() {
        val lru = tail.prev!!
        removeFromList(lru)
        cache.remove(lru.key)
    }

    private inner class Node(var key: Int, var value: Int) {
        var prev: Node? = null
        var next: Node? = null
    }
}

/**
 * Solution 2: Using LinkedHashMap (Java built-in) - Time: O(1), Space: O(capacity)
 * LinkedHashMap maintains insertion order. With accessOrder=true, it maintains access order.
 */
class LRUCacheLinkedHashMap(private val capacity: Int) {
    private val cache = object : LinkedHashMap<Int, Int>(capacity, 0.75f, true) {
        override fun removeEldestEntry(eldest: Map.Entry<Int, Int>) = size > capacity
    }

    fun get(key: Int): Int = cache.getOrDefault(key, -1)

    fun put(key: Int, value: Int) { cache[key] = value }
}
