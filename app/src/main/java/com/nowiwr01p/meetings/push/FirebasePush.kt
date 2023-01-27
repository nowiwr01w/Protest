package com.nowiwr01p.meetings.push

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent.*
import android.content.Intent
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.nowiwr01p.meetings.R
import com.nowiwr01p.meetings.ui.MainActivity
import timber.log.Timber
import java.util.*

class FirebasePush: FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Timber.tag("Zhopa").d("onNewToken() = $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val text = message.notification?.body
        val title = message.notification?.title

        if (text != null && title != null) {
            showNotification(title, text)
        }
    }

    private fun showNotification(title: String, text: String) {
        val manager = applicationContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        Channel.getChannels().forEach { channel ->
            createChannel(channel, manager)
        }

        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        val pendingIntent = getActivity(
            this,
            0,
            intent,
            FLAG_IMMUTABLE or FLAG_UPDATE_CURRENT
        )

        val notificationBuilder = NotificationCompat.Builder(applicationContext, packageName)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(text)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setVibrate(longArrayOf(1000, 1000))
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        manager.notify(UUID.randomUUID().hashCode(), notificationBuilder.build())
    }

    private fun createChannel(channelInfo: Channel, manager: NotificationManager) = with(channelInfo) {
        val channel = NotificationChannel(id, name, priority).apply {
            description = details
        }
        manager.createNotificationChannel(channel)
    }
}