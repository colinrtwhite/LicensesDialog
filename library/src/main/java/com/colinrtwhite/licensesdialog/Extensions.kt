package com.colinrtwhite.licensesdialog

import android.content.Context
import android.content.Intent
import android.content.res.TypedArray
import android.graphics.Color
import android.net.Uri
import android.support.annotation.AttrRes
import android.support.annotation.ColorInt
import android.support.annotation.LayoutRes
import android.support.customtabs.CustomTabsIntent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.colinrtwhite.licensesdialog.chrome.CustomTabData


internal var View.isVisible: Boolean
	get() = visibility == View.VISIBLE
	set(value) {
		visibility = if (value) View.VISIBLE else View.GONE
	}

internal val Context.primaryColor: Int
	@ColorInt get() = getColorResource(R.attr.colorPrimary, Color.BLACK)

@ColorInt
internal fun Context.getColorResource(@AttrRes colorResId: Int, @ColorInt defaultValue: Int): Int {
	var typedArray: TypedArray? = null
	return try {
		typedArray = obtainStyledAttributes(intArrayOf(colorResId))
		typedArray.getColor(0, defaultValue)
	} catch (e: Exception) {
		defaultValue
	} finally {
		typedArray?.recycle()
	}
}

@Suppress("UNCHECKED_CAST")
internal fun <T : View> ViewGroup.inflate(@LayoutRes resource: Int): T {
	return LayoutInflater.from(context).inflate(resource, this, false) as T
}

internal fun Context.openWebPage(uri: Uri, data: CustomTabData): Boolean {
	// Try using Chrome Custom Tabs.
	try {
		val intent = CustomTabsIntent.Builder()
			.setShowTitle(true)
			.setToolbarColor(data.toolbarColor)
			.build()
		intent.launchUrl(this, uri)
		return true
	} catch (ignored: Exception) {}

	// Fall back to launching a default web browser intent.
	try {
		val intent = Intent(Intent.ACTION_VIEW, uri)
		if (intent.resolveActivity(packageManager) != null) {
			startActivity(intent)
			return true
		}
	} catch (ignored: Exception) {}

	// We were unable to show the web page.
	return false
}