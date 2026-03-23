package com.leetcode.solutions.trie.core

/**
 * Trie Node — two implementations:
 * - Array[26]: fastest (O(1) lookup), only for lowercase 'a'-'z'
 * - HashMap: flexible (supports any character set)
 */

/** Array-based TrieNode (preferred for lowercase alphabet problems) */
class TrieNodeArray {
    val children = arrayOfNulls<TrieNodeArray>(26)
    var isEnd = false
    var word: String? = null     // optional: store the word at leaf for Word Search II
}

/** HashMap-based TrieNode (general purpose) */
class TrieNodeMap {
    val children = HashMap<Char, TrieNodeMap>()
    var isEnd = false
}
