package com.leetcode.solutions.graph.problems.medium

/**
 * LeetCode 207: Course Schedule
 * https://leetcode.com/problems/course-schedule/
 *
 * Difficulty: Medium
 * Companies: Google, Amazon, Microsoft, Meta, Apple
 *
 * Can you finish all courses? Return true if no cycle in prerequisite graph.
 *
 * Example: numCourses = 2, prerequisites = [[1,0]] → true
 * Example: numCourses = 2, prerequisites = [[1,0],[0,1]] → false (cycle)
 */

/** Solution 1: Kahn's Algorithm BFS (Topological Sort) - Time: O(V+E), Space: O(V+E) */
class CourseSchedule1 {
    fun canFinish(numCourses: Int, prerequisites: Array<IntArray>): Boolean {
        val (graph, inDegree) = buildGraphAndInDegree(numCourses, prerequisites)
        return processAllNodes(numCourses, graph, inDegree)
    }

    private fun buildGraphAndInDegree(
        n: Int, prereqs: Array<IntArray>
    ): Pair<Array<MutableList<Int>>, IntArray> {
        val graph = Array(n) { mutableListOf<Int>() }
        val inDegree = IntArray(n)

        for ((course, prereq) in prereqs) {
            graph[prereq].add(course)
            inDegree[course]++
        }

        return graph to inDegree
    }

    private fun processAllNodes(
        n: Int, graph: Array<MutableList<Int>>, inDegree: IntArray
    ): Boolean {
        val queue = ArrayDeque<Int>()
        inDegree.indices.filter { inDegree[it] == 0 }.forEach { queue.addLast(it) }

        var completed = 0
        while (queue.isNotEmpty()) {
            val course = queue.removeFirst()
            completed++

            graph[course].forEach { next ->
                if (--inDegree[next] == 0) queue.addLast(next)
            }
        }

        return completed == n
    }
}

/** Solution 2: DFS Cycle Detection - Time: O(V+E), Space: O(V+E) */
class CourseSchedule2 {
    fun canFinish(numCourses: Int, prerequisites: Array<IntArray>): Boolean {
        val graph = buildGraph(numCourses, prerequisites)

        val visited = IntArray(numCourses)

        return (0 until numCourses).all { course ->
            if (visited[course] == 0) !hasCycle(course, graph, visited)
            else true
        }
    }

    private fun buildGraph(n: Int, prereqs: Array<IntArray>): Array<MutableList<Int>> {
        val graph = Array(n) { mutableListOf<Int>() }
        prereqs.forEach { (course, prereq) -> graph[prereq].add(course) }
        return graph
    }

    private fun hasCycle(node: Int, graph: Array<MutableList<Int>>, visited: IntArray): Boolean {
        if (visited[node] == 1) return true   // currently on stack = cycle
        if (visited[node] == 2) return false  // fully processed

        visited[node] = 1

        val foundCycle = graph[node].any { hasCycle(it, graph, visited) }

        visited[node] = 2
        return foundCycle
    }

    private fun buildGraph(n: Int, prereqs: Array<IntArray>): Array<MutableList<Int>> {
        val graph = Array(n) { mutableListOf<Int>() }
        prereqs.forEach { (course, prereq) -> graph[prereq].add(course) }
        return graph
    }
}
