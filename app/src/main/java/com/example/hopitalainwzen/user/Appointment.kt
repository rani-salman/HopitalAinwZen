

package com.example.hopitalainwzen.user

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.hopitalainwzen.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class Appointment : AppCompatActivity() {
    lateinit var doctor: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment)
        doctor = intent.getSerializableExtra("doctor") as User
    }
    fun Onclick(view: View) {
        val username = findViewById<EditText>(R.id.Name).text.toString()
        val email = findViewById<EditText>(R.id.Email).text.toString()
        val phone = findViewById<EditText>(R.id.phone).text.toString()
        val sickness = findViewById<EditText>(R.id.sickness).text.toString()
        val notes = findViewById<EditText>(R.id.notes).text.toString()
        var patientProp = PatientProperties(
                name = username,
                email = email,
                phone = phone,
                sickness = sickness,
                notes = notes
        )
        val  mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.getCurrentUser()
        var type=""
       // var ppk: String? = null
        if (doctor.type=="doctor"){type="patient"}else{type="doctor"}
      //  ppk=intent?.getStringExtra("pp")
        var UserToUpload = User(DoctorProperties("", ""), patientProp, doctor.token, username, type, currentUser.uid)
        saveFireStore(UserToUpload)
        val intent = Intent(this, Calender::class.java)
        intent.putExtra("user", UserToUpload)
        startActivity(intent)
    }
    fun saveFireStore(user: User) {
        val db = FirebaseFirestore.getInstance()
        val userID = Firebase.auth.currentUser.uid
        db.collection("users").whereEqualTo("userId", userID).get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        Log.d(TAG, "${document.id}=>${document.data}")
                        db.collection("users").document(document.id).set(user)
                                .addOnSuccessListener {
                                    Toast.makeText(this, "record added successfully  ", Toast.LENGTH_SHORT)
                                            .show()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(this, "record falied to add", Toast.LENGTH_SHORT).show()
                                }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents:", exception)
                }
    }
}