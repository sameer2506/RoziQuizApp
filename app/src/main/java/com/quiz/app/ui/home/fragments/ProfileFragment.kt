package com.quiz.app.ui.home.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.quiz.app.AppPreferences
import com.quiz.app.databinding.FragmentProfileBinding
import com.quiz.app.ui.auth.activty.AuthenticationActivity
import com.quiz.app.ui.home.viewModel.UserVM
import com.quiz.app.ui.home.viewModel.UserVMF
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class ProfileFragment : Fragment(), KodeinAware {

    override val kodein by kodein()

    private val factory: UserVMF by instance()

    private lateinit var binding: FragmentProfileBinding

    private lateinit var viewModel: UserVM

    private lateinit var appPreferences: AppPreferences

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(requireActivity(), factory)[UserVM::class.java]

        appPreferences = AppPreferences(requireContext())

        auth = FirebaseAuth.getInstance()

        viewModel.fragmentTitle.postValue("Profile")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvName.text = appPreferences.getName()

        binding.tvLogOut.setOnClickListener {
            logout()
        }

    }

    private fun logout() {
        auth.signOut()
        appPreferences.saveName("")
        appPreferences.saveId("")
        startActivity(Intent(requireContext(), AuthenticationActivity::class.java))
        requireActivity().finish()
    }

}