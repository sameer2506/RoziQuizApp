package com.quiz.app.ui.home.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.quiz.app.AppPreferences
import com.quiz.app.R
import com.quiz.app.data.QuizQuestions
import com.quiz.app.databinding.FragmentTakeQuizBinding
import com.quiz.app.ui.home.adapter.QuizQuestionsAdapter
import com.quiz.app.ui.home.viewModel.UserVM
import com.quiz.app.ui.home.viewModel.UserVMF
import com.quiz.app.utils.log
import com.quiz.app.utils.logError
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class TakeQuizFragment : Fragment(), KodeinAware, QuizQuestionsAdapter.OnClickSelect {

    override val kodein by kodein()

    private val factory: UserVMF by instance()

    private lateinit var binding: FragmentTakeQuizBinding
    private lateinit var viewModel: UserVM

    private lateinit var firestore: FirebaseFirestore

    private lateinit var appPreferences: AppPreferences

    private var score = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTakeQuizBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(requireActivity(), factory)[UserVM::class.java]

        firestore = FirebaseFirestore.getInstance()

        appPreferences = AppPreferences(requireContext())

        viewModel.fragmentTitle.postValue("Quiz")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getListOfQuestions()

        binding.btnSubmit.setOnClickListener {
            submit()
        }

    }

    private fun submit() {
        log("Score: $score")

        val timestamp = Timestamp.now()
        val milliseconds = timestamp.seconds * 1000 + timestamp.nanoseconds / 1000000
        val date = Date(milliseconds)

        val data = HashMap<String, Any>()
        data["addedOn"] = date
        data["score"] = score
        data["name"] = appPreferences.getId().toString()
        data["id"] = appPreferences.getName().toString()

        firestore
            .collection("leaderboard/${viewModel.quizId.value}/scores")
            .document()
            .set(data)
            .addOnSuccessListener {
                findNavController().navigate(R.id.home_fragment)
            }
            .addOnFailureListener {
                logError(it.localizedMessage!!)
            }
    }

    private fun getListOfQuestions() {

        val quizId = viewModel.quizId.value.toString()

        firestore
            .collection("questions/$quizId/quest")
            .get()
            .addOnSuccessListener {
                val data = it.toObjects(QuizQuestions::class.java) as ArrayList<QuizQuestions>
                bindUi(data)
            }
            .addOnFailureListener {
                logError(it.localizedMessage!!)
            }

    }

    private fun bindUi(data: ArrayList<QuizQuestions>) {
        binding.quizQuestionRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = QuizQuestionsAdapter(data, score, this@TakeQuizFragment)
        }
    }

    override fun scoreUpdate(updateScore: Int) {
        score = updateScore
        log("Update score: $updateScore")
    }

}