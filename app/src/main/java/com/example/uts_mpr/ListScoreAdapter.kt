package com.example.myrecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.uts_mpr.R
import com.example.uts_mpr.highscore

class ListScoreAdapter(private val listScore: ArrayList<highscore>) : RecyclerView.Adapter<ListScoreAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_highscore, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, description, photo, score) = listScore[position]
        holder.imgPhoto.setImageResource(photo)
        holder.tvName.text = name
        holder.tvDescription.text = description
        holder.tvScore.text = score
        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Kamu memilih " + listScore[holder.adapterPosition].name, Toast.LENGTH_SHORT).show()
        }
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listScore[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int = listScore.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        var tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        var tvDescription: TextView = itemView.findViewById(R.id.tv_item_datetime)
        var tvScore: TextView = itemView.findViewById(R.id.tv_item_score)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: highscore)
    }
}