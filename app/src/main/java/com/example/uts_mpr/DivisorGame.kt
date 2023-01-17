package com.example.uts_mpr

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import kotlin.random.Random

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
/**
 * A simple [Fragment] subclass.
 * Use the [DivisorGame.newInstance] factory method to
 * create an instance of this fragment.
 */
class DivisorGame : Fragment(), MathAppGame {
    private var param1: String? = null
    private var param2: String? = null

    lateinit var button1: Button
    lateinit var button2: Button
    lateinit var button3: Button
    lateinit var button4: Button

    lateinit var buttons: Array<Button>

    lateinit var resultTextView: TextView
    lateinit var tempTextView: TextView
    lateinit var validationTextView: TextView

    var firstButtonPress = false
    var firstValue = 0
    var secondValue = 0

    var currentScore = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_divisor_game, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttons = arrayOf(
            view.findViewById(R.id.button1),
            view.findViewById(R.id.button2),
            view.findViewById(R.id.button3),
            view.findViewById(R.id.button4)
        )

        for (button in buttons) {
            button.setOnClickListener() {
                    view: View ->
                checkButton(view)
            }
        }
        resultTextView = view.findViewById(R.id.resultTextView)
        tempTextView = view.findViewById(R.id.tempTextView)
        validationTextView = view.findViewById(R.id.validationTextView)
        validationTextView.text = ""
        generateQuestion()

    }

    private fun generateQuestion() {
        for (button in buttons)
            button.isEnabled = true;

        firstButtonPress = false;
        firstValue = 0
        secondValue = 0

        var randomGenerator = Random(System.currentTimeMillis())

        var result = randomGenerator.nextInt(2, 7)
        while (result == 5) {
            result = randomGenerator.nextInt(2, 7)
        }
        resultTextView.text = result.toString()

        var firstValue = randomGenerator.nextInt(7, 50)
        while (firstValue.mod(result) != 0) {
            firstValue = randomGenerator.nextInt(7, 50)
        }

        var secondValue = randomGenerator.nextInt(7, 50)
        while (secondValue.mod(result) != 0 && secondValue != firstValue) {
            secondValue = randomGenerator.nextInt(7, 50)
        }
        tempTextView.text = result.toString()


        var firstWrongValue = randomGenerator.nextInt(7, 50)
        while (firstWrongValue.mod(result) == 0) {
            firstWrongValue = randomGenerator.nextInt(7, 50)
        }
        var secondWrongValue = randomGenerator.nextInt(7, 50)
        while (secondWrongValue.mod(result) == 0) {
            secondWrongValue = randomGenerator.nextInt(7, 50)
        }


        var arrayInt = arrayOf(0, 1, 2, 3)
        arrayInt.shuffle(randomGenerator)

        buttons[arrayInt[0]].text = firstValue.toString();
        buttons[arrayInt[1]].text = secondValue.toString();
        buttons[arrayInt[2]].text = firstWrongValue.toString();
        buttons[arrayInt[3]].text = secondWrongValue.toString();


        for (x in arrayInt) println(x)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun finishGame(){
        for (button in buttons)
            button.isEnabled = false;
            resultTextView.text = "YOUR SCORE: " + currentScore.toString()
    }

    private fun checkButton(view: View) {
        var buttonPressed = view as Button;

        if (firstButtonPress) {
            secondValue = buttonPressed.text.toString().toInt()


            //validate
            val answer = tempTextView.text.toString().toInt()
            if (firstValue.mod(answer) == 0 && secondValue.mod(answer) == 0) {
                validationTextView.text = "CORRECT"
                validationTextView.setTextColor(Color.GREEN)
                currentScore = currentScore + 1
            } else {
                validationTextView.text = "INCORRECT"
                validationTextView.setTextColor(Color.RED)
            }
            generateQuestion()
        } else {
            firstValue = buttonPressed.text.toString().toInt()
            firstButtonPress = true
            buttonPressed.isEnabled = false
            //wait for another button
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OperationMath.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OperationMath().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
            }
        }
    }
}
