package com.colinrtwhite.licensesdialog

import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.colinrtwhite.licensesdialog.chrome.CustomTabData
import com.colinrtwhite.licensesdialog.chrome.CustomTabsURLSpan
import com.colinrtwhite.licensesdialog.model.Notice

internal class LicensesListAdapter(
	private val list: androidx.recyclerview.widget.RecyclerView,
	private val notices: List<Notice>,
	private val customTabData: CustomTabData,
	@ColorInt private val backgroundColor: Int,
	private val alwaysExpandLicenses: Boolean
) : RecyclerView.Adapter<LicensesListAdapter.ViewHolder>() {

	private var selectedPosition = -1
		set(value) {
			if (field != -1) {
				notifyItemChanged(field)
			}
			field = value
			if (field != -1) {
				notifyItemChanged(field)
				list.scrollToPosition(field)
			}
		}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		return ViewHolder(parent.inflate(R.layout.license_dialog_list_item)).apply {
			url.movementMethod = LinkMovementMethod.getInstance()
			licenseText.setBackgroundColor(backgroundColor)
		}
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val isSelected = alwaysExpandLicenses || selectedPosition == position
		holder.licenseText.isVisible = isSelected
		holder.licenseTitle.isVisible = !isSelected

		val notice = notices[position]
		val license = notice.license
		holder.title.text = notice.title
		holder.licenseText.text = notice.licenseText
		holder.licenseTitle.text = license.title

		if (notice.url != null) {
			val span = SpannableString(notice.url.text)
			span.setSpan(CustomTabsURLSpan(notice.url.uri, customTabData), 0, span.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
			holder.url.text = span
			holder.url.isVisible = true
		} else {
			holder.url.text = ""
			holder.url.isVisible = false
		}

		if (!alwaysExpandLicenses) {
			holder.itemView.setOnClickListener {
				selectedPosition = if (selectedPosition == position) -1 else position
			}
		}
	}

	override fun getItemCount() = notices.size

	class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		val title: TextView = itemView.findViewById(R.id.title)
		val url: TextView = itemView.findViewById(R.id.url)
		val licenseTitle: TextView = itemView.findViewById(R.id.license_title)
		val licenseText: TextView = itemView.findViewById(R.id.license_text)
	}
}