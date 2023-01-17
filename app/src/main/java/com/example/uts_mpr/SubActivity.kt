package com.example.uts_mpr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import org.w3c.dom.Text

lateinit var button5 : Button
lateinit var button6 : Button
lateinit var button7 : Button
lateinit var editText : EditText
lateinit var inputUsername : Button
lateinit var usernameTextView : TextView

class SubActivity : AppCompatActivity() {

    var username ="a"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub)

        button5 = findViewById(R.id.button5)
        button6 = findViewById(R.id.button6)
        button7 = findViewById(R.id.button7)
        editText = findViewById(R.id.username)
        inputUsername = findViewById(R.id.inputUsername)
        usernameTextView = findViewById(R.id.textView4)

        if (username != "a") {
            username = intent.getStringExtra("username")!!
            usernameTextView.text = username
        }

        button5.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("mode", 1);
            intent.putExtra("username", usernameTextView.text.toString())
            // start your next activity
            startActivity(intent)
        }
        button6.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("mode", 2);
            intent.putExtra("username", usernameTextView.text.toString())
            // start your next activity
            startActivity(intent)
        }
        button7.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("mode", 3);
            intent.putExtra("username", usernameTextView.text.toString())
            // start your next activity
            startActivity(intent)
        }
        inputUsername.setOnClickListener {
            val editText = editText.text
            usernameTextView.text = editText
        }
    }
}