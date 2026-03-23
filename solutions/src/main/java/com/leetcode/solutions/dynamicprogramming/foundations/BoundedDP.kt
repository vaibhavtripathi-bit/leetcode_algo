package com.leetcode.solutions.dynamicprogramming.foundations

/**
 * ═══════════════════════════════════════════════════════════════════════
 *  BOUNDED DP — 0/1 Knapsack (Generic Template)
 * ═══════════════════════════════════════════════════════════════════════
 *
 *  RULE: Each item can be picked AT MOST ONCE.
 *
 *  PROBLEM:
 *    Given n items with weights[] and values[], and a knapsack of
 *    capacity maxWeight — maximize total value without exceeding maxWeight.
 *
 *  EXAMPLE:
 *    weights   = [1, 2, 3]
 *    values    = [6, 10, 12]
 *    maxWeight = 5
 *    Answer    = 22  →  item[1] + item[2]  (weight 2+3=5, value 10+12=22)
 *
 *  SUBPROBLEM:
 *    bestValue(itemIdx, remainingCap) = max value using items[itemIdx..n-1]
 *                                      with remainingCap capacity left
 *
 *  RECURRENCE:
 *    bestValue(itemIdx, remainingCap) = max(
 *        bestValue(itemIdx + 1, remainingCap),                              // skip item
 *        values[itemIdx] + bestValue(itemIdx + 1, remainingCap - weights[itemIdx])  // take item
 *    )
 *
 *  BASE CASES:
 *    bestValue(n, _)  = 0   →  no items left
 *    bestValue(_, 0)  = 0   →  no capacity left
 *
 * ═══════════════════════════════════════════════════════════════════════
 */


// ─────────────────────────────────────────────────────────────────────
//  APPROACH 1 — Pure Recursion
//
//  For each item, branch into two choices: skip or take.
//  No caching — overlapping subproblems are recomputed every time.
//
//  Time  : O(2^n)  — binary choice tree of depth n
//  Space : O(n)    — call stack depth equals number of items
// ─────────────────────────────────────────────────────────────────────
class BoundedDP_Recursion {

    fun solve(weights: IntArray, values: IntArray, maxWeight: Int): Int {
        return bestValue(weights, values, itemIdx = 0, remainingCap = maxWeight)
    }

    private fun bestValue(
        weights: IntArray,
        values: IntArray,
        itemIdx: Int,
        remainingCap: Int
    ): Int {
        if (itemIdx == weights.size || remainingCap == 0) return 0

        val valueIfSkipped = bestValue(weights, values, itemIdx + 1, remainingCap)

        val valueIfTaken = if (weights[itemIdx] <= remainingCap) {
            values[itemIdx] + bestValue(weights, values, itemIdx + 1, remainingCap - weights[itemIdx])
        } else 0

        return maxOf(valueIfSkipped, valueIfTaken)
    }
}


// ─────────────────────────────────────────────────────────────────────
//  APPROACH 2 — Top-Down DP (Memoization)
//
//  Same recursion as above, but cache results in a memo table.
//  There are only (n * maxWeight) unique (itemIdx, remainingCap) states.
//  Each state is computed once and looked up in O(1) after that.
//
//  Time  : O(n * maxWeight)  — each unique state computed exactly once
//  Space : O(n * maxWeight)  — memo table + O(n) call stack
// ─────────────────────────────────────────────────────────────────────
class BoundedDP_Memoization {

    fun solve(weights: IntArray, values: IntArray, maxWeight: Int): Int {
        // memo[itemIdx][cap] = best value from items[itemIdx..n-1] with cap remaining
        // Initialized to -1 (= not yet computed)
        val memo = Array(weights.size) { IntArray(maxWeight + 1) { -1 } }
        return bestValue(weights, values, itemIdx = 0, remainingCap = maxWeight, memo)
    }

    private fun bestValue(
        weights: IntArray,
        values: IntArray,
        itemIdx: Int,
        remainingCap: Int,
        memo: Array<IntArray>
    ): Int {
        if (itemIdx == weights.size || remainingCap == 0) return 0
        if (memo[itemIdx][remainingCap] != -1) return memo[itemIdx][remainingCap]

        val valueIfSkipped = bestValue(weights, values, itemIdx + 1, remainingCap, memo)

        val valueIfTaken = if (weights[itemIdx] <= remainingCap) {
            values[itemIdx] + bestValue(weights, values, itemIdx + 1, remainingCap - weights[itemIdx], memo)
        } else 0

        memo[itemIdx][remainingCap] = maxOf(valueIfSkipped, valueIfTaken)
        return memo[itemIdx][remainingCap]
    }
}


// ─────────────────────────────────────────────────────────────────────
//  APPROACH 3 — Bottom-Up DP (Tabulation)
//
//  Build the answer iteratively using a 2D table.
//  dp[itemCount][cap] = max value using first itemCount items with cap capacity.
//
//  TRANSITION:
//    dp[itemCount][cap] = max(
//        dp[itemCount - 1][cap],                                         // skip item
//        values[itemCount-1] + dp[itemCount-1][cap - weights[itemCount-1]]  // take item
//    )
//
//  No recursion stack. Every subproblem is solved before it is needed.
//
//  Time  : O(n * maxWeight)  — fill every cell of the table
//  Space : O(n * maxWeight)  — the 2D dp table
// ─────────────────────────────────────────────────────────────────────
class BoundedDP_Tabulation {

    fun solve(weights: IntArray, values: IntArray, maxWeight: Int): Int {
        val itemCount = weights.size

        // dp[itemCount][cap] = best value using first `itemCount` items, capacity `cap`
        val dp = Array(itemCount + 1) { IntArray(maxWeight + 1) }

        for (itemCount in 1..weights.size) {
            val currentWeight = weights[itemCount - 1]
            val currentValue  = values[itemCount - 1]

            for (cap in 0..maxWeight) {
                dp[itemCount][cap] = dp[itemCount - 1][cap]  // default: skip this item

                if (currentWeight <= cap) {
                    val valueIfTaken = currentValue + dp[itemCount - 1][cap - currentWeight]
                    dp[itemCount][cap] = maxOf(dp[itemCount][cap], valueIfTaken)
                }
            }
        }

        return dp[weights.size][maxWeight]
    }
}


// ─────────────────────────────────────────────────────────────────────
//  APPROACH 4 — Space-Optimized DP (1D Array)
//
//  Observation: dp[itemCount][cap] only depends on the previous row dp[itemCount-1].
//  So we collapse the 2D table into a single 1D array, updated in-place.
//
//  !! WHY RIGHT → LEFT FOR BOUNDED DP !!
//
//  When updating dp[cap], we need dp[cap - currentWeight] from the PREVIOUS item.
//  If we go left→right, dp[cap - currentWeight] gets updated first in this
//  same pass — meaning currentItem would be counted twice (wrong).
//
//  Going right→left guarantees dp[cap - currentWeight] still holds the
//  previous item's value when dp[cap] is updated → each item used at most once.
//
//  Time  : O(n * maxWeight)  — same work, no table overhead
//  Space : O(maxWeight)      — single 1D array replaces the n×W table
// ─────────────────────────────────────────────────────────────────────
class BoundedDP_SpaceOptimized {

    fun solve(weights: IntArray, values: IntArray, maxWeight: Int): Int {
        // dp[cap] = best value achievable with exactly `cap` capacity
        val dp = IntArray(maxWeight + 1)

        for (itemIdx in weights.indices) {
            val currentWeight = weights[itemIdx]
            val currentValue  = values[itemIdx]

            // ⚠️ RIGHT → LEFT: ensures each item is counted at most once
            for (cap in maxWeight downTo currentWeight) {
                dp[cap] = maxOf(dp[cap], currentValue + dp[cap - currentWeight])
            }
        }

        return dp[maxWeight]
    }
}