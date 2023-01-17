package com.example.uts_mpr

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import kotlin.random.Random

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
/**
 * A simple [Fragment] subclass.
 * Use the [OperationMath.newInstance] factory method to
 * create an instance of this fragment.
 */
class OperationMath : Fragment(), MathAppGame {
    private var param1: String? = null
    private var param2: String? = null

    lateinit var button1: Button
    lateinit var button2: Button
    lateinit var button3: Button

    lateinit var buttons: Array<Button>

    lateinit var resultTextView: TextView
    lateinit var tempTextView: TextView
    lateinit var validationTextView: TextView

    var firstButtonPress = false
    var firstValue = 0
    var secondValue = 0
    var thirdValue = 0

    var currentScore = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_operation_math, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttons = arrayOf(
            view.findViewById(R.id.button1),
            view.findViewById(R.id.button2),
            view.findViewById(R.id.button3)
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
        thirdValue = 0

        var randomGenerator = Random(System.currentTimeMillis())

        var value11 = randomGenerator.nextInt(1, 20)
        var value12 = randomGenerator.nextInt(1, 20)
        firstValue = value11 + value12

        var value21 = randomGenerator.nextInt(1, 20)
        var value22 = randomGenerator.nextInt(1, 20)
        secondValue = value21 + value22

        var value31 = randomGenerator.nextInt(3, 50)
        var value32 = randomGenerator.nextInt(1, value31-1)
        thirdValue = value31 - value32

        if (firstValue > secondValue && firstValue > thirdValue){
            tempTextView.text = firstValue.toString()

        } else if (secondValue > firstValue && secondValue > thirdValue){
            tempTextView.text = secondValue.toString()

        } else {
            tempTextView.text = thirdValue.toString()
        }

        var arrayInt = arrayOf(0,1,2)
        arrayInt.shuffle(randomGenerator)

        buttons[arrayInt[0]].text = value11.toString() + "+" + value12.toString();
        buttons[arrayInt[1]].text = value21.toString() + "+" + value22.toString();
        buttons[arrayInt[2]].text = value31.toString() + "-" + value32.toString();

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
        var result = 0

        var tempString = buttonPressed.text.toString()
        if (tempString.get(1).toString() == "+" || tempString.get(2).toString() == "+"){
            val s = tempString.split("+")
            result = s[0].toInt() + s[1].toInt()
        } else {
            val s = tempString.split("-")
            result = s[0].toInt() - s[1].toInt()
        }

        val answer = tempTextView.text.toString().toInt()
        if (answer == result) {
            validationTextView.text = "CORRECT"
            validationTextView.setTextColor(Color.GREEN)
            currentScore = currentScore + 1
        } else {
            validationTextView.text = "INCORRECT"
            validationTextView.setTextColor(Color.RED)
        }
        generateQuestion()
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