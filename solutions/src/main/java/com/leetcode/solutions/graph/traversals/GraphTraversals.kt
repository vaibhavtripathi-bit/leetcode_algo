package com.leetcode.solutions.graph.traversals

/**
 * Core Graph Traversal Templates
 */
object GraphTraversals {

    // ============== DFS ==============

    fun dfsRecursive(graph: Map<Int, List<Int>>, start: Int): List<Int> {
        val visited = HashSet<Int>()
        val result = mutableListOf<Int>()
        dfsHelper(start, graph, visited, result)
        return result
    }

    private fun dfsHelper(
        node: Int,
        graph: Map<Int, List<Int>>,
        visited: HashSet<Int>,
        result: MutableList<Int>
    ) {
        if (node in visited) return
        visited.add(node)
        result.add(node)
        graph[node]?.forEach { dfsHelper(it, graph, visited, result) }
    }

    fun dfsIterative(graph: Map<Int, List<Int>>, start: Int): List<Int> {
        val visited = HashSet<Int>()
        val result = mutableListOf<Int>()
        val stack = ArrayDeque<Int>()
        stack.addLast(start)

        while (stack.isNotEmpty()) {
            val node = stack.removeLast()
            if (node in visited) continue
            visited.add(node)
            result.add(node)
            graph[node]?.reversed()?.forEach { stack.addLast(it) }
        }

        return result
    }

    // ============== BFS ==============

    fun bfs(graph: Map<Int, List<Int>>, start: Int): List<Int> {
        val visited = HashSet<Int>()
        val result = mutableListOf<Int>()
        val queue = ArrayDeque<Int>()

        queue.addLast(start)
        visited.add(start)

        while (queue.isNotEmpty()) {
            val node = queue.removeFirst()
            result.add(node)

            graph[node]?.forEach { neighbor ->
                if (neighbor !in visited) {
                    visited.add(neighbor)
                    queue.addLast(neighbor)
                }
            }
        }

        return result
    }

    fun shortestPath(graph: Map<Int, List<Int>>, start: Int, end: Int): Int {
        val visited = HashSet<Int>()
        val queue = ArrayDeque<Pair<Int, Int>>()
        queue.addLast(start to 0)
        visited.add(start)

        while (queue.isNotEmpty()) {
            val (node, dist) = queue.removeFirst()
            if (node == end) return dist

            graph[node]?.forEach { neighbor ->
                if (neighbor !in visited) {
                    visited.add(neighbor)
                    queue.addLast(neighbor to dist + 1)
                }
            }
        }

        return -1
    }

    // ============== Topological Sort ==============

    fun topologicalSort(numNodes: Int, edges: List<Pair<Int, Int>>): List<Int>? {
        val graph = buildAdjList(numNodes, edges)
        val inDegree = computeInDegrees(numNodes, edges)
        return kahnBFS(numNodes, graph, inDegree)
    }

    private fun buildAdjList(n: Int, edges: List<Pair<Int, Int>>): Map<Int, List<Int>> {
        val graph = HashMap<Int, MutableList<Int>>()
        repeat(n) { graph[it] = mutableListOf() }
        edges.forEach { (from, to) -> graph[from]!!.add(to) }
        return graph
    }

    private fun computeInDegrees(n: Int, edges: List<Pair<Int, Int>>): IntArray {
        val inDegree = IntArray(n)
        edges.forEach { (_, to) -> inDegree[to]++ }
        return inDegree
    }

    private fun kahnBFS(n: Int, graph: Map<Int, List<Int>>, inDegree: IntArray): List<Int>? {
        val queue = ArrayDeque<Int>()
        inDegree.indices.filter { inDegree[it] == 0 }.forEach { queue.addLast(it) }

        val order = mutableListOf<Int>()
        while (queue.isNotEmpty()) {
            val node = queue.removeFirst()
            order.add(node)
            graph[node]?.forEach { neighbor ->
                if (--inDegree[neighbor] == 0) queue.addLast(neighbor)
            }
        }

        return if (order.size == n) order else null
    }
}
