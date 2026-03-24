package com.leetcode.solutions.utils.general

/**
 * CollectionConversions — convert between collection types and reshape data.
 *
 * Covers the constant annoyance of converting between arrays, lists, maps,
 * stacks, queues and pairs — things you do before the real algorithm starts.
 */

// ─────────────────────────────────────────────
// Map ↔ List of Pairs
// ─────────────────────────────────────────────

/** Map<K,V> → List of Pair<K,V>. {a:1, b:2} → [(a,1),(b,2)] */
fun <K, V> Map<K, V>.toPairList(): List<Pair<K, V>> = this.entries.map { it.key to it.value }

/** List of Pair<K,V> → Map<K,V>. Duplicate keys: last one wins. */
fun <K, V> List<Pair<K, V>>.toMap(): Map<K, V> = this.associate { it }

/** List of Pair<K,V> → MutableMap<K,V>. */
fun <K, V> List<Pair<K, V>>.toMutableMap(): MutableMap<K, V> = this.associate { it }.toMutableMap()

// ─────────────────────────────────────────────
// Flatten / nest
// ─────────────────────────────────────────────

/** Flatten List<List<T>> → List<T>. [[1,2],[3,4]] → [1,2,3,4] */
fun <T> List<List<T>>.flatten(): List<T> = this.flatMap { it }

/** Flatten Array<IntArray> → IntArray. [[1,2],[3,4]] → [1,2,3,4] */
fun Array<IntArray>.flatten(): IntArray = this.flatMap { it.toList() }.toIntArray()

/**
 * Groups a flat list into chunks of given size.
 * [1,2,3,4,5,6].chunked(2) → [[1,2],[3,4],[5,6]]
 * Kotlin built-in, aliased for discoverability.
 */
fun <T> List<T>.groupIntoChunks(size: Int): List<List<T>> = this.chunked(size)

// ─────────────────────────────────────────────
// Zip / Unzip
// ─────────────────────────────────────────────

/**
 * Zips two lists into a list of pairs. Stops at the shorter one.
 * [1,2,3].zipWith(['a','b','c']) → [(1,'a'),(2,'b'),(3,'c')]
 */
fun <A, B> List<A>.zipWith(other: List<B>): List<Pair<A, B>> = this.zip(other)

/**
 * Unzips a list of pairs into two separate lists.
 * [(1,'a'),(2,'b')] → ([1,2], ['a','b'])
 */
fun <A, B> List<Pair<A, B>>.unzip(): Pair<List<A>, List<B>> = this.unzip()

/** Zips two IntArrays element-wise into a list of pairs. */
fun IntArray.zipWith(other: IntArray): List<Pair<Int, Int>> =
    this.toList().zip(other.toList())

// ─────────────────────────────────────────────
// Stack / Queue ↔ List
// ─────────────────────────────────────────────

/**
 * List → ArrayDeque (Kotlin's general-purpose stack/queue).
 * ArrayDeque supports both stack ops (addLast/removeLast)
 * and queue ops (addLast/removeFirst).
 */
fun <T> List<T>.toDeque(): ArrayDeque<T> = ArrayDeque(this)

/** ArrayDeque → List (top of stack = last element). */
fun <T> ArrayDeque<T>.toList(): List<T> = ArrayList(this)

/** Drains an ArrayDeque into a List (empties the deque). */
fun <T> ArrayDeque<T>.drainToList(): List<T> {
    val result = mutableListOf<T>()
    while (isNotEmpty()) result.add(removeFirst())
    return result
}

// ─────────────────────────────────────────────
// Set operations
// ─────────────────────────────────────────────

/** Intersection of two lists (elements in both). [1,2,3].intersectWith([2,3,4]) → [2,3] */
fun <T> List<T>.intersectWith(other: List<T>): List<T> =
    this.toSet().intersect(other.toSet()).toList()

/** Union of two lists (all unique elements). [1,2,3].unionWith([2,3,4]) → [1,2,3,4] */
fun <T> List<T>.unionWith(other: List<T>): List<T> =
    this.toSet().union(other.toSet()).toList()

/** Difference: elements in this list but not in other. [1,2,3].minus([2]) → [1,3] */
fun <T> List<T>.differenceWith(other: List<T>): List<T> =
    this.toSet().subtract(other.toSet()).toList()

// ─────────────────────────────────────────────
// Rotate collections
// ─────────────────────────────────────────────

/**
 * Rotates a list left by k positions. [1,2,3,4,5].rotateLeft(2) → [3,4,5,1,2]
 * Used in: LC 189 — Rotate Array (conceptually)
 */
fun <T> List<T>.rotateLeft(k: Int): List<T> {
    if (isEmpty()) return this
    val n = size; val shift = k % n
    return subList(shift, n) + subList(0, shift)
}

/** Rotates a list right by k positions. [1,2,3,4,5].rotateRight(2) → [4,5,1,2,3] */
fun <T> List<T>.rotateRight(k: Int): List<T> = rotateLeft(size - k % size)

// ─────────────────────────────────────────────
// Transpose (2D matrix rows ↔ columns)
// ─────────────────────────────────────────────

/**
 * Transposes a rectangular List<List<T>>.
 * [[1,2,3],[4,5,6]] → [[1,4],[2,5],[3,6]]
 */
fun <T> List<List<T>>.transpose(): List<List<T>> {
    if (isEmpty() || this[0].isEmpty()) return emptyList()
    return (this[0].indices).map { col -> this.map { row -> row[col] } }
}

/** Transposes a rectangular Array<IntArray>. */
fun Array<IntArray>.transpose(): Array<IntArray> {
    val rows = size; val cols = this[0].size
    return Array(cols) { col -> IntArray(rows) { row -> this[row][col] } }
}
