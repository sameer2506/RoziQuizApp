package com.quiz.app.ui.auth.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.quiz.app.R
import com.quiz.app.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(layoutInflater)

        auth = FirebaseAuth.getInstance()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignIn.setOnClickListener {
            signIn()
        }

    }

    private fun signIn() {
        val emailId = binding.etEmailId.text.toString()
        val password = binding.etPassword.text.toString()

        Log.d("appLogData","$emailId $password")

        auth.signInWithEmailAndPassword(emailId,password)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "Sign In Successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Log.d("appLogData","${it.localizedMessage}")
                Toast.makeText(requireContext(), "Sign In Failed", Toast.LENGTH_SHORT).show()
            }

    }

}