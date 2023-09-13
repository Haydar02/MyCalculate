package com.example.mycalculate

import android.icu.math.BigDecimal
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.example.mycalculate.databinding.ActivityMainBinding
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var selectedOperation: String = ""
    private lateinit var activeEditText:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

         val editText = binding.editText
        editText.requestFocus()


        val ResultTextView = binding.resultTextView
        val OperationRadioGroup = binding.operationRadioGroup

        val resultTextView = binding.resultTextView
        val operationRadioGroup = binding.operationRadioGroup

        val buttonAC = binding.buttonAC
        val buttonBackspace = binding.buttonBackspace

        buttonBackspace.setOnClickListener {
            val currentText = editText.text.toString()
            if (currentText.isNotEmpty()) {
                val newText = currentText.substring(0, currentText.length - 1)
                editText.setText(newText)
            }
        }

        val numberButtons = listOf(
            binding.button0,
            binding.button1,
            binding.button2,
            binding.button3,
            binding.button4,
            binding.button5,
            binding.button6,
            binding.button7,
            binding.button8,
            binding.button9
        )
        numberButtons.forEachIndexed { index, button ->
            button.setOnClickListener {
                val currentText = editText.text.toString()
                val newText = "$currentText$index"
                editText.setText(newText)
            }
        }

        buttonAC.setOnClickListener {
            editText.text.clear()
            resultTextView.text = ""
        }

        val operationButtons = listOf(
            binding.additionRadioButton,
            binding.subtractionRadioButton,
            binding.multiplicationRadioButton,
            binding.divisionRadioButton
        )
        operationButtons.forEach { button ->
            button.setOnClickListener {
                val currentText = editText.text.toString()
                val newText = "$currentText ${button.text} "
                editText.setText(newText)
            }
        }

        binding.CalculateButton.setOnClickListener {
            val input = editText.text.toString()
            val parts = input.split(" ")

            if (parts.size != 3) {
                resultTextView.text = "Invalid input"
                return@setOnClickListener
            }

            val input1 = parts[0]
            val input2 = parts[2]
            val operation = parts[1]

            try {
                val number1 = BigDecimal(input1)
                val number2 = BigDecimal(input2)

                val result: BigDecimal = when (operation) {
                    "+" -> number1.add(number2)
                    "-" -> number1.subtract(number2)
                    "*" -> number1.multiply(number2)
                    "/" -> {
                        if (number2 != BigDecimal.ZERO) {
                            number1.divide(number2, 2, BigDecimal.ROUND_HALF_UP)
                        } else {
                            resultTextView.text = "Division by zero"
                            return@setOnClickListener
                        }
                    }
                    else -> BigDecimal.ZERO
                }

                val resultText = "$input1 $operation $input2 = $result"
                resultTextView.text = resultText
                editText.text.clear()
                editText.setText(result.toString())

            } catch (e: NumberFormatException) {
                editText.error = "Invalid input"
            }
        }
    }
}



