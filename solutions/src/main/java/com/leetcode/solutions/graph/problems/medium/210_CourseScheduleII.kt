package com.leetcode.solutions.graph.problems.medium

/**
 * LeetCode 210: Course Schedule II
 * https://leetcode.com/problems/course-schedule-ii/
 *
 * Difficulty: Medium
 * Companies: Google, Amazon, Microsoft, Meta
 *
 * Return the order to take courses (topological sort), or [] if impossible.
 */

/** Solution 1: Kahn's Algorithm BFS - Time: O(V+E), Space: O(V+E) */
class CourseScheduleII1 {
    fun findOrder(numCourses: Int, prerequisites: Array<IntArray>): IntArray {
        val graph = Array(numCourses) { mutableListOf<Int>() }
        val inDegree = IntArray(numCourses)

        for ((course, prereq) in prerequisites) {
            graph[prereq].add(course)
            inDegree[course]++
        }

        val queue = ArrayDeque<Int>()
        inDegree.indices.filter { inDegree[it] == 0 }.forEach { queue.addLast(it) }

        val order = mutableListOf<Int>()
        while (queue.isNotEmpty()) {
            val course = queue.removeFirst()
            order.add(course)
            graph[course].forEach { next ->
                if (--inDegree[next] == 0) queue.addLast(next)
            }
        }

        return if (order.size == numCourses) order.toIntArray() else intArrayOf()
    }
}

/** Solution 2: DFS Topological Sort - Time: O(V+E), Space: O(V+E) */
class CourseScheduleII2 {
    fun findOrder(numCourses: Int, prerequisites: Array<IntArray>): IntArray {
        val graph = Array(numCourses) { mutableListOf<Int>() }
        prerequisites.forEach { (course, prereq) -> graph[prereq].add(course) }

        val visited = IntArray(numCourses)
        val result = ArrayDeque<Int>()

        for (i in 0 until numCourses) {
            if (visited[i] == 0) {
                if (hasCycle(i, graph, visited, result)) return intArrayOf()
            }
        }

        return result.toIntArray()
    }

    private fun hasCycle(
        node: Int, graph: Array<MutableList<Int>>,
        visited: IntArray, result: ArrayDeque<Int>
    ): Boolean {
        if (visited[node] == 1) return true
        if (visited[node] == 2) return false

        visited[node] = 1

        if (graph[node].any { hasCycle(it, graph, visited, result) }) return true

        visited[node] = 2
        result.addFirst(node)
        return false
    }
}
