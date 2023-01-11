package com.quiz.app.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.quiz.app.data.QuizDetailsData
import com.quiz.app.network.Results
import com.quiz.app.network.dataSource.HomeDS
import com.quiz.app.utils.log
import com.quiz.app.utils.logError
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class HomeRepository:HomeDS {

    private var firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override suspend fun getListOfQuiz(): Results<ArrayList<QuizDetailsData>> =
        suspendCoroutine { cont ->
            firebaseFirestore
                .collection("questions")
                .get()
                .addOnSuccessListener {
                    val data = it.toObjects(QuizDetailsData::class.java) as ArrayList<QuizDetailsData>
                    cont.resume(Results.Success(data))
                }
                .addOnFailureListener {
                    cont.resume(Results.Error(it))
                }
        }

}