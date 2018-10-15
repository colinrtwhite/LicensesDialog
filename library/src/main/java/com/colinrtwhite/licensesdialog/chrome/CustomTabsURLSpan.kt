package com.colinrtwhite.licensesdialog.chrome

import android.net.Uri
import android.os.Parcelable
import android.text.style.URLSpan
import android.view.View
import com.colinrtwhite.licensesdialog.openWebPage
import kotlinx.android.parcel.Parcelize

@Parcelize
class CustomTabsURLSpan(
	private val uri: Uri,
	private val data: CustomTabData
) : URLSpan(uri.toString()), Parcelable {

	override fun onClick(widget: View) {
		val isSuccessful = widget.context.openWebPage(uri, data)
		if (!isSuccessful) {
			// Fall back to the default system behaviour for handling URL spans.
			super.onClick(widget)
		}
	}
}
