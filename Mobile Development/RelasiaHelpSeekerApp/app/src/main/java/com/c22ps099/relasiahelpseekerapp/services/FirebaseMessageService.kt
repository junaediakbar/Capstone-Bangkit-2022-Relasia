package com.c22ps099.relasiahelpseekerapp.services

import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject

/**
 * Created by Dimas Aprizawandi on 04/03/2020
 * Email : animatorist@gmail.com
 * Mobile App Developer
 */
@RequiresApi(api = Build.VERSION_CODES.M)
class FirebaseMessageService : FirebaseMessagingService() {
    private val mNotifyManager: NotificationManager? = null
    override fun onNewToken(s: String) {
        super.onNewToken(s)


    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val title = remoteMessage.notification?.title
        val body = remoteMessage.notification?.body
        // val image = remoteMessage.notification?.imageUrl
        Log.e("TAG", "ini $title")
        Log.e("TAG", "body fcm ${remoteMessage.data}")
        Log.e("TAG", "body fcm ${remoteMessage.notification}")

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (remoteMessage.data.isNotEmpty()) { //
            val jsonObject = JSONObject(remoteMessage.data as Map<*, *>)
            if (jsonObject != null) {
                val jsonObjectTitle = jsonObject.getString("title")
                val jsonObjectBody = jsonObject.getString("body")
                val jsonObjectBiodata = jsonObject.getString("biodata")
                //val id = jsonObject.getString("id").toString()
                NotificationManagers(this).displayNotification(
                    jsonObjectTitle,
                    jsonObjectBody,
                    "image",
                    "id"
                )

            }
        }


    }


}