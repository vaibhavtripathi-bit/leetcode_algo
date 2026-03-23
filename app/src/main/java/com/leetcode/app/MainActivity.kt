package com.leetcode.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.leetcode.app.databinding.ActivityMainBinding
import com.leetcode.solutions.common.ListNode
import com.leetcode.solutions.common.ListNodeUtil

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
    }

    private fun setupUI() {
        binding.runButton.setOnClickListener {
            runSampleSolution()
        }
    }

    private fun runSampleSolution() {
        val output = StringBuilder()
        
        output.appendLine("=== LeetCode Solutions Demo ===")
        output.appendLine()
        
        output.appendLine("Creating linked list: 1 -> 2 -> 3 -> 4 -> 5")
        val list = ListNodeUtil.create(1, 2, 3, 4, 5)
        output.appendLine("List: ${ListNodeUtil.toString(list)}")
        output.appendLine()
        
        output.appendLine("Solutions library is ready!")
        output.appendLine("Check the 'solutions' module for all LeetCode problems.")
        
        binding.outputText.text = output.toString()
    }
}
