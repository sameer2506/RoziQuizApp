package com.quiz.app.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.quiz.app.data.ScoreData
import com.quiz.app.databinding.ScoreLayoutBinding

class ScoresAdapter(
    private val list: ArrayList<ScoreData>
) : RecyclerView.Adapter<ScoresAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ScoreLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = ScoreLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.binding.tvName.text = data.name
        holder.binding.tvScore.text = data.score.toString()
    }

    override fun getItemCount(): Int = list.size

}