package com.leetcode.solutions.graph.problems.hard

/**
 * LeetCode 127: Word Ladder
 * https://leetcode.com/problems/word-ladder/
 *
 * Difficulty: Hard
 * Companies: Google, Amazon, Microsoft, Meta, Apple
 *
 * Transform beginWord to endWord one letter at a time, each step must be in wordList.
 * Return minimum number of transformations (or 0 if not possible).
 *
 * Example: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"] → 5
 */

/** Solution 1: BFS - Time: O(M² × N), Space: O(M² × N) where M=word length, N=wordList size */
class WordLadder1 {
    fun ladderLength(beginWord: String, endWord: String, wordList: List<String>): Int {
        val wordSet = wordList.toHashSet()
        if (endWord !in wordSet) return 0

        val queue = ArrayDeque<String>()
        queue.addLast(beginWord)
        var steps = 1

        while (queue.isNotEmpty()) {
            repeat(queue.size) {
                val word = queue.removeFirst()
                if (word == endWord) return steps

                for (next in getNeighbors(word, wordSet)) {
                    wordSet.remove(next)
                    queue.addLast(next)
                }
            }
            steps++
        }

        return 0
    }

    private fun getNeighbors(word: String, wordSet: Set<String>): List<String> {
        val neighbors = mutableListOf<String>()
        val chars = word.toCharArray()

        for (i in chars.indices) {
            val original = chars[i]
            for (c in 'a'..'z') {
                if (c == original) continue
                chars[i] = c
                val candidate = String(chars)
                if (candidate in wordSet) neighbors.add(candidate)
            }
            chars[i] = original
        }

        return neighbors
    }
}

/** Solution 2: Bidirectional BFS (faster in practice) - Time: O(M² × N), Space: O(M² × N) */
class WordLadder2 {
    fun ladderLength(beginWord: String, endWord: String, wordList: List<String>): Int {
        val wordSet = wordList.toHashSet()
        if (endWord !in wordSet) return 0

        var front = hashSetOf(beginWord)
        var back = hashSetOf(endWord)
        var steps = 1

        while (front.isNotEmpty() && back.isNotEmpty()) {
            if (front.size > back.size) { val tmp = front; front = back; back = tmp }

            val next = HashSet<String>()
            for (word in front) {
                for (neighbor in getNeighbors(word, wordSet)) {
                    if (neighbor in back) return steps + 1
                    next.add(neighbor)
                    wordSet.remove(neighbor)
                }
            }

            front = next
            steps++
        }

        return 0
    }

    private fun getNeighbors(word: String, wordSet: Set<String>): List<String> {
        val neighbors = mutableListOf<String>()
        val chars = word.toCharArray()

        for (i in chars.indices) {
            val original = chars[i]
            for (c in 'a'..'z') {
                if (c == original) continue
                chars[i] = c
                val candidate = String(chars)
                if (candidate in wordSet) neighbors.add(candidate)
            }
            chars[i] = original
        }

        return neighbors
    }
}
