package com.leetcode.solutions.graph.problems.medium

/**
 * LeetCode 200: Number of Islands
 * https://leetcode.com/problems/number-of-islands/
 *
 * Difficulty: Medium
 * Companies: Google, Amazon, Microsoft, Meta, Apple
 *
 * Count the number of islands (connected '1's surrounded by '0's).
 *
 * Example: grid = [["1","1","0"],["0","1","0"],["0","0","1"]] → 2
 */

/** Solution 1: DFS (Sink the Island) - Time: O(m*n), Space: O(m*n) */
class NumberOfIslands1 {
    fun numIslands(grid: Array<CharArray>): Int {
        var count = 0

        for (r in grid.indices) {
            for (c in grid[0].indices) {
                if (isUnvisitedLand(grid, r, c)) {
                    sinkIsland(grid, r, c)
                    count++
                }
            }
        }

        return count
    }

    private fun isUnvisitedLand(grid: Array<CharArray>, r: Int, c: Int) = grid[r][c] == '1'

    private fun sinkIsland(grid: Array<CharArray>, r: Int, c: Int) {
        if (r < 0 || r >= grid.size || c < 0 || c >= grid[0].size || grid[r][c] != '1') return

        grid[r][c] = '0'

        sinkIsland(grid, r + 1, c)
        sinkIsland(grid, r - 1, c)
        sinkIsland(grid, r, c + 1)
        sinkIsland(grid, r, c - 1)
    }
}

/** Solution 2: BFS - Time: O(m*n), Space: O(min(m,n)) */
class NumberOfIslands2 {
    fun numIslands(grid: Array<CharArray>): Int {
        var count = 0

        for (r in grid.indices) {
            for (c in grid[0].indices) {
                if (grid[r][c] == '1') {
                    bfsFlood(grid, r, c)
                    count++
                }
            }
        }

        return count
    }

    private fun bfsFlood(grid: Array<CharArray>, startR: Int, startC: Int) {
        val queue = ArrayDeque<Pair<Int, Int>>()
        queue.addLast(startR to startC)
        grid[startR][startC] = '0'

        val directions = listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)

        while (queue.isNotEmpty()) {
            val (r, c) = queue.removeFirst()

            for ((dr, dc) in directions) {
                val nr = r + dr
                val nc = c + dc
                if (isValidLand(grid, nr, nc)) {
                    grid[nr][nc] = '0'
                    queue.addLast(nr to nc)
                }
            }
        }
    }

    private fun isValidLand(grid: Array<CharArray>, r: Int, c: Int) =
        r in grid.indices && c in grid[0].indices && grid[r][c] == '1'
}

/** Solution 3: Union-Find - Time: O(m*n), Space: O(m*n) */
class NumberOfIslands3 {
    fun numIslands(grid: Array<CharArray>): Int {
        val rows = grid.size
        val cols = grid[0].size
        val uf = UnionFind(rows * cols)
        var count = 0

        for (r in grid.indices) {
            for (c in grid[0].indices) {
                if (grid[r][c] == '1') {
                    count++
                    connectNeighbors(grid, r, c, rows, cols, uf) { count-- }
                }
            }
        }

        return count
    }

    private fun connectNeighbors(
        grid: Array<CharArray>, r: Int, c: Int,
        rows: Int, cols: Int, uf: UnionFind,
        onMerge: () -> Unit
    ) {
        val current = r * cols + c
        val directions = listOf(0 to 1, 1 to 0)

        for ((dr, dc) in directions) {
            val nr = r + dr; val nc = c + dc
            if (nr < rows && nc < cols && grid[nr][nc] == '1') {
                if (uf.union(current, nr * cols + nc)) onMerge()
            }
        }
    }

    private class UnionFind(n: Int) {
        val parent = IntArray(n) { it }
        val rank = IntArray(n)

        fun find(x: Int): Int {
            if (parent[x] != x) parent[x] = find(parent[x])
            return parent[x]
        }

        fun union(x: Int, y: Int): Boolean {
            val px = find(x); val py = find(y)
            if (px == py) return false
            if (rank[px] < rank[py]) parent[px] = py
            else if (rank[px] > rank[py]) parent[py] = px
            else { parent[py] = px; rank[px]++ }
            return true
        }
    }
}
