package com.leetcode.solutions.heap.problems.medium

import java.util.PriorityQueue
import java.util.LinkedList

/**
 * 621. Task Scheduler
 * https://leetcode.com/problems/task-scheduler/
 *
 * Given a characters array tasks, representing the tasks a CPU needs to do, where each letter
 * represents a different task. Tasks could be done in any order. Each task is done in one unit
 * of time. For each unit of time, the CPU could complete either one task or just be idle.
 *
 * However, there is a non-negative integer n that represents the cooldown period between two
 * same tasks (the same letter in the array), that is that there must be at least n units of
 * time between any two same tasks.
 *
 * Return the least number of units of times that the CPU will take to finish all the given tasks.
 *
 * Example 1:
 * Input: tasks = ["A","A","A","B","B","B"], n = 2
 * Output: 8
 * Explanation: A -> B -> idle -> A -> B -> idle -> A -> B
 *
 * Example 2:
 * Input: tasks = ["A","A","A","B","B","B"], n = 0
 * Output: 6
 * Explanation: On this case any permutation of size 6 would work since n = 0.
 *
 * Example 3:
 * Input: tasks = ["A","A","A","A","A","A","B","C","D","E","F","G"], n = 2
 * Output: 16
 * Explanation: One possible solution is: A -> B -> C -> A -> D -> E -> A -> F -> G -> A -> idle -> idle -> A -> idle -> idle -> A
 *
 * Constraints:
 * - 1 <= task.length <= 10^4
 * - tasks[i] is upper-case English letter.
 * - The integer n is in the range [0, 100].
 *
 * Companies: Facebook, Amazon, Google, Microsoft, Apple, Uber, Bloomberg, Salesforce
 */

/**
 * Solution 1: Max Heap with Cooldown Queue
 * Use max-heap to always pick the task with highest frequency.
 * Use a queue to track tasks in cooldown period.
 *
 * Time Complexity: O(n * m) where n is total tasks and m is cooldown period
 * Space Complexity: O(26) = O(1) for task frequencies
 */
fun leastInterval(tasks: CharArray, n: Int): Int {
    val frequencies = IntArray(26)
    for (task in tasks) {
        frequencies[task - 'A']++
    }

    val maxHeap = PriorityQueue<Int>(reverseOrder())
    for (freq in frequencies) {
        if (freq > 0) {
            maxHeap.offer(freq)
        }
    }

    var time = 0
    val cooldownQueue = LinkedList<Pair<Int, Int>>()

    while (maxHeap.isNotEmpty() || cooldownQueue.isNotEmpty()) {
        time++

        if (maxHeap.isNotEmpty()) {
            val freq = maxHeap.poll() - 1
            if (freq > 0) {
                cooldownQueue.offer(Pair(freq, time + n))
            }
        }

        if (cooldownQueue.isNotEmpty() && cooldownQueue.peek().second == time) {
            maxHeap.offer(cooldownQueue.poll().first)
        }
    }

    return time
}

/**
 * Solution 2: Mathematical Formula
 * Calculate based on max frequency and number of tasks with max frequency.
 * Formula: max((maxFreq - 1) * (n + 1) + maxCount, totalTasks)
 *
 * Time Complexity: O(n)
 * Space Complexity: O(1)
 */
fun leastIntervalMath(tasks: CharArray, n: Int): Int {
    val frequencies = IntArray(26)
    for (task in tasks) {
        frequencies[task - 'A']++
    }

    val maxFreq = frequencies.maxOrNull() ?: 0
    val maxCount = frequencies.count { it == maxFreq }

    val minLength = (maxFreq - 1) * (n + 1) + maxCount
    return maxOf(minLength, tasks.size)
}

/**
 * Solution 3: Greedy with Sorting
 * Sort by frequency and fill time slots greedily.
 *
 * Time Complexity: O(n * 26 log 26) ≈ O(n)
 * Space Complexity: O(26) = O(1)
 */
fun leastIntervalGreedy(tasks: CharArray, n: Int): Int {
    val frequencies = IntArray(26)
    for (task in tasks) {
        frequencies[task - 'A']++
    }

    frequencies.sortDescending()
    var time = 0

    while (frequencies[0] > 0) {
        var i = 0
        while (i <= n) {
            if (frequencies[0] == 0) break

            if (i < 26 && frequencies[i] > 0) {
                frequencies[i]--
            }
            time++
            i++
        }
        frequencies.sortDescending()
    }

    return time
}

/**
 * Solution 4: Priority Queue with Round-Robin Simulation
 * Simulate the actual scheduling process with detailed tracking.
 *
 * Time Complexity: O(totalTime * 26)
 * Space Complexity: O(26) = O(1)
 */
fun leastIntervalSimulation(tasks: CharArray, n: Int): Int {
    val frequencies = HashMap<Char, Int>()
    for (task in tasks) {
        frequencies[task] = frequencies.getOrDefault(task, 0) + 1
    }

    val maxHeap = PriorityQueue<Pair<Char, Int>>(compareByDescending { it.second })
    for ((task, freq) in frequencies) {
        maxHeap.offer(Pair(task, freq))
    }

    var cycles = 0
    while (maxHeap.isNotEmpty()) {
        val temp = mutableListOf<Pair<Char, Int>>()
        var taskCount = 0

        for (i in 0..n) {
            if (maxHeap.isNotEmpty()) {
                val (task, freq) = maxHeap.poll()
                if (freq > 1) {
                    temp.add(Pair(task, freq - 1))
                }
                taskCount++
            }
        }

        for (pair in temp) {
            maxHeap.offer(pair)
        }

        cycles += if (maxHeap.isEmpty()) taskCount else n + 1
    }

    return cycles
}
