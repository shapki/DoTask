package com.example.dotask

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.ColorUtils
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var firstNumTextView: TextView
    private lateinit var secondNumTextView: TextView
    private lateinit var operandTextView: TextView
    private lateinit var answerEditText: EditText
    private lateinit var checkButton: Button
    private lateinit var startButton: Button
    private lateinit var correctTextView: TextView
    private lateinit var incorrectTextView: TextView
    private lateinit var totalTasksTextView: TextView
    private lateinit var totalTasksNumTextView: TextView
    private lateinit var correctNumsTextView: TextView
    private lateinit var notCorrectNumsTextView: TextView
    private lateinit var mainLayout: ConstraintLayout

    private var correctAnswers = 0
    private var incorrectAnswers = 0
    private var totalTasks = 0
    private var correctAnswer: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firstNumTextView = findViewById(R.id.firstNum)
        secondNumTextView = findViewById(R.id.secondNum)
        operandTextView = findViewById(R.id.operand)
        answerEditText = findViewById(R.id.answerTextInput)
        checkButton = findViewById(R.id.checkButton)
        startButton = findViewById(R.id.startButton)
        correctTextView = findViewById(R.id.correctTextView)
        incorrectTextView = findViewById(R.id.incorrectTextView)
        totalTasksTextView = findViewById(R.id.totalTasksTextView)
        totalTasksNumTextView = findViewById(R.id.totalTasksNum)
        correctNumsTextView = findViewById(R.id.correctNums)
        notCorrectNumsTextView = findViewById(R.id.notCorrectNums)
        mainLayout = findViewById(R.id.mainLayout)

        checkButton.isEnabled = false
        answerEditText.isEnabled = false

        startButton.setOnClickListener {
            generateExample()
            startButton.isEnabled = false
            checkButton.isEnabled = true
            answerEditText.isEnabled = true
            answerEditText.text.clear()
            answerEditText.requestFocus()
            mainLayout.setBackgroundColor(Color.WHITE)
        }

        checkButton.setOnClickListener {
            checkAnswer()
            startButton.isEnabled = true
            checkButton.isEnabled = false
            answerEditText.isEnabled = false
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

        firstNumTextView.text = num1.toString()
        secondNumTextView.text = num2.toString()
        operandTextView.text = operation

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
        val userAnswer = answerEditText.text.toString().toIntOrNull()

        if (userAnswer != null && userAnswer == correctAnswer) {
            correctAnswers++
            val lighterGreen = ColorUtils.blendARGB(Color.GREEN, Color.WHITE, 0.6f)
            mainLayout.setBackgroundColor(lighterGreen)
        } else {
            incorrectAnswers++
            val lighterRed = ColorUtils.blendARGB(Color.RED, Color.WHITE, 0.6f)
            mainLayout.setBackgroundColor(lighterRed)
        }

        updateStatistics()
    }

    private fun updateStatistics() {
        correctNumsTextView.text = correctAnswers.toString()
        notCorrectNumsTextView.text = incorrectAnswers.toString()
        totalTasksNumTextView.text = totalTasks.toString()
    }

    private fun findDivisor(number: Int): Int {
        val divisors = mutableListOf<Int>()
        for (i in 1..number) {
            if (number % i == 0 && i in 10..99) {
                divisors.add(i)
            }
        }
        return divisors.randomOrNull() ?: number
    }
}
