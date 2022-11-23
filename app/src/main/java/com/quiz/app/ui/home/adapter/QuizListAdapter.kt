package com.quiz.app.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.quiz.app.data.QuizDetailsData
import com.quiz.app.databinding.QuestionListLayoutBinding

class QuizListAdapter(
    private val list: ArrayList<QuizDetailsData>,
    private val listener:OnClickSelect
) : RecyclerView.Adapter<QuizListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = QuestionListLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = list[position]
        holder.binding.tvQuizName.text = data.name
        holder.binding.tvQuizCategory.text = data.category

        holder.binding.quizCard.setOnClickListener {
            listener.quizSelected(data)
        }

    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(val binding: QuestionListLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnClickSelect{
        fun quizSelected(data: QuizDetailsData)
    }

}