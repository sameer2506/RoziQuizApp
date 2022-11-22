package com.quiz.app.ui.home.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.quiz.app.R
import com.quiz.app.data.QuizQuestions
import com.quiz.app.databinding.FragmentQuizQuestionBinding
import com.quiz.app.ui.home.viewModel.UserVM
import com.quiz.app.ui.home.viewModel.UserVMF
import com.quiz.app.utils.log
import com.quiz.app.utils.logError
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.util.*

class QuizQuestionFragment : Fragment(),KodeinAware {

    override val kodein by kodein()

    private val factory: UserVMF by instance()

    private lateinit var binding: FragmentQuizQuestionBinding
    private lateinit var viewModel: UserVM

    private lateinit var firestore: FirebaseFirestore

    private var questionList = ArrayList<QuizQuestions>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuizQuestionBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(requireActivity(), factory)[UserVM::class.java]

        firestore = FirebaseFirestore.getInstance()

        viewModel.fragmentTitle.postValue("Questions")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSave.setOnClickListener {
            save()
        }

        binding.btnSubmit.setOnClickListener {
            saveQuizDetails()
        }

    }

    private fun saveQuizDetails(){

        val timestamp = Timestamp.now()
        val milliseconds = timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
        val date = Date(milliseconds)

        val data = HashMap<String,Any>()
        data["addedOn"] = date

        firestore
            .collection("questions")
            .add(data)
            .addOnSuccessListener{
                updateQuizDetails(it.id)
            }
            .addOnFailureListener {
                logError(it.localizedMessage!!)
            }

    }

    private fun updateQuizDetails(id:String){

        val data = kotlin.collections.HashMap<String,Any>()
        data["name"] = viewModel.quizName.value.toString()
        data["category"] = viewModel.quizCategory.value.toString()
        data["id"] = id

        firestore
            .collection("questions")
            .document(id)
            .update(data)
            .addOnSuccessListener {
                saveQuestion(id)
                findNavController().navigate(R.id.home_fragment)
            }
            .addOnFailureListener {
                logError(it.localizedMessage!!)
            }

    }

    private fun saveQuestion(id:String){

        for (question in questionList){

            val data = kotlin.collections.HashMap<String,Any>()
            data["question"] = question.question
            data["option1"] = question.option1
            data["option2"] = question.option2
            data["answer"]=question.answer

            firestore
                .collection("questions/$id/quest")
                .add(data)
                .addOnSuccessListener {
                    log("saved")
                }
                .addOnFailureListener {
                    logError(it.localizedMessage!!)
                }

        }

    }

    private fun save(){
        val question = binding.etQuestion.text.toString()
        val option1 = binding.etOption1.text.toString()
        val option2 = binding.etOption2.text.toString()
        val answer = binding.etAnswer.text.toString()

        val data = QuizQuestions(question,option1, option2, answer)
        questionList.add(data)

        binding.etQuestion.setText("")
        binding.etOption1.setText("")
        binding.etOption2.setText("")
        binding.etAnswer.setText("")

        log(questionList.toString())
    }

}