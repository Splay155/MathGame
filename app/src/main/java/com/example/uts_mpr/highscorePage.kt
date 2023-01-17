package com.example.uts_mpr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myrecyclerview.ListScoreAdapter



class highscorePage : AppCompatActivity() {
    private lateinit var rvScore: RecyclerView
    lateinit var menu : Button
    private val list = ArrayList<highscore>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_highscore_page)

        menu = findViewById(R.id.mainMenu2)
        rvScore = findViewById(R.id.rv_Score)
        rvScore.setHasFixedSize(true)
        list.addAll(listScore)
        showRecyclerList()

        menu.setOnClickListener() {
            val intent = Intent(this, SubActivity::class.java)
            startActivity(intent)
        }
    }
    private val listScore: ArrayList<highscore>
        get() {
            val dataName = resources.getStringArray(R.array.data_name)
            val dataDescription = resources.getStringArray(R.array.data_description)
            val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
            val dataScore = resources.getStringArray(R.array.data_score)
            val listHighscores = ArrayList<highscore>()
            for (i in dataName.indices) {
                val highscore = highscore(
                    dataName[i],
                    dataDescription[i],
                    dataPhoto.getResourceId(i, -1),
                    dataScore[i],

                )
                listHighscores.add(highscore)
            }
            return listHighscores
        }
    private fun showRecyclerList() {
        rvScore.layoutManager = LinearLayoutManager(this)
        val listHeroAdapter = ListScoreAdapter(list)
        rvScore.adapter = listHeroAdapter

            }
}