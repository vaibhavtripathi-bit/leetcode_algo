package com.leetcode.solutions.graph.union_find

/**
 * Union-Find (Disjoint Set Union) Data Structure
 *
 * Optimizations:
 * - Path Compression in find(): makes trees flat
 * - Union by Rank: attaches smaller tree under larger
 *
 * Both together give near O(1) per operation (O(α(n)) inverse Ackermann)
 */
class UnionFind(n: Int) {
    private val parent = IntArray(n) { it }
    private val rank = IntArray(n)
    var components = n
        private set

    fun find(x: Int): Int {
        if (parent[x] != x) parent[x] = find(parent[x])    // path compression
        return parent[x]
    }

    fun union(x: Int, y: Int): Boolean {
        val px = find(x)
        val py = find(y)
        if (px == py) return false    // already same component

        attachByRank(px, py)
        components--
        return true
    }

    fun connected(x: Int, y: Int) = find(x) == find(y)

    private fun attachByRank(px: Int, py: Int) {
        when {
            rank[px] < rank[py] -> parent[px] = py
            rank[px] > rank[py] -> parent[py] = px
            else -> { parent[py] = px; rank[px]++ }
        }
    }
}

/**
 * LeetCode 684: Redundant Connection
 * https://leetcode.com/problems/redundant-connection/
 *
 * Difficulty: Medium
 * Companies: Amazon, Google
 *
 * Find the edge that creates a cycle in a tree. Return last such edge.
 */
class RedundantConnection {
    fun findRedundantConnection(edges: Array<IntArray>): IntArray {
        val uf = UnionFind(edges.size + 1)

        for (edge in edges) {
            if (!uf.union(edge[0], edge[1])) return edge
        }

        return intArrayOf()
    }
}

/**
 * LeetCode 323: Number of Connected Components in Undirected Graph
 * https://leetcode.com/problems/number-of-connected-components-in-an-undirected-graph/
 *
 * Difficulty: Medium
 * Companies: LinkedIn, Google, Amazon
 */
class NumberOfComponents {
    fun countComponents(n: Int, edges: Array<IntArray>): Int {
        val uf = UnionFind(n)
        edges.forEach { (u, v) -> uf.union(u, v) }
        return uf.components
    }
}

/**
 * LeetCode 547: Number of Provinces
 * https://leetcode.com/problems/number-of-provinces/
 *
 * Difficulty: Medium
 * Companies: Google, Amazon, Microsoft
 */
class NumberOfProvinces {
    fun findCircleNum(isConnected: Array<IntArray>): Int {
        val n = isConnected.size
        val uf = UnionFind(n)

        for (i in 0 until n) {
            for (j in i + 1 until n) {
                if (isConnected[i][j] == 1) uf.union(i, j)
            }
        }

        return uf.components
    }
}
