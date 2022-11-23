package com.quiz.app.ui.home.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.quiz.app.R
import com.quiz.app.databinding.FragmentQuizDetailsBinding
import com.quiz.app.ui.home.viewModel.UserVM
import com.quiz.app.ui.home.viewModel.UserVMF
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class QuizDetailsFragment : Fragment(),KodeinAware {

    override val kodein by kodein()

    private val factory: UserVMF by instance()

    private lateinit var binding: FragmentQuizDetailsBinding
    private lateinit var viewModel: UserVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuizDetailsBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(requireActivity(), factory)[UserVM::class.java]

        viewModel.fragmentTitle.postValue("Details")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNext.setOnClickListener {
            save()
        }

    }

    private fun save(){

        val name = binding.etQuizName.text.toString()
        val category = binding.etQuizCategory.text.toString()

        viewModel.quizName.postValue(name)
        viewModel.quizCategory.postValue(category)

        findNavController().navigate(R.id.quiz_question_fragment)

    }

}