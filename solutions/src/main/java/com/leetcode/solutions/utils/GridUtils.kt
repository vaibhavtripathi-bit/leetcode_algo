package com.leetcode.solutions.utils

/**
 * GridUtils — helpers for 2D grid / matrix problems.
 *
 * Used in virtually every BFS/DFS grid problem:
 * Number of Islands, Rotten Oranges, Word Search,
 * Surrounded Regions, Pacific Atlantic Water Flow, etc.
 *
 * Usage pattern:
 *   val dirs = DIRS_4
 *   for ((dr, dc) in dirs) {
 *       val nr = row + dr; val nc = col + dc
 *       if (isValid(nr, nc, rows, cols)) { ... }
 *   }
 */

// ─────────────────────────────────────────────
// Direction arrays
// ─────────────────────────────────────────────

/** 4-directional movement: up, down, left, right. */
val DIRS_4 = arrayOf(
    intArrayOf(-1, 0),   // up
    intArrayOf(1, 0),    // down
    intArrayOf(0, -1),   // left
    intArrayOf(0, 1)     // right
)

/**
 * 8-directional movement: up, down, left, right + 4 diagonals.
 * Used in: Word Search II, Game of Life, etc.
 */
val DIRS_8 = arrayOf(
    intArrayOf(-1, 0), intArrayOf(1, 0),
    intArrayOf(0, -1), intArrayOf(0, 1),
    intArrayOf(-1, -1), intArrayOf(-1, 1),
    intArrayOf(1, -1),  intArrayOf(1, 1)
)

// ─────────────────────────────────────────────
// Boundary check
// ─────────────────────────────────────────────

/** True if (row, col) is inside a grid of given dimensions. */
fun isValid(row: Int, col: Int, rows: Int, cols: Int): Boolean =
    row in 0 until rows && col in 0 until cols

// ─────────────────────────────────────────────
// Neighbor helpers
// ─────────────────────────────────────────────

/**
 * Returns all valid 4-directional neighbors of (row, col).
 * Each element is an IntArray of [neighborRow, neighborCol].
 */
fun getNeighbors4(row: Int, col: Int, rows: Int, cols: Int): List<IntArray> =
    DIRS_4.mapNotNull { (dr, dc) ->
        val nr = row + dr; val nc = col + dc
        if (isValid(nr, nc, rows, cols)) intArrayOf(nr, nc) else null
    }

/**
 * Returns all valid 8-directional neighbors of (row, col).
 */
fun getNeighbors8(row: Int, col: Int, rows: Int, cols: Int): List<IntArray> =
    DIRS_8.mapNotNull { (dr, dc) ->
        val nr = row + dr; val nc = col + dc
        if (isValid(nr, nc, rows, cols)) intArrayOf(nr, nc) else null
    }

// ─────────────────────────────────────────────
// Grid creation helpers
// ─────────────────────────────────────────────

/** Creates a 2D boolean visited array of the same dimensions as the grid. */
fun <T> Array<Array<T>>.createVisited(): Array<BooleanArray> =
    Array(size) { BooleanArray(this[0].size) }

fun Array<IntArray>.createVisited(): Array<BooleanArray> =
    Array(size) { BooleanArray(this[0].size) }

/** Converts 2D (row, col) to a single Int key — avoids Pair allocation in BFS. */
fun encodeCell(row: Int, col: Int, cols: Int): Int = row * cols + col

/** Decodes a single Int key back to (row, col). */
fun decodeCell(key: Int, cols: Int): Pair<Int, Int> = key / cols to key % cols
