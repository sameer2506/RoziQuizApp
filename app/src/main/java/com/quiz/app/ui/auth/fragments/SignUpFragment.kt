package com.quiz.app.ui.auth.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.quiz.app.R
import com.quiz.app.databinding.FragmentSignUpBinding
import com.quiz.app.utils.log
import com.quiz.app.utils.logError

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(layoutInflater)

        auth = FirebaseAuth.getInstance()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignUp.setOnClickListener {
            signUp()
        }

    }

    private fun signUp() {

        val emailId = binding.etEmailId.text.toString()
        val password = binding.etPassword.text.toString()
        val confirmPassword = binding.etConfirmPassword.text.toString()

        if (password != confirmPassword) {
            Toast.makeText(requireContext(), "Wrong password", Toast.LENGTH_SHORT).show()
            return
        }

        auth
            .createUserWithEmailAndPassword(emailId, password)
            .addOnSuccessListener {
                log(it.user!!.uid)
                findNavController().navigate(R.id.sign_in_fragment)
            }
            .addOnFailureListener {
                logError(it.localizedMessage!!)
            }

    }

}