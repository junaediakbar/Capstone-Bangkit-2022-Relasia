package com.c22ps099.relasiahelpseekerapp.services

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.c22ps099.relasiahelpseekerapp.R
import com.c22ps099.relasiahelpseekerapp.ui.main.MainActivity
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by Dimas Aprizawandi on 04/03/2020
 * Email : animatorist@gmail.com
 * Mobile App Developer
 */
class NotificationManagers(private val mCtx: Context) {
    @SuppressLint("ServiceCast")
    fun displayNotification(
            title: String?,
            body: String?,
            jsooObject: String?,
            imageUrl: String?
    ) {


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val mNotificationManager =
                    mCtx.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
            val importance = android.app.NotificationManager.IMPORTANCE_HIGH
            val mChannel =
                    NotificationChannel("Constant.CHANNEL_ID", "Constant.CHANNEL_NAME", importance)
            mChannel.description = "Constant.CHANNEL_DESCRIPTION"
            mChannel.enableLights(true)
            mChannel.lightColor = Color.RED
            mChannel.enableVibration(true)

            mNotificationManager.createNotificationChannel(mChannel)
        }
        val drawable = ContextCompat.getDrawable(mCtx, R.mipmap.ic_launcher_round)
        val bitmap = (drawable as BitmapDrawable?)?.bitmap
        val mBuilder =
                NotificationCompat.Builder(mCtx, "Constant.CHANNEL_ID")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(bitmap)
                        .setContentTitle(title)
                        .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
                        //.setSound(soundUri)
                        .setContentText(body)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        //set image notif
        val imageUrl = getBitmapfromUrl(imageUrl.toString())
        if (imageUrl != null) {
            mBuilder.setStyle(NotificationCompat.BigPictureStyle()
                    .bigPicture(imageUrl)
                    .bigLargeIcon(null)).setLargeIcon(bitmap)
        }

        //intent splash
       // val resultIntent = Intent(mCtx, MainActivity::class.java)
        //data yang dikirim -type A = page A, B = Page B
        val resultIntent = Intent(mCtx, MainActivity::class.java)
        resultIntent.putExtra("DATA", jsooObject)

        val pendingIntent = PendingIntent.getActivity(
                mCtx, 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        )

        mBuilder.setContentIntent(pendingIntent)

        val mNotifyMgr =
                mCtx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        mNotifyMgr?.notify(1, mBuilder.build())
    }

    //get bitmapurl
    private fun getBitmapfromUrl(s: String): Bitmap? {
        return try {
            val url = URL(s)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (e: Exception) {
            Log.e("awesome", "Error in getting notification image: " + e.localizedMessage)
            null
        }
    }

    companion object {
        private var mInstance: NotificationManagers? = null
        @Synchronized
        fun getInstance(context: Context): NotificationManagers? {
            if (mInstance == null) {
                mInstance = NotificationManagers(context)
            }
            return mInstance
        }
    }

}
