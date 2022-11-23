package com.quiz.app.ui.home.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserVM : ViewModel() {

    var fragmentTitle = MutableLiveData<String>()

    var quizName = MutableLiveData<String>()
    var quizCategory = MutableLiveData<String>()

    var quizId = MutableLiveData<String>()
    var quizScore = MutableLiveData<Int>()

}