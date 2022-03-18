package com.chinmay.horobook

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PushNotificationService: FirebaseMessagingService() {

    override fun onNewToken(s: String) {
        Log.d("NEW_TOKEN", s)
    }



    override fun onMessageReceived(message: RemoteMessage) {
        val title = message.notification!!.title
        val desc = message.notification!!.body
        //val img = message.notification!!.icon

         val channelID = "HEADS_UP_NOTIFICATION"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =  NotificationChannel(
                channelID,
                "Heads Up Notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)

            val notification = Notification.Builder(this, channelID)
                .setContentTitle(title)
                .setContentText(desc)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.mipmap.ic_launcher))
            NotificationManagerCompat.from(this).notify(1, notification.build())

        } else {

        }




        super.onMessageReceived(message)
    }
}