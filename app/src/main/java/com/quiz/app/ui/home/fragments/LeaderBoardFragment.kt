package com.quiz.app.ui.home.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.quiz.app.R
import com.quiz.app.data.QuizDetailsData
import com.quiz.app.databinding.FragmentLeaderBoardBinding
import com.quiz.app.ui.home.adapter.QuizListAdapter
import com.quiz.app.ui.home.viewModel.UserVM
import com.quiz.app.ui.home.viewModel.UserVMF
import com.quiz.app.utils.logError
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class LeaderBoardFragment : Fragment(), KodeinAware, QuizListAdapter.OnClickSelect {

    override val kodein by kodein()

    private val factory: UserVMF by instance()

    private lateinit var binding: FragmentLeaderBoardBinding
    private lateinit var viewModel: UserVM

    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLeaderBoardBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(requireActivity(), factory)[UserVM::class.java]

        firestore = FirebaseFirestore.getInstance()

        viewModel.fragmentTitle.postValue("Leaderboard")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getListOfQuiz()

    }

    private fun getListOfQuiz() {
        firestore
            .collection("questions")
            .get()
            .addOnSuccessListener {
                val data = it.toObjects(QuizDetailsData::class.java) as ArrayList<QuizDetailsData>
                bindUi(data)
            }
            .addOnFailureListener {
                logError(it.localizedMessage!!)
            }
    }

    private fun bindUi(data: ArrayList<QuizDetailsData>) {
        with(binding.quizListRecyclerView) {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = QuizListAdapter(data, this@LeaderBoardFragment)
        }
    }

    override fun quizSelected(data: QuizDetailsData) {
        viewModel.quizId.postValue(data.id)
        findNavController().navigate(R.id.show_leaderboard_fragment)
    }


}