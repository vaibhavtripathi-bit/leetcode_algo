package com.leetcode.solutions.design.problems.hard

/**
 * LeetCode 432: All O`one Data Structure
 * https://leetcode.com/problems/all-oone-data-structure/
 *
 * Difficulty: Hard
 * Companies: Google, Amazon
 *
 * Design a structure to:
 * - inc(key): increment count of key by 1
 * - dec(key): decrement count of key by 1 (remove if count reaches 0)
 * - getMaxKey(): return one key with maximum count
 * - getMinKey(): return one key with minimum count
 * All in O(1).
 *
 * Key idea: Doubly Linked List of frequency buckets + HashMap (key → bucket node)
 * Each bucket stores all keys with the same frequency.
 * Buckets are sorted by frequency (head=min, tail=max).
 */
class AllOne {

    private data class Bucket(val freq: Int, val keys: LinkedHashSet<String> = LinkedHashSet()) {
        var prev: Bucket? = null
        var next: Bucket? = null
    }

    private val keyToFreq  = HashMap<String, Int>()
    private val freqToBucket = HashMap<Int, Bucket>()
    private val head = Bucket(0)   // dummy min sentinel
    private val tail = Bucket(0)   // dummy max sentinel

    init { head.next = tail; tail.prev = head }

    fun inc(key: String) {
        val freq = keyToFreq.getOrDefault(key, 0)
        keyToFreq[key] = freq + 1
        val nextBucket = getOrCreateBucketAfter(freq + 1, freqToBucket[freq] ?: head)
        nextBucket.keys.add(key)
        removefromBucket(key, freq)
    }

    fun dec(key: String) {
        val freq = keyToFreq[key] ?: return
        if (freq == 1) keyToFreq.remove(key) else keyToFreq[key] = freq - 1
        if (freq > 1) {
            val prevBucket = getOrCreateBucketBefore(freq - 1, freqToBucket[freq]!!)
            prevBucket.keys.add(key)
        }
        removefromBucket(key, freq)
    }

    fun getMaxKey(): String = if (tail.prev == head) "" else tail.prev!!.keys.first()
    fun getMinKey(): String = if (head.next == tail) "" else head.next!!.keys.first()

    private fun removefromBucket(key: String, freq: Int) {
        val bucket = freqToBucket[freq] ?: return
        bucket.keys.remove(key)
        if (bucket.keys.isEmpty()) {
            bucket.prev!!.next = bucket.next
            bucket.next!!.prev = bucket.prev
            freqToBucket.remove(freq)
        }
    }

    private fun getOrCreateBucketAfter(freq: Int, after: Bucket): Bucket {
        return freqToBucket.getOrPut(freq) {
            val b = Bucket(freq)
            b.next = after.next; b.prev = after
            after.next!!.prev = b; after.next = b
            b
        }
    }

    private fun getOrCreateBucketBefore(freq: Int, before: Bucket): Bucket {
        return freqToBucket.getOrPut(freq) {
            val b = Bucket(freq)
            b.prev = before.prev; b.next = before
            before.prev!!.next = b; before.prev = b
            b
        }
    }
}
