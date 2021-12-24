package com.example.hopitalainwzen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.example.hopitalainwzen.Notification.NotificationData
import com.example.hopitalainwzen.Notification.PushNotification
import com.example.hopitalainwzen.Notification.RetrofitInstance
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DoctorResponse : AppCompatActivity() {
    lateinit var patient: User
    val TAG = "DoctorResponse "
    var dd: String? = null
    lateinit var message : String
    lateinit var doctorname: String
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_response)
        val sickness = findViewById<TextView>(R.id.sickness)
        val name=findViewById<TextView>(R.id.name)
        patient = intent.getSerializableExtra("patient") as User
        name.text="Patient's Name : " +  patient?.patientProperties?.name.toString()
        sickness.text= "Sickness: " + patient?.patientProperties?.sickness.toString()
        val userId = patient.userId
        val token = patient.token

        val Calender: CalendarView = findViewById(R.id.calendarView)
        Calender.setOnDateChangeListener(CalendarView.OnDateChangeListener() { calendarView: CalendarView, i: Int, i1: Int, i2: Int ->
            dd = "$i2 / " + (i1 + 1) + "/ $i"
        })
        val send = findViewById<Button>(R.id.btnSend)
        send.setOnClickListener {
            val keynote = findViewById<EditText>(R.id.Keynotes).text.toString()
            val timer = findViewById<EditText>(R.id.editTextTime2).text.toString()
    
            val title = "Response from Dr.Rawad"
  
            if (keynote != null) {
                 message = "the date is $dd at $timer , Note : $keynote"
            }
            else {
                 message = "the date is $dd at $timer"
            }
                token?.let {
                    if (title.isNotEmpty() && message.isNotEmpty()) {
                    PushNotification(
                            NotificationData(title, message),
                            it
                    ).also {
                        sendNotification(it)
                        Toast.makeText(this@DoctorResponse, "submitted to patient", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    private fun sendNotification(notification: PushNotification) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = RetrofitInstance.api.postNotification(notification)
            if (response.isSuccessful) {
                Log.d(TAG, "Response :${Gson().toJson(response)}")

            } else {
                Log.e(TAG, response.errorBody().toString())

            }
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
            Toast.makeText(this@DoctorResponse, "submit is failed", Toast.LENGTH_SHORT).show()
        }
    }
}