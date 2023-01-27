package com.nowiwr01p.meetings.push

import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.app.NotificationManager.IMPORTANCE_HIGH

data class Channel(
    val id: String,
    val name: String,
    val priority: Int,
    val details: String
) {
    companion object {
        fun getChannels() = listOf(
            Channel(NEWS_ID, NEWS_NAME, IMPORTANCE_DEFAULT, NEWS_DESCRIPTION),
            Channel(STORIES_ID, STORIES_NAME, IMPORTANCE_HIGH, STORIES_DESCRIPTION),
            Channel(MEETINGS_ID, MEETINGS_NAME, IMPORTANCE_HIGH, MEETINGS_DESCRIPTION)
        )
    }
}


private const val NEWS_ID = "news"
private const val STORIES_ID = "stories"
private const val MEETINGS_ID = "meetings"

private const val NEWS_NAME = "Новости"
private const val STORIES_NAME = "Истории"
private const val MEETINGS_NAME = "Митинги"

private const val NEWS_DESCRIPTION = "Вам не расскажут это в телевизоре"
private const val STORIES_DESCRIPTION = "Самые важные объявления"
private const val MEETINGS_DESCRIPTION = "Уведомления о новых митингах"
