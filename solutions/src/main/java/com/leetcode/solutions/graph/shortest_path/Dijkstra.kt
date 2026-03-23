package com.leetcode.solutions.graph.shortest_path

import java.util.PriorityQueue

/**
 * Dijkstra's Algorithm — Single Source Shortest Path (Non-Negative Weights)
 *
 * Key Ideas:
 * - Greedy: always expand the node with the current shortest known distance
 * - Use a min-heap for efficient "find minimum distance" operation
 * - Skip stale entries in the heap (lazy deletion)
 *
 * Time: O((V + E) log V), Space: O(V)
 */
object Dijkstra {

    fun shortestPath(
        graph: Map<Int, List<Pair<Int, Int>>>,   // node -> [(neighbor, weight)]
        source: Int,
        numNodes: Int
    ): IntArray {
        val dist = IntArray(numNodes) { Int.MAX_VALUE }
        dist[source] = 0

        val minHeap = PriorityQueue<Pair<Int, Int>>(compareBy { it.first })
        minHeap.offer(0 to source)

        while (minHeap.isNotEmpty()) {
            val (currentDist, node) = minHeap.poll()

            if (isStale(currentDist, dist[node])) continue

            for ((neighbor, weight) in graph[node] ?: emptyList()) {
                val newDist = dist[node] + weight
                if (newDist < dist[neighbor]) {
                    dist[neighbor] = newDist
                    minHeap.offer(newDist to neighbor)
                }
            }
        }

        return dist
    }

    private fun isStale(currentDist: Int, bestKnown: Int) = currentDist > bestKnown
}

/**
 * LeetCode 743: Network Delay Time
 * https://leetcode.com/problems/network-delay-time/
 *
 * Difficulty: Medium
 * Companies: Google, Amazon, Meta
 *
 * Find the time for all nodes to receive signal from source k.
 * Return -1 if some node never receives signal.
 */
class NetworkDelayTime {
    fun networkDelayTime(times: Array<IntArray>, n: Int, k: Int): Int {
        val graph = buildGraph(times)
        val dist = Dijkstra.shortestPath(graph, k - 1, n)

        val maxDelay = dist.max()!!
        return if (maxDelay == Int.MAX_VALUE) -1 else maxDelay
    }

    private fun buildGraph(times: Array<IntArray>): Map<Int, List<Pair<Int, Int>>> {
        val graph = HashMap<Int, MutableList<Pair<Int, Int>>>()
        for ((u, v, w) in times) {
            graph.getOrPut(u - 1) { mutableListOf() }.add(v - 1 to w)
        }
        return graph
    }
}
