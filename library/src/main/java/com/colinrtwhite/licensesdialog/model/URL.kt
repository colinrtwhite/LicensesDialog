@file:Suppress("MemberVisibilityCanBePrivate")

package com.colinrtwhite.licensesdialog.model

import android.net.Uri

class URL @JvmOverloads constructor(
	val text: String,
	val isHttps: Boolean = true
) {

	// Format the URL properly.
	val uri: Uri = run {
		val baseUri = Uri.parse(text)
		if (baseUri.scheme.isNullOrBlank()) {
			Uri.parse(if (isHttps) "https://$text" else "http://$text")
		} else {
			baseUri
		}
	}
}