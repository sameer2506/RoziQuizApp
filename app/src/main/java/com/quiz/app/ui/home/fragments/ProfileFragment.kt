package com.quiz.app.ui.home.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.quiz.app.R
import com.quiz.app.databinding.FragmentProfileBinding
import com.quiz.app.ui.home.viewModel.UserVM
import com.quiz.app.ui.home.viewModel.UserVMF
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.bindings.Binding
import org.kodein.di.generic.instance

class ProfileFragment : Fragment(),KodeinAware {

    override val kodein by kodein()

    private val factory:UserVMF by instance()

    private lateinit var binding: FragmentProfileBinding

    private lateinit var viewModel:UserVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentProfileBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(requireActivity(),factory )[UserVM::class.java]

        viewModel.fragmentTitle.postValue("Profile")

        return binding.root
    }

}