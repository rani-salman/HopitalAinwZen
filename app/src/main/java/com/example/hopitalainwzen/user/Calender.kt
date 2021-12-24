package com.example.hopitalainwzen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.Toast
import com.example.hopitalainwzen.Notification.NotificationData
import com.example.hopitalainwzen.Notification.PushNotification
import com.example.hopitalainwzen.Notification.RetrofitInstance
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Calender : AppCompatActivity() {
    val TAG = "Calender"
    var dd: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        val doctor  = intent.getSerializableExtra("user") as User
        val userId =doctor.userId
        val token =doctor.token
        val name = doctor.name
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calender)
        val Calender: CalendarView = findViewById(R.id.calendarView)
        Calender.setOnDateChangeListener(CalendarView.OnDateChangeListener() { calendarView: CalendarView, i: Int, i1: Int, i2: Int ->
            dd = "$i2 / " + (i1 + 1) + "/ $i"
        })
        val send = findViewById<Button>(R.id.btnSend)
        send.setOnClickListener {
            val time = findViewById<EditText>(R.id.editTextTime2).text.toString()
            val title = "Appointemnt from patient $name"
            val message = "the date is $dd at $time"
            token?.let {
                if (title.isNotEmpty() && message.isNotEmpty()) {
                    PushNotification(
                        NotificationData(title, message),
                        it
                    ).also {
                        sendNotification(it)
                        Toast.makeText(this@Calender, "submitted to doctor", Toast.LENGTH_SHORT).show()
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
            Toast.makeText(this@Calender, "failed", Toast.LENGTH_SHORT).show()
        }
    }
}