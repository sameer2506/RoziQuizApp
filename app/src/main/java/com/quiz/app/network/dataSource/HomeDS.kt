package com.quiz.app.network.dataSource

import com.quiz.app.data.QuizDetailsData
import com.quiz.app.network.Results

interface HomeDS {

    suspend fun getListOfQuiz(): Results<ArrayList<QuizDetailsData>>

}