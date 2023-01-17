package com.example.uts_mpr

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random
import kotlin.system.measureTimeMillis

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FastAndNumerous.newInstance] factory method to
 * create an instance of this fragment.
 */
class FastAndNumerous : Fragment(), MathAppGame {
    lateinit var button1: Button
    lateinit var button2: Button
    lateinit var button3: Button
    lateinit var button4: Button
    lateinit var resetButton: Button

    lateinit var buttons: Array<Button>

    lateinit var buttonTimers : Array<Long>

    var elapsed = 0L

    lateinit var resultTextView: TextView

    lateinit var validationTextView: TextView

    var currentScore = 0

    var randomGenerator = Random(System.currentTimeMillis())

    lateinit var coroutine: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fast_and_numerous, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttons = arrayOf(
            view.findViewById(R.id.button1),
            view.findViewById(R.id.button2),
            view.findViewById(R.id.button3),
            view.findViewById(R.id.button4),
        )

        for (button in buttons) {
            button.setOnClickListener()
            { view: View ->
                checkButton(view)
            }
        }

        buttonTimers = arrayOf(0L, 0L, 0L, 0L)

        validationTextView = view.findViewById(R.id.validationTextView)

        resultTextView = view.findViewById(R.id.resultTextView)

        coroutine = lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED)
            {
                generateQuestion()
            }
        }
    }

    suspend fun generateQuestion() {
        while (true) {
            elapsed = measureTimeMillis {
                delay(100L)
            }

            var randomValue = 0
            for (index in 0..3)
            {
                buttonTimers[index] = buttonTimers[index] - elapsed

                if (buttonTimers[index] <= 0)
                {
                    randomValue = randomGenerator.nextInt(1,20)
                    buttons[index].text = randomValue.toString()

                    randomValue = randomGenerator.nextInt(0,2)

                    buttonTimers[index] = 1000L
                    if (randomValue == 0)
                        buttons[index].backgroundTintList = ColorStateList.valueOf(Color.GREEN)
                    else
                        buttons[index].backgroundTintList = ColorStateList.valueOf(Color.YELLOW)
                }
            }
        }
    }

    fun checkButton(view : View)
    {
        var button = view as Button

        var buttonIndex =  buttons.indexOf(button)
        buttonTimers[buttonIndex] = 0L

        var buttonValue = button.text.toString().toInt()

        var defaultColor = button.backgroundTintList
            ?.getColorForState(IntArray(android.R.attr.state_enabled), 0)

        // IF
        if ( (buttonValue % 2 == 0 && defaultColor != Color.GREEN) ||
            (buttonValue % 2 == 1 && defaultColor != Color.YELLOW))
        {
            validationTextView.text = "CORRECT"
            validationTextView.setTextColor(Color.GREEN)
                    currentScore += 1
        }
        else
        {
            validationTextView.text = "INCORRECT"
            validationTextView.setTextColor(Color.RED)
        }
    }


    override fun finishGame(){
        for (button in buttons)
            button.isEnabled = false;
        resultTextView.text = "YOUR SCORE: " + currentScore.toString()
        resultTextView.isVisible = true
        coroutine?.cancel()
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FastAndNumerous.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FastAndNumerous().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}