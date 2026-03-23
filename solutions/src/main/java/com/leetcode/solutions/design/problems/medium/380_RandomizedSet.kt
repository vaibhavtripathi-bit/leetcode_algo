package com.leetcode.solutions.design.problems.medium

/**
 * LeetCode 380: Insert Delete GetRandom O(1)
 * https://leetcode.com/problems/insert-delete-getrandom-o1/
 *
 * Difficulty: Medium
 * Companies: Meta, Google, Amazon
 *
 * Design a set that supports insert, delete, and getRandom all in average O(1).
 *
 * Key insight: Array for O(1) random access + HashMap for O(1) delete.
 * For O(1) delete: swap the target with the last element, then pop last.
 */
class RandomizedSet {
    private val list    = ArrayList<Int>()
    private val indexMap = HashMap<Int, Int>()  // value → index in list
    private val random  = java.util.Random()

    fun insert(`val`: Int): Boolean {
        if (`val` in indexMap) return false
        list.add(`val`)
        indexMap[`val`] = list.lastIndex
        return true
    }

    fun remove(`val`: Int): Boolean {
        if (`val` !in indexMap) return false

        val idx     = indexMap[`val`]!!
        val last    = list.last()

        list[idx]   = last
        indexMap[last] = idx

        list.removeAt(list.lastIndex)
        indexMap.remove(`val`)
        return true
    }

    fun getRandom(): Int = list[random.nextInt(list.size)]
}
