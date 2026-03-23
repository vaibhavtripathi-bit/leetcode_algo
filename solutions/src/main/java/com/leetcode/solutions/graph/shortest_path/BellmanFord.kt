package com.leetcode.solutions.graph.shortest_path

/**
 * Bellman-Ford Algorithm — Handles Negative Edge Weights
 *
 * Key Ideas:
 * - Relax ALL edges V-1 times (longest simple path has V-1 edges)
 * - If V-th relaxation still improves distance → negative cycle
 *
 * Time: O(V * E), Space: O(V)
 * Use Bellman-Ford over Dijkstra when: negative edges exist
 */
object BellmanFord {

    fun shortestPath(
        edges: List<Triple<Int, Int, Int>>,    // (from, to, weight)
        source: Int,
        numNodes: Int
    ): IntArray? {
        val dist = IntArray(numNodes) { Int.MAX_VALUE }
        dist[source] = 0

        relaxAllEdges(edges, dist, numNodes - 1)

        return if (hasNegativeCycle(edges, dist)) null else dist
    }

    private fun relaxAllEdges(
        edges: List<Triple<Int, Int, Int>>, dist: IntArray, times: Int
    ) {
        repeat(times) {
            for ((u, v, w) in edges) {
                if (dist[u] != Int.MAX_VALUE && dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w
                }
            }
        }
    }

    private fun hasNegativeCycle(
        edges: List<Triple<Int, Int, Int>>, dist: IntArray
    ): Boolean {
        return edges.any { (u, v, w) ->
            dist[u] != Int.MAX_VALUE && dist[u] + w < dist[v]
        }
    }
}

/**
 * LeetCode 787: Cheapest Flights Within K Stops
 * https://leetcode.com/problems/cheapest-flights-within-k-stops/
 *
 * Difficulty: Medium
 * Companies: Amazon, Google, Meta
 *
 * Find cheapest price from src to dst with at most k stops.
 * Key: use k+1 relaxations of Bellman-Ford (k stops = k+1 edges).
 */
class CheapestFlights {
    fun findCheapestPrice(n: Int, flights: Array<IntArray>, src: Int, dst: Int, k: Int): Int {
        var dist = IntArray(n) { Int.MAX_VALUE }
        dist[src] = 0

        repeat(k + 1) {
            val temp = dist.copyOf()
            for ((u, v, w) in flights) {
                if (dist[u] != Int.MAX_VALUE && dist[u] + w < temp[v]) {
                    temp[v] = dist[u] + w
                }
            }
            dist = temp
        }

        return if (dist[dst] == Int.MAX_VALUE) -1 else dist[dst]
    }
}
