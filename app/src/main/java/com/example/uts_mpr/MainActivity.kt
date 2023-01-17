package com.example.uts_mpr

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import android.content.Intent
import android.content.Intent.getIntent
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    var divisor = DivisorGame()
    var opMath = OperationMath()
    var reflex = FastAndNumerous()
    var mode = 0
    var username = "a"

    lateinit var restart: Button
    lateinit var highscoreTab: Button
    lateinit var mainMenu: Button
    lateinit var usernameTextView : TextView

    val maxTimeInMillis = 10000L
    val minTimeInMillis = 0L
    val intervalInMillis = 10L

    lateinit var timerProgressBar: ProgressBar

    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        intent = getIntent();
        mode = intent.getIntExtra("mode", 1)
        username = intent.getStringExtra("username")!!


        if (mode == 1) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainerView, divisor)
                .commit()
        } else if (mode == 2) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainerView, opMath)
                .commit()
        } else {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainerView, reflex)
                .commit()
        }
        usernameTextView = findViewById(R.id.textView4)
        restart = findViewById(R.id.restart)
        highscoreTab = findViewById(R.id.highscoreTab)
        mainMenu = findViewById(R.id.mainMenu)

        usernameTextView.text = username
        restart.setOnClickListener() {
            finish()
            startActivity(getIntent())
        }
        highscoreTab.setOnClickListener() {
            val intent = Intent(this, highscorePage::class.java)
            startActivity(intent)
        }
        mainMenu.setOnClickListener() {
            val intent = Intent(this, SubActivity::class.java)
            intent.putExtra("username", username)
            startActivity(intent)
        }

            timerProgressBar = findViewById(R.id.timerProgressBar)
            timerProgressBar.max = (maxTimeInMillis).toInt()
            timerProgressBar.min = (minTimeInMillis).toInt()

            val timer = object : CountDownTimer(maxTimeInMillis, intervalInMillis) {
                override fun onTick(millisUntilFinished: Long) {
                    timerProgressBar.progress = (millisUntilFinished).toInt()
                }

                override fun onFinish() {
                    val f = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
                    val mathAppGame = f as MathAppGame
                    mathAppGame.finishGame()
                }
            }
            timer.start()
        }
    }
