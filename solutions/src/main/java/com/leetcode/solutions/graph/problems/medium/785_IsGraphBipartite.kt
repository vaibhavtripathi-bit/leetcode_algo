package com.leetcode.solutions.graph.problems.medium

/**
 * LeetCode 785: Is Graph Bipartite?
 * https://leetcode.com/problems/is-graph-bipartite/
 *
 * Difficulty: Medium
 * Companies: Amazon, Google, Meta
 *
 * A graph is bipartite if nodes can be colored with 2 colors such that
 * no two adjacent nodes share the same color.
 *
 * Example: graph = [[1,3],[0,2],[1,3],[0,2]] → true
 */

/** Solution 1: BFS Coloring - Time: O(V+E), Space: O(V) */
class IsBipartite1 {
    fun isBipartite(graph: Array<IntArray>): Boolean {
        val color = IntArray(graph.size) { -1 }

        for (node in graph.indices) {
            if (color[node] == -1) {
                if (!bfsColor(graph, node, color)) return false
            }
        }

        return true
    }

    private fun bfsColor(graph: Array<IntArray>, start: Int, color: IntArray): Boolean {
        val queue = ArrayDeque<Int>()
        queue.addLast(start)
        color[start] = 0

        while (queue.isNotEmpty()) {
            val node = queue.removeFirst()
            for (neighbor in graph[node]) {
                when {
                    color[neighbor] == -1 -> {
                        color[neighbor] = 1 - color[node]
                        queue.addLast(neighbor)
                    }
                    color[neighbor] == color[node] -> return false
                }
            }
        }

        return true
    }
}

/** Solution 2: DFS Coloring - Time: O(V+E), Space: O(V) */
class IsBipartite2 {
    fun isBipartite(graph: Array<IntArray>): Boolean {
        val color = IntArray(graph.size) { -1 }

        return graph.indices.all { node ->
            color[node] != -1 || dfsColor(graph, node, 0, color)
        }
    }

    private fun dfsColor(graph: Array<IntArray>, node: Int, c: Int, color: IntArray): Boolean {
        color[node] = c

        return graph[node].all { neighbor ->
            when {
                color[neighbor] == -1 -> dfsColor(graph, neighbor, 1 - c, color)
                color[neighbor] != c -> true
                else -> false
            }
        }
    }
}

/** Solution 3: Union-Find (nodes in same group can't be adjacent) - Time: O(V+E), Space: O(V) */
class IsBipartite3 {
    fun isBipartite(graph: Array<IntArray>): Boolean {
        val parent = IntArray(graph.size) { it }

        fun find(x: Int): Int {
            if (parent[x] != x) parent[x] = find(parent[x])
            return parent[x]
        }

        fun union(x: Int, y: Int) { parent[find(x)] = find(y) }

        for (node in graph.indices) {
            if (graph[node].isEmpty()) continue

            val firstNeighbor = graph[node][0]
            for (neighbor in graph[node]) {
                if (find(node) == find(neighbor)) return false
                union(firstNeighbor, neighbor)
            }
        }

        return true
    }
}
