package com.leetcode.solutions.utils

/**
 * GraphUtils — reusable graph building blocks for FAANG interviews.
 *
 * Building an adjacency list from edges is the first thing you write
 * in almost every graph problem. Having it as a utility means you
 * can skip boilerplate and focus on the algorithm.
 */

// ─────────────────────────────────────────────
// Adjacency list builders
// ─────────────────────────────────────────────

/**
 * Builds an undirected adjacency list from an edge array.
 * edges[i] = [u, v]
 *
 * Usage:
 *   val adj = buildAdjList(n, edges)
 *   for (neighbor in adj[node]) { ... }
 */
fun buildAdjList(n: Int, edges: Array<IntArray>): Array<MutableList<Int>> {
    val adj = Array(n) { mutableListOf<Int>() }
    for ((u, v) in edges) {
        adj[u].add(v)
        adj[v].add(u)
    }
    return adj
}

/**
 * Builds a directed adjacency list from an edge array.
 * edges[i] = [from, to]
 */
fun buildDirectedAdjList(n: Int, edges: Array<IntArray>): Array<MutableList<Int>> {
    val adj = Array(n) { mutableListOf<Int>() }
    for ((from, to) in edges) adj[from].add(to)
    return adj
}

/**
 * Builds a weighted undirected adjacency list.
 * edges[i] = [u, v, weight]
 * Each entry is a Pair(neighbor, weight).
 */
fun buildWeightedAdjList(n: Int, edges: Array<IntArray>): Array<MutableList<Pair<Int, Int>>> {
    val adj = Array(n) { mutableListOf<Pair<Int, Int>>() }
    for ((u, v, w) in edges) {
        adj[u].add(v to w)
        adj[v].add(u to w)
    }
    return adj
}

/**
 * Builds a weighted directed adjacency list.
 * edges[i] = [from, to, weight]
 */
fun buildWeightedDirectedAdjList(n: Int, edges: Array<IntArray>): Array<MutableList<Pair<Int, Int>>> {
    val adj = Array(n) { mutableListOf<Pair<Int, Int>>() }
    for ((from, to, w) in edges) adj[from].add(to to w)
    return adj
}

// ─────────────────────────────────────────────
// Union-Find (Disjoint Set Union)
//
// Reusable class for:
//   Number of Connected Components, Redundant Connection,
//   Accounts Merge, Graph Valid Tree, Kruskal's MST, etc.
// ─────────────────────────────────────────────

/**
 * Union-Find with path compression and union by rank.
 * All operations are effectively O(α(n)) ≈ O(1).
 */
class UnionFind(n: Int) {

    private val parent = IntArray(n) { it }
    private val rank   = IntArray(n) { 0 }

    /** Number of distinct connected components. */
    var components: Int = n
        private set

    /** Find root of x with path compression. */
    fun find(x: Int): Int {
        if (parent[x] != x) parent[x] = find(parent[x])
        return parent[x]
    }

    /**
     * Union the components containing x and y.
     * Returns true if they were in different components (a new union happened).
     */
    fun union(x: Int, y: Int): Boolean {
        val px = find(x); val py = find(y)
        if (px == py) return false

        when {
            rank[px] < rank[py] -> parent[px] = py
            rank[px] > rank[py] -> parent[py] = px
            else                -> { parent[py] = px; rank[px]++ }
        }

        components--
        return true
    }

    /** True if x and y belong to the same component. */
    fun connected(x: Int, y: Int): Boolean = find(x) == find(y)
}

// ─────────────────────────────────────────────
// Topological sort (Kahn's BFS-based)
//
// Used in: Course Schedule I/II, Alien Dictionary,
//          Minimum Height Trees, etc.
// ─────────────────────────────────────────────

/**
 * Kahn's topological sort on a directed graph.
 * Returns a valid topological order, or an empty list if a cycle exists.
 *
 * @param n number of nodes (0-indexed)
 * @param adj directed adjacency list
 */
fun topologicalSort(n: Int, adj: Array<MutableList<Int>>): List<Int> {
    val inDegree = IntArray(n)
    for (u in 0 until n) for (v in adj[u]) inDegree[v]++

    val queue = ArrayDeque<Int>()
    for (i in 0 until n) if (inDegree[i] == 0) queue.add(i)

    val order = mutableListOf<Int>()
    while (queue.isNotEmpty()) {
        val node = queue.removeFirst()
        order.add(node)
        for (neighbor in adj[node]) {
            inDegree[neighbor]--
            if (inDegree[neighbor] == 0) queue.add(neighbor)
        }
    }

    return if (order.size == n) order else emptyList()  // empty = cycle detected
}
