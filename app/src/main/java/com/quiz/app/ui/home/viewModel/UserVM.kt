package com.quiz.app.ui.home.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quiz.app.data.QuizDetailsData
import com.quiz.app.network.Results
import com.quiz.app.repository.HomeRepository
import kotlinx.coroutines.launch

class UserVM(
    private val repository: HomeRepository
) : ViewModel() {

    var fragmentTitle = MutableLiveData<String>()

    var quizName = MutableLiveData<String>()
    var quizCategory = MutableLiveData<String>()

    var quizId = MutableLiveData<String>()
    var quizScore = MutableLiveData<Int>()

    private val _listOfQuiz: MutableLiveData<Results<ArrayList<QuizDetailsData>>> =
        MutableLiveData()
    val listOfQuiz: LiveData<Results<ArrayList<QuizDetailsData>>>
        get() = _listOfQuiz

    fun getListOfQuiz() = viewModelScope.launch {
        _listOfQuiz.value = repository.getListOfQuiz()
    }

}