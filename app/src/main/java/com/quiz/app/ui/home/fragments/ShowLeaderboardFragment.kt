package com.quiz.app.ui.home.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.quiz.app.R
import com.quiz.app.data.ScoreData
import com.quiz.app.databinding.FragmentLeaderBoardBinding
import com.quiz.app.databinding.FragmentShowLeaderboardBinding
import com.quiz.app.ui.home.adapter.ScoresAdapter
import com.quiz.app.ui.home.viewModel.UserVM
import com.quiz.app.ui.home.viewModel.UserVMF
import com.quiz.app.utils.log
import com.quiz.app.utils.logError
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class ShowLeaderboardFragment : Fragment(), KodeinAware {

    override val kodein by kodein()

    private val factory: UserVMF by instance()

    private lateinit var binding: FragmentShowLeaderboardBinding
    private lateinit var viewModel: UserVM

    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShowLeaderboardBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(requireActivity(), factory)[UserVM::class.java]

        firestore = FirebaseFirestore.getInstance()

        viewModel.fragmentTitle.postValue("Score")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getListOfScores(viewModel.quizId.value.toString())

    }

    private fun getListOfScores(quizId: String) {

        firestore
            .collection("leaderboard/${quizId}/scores")
            .orderBy("score", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener {
                val data = it.toObjects(ScoreData::class.java) as ArrayList<ScoreData>
                log(data.toString())
                bindUi(data)
            }
            .addOnFailureListener {
                logError(it.localizedMessage!!)
            }

    }

    private fun bindUi(data: ArrayList<ScoreData>) {
        binding.leaderboardRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = ScoresAdapter(data)
        }
    }

}