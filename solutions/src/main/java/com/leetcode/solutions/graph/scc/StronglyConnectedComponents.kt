package com.leetcode.solutions.graph.scc

/**
 * Strongly Connected Components (SCC)
 *
 * SCC: A maximal set of vertices such that every vertex is reachable from every other.
 *
 * Tarjan's Algorithm (one DFS pass):
 * - disc[v]: discovery time
 * - low[v]: lowest disc reachable from subtree rooted at v
 * - If low[v] == disc[v]: v is root of an SCC → pop stack
 *
 * Time: O(V + E), Space: O(V)
 */
object Tarjan {
    private var timer = 0

    fun findSCCs(n: Int, graph: Array<MutableList<Int>>): List<List<Int>> {
        timer = 0
        val disc = IntArray(n) { -1 }
        val low = IntArray(n)
        val onStack = BooleanArray(n)
        val stack = ArrayDeque<Int>()
        val sccs = mutableListOf<List<Int>>()

        for (v in 0 until n) {
            if (disc[v] == -1) dfs(v, graph, disc, low, onStack, stack, sccs)
        }

        return sccs
    }

    private fun dfs(
        v: Int, graph: Array<MutableList<Int>>,
        disc: IntArray, low: IntArray, onStack: BooleanArray,
        stack: ArrayDeque<Int>, sccs: MutableList<List<Int>>
    ) {
        disc[v] = timer
        low[v] = timer
        timer++
        stack.addLast(v)
        onStack[v] = true

        for (neighbor in graph[v]) {
            if (disc[neighbor] == -1) {
                dfs(neighbor, graph, disc, low, onStack, stack, sccs)
                low[v] = minOf(low[v], low[neighbor])
            } else if (onStack[neighbor]) {
                low[v] = minOf(low[v], disc[neighbor])
            }
        }

        if (low[v] == disc[v]) {
            sccs.add(popSCC(stack, onStack, v))
        }
    }

    private fun popSCC(stack: ArrayDeque<Int>, onStack: BooleanArray, root: Int): List<Int> {
        val scc = mutableListOf<Int>()
        while (true) {
            val node = stack.removeLast()
            onStack[node] = false
            scc.add(node)
            if (node == root) break
        }
        return scc
    }
}

/**
 * LeetCode 1192: Critical Connections in a Network (Bridges)
 * https://leetcode.com/problems/critical-connections-in-a-network/
 *
 * Difficulty: Hard
 * Companies: Amazon, Google
 *
 * A bridge is an edge whose removal disconnects the graph.
 * Same idea as Tarjan but looking for bridges, not SCCs.
 *
 * If low[neighbor] > disc[node]: edge (node, neighbor) is a bridge
 */
class CriticalConnections {
    private var timer = 0

    fun criticalConnections(n: Int, connections: List<List<Int>>): List<List<Int>> {
        val graph = buildGraph(n, connections)
        val disc = IntArray(n) { -1 }
        val low = IntArray(n)
        val bridges = mutableListOf<List<Int>>()

        for (v in 0 until n) {
            if (disc[v] == -1) dfs(v, -1, graph, disc, low, bridges)
        }

        return bridges
    }

    private fun dfs(
        v: Int, parent: Int, graph: Array<MutableList<Int>>,
        disc: IntArray, low: IntArray, bridges: MutableList<List<Int>>
    ) {
        disc[v] = timer
        low[v] = timer
        timer++

        for (neighbor in graph[v]) {
            if (neighbor == parent) continue
            if (disc[neighbor] == -1) {
                dfs(neighbor, v, graph, disc, low, bridges)
                low[v] = minOf(low[v], low[neighbor])
                if (low[neighbor] > disc[v]) bridges.add(listOf(v, neighbor))
            } else {
                low[v] = minOf(low[v], disc[neighbor])
            }
        }
    }

    private fun buildGraph(n: Int, connections: List<List<Int>>): Array<MutableList<Int>> {
        val graph = Array(n) { mutableListOf<Int>() }
        for ((u, v) in connections) {
            graph[u].add(v)
            graph[v].add(u)
        }
        return graph
    }
}
