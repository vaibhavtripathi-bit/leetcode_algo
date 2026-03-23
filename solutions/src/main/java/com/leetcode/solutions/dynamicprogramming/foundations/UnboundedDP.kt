package com.leetcode.solutions.dynamicprogramming.foundations

/**
 * ═══════════════════════════════════════════════════════════════════════
 *  UNBOUNDED DP — Unbounded Knapsack (Generic Template)
 * ═══════════════════════════════════════════════════════════════════════
 *
 *  RULE: Each item can be picked ANY NUMBER of times (unlimited reuse).
 *
 *  PROBLEM:
 *    Given n items with weights[] and values[], and a knapsack of
 *    capacity maxWeight — maximize total value without exceeding maxWeight.
 *    (Same setup as 0/1, but items are freely reusable.)
 *
 *  EXAMPLE:
 *    weights   = [1, 2, 3]
 *    values    = [6, 10, 12]
 *    maxWeight = 5
 *    Answer    = 30  →  item[0] picked 5 times  (weight 1×5=5, value 6×5=30)
 *
 *  SUBPROBLEM:
 *    bestValue(itemIdx, remainingCap) = max value using items[itemIdx..n-1]
 *                                      with remainingCap capacity left
 *
 *  RECURRENCE:
 *    bestValue(itemIdx, remainingCap) = max(
 *        bestValue(itemIdx + 1, remainingCap),                         // skip item type
 *        values[itemIdx] + bestValue(itemIdx, remainingCap - weights[itemIdx])  // take item (stay at same itemIdx!)
 *    )
 *                                               ↑
 *                          index stays at itemIdx, NOT itemIdx+1
 *                          → the same item type can be taken again
 *
 *  BASE CASES:
 *    bestValue(n, _)  = 0   →  no item types left
 *    bestValue(_, 0)  = 0   →  no capacity left
 *
 * ═══════════════════════════════════════════════════════════════════════
 */


// ─────────────────────────────────────────────────────────────────────
//  APPROACH 1 — Pure Recursion
//
//  For each item type, branch into two choices:
//    - Skip this type entirely and move to the next
//    - Take one unit and STAY on the same type (enabling unlimited reuse)
//
//  No caching — deeply overlapping subproblems recomputed every time.
//
//  Time  : O(n ^ (maxWeight / minWeight))  — exponential branching
//  Space : O(maxWeight / minWeight)        — max call stack depth
// ─────────────────────────────────────────────────────────────────────
class UnboundedDPRecursion {

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

        // Stay at itemIdx (not itemIdx + 1) — allows picking this type again
        val valueIfTaken = if (weights[itemIdx] <= remainingCap) {
            values[itemIdx] + bestValue(weights, values, itemIdx, remainingCap - weights[itemIdx])
        } else 0

        return maxOf(valueIfSkipped, valueIfTaken)
    }
}


// ─────────────────────────────────────────────────────────────────────
//  APPROACH 2 — Top-Down DP (Memoization)
//
//  Same recursion as above, but cache on (itemIdx, remainingCap).
//  Even with unlimited reuse, the (itemIdx, remainingCap) pair uniquely
//  identifies a subproblem — so caching still eliminates all redundancy.
//
//  Time  : O(n * maxWeight)  — each unique (itemIdx, cap) state computed once
//  Space : O(n * maxWeight)  — memo table + O(maxWeight) call stack
// ─────────────────────────────────────────────────────────────────────
class UnboundedDPMemoization {

    fun solve(weights: IntArray, values: IntArray, maxWeight: Int): Int {
        // memo[itemIdx][cap] = best value from item types [itemIdx..n-1] with cap remaining
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
            values[itemIdx] + bestValue(weights, values, itemIdx, remainingCap - weights[itemIdx], memo)
        } else 0

        memo[itemIdx][remainingCap] = maxOf(valueIfSkipped, valueIfTaken)
        return memo[itemIdx][remainingCap]
    }
}


// ─────────────────────────────────────────────────────────────────────
//  APPROACH 3 — Bottom-Up DP (Tabulation)
//
//  dp[itemCount][cap] = max value using item types [itemCount..n-1] with cap capacity.
//  Fill the table bottom-up: start from the last item type, work toward first.
//
//  TRANSITION:
//    dp[itemCount][cap] = max(
//        dp[itemCount + 1][cap],                                    // skip this type
//        values[itemCount] + dp[itemCount][cap - weights[itemCount]] // take one unit (uses SAME row!)
//    )
//
//  KEY DIFFERENCE FROM BOUNDED:
//    Bounded take → dp[itemCount - 1][cap - weight]  (previous row → no reuse)
//    Unbounded take → dp[itemCount][cap - weight]    (same row → reuse allowed)
//
//  Time  : O(n * maxWeight)  — fill every cell of the table
//  Space : O(n * maxWeight)  — the 2D dp table
// ─────────────────────────────────────────────────────────────────────
class UnboundedDPTabulation {

    fun solve(weights: IntArray, values: IntArray, maxWeight: Int): Int {
        val itemCount = weights.size

        // dp[typeIdx][cap] = best value using item types [typeIdx..n-1] with cap capacity
        val dp = Array(itemCount + 1) { IntArray(maxWeight + 1) }

        for (typeIdx in itemCount - 1 downTo 0) {
            val currentWeight = weights[typeIdx]
            val currentValue  = values[typeIdx]

            for (cap in 0..maxWeight) {
                dp[typeIdx][cap] = dp[typeIdx + 1][cap]  // default: skip this type

                if (currentWeight <= cap) {
                    // dp[typeIdx][...] — same row, so this type can appear multiple times
                    val valueIfTaken = currentValue + dp[typeIdx][cap - currentWeight]
                    dp[typeIdx][cap] = maxOf(dp[typeIdx][cap], valueIfTaken)
                }
            }
        }

        return dp[0][maxWeight]
    }
}


// ─────────────────────────────────────────────────────────────────────
//  APPROACH 4 — Space-Optimized DP (1D Array)
//
//  Collapse the 2D table into a single 1D array updated in-place.
//
//  !! WHY LEFT → RIGHT FOR UNBOUNDED DP !!
//
//  When updating dp[cap], we need dp[cap - currentWeight] from the
//  CURRENT item's pass (same row in the 2D sense). Going left→right
//  means dp[cap - currentWeight] is ALREADY updated in this pass —
//  which is exactly what we want, since reusing the same item is valid.
//
//  ┌─────────────────────────────────────────────────────────────────┐
//  │  THE ONE LINE THAT SEPARATES BOUNDED FROM UNBOUNDED:            │
//  │                                                                 │
//  │  Bounded   →  for (cap in maxWeight downTo currentWeight)       │
//  │  Unbounded →  for (cap in currentWeight..maxWeight)             │
//  └─────────────────────────────────────────────────────────────────┘
//
//  Time  : O(n * maxWeight)  — same work, no table overhead
//  Space : O(maxWeight)      — single 1D array replaces the n×W table
// ─────────────────────────────────────────────────────────────────────
class UnboundedDPSpaceOptimized {

    fun solve(weights: IntArray, values: IntArray, maxWeight: Int): Int {
        // dp[cap] = best value achievable using any items with exactly `cap` capacity
        val dp = IntArray(maxWeight + 1)

        for (itemIdx in weights.indices) {
            val currentWeight = weights[itemIdx]
            val currentValue  = values[itemIdx]

            // ⚠️ LEFT → RIGHT: dp[cap - currentWeight] is already updated this pass → reuse OK
            for (cap in currentWeight..maxWeight) {
                dp[cap] = maxOf(dp[cap], currentValue + dp[cap - currentWeight])
            }
        }

        return dp[maxWeight]
    }
}