package com.quiz.app.ui.auth.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.quiz.app.AppPreferences
import com.quiz.app.R
import com.quiz.app.databinding.FragmentSignInBinding
import com.quiz.app.ui.home.activity.HomeActivity

class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private lateinit var auth: FirebaseAuth

    private lateinit var appPreferences: AppPreferences

    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(layoutInflater)

        auth = FirebaseAuth.getInstance()

        appPreferences = AppPreferences(requireContext())

        firestore = FirebaseFirestore.getInstance()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignIn.setOnClickListener {
            signIn()
        }

        binding.etEmailId.setText("sameer@gmail.com")
        binding.etPassword.setText("123456")

        binding.btnSignInToSignUp.setOnClickListener {
            findNavController().navigate(R.id.sign_up_fragment)
        }

    }

    private fun signIn() {
        val emailId = binding.etEmailId.text.toString()
        val password = binding.etPassword.text.toString()

        Log.d("appLogData", "$emailId $password")

        auth.signInWithEmailAndPassword(emailId, password)
            .addOnSuccessListener {
                appPreferences.saveId(it.user!!.uid)
                Toast.makeText(requireContext(), "Sign In Successfully", Toast.LENGTH_SHORT).show()
                getUserDetails()
            }
            .addOnFailureListener {
                Log.d("appLogData", it.localizedMessage!!)
                Toast.makeText(requireContext(), "Sign In Failed", Toast.LENGTH_SHORT).show()
            }

    }

    private fun getUserDetails() {
        firestore
            .collection("users")
            .document(appPreferences.getId().toString())
            .get()
            .addOnSuccessListener {
                if (!it.exists()) {
                    findNavController().navigate(R.id.user_details_fragment)
                } else {
                    val name = it.get("name").toString()
                    appPreferences.saveName(name)

                    startActivity(Intent(requireContext(), HomeActivity::class.java))
                    requireActivity().finish()
                }
            }

    }

}