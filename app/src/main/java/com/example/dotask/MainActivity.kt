package com.example.dotask

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.graphics.ColorUtils
import com.example.dotask.databinding.ActivityMainBinding
import kotlin.random.Random
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var correctAnswers = 0
    private var incorrectAnswers = 0
    private var totalTasks = 0
    private var correctAnswer: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.checkButton.isEnabled = false
        binding.answerTextInput.isEnabled = false

        binding.startButton.setOnClickListener {
            generateExample()
            binding.startButton.isEnabled = false
            binding.checkButton.isEnabled = true
            binding.answerTextInput.isEnabled = true
            binding.answerTextInput.text.clear()
            binding.answerTextInput.requestFocus()
            binding.mainLayout.setBackgroundColor(Color.WHITE)
        }

        binding.checkButton.setOnClickListener {
            checkAnswer()
            binding.startButton.isEnabled = true
            binding.checkButton.isEnabled = false
            binding.answerTextInput.isEnabled = false
        }

        updateStatistics()
    }

    private fun generateExample() {
        val num1 = Random.nextInt(10, 100)
        var num2 = Random.nextInt(10, 100)
        val operations = arrayOf("*", "/", "-", "+")
        val operation = operations[Random.nextInt(operations.size)]

        if (operation == "/") {
            num2 = findDivisor(num1)
        }

        binding.firstNum.text = num1.toString()
        binding.secondNum.text = num2.toString()
        binding.operand.text = operation

        correctAnswer = when (operation) {
            "*" -> num1 * num2
            "/" -> num1 / num2
            "-" -> num1 - num2
            "+" -> num1 + num2
            else -> 0
        }
    }

    private fun checkAnswer() {
        totalTasks++
        val userAnswer = binding.answerTextInput.text.toString().toIntOrNull()
        val lighterGreen = ColorUtils.blendARGB(Color.GREEN, Color.WHITE, 0.6f)
        val lighterRed = ColorUtils.blendARGB(Color.RED, Color.WHITE, 0.6f)

        if (userAnswer != null) {
            if (userAnswer == correctAnswer) {
                correctAnswers++
                binding.mainLayout.setBackgroundColor(lighterGreen)

            } else {
                incorrectAnswers++
                binding.mainLayout.setBackgroundColor(lighterRed)
            }
        } else {
            incorrectAnswers++
            binding.mainLayout.setBackgroundColor(lighterRed)
        }

        updateStatistics()
    }

    private fun findDivisor(number: Int): Int {
        val divisors = mutableListOf<Int>()
        for (i in 1..number) {
            if (number % i == 0) {
                divisors.add(i)
            }
        }
        return divisors.random()
    }

    private fun updateStatistics() {
        binding.correctNums.text = correctAnswers.toString()
        binding.incorrectNums.text = incorrectAnswers.toString()
        binding.totalTasksNum.text = totalTasks.toString()

        val percentage = if (totalTasks > 0) {
            (correctAnswers.toDouble() / totalTasks.toDouble()) * 100
        } else {
            0.0
        }

        val df = DecimalFormat("0.00")
        val formattedPercentage = df.format(percentage) + "%"

        binding.percentTextView.text = formattedPercentage
    }
}
