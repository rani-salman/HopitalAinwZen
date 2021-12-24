package com.example.hopitalainwzen.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.hopitalainwzen.Calender
import com.example.hopitalainwzen.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

private const val CHANNEL_ID ="my_channel"

class FirebaseService : FirebaseMessagingService() {

//    companion object{
//        var sharePref: SharedPreferences?=null
//        var token : String?
//        get(){
//            return sharePref?.getString("token" ," ")
//        }
//        set(value){
//            sharePref?.edit()?.putString("token",value)?.apply()
//        }
//
//    }

    //override fun onNewToken(newToken: String) {
        //super.onNewToken(newToken)
      //  token = newToken
   // }


    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val intent = Intent(this,Calender::class.java)
        val notificationManager =getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationID = Random.nextInt()

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createNotificationChannel(notificationManager)
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this,0, intent, FLAG_ONE_SHOT)
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(message.data["title"])
                .setContentText(message.data["message"])
                .setSmallIcon(R.drawable.ic_baseline_campaign_24)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build()

        notificationManager.notify(notificationID,notification)
//        val intent1 = Intent(this, MainActivity::class.java).apply {
//       flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//    }
//        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent1, 0)
//        val builder=NotificationCompat.Builder(this ,CHANNEL_ID)
//                .setSmallIcon(R.drawable.ic_launcher_foreground)
//                .setContentTitle("Appointment")
//                .setContentText(dd)
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//            // Set the intent that will fire when the user taps the notification
//            .setContentIntent(pendingIntent)
//            .setAutoCancel(true)
//        with(NotificationManagerCompat.from(this)){
//            notify(notificationId ,builder.build())


    }
@RequiresApi(Build.VERSION_CODES.O)
private fun createNotificationChannel(notificationManager: NotificationManager){
    val channelName ="channelName"
    val channel = NotificationChannel(CHANNEL_ID,channelName,IMPORTANCE_HIGH).apply {
        description="My channel description"
        enableLights(true)
        lightColor= Color.GREEN
    }
    notificationManager.createNotificationChannel(channel)
}
}