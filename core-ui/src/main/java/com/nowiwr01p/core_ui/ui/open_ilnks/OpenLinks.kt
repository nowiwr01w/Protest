package com.nowiwr01p.core_ui.ui.open_ilnks

import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsService
import com.nowiwr01p.core_ui.R

fun openLink(
    url: String?,
    context: Context
) {
    if (url == null) {
        return
    }

    val uri = Uri.parse(url)
    val supported = containsAppSupportedCustomTabs(context)

    if (supported) {
        CustomTabsIntent.Builder().apply {
            setStartAnimations(context, R.anim.slide_in_right, R.anim.slide_out_left)
            setExitAnimations(context, R.anim.slide_in_left, R.anim.slide_out_right)
        }.also {
            it.build().launchUrl(context, uri)
        }
    } else {
        val intent = Intent(Intent.ACTION_VIEW, uri)
        context.startActivity(intent)
    }
}

private fun containsAppSupportedCustomTabs(context: Context): Boolean {
    val packageManager = context.packageManager
    val activityIntent = Intent()
        .setAction(Intent.ACTION_VIEW)
        .addCategory(Intent.CATEGORY_BROWSABLE)
        .setData(Uri.fromParts("https", "", null))

    val resolvedActivityList = packageManager.queryIntentActivities(activityIntent, 0)
    val packagesSupportingCustomTabs = mutableListOf<ResolveInfo>()

    resolvedActivityList.forEach { info ->
        val serviceIntent = Intent().apply {
            action = CustomTabsService.ACTION_CUSTOM_TABS_CONNECTION
            `package` = info.activityInfo.packageName
        }
        if (packageManager.resolveService(serviceIntent, 0) != null) {
            packagesSupportingCustomTabs.add(info)
        }
    }
    return packagesSupportingCustomTabs.isNotEmpty()
}