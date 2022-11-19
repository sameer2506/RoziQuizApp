package com.quiz.app.ui.auth.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.quiz.app.AppPreferences
import com.quiz.app.databinding.FragmentUserDetailsBinding
import com.quiz.app.ui.home.activity.HomeActivity
import com.quiz.app.utils.logError
import com.quiz.app.utils.toast

class UserDetailsFragment : Fragment() {

    private lateinit var binding: FragmentUserDetailsBinding

    private lateinit var appPreferences: AppPreferences

    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserDetailsBinding.inflate(layoutInflater)
        appPreferences = AppPreferences(requireContext())
        firestore = FirebaseFirestore.getInstance()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSaveDetails.setOnClickListener {
            save()
        }

    }

    private fun save() {
        val name = binding.etUserName.text.toString()
        val userId = appPreferences.getId().toString()


        val data = HashMap<String, Any>()
        data["name"] = name
        data["id"] = userId

        firestore.collection("users")
            .document(userId)
            .set(data)
            .addOnSuccessListener {
                appPreferences.saveName(name)
                startActivity(Intent(requireContext(), HomeActivity::class.java))
                requireActivity().finish()
            }
            .addOnFailureListener {
                requireContext().toast(it.localizedMessage!!)
                logError(it.localizedMessage!!)
            }

    }

}