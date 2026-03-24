package com.leetcode.solutions.utils

/**
 * CollectionUtils — high-frequency collection helpers for FAANG interviews.
 *
 * Covers: frequency maps, sorting helpers, interval merging.
 * All are called repeatedly across dozens of problem categories.
 */

// ─────────────────────────────────────────────
// Frequency maps
// The most reused collection pattern in all of algorithms.
// ─────────────────────────────────────────────

/** Builds a frequency map from any Iterable. [1,2,2,3] → {1:1, 2:2, 3:1} */
fun <T> Iterable<T>.frequencyMap(): HashMap<T, Int> {
    val freq = HashMap<T, Int>()
    for (item in this) freq[item] = freq.getOrDefault(item, 0) + 1
    return freq
}

/** Builds a frequency map from an IntArray. */
fun IntArray.frequencyMap(): HashMap<Int, Int> = this.asIterable().frequencyMap()

/** Builds a frequency map from a String (each character). */
fun String.frequencyMap(): HashMap<Char, Int> = this.asIterable().frequencyMap()

// ─────────────────────────────────────────────
// Sorting helpers
// ─────────────────────────────────────────────

/** Sort IntArray descending, returns new array. */
fun IntArray.sortedDescending(): IntArray = this.sortedArrayDescending()

/** Sort list of int arrays by first element, then second (interval-style). */
fun Array<IntArray>.sortByStartThenEnd(): Array<IntArray> =
    sortedWith(compareBy({ it[0] }, { it[1] })).toTypedArray()

/** Sort entries of a frequency map by frequency descending (top-k style). */
fun <T> HashMap<T, Int>.sortedByFrequencyDesc(): List<Map.Entry<T, Int>> =
    entries.sortedByDescending { it.value }

/** Sort entries of a frequency map by frequency ascending (least frequent first). */
fun <T> HashMap<T, Int>.sortedByFrequencyAsc(): List<Map.Entry<T, Int>> =
    entries.sortedBy { it.value }

// ─────────────────────────────────────────────
// Interval merging
// Used in: Merge Intervals, Insert Interval, Meeting Rooms,
//          Employee Free Time, etc.
// ─────────────────────────────────────────────

/**
 * Merges overlapping intervals from a sorted-by-start list.
 * Input: [[1,3],[2,6],[8,10]] → Output: [[1,6],[8,10]]
 *
 * Assumes input is sorted by start time. Use sortByStartThenEnd() first if not.
 */
fun mergeIntervals(intervals: Array<IntArray>): Array<IntArray> {
    if (intervals.isEmpty()) return emptyArray()

    val sorted = intervals.sortedBy { it[0] }
    val merged = mutableListOf(sorted[0].copyOf())

    for (current in sorted.drop(1)) {
        val last = merged.last()
        if (current[0] <= last[1]) {
            last[1] = maxOf(last[1], current[1])
        } else {
            merged.add(current.copyOf())
        }
    }

    return merged.toTypedArray()
}

/** Returns true if two intervals [a0, a1] and [b0, b1] overlap. */
fun intervalsOverlap(a: IntArray, b: IntArray): Boolean = a[0] <= b[1] && b[0] <= a[1]

// ─────────────────────────────────────────────
// Priority queue helpers
// ─────────────────────────────────────────────

/** Creates a min-heap priority queue for Int. */
fun minHeap(): java.util.PriorityQueue<Int> = java.util.PriorityQueue()

/** Creates a max-heap priority queue for Int. */
fun maxHeap(): java.util.PriorityQueue<Int> = java.util.PriorityQueue(compareByDescending { it })

/** Creates a min-heap of IntArray sorted by first element (e.g., Dijkstra). */
fun minHeapByFirst(): java.util.PriorityQueue<IntArray> =
    java.util.PriorityQueue(compareBy { it[0] })

/** Creates a min-heap of Pair<Int,Int> sorted by first element. */
fun minHeapPairByFirst(): java.util.PriorityQueue<Pair<Int, Int>> =
    java.util.PriorityQueue(compareBy { it.first })

// ─────────────────────────────────────────────
// Map utilities
// ─────────────────────────────────────────────

/**
 * Inverts a map: each value maps to the list of keys that produced it.
 * {a:1, b:1, c:2} → {1:[a,b], 2:[c]}
 * Used in: Group Anagrams variant, Accounts Merge.
 */
fun <K, V> Map<K, V>.invertToMultiMap(): HashMap<V, MutableList<K>> {
    val result = HashMap<V, MutableList<K>>()
    for ((k, v) in this) result.getOrPut(v) { mutableListOf() }.add(k)
    return result
}
