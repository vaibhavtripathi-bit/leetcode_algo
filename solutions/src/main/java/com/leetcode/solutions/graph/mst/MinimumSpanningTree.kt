package com.leetcode.solutions.graph.mst

import java.util.PriorityQueue
import com.leetcode.solutions.graph.union_find.UnionFind

/**
 * Minimum Spanning Tree (MST)
 *
 * MST = subset of edges connecting all nodes with minimum total weight (no cycles).
 *
 * Kruskal: Sort edges, add greedily using Union-Find
 * Prim:    Grow tree from a start node using min-heap
 */

/** Kruskal's Algorithm - Time: O(E log E), Space: O(V) */
object Kruskal {
    fun mst(n: Int, edges: List<Triple<Int, Int, Int>>): Int {
        val sorted = edges.sortedBy { it.third }
        val uf = UnionFind(n)
        var totalWeight = 0

        for ((u, v, w) in sorted) {
            if (uf.union(u, v)) {
                totalWeight += w
                if (uf.components == 1) break
            }
        }

        return if (uf.components == 1) totalWeight else -1
    }
}

/** Prim's Algorithm (with adjacency list + min-heap) - Time: O(E log V), Space: O(V) */
object Prim {
    fun mst(n: Int, graph: Map<Int, List<Pair<Int, Int>>>): Int {
        val visited = BooleanArray(n)
        val minHeap = PriorityQueue<Pair<Int, Int>>(compareBy { it.first })
        minHeap.offer(0 to 0)
        var totalWeight = 0
        var nodesInMST = 0

        while (minHeap.isNotEmpty() && nodesInMST < n) {
            val (cost, node) = minHeap.poll()
            if (visited[node]) continue

            visited[node] = true
            totalWeight += cost
            nodesInMST++

            for ((neighbor, weight) in graph[node] ?: emptyList()) {
                if (!visited[neighbor]) minHeap.offer(weight to neighbor)
            }
        }

        return if (nodesInMST == n) totalWeight else -1
    }
}

/**
 * LeetCode 1584: Min Cost to Connect All Points
 * https://leetcode.com/problems/min-cost-to-connect-all-points/
 *
 * Difficulty: Medium
 * Companies: Google, Amazon
 *
 * Connect all points with min cost. Cost = Manhattan distance.
 */
class MinCostConnectPoints {

    /** Kruskal approach */
    fun minCostConnectPoints(points: Array<IntArray>): Int {
        val edges = buildAllEdges(points)
        return Kruskal.mst(points.size, edges)
    }

    private fun buildAllEdges(points: Array<IntArray>): List<Triple<Int, Int, Int>> {
        val edges = mutableListOf<Triple<Int, Int, Int>>()
        for (i in points.indices) {
            for (j in i + 1 until points.size) {
                edges.add(Triple(i, j, manhattan(points[i], points[j])))
            }
        }
        return edges
    }

    private fun manhattan(a: IntArray, b: IntArray) =
        Math.abs(a[0] - b[0]) + Math.abs(a[1] - b[1])

    /** Prim approach (better for dense graphs) */
    fun minCostPrim(points: Array<IntArray>): Int {
        val n = points.size
        val minDist = IntArray(n) { Int.MAX_VALUE }
        val visited = BooleanArray(n)
        minDist[0] = 0
        var total = 0

        repeat(n) {
            val u = findMinUnvisited(minDist, visited)
            visited[u] = true
            total += minDist[u]

            for (v in 0 until n) {
                if (!visited[v]) {
                    val d = manhattan(points[u], points[v])
                    if (d < minDist[v]) minDist[v] = d
                }
            }
        }

        return total
    }

    private fun findMinUnvisited(dist: IntArray, visited: BooleanArray): Int {
        var min = Int.MAX_VALUE
        var idx = -1
        for (i in dist.indices) {
            if (!visited[i] && dist[i] < min) { min = dist[i]; idx = i }
        }
        return idx
    }
}
