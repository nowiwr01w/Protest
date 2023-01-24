package com.nowiwr01p.profile.ui.data

import androidx.annotation.DrawableRes
import com.nowiwr01p.profile.BuildConfig
import com.nowiwr01p.profile.BuildConfig.*
import com.nowiwr01p.profile.R
import com.nowiwr01p.profile.ui.ProfileContract.*
import com.nowiwr01p.profile.ui.data.ProfileItem.*

sealed class ProfileItem(
    open val name: String,
    @DrawableRes open val startIcon: Int,
    open val onClick: () -> Unit = {},
    @DrawableRes open val endIcon: Int? = null
) {
    /**
     * ACCESS
     */
    data class OrganizerItem(
        val state: State,
        val listener: Listener?,
        override val name: String = "Стать организатором",
        override val startIcon: Int = R.drawable.ic_brain,
        override val endIcon: Int? = if (state.user.organizer) R.drawable.ic_done else null,
        override val onClick: () -> Unit = { listener?.openLink(ORGANIZER_LINK) },
    ): ProfileItem(name, startIcon, onClick)

    data class NewsWriterItem(
        val state: State,
        val listener: Listener?,
        override val name: String = "Стать редактором новостей",
        override val startIcon: Int = R.drawable.ic_lamp,
        override val endIcon: Int? = if (state.user.writer) R.drawable.ic_done else null,
        override val onClick: () -> Unit = { listener?.openLink(WRITER_LINK) },
    ): ProfileItem(name, startIcon, onClick)

    /**
     * APP
     */
    data class CityItem(
        val listener: Listener?,
        override val name: String = "Сменить город",
        override val startIcon: Int = R.drawable.ic_location,
        override val onClick: () -> Unit = { listener?.toChangeCity() },
    ): ProfileItem(name, startIcon, onClick)

    data class NotificationItem(
        val listener: Listener?,
        override val name: String = "Уведомления",
        override val startIcon: Int = R.drawable.ic_bell,
        override val onClick: () -> Unit = { listener?.openLink(WRITER_LINK) },
    ): ProfileItem(name, startIcon, onClick)

    /**
     * ABOUT PROJECT
     */
    data class BugItem(
        val listener: Listener?,
        override val name: String = "Сообщить о баге",
        override val startIcon: Int = R.drawable.ic_bug,
        override val onClick: () -> Unit = { listener?.openLink(BUG_LINK) },
    ): ProfileItem(name, startIcon, onClick)

    data class SuggestIdeaItem(
        val listener: Listener?,
        override val name: String = "Предложить свою идею",
        override val startIcon: Int = R.drawable.ic_suggest_idea,
        override val onClick: () -> Unit = { listener?.openLink(SUGGEST_IDEA_LINK) },
    ): ProfileItem(name, startIcon, onClick)

    data class DevelopmentItem(
        val listener: Listener?,
        override val name: String = "Присоединиться к разработке",
        override val startIcon: Int = R.drawable.ic_development,
        override val onClick: () -> Unit = { listener?.openLink(DEVELOPMENT_LINK) },
    ): ProfileItem(name, startIcon, onClick)

    data class SupportProjectItem(
        val listener: Listener?,
        override val name: String = "Поддержать проект",
        override val startIcon: Int = R.drawable.ic_money,
        override val onClick: () -> Unit = { listener?.openLink(SUPPORT_PROJECT_LINK) },
    ): ProfileItem(name, startIcon, onClick)

    /**
     * POLITICS
     */
    data class PrivacyItem(
        val listener: Listener?,
        override val name: String = "Политика приватности",
        override val startIcon: Int = R.drawable.ic_security,
        override val onClick: () -> Unit = { listener?.openLink(PRIVACY_LINK) },
    ): ProfileItem(name, startIcon, onClick)

    data class TermsItem(
        val listener: Listener?,
        override val name: String = "Политика обработки данных",
        override val startIcon: Int = R.drawable.ic_data_management,
        override val onClick: () -> Unit = { listener?.openLink(TERMS_LINK) },
    ): ProfileItem(name, startIcon, onClick)

    /**
     * POLITICS
     */
    data class LogoutItem(
        val listener: Listener?,
        override val name: String = "Выйти из аккаунта",
        override val startIcon: Int = R.drawable.ic_logout,
        override val onClick: () -> Unit = { listener?.showLogoutAlert(true) }
    ): ProfileItem(name, startIcon, onClick)

    data class DeleteAccountItem(
        val listener: Listener?,
        override val name: String = "Удалить аккаунт",
        override val startIcon: Int = R.drawable.ic_delete_account,
        override val onClick: () -> Unit = { listener?.showDeleteAccountAlert(true) }
    ): ProfileItem(name, startIcon, onClick)
}

/**
 * PROFILE ITEMS
 */
internal fun getProfileItems(state: State, listener: Listener?) = listOf(
    "Получить доступ" to listOf(
        OrganizerItem(state, listener),
        NewsWriterItem(state, listener)
    ),
    "Приложение" to listOf(
        CityItem(listener),
        NotificationItem(listener)
    ),
    "О проекте" to listOf(
        BugItem(listener),
        SuggestIdeaItem(listener),
        DevelopmentItem(listener),
        SupportProjectItem(listener)
    ),
    "Условия пользования" to listOf(
        PrivacyItem(listener),
        TermsItem(listener)
    ),
    "Выход" to listOf(
        LogoutItem(listener),
        DeleteAccountItem(listener)
    ),
)