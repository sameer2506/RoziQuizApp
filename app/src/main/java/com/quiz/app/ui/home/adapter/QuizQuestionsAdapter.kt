package com.quiz.app.ui.home.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.quiz.app.R
import com.quiz.app.data.QuizQuestions
import com.quiz.app.databinding.QuizQuestionsLayoutBinding
import com.quiz.app.ui.home.viewModel.UserVM

class QuizQuestionsAdapter(
    private var list: ArrayList<QuizQuestions>,
    private var score: Int,
    private val listener:OnClickSelect
) : RecyclerView.Adapter<QuizQuestionsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = QuizQuestionsLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder) {
            with(binding) {
                val data = list[position]

                tvQuestion.text = data.question
                tvOption1.text = data.option1
                tvOption2.text = data.option2

                tvOption1.setOnClickListener {
                    tvOption1.setTextColor(Color.parseColor("#00FF00"))
                    tvOption2.setTextColor(R.color.black)

                    if (data.answer == "1") {
                        score += 10
                        listener.scoreUpdate(score)
                    } else {
                        score -= 10
                        listener.scoreUpdate(score)
                    }

                }

                tvOption2.setOnClickListener {
                    tvOption2.setTextColor(Color.parseColor("#00FF00"))
                    tvOption1.setTextColor(R.color.black)

                    if (data.answer == "2") {
                        score += 10
                        listener.scoreUpdate(score)
                    } else {
                        score -= 10
                        listener.scoreUpdate(score)
                    }

                }

            }
        }

    }

    override fun getItemCount(): Int = list.size

    inner class ViewHolder(val binding: QuizQuestionsLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnClickSelect {
        fun scoreUpdate(updateScore: Int)
    }

}