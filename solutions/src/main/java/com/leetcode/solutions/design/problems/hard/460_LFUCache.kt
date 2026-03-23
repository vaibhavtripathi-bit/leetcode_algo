package com.leetcode.solutions.design.problems.hard

/**
 * LeetCode 460: LFU Cache
 * https://leetcode.com/problems/lfu-cache/
 *
 * Difficulty: Hard
 * Companies: Google, Amazon, Meta — asked when they want to test design depth
 *
 * LFU = Least Frequently Used. Among ties in frequency, evict the Least Recently Used.
 * All operations must be O(1).
 *
 * Key structures:
 * - keyToVal:   key → value
 * - keyToFreq:  key → frequency
 * - freqToKeys: frequency → LinkedHashSet<key> (insertion order = LRU within frequency)
 * - minFreq:    track current minimum frequency for O(1) eviction
 */
class LFUCache(private val capacity: Int) {

    private val keyToVal  = HashMap<Int, Int>()
    private val keyToFreq = HashMap<Int, Int>()
    private val freqToKeys = HashMap<Int, LinkedHashSet<Int>>()
    private var minFreq = 0

    fun get(key: Int): Int {
        if (key !in keyToVal) return -1
        incrementFrequency(key)
        return keyToVal[key]!!
    }

    fun put(key: Int, value: Int) {
        if (capacity <= 0) return

        if (key in keyToVal) {
            keyToVal[key] = value
            incrementFrequency(key)
        } else {
            if (keyToVal.size == capacity) evictLeastFrequent()
            insertNewKey(key, value)
        }
    }

    private fun incrementFrequency(key: Int) {
        val freq = keyToFreq[key]!!

        keyToFreq[key] = freq + 1

        freqToKeys[freq]!!.remove(key)
        if (freqToKeys[freq]!!.isEmpty() && freq == minFreq) minFreq++

        freqToKeys.getOrPut(freq + 1) { LinkedHashSet() }.add(key)
    }

    private fun evictLeastFrequent() {
        val keys = freqToKeys[minFreq]!!
        val lruKey = keys.first()
        keys.remove(lruKey)
        keyToVal.remove(lruKey)
        keyToFreq.remove(lruKey)
    }

    private fun insertNewKey(key: Int, value: Int) {
        keyToVal[key] = value
        keyToFreq[key] = 1
        freqToKeys.getOrPut(1) { LinkedHashSet() }.add(key)
        minFreq = 1
    }
}
