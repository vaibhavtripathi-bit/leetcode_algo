package com.leetcode.solutions.graph.problems.medium

/**
 * LeetCode 994: Rotting Oranges
 * https://leetcode.com/problems/rotting-oranges/
 *
 * Difficulty: Medium
 * Companies: Amazon, Google, Meta
 *
 * 0 = empty, 1 = fresh, 2 = rotten. Each minute, rotten spreads to adjacent fresh.
 * Return minimum minutes for all oranges to rot, or -1 if impossible.
 *
 * Example: [[2,1,1],[1,1,0],[0,1,1]] → 4
 */

/** Solution 1: Multi-Source BFS - Time: O(m*n), Space: O(m*n) */
class RottingOranges1 {
    fun orangesRotting(grid: Array<IntArray>): Int {
        val (rottedQueue, freshCount) = scanGrid(grid)

        if (freshCount == 0) return 0

        return spreadRot(grid, rottedQueue, freshCount)
    }

    private fun scanGrid(grid: Array<IntArray>): Pair<ArrayDeque<Pair<Int, Int>>, Int> {
        val queue = ArrayDeque<Pair<Int, Int>>()
        var fresh = 0

        for (r in grid.indices) {
            for (c in grid[0].indices) {
                when (grid[r][c]) {
                    2 -> queue.addLast(r to c)
                    1 -> fresh++
                }
            }
        }

        return queue to fresh
    }

    private fun spreadRot(
        grid: Array<IntArray>,
        queue: ArrayDeque<Pair<Int, Int>>,
        initialFresh: Int
    ): Int {
        val directions = listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)
        var minutes = 0
        var fresh = initialFresh

        while (queue.isNotEmpty() && fresh > 0) {
            minutes++
            repeat(queue.size) {
                val (r, c) = queue.removeFirst()
                for ((dr, dc) in directions) {
                    val nr = r + dr; val nc = c + dc
                    if (isFresh(grid, nr, nc)) {
                        grid[nr][nc] = 2
                        fresh--
                        queue.addLast(nr to nc)
                    }
                }
            }
        }

        return if (fresh == 0) minutes else -1
    }

    private fun isFresh(grid: Array<IntArray>, r: Int, c: Int) =
        r in grid.indices && c in grid[0].indices && grid[r][c] == 1
}
