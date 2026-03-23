package com.leetcode.solutions.graph.problems.medium

/**
 * LeetCode 695: Max Area of Island
 * https://leetcode.com/problems/max-area-of-island/
 *
 * Difficulty: Medium
 * Companies: Amazon, Google, Meta
 *
 * Return the maximum area of an island (connected 1s). Return 0 if no island.
 *
 * Example: grid with islands → 6
 */

/** Solution 1: DFS - Time: O(m*n), Space: O(m*n) */
class MaxAreaOfIsland1 {
    fun maxAreaOfIsland(grid: Array<IntArray>): Int {
        var maxArea = 0

        for (r in grid.indices) {
            for (c in grid[0].indices) {
                if (grid[r][c] == 1) {
                    maxArea = maxOf(maxArea, dfs(grid, r, c))
                }
            }
        }

        return maxArea
    }

    private fun dfs(grid: Array<IntArray>, r: Int, c: Int): Int {
        if (r !in grid.indices || c !in grid[0].indices || grid[r][c] != 1) return 0

        grid[r][c] = 0

        return 1 + dfs(grid, r + 1, c) + dfs(grid, r - 1, c) +
                   dfs(grid, r, c + 1) + dfs(grid, r, c - 1)
    }
}

/** Solution 2: BFS - Time: O(m*n), Space: O(m*n) */
class MaxAreaOfIsland2 {
    fun maxAreaOfIsland(grid: Array<IntArray>): Int {
        var maxArea = 0

        for (r in grid.indices) {
            for (c in grid[0].indices) {
                if (grid[r][c] == 1) {
                    maxArea = maxOf(maxArea, bfsArea(grid, r, c))
                }
            }
        }

        return maxArea
    }

    private fun bfsArea(grid: Array<IntArray>, startR: Int, startC: Int): Int {
        val queue = ArrayDeque<Pair<Int, Int>>()
        queue.addLast(startR to startC)
        grid[startR][startC] = 0
        var area = 0

        val dirs = listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)

        while (queue.isNotEmpty()) {
            val (r, c) = queue.removeFirst()
            area++

            for ((dr, dc) in dirs) {
                val nr = r + dr; val nc = c + dc
                if (nr in grid.indices && nc in grid[0].indices && grid[nr][nc] == 1) {
                    grid[nr][nc] = 0
                    queue.addLast(nr to nc)
                }
            }
        }

        return area
    }
}
