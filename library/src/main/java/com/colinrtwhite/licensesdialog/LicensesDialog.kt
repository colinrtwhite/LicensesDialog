@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package com.colinrtwhite.licensesdialog

import android.content.Context
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.MainThread
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.colinrtwhite.licensesdialog.chrome.CustomTabData
import com.colinrtwhite.licensesdialog.license.ApacheLicense20
import com.colinrtwhite.licensesdialog.model.Copyright
import com.colinrtwhite.licensesdialog.model.Notice
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.Collections.emptyList

/**
 * Use [LicensesDialog.Builder] to create and show a list of open source software notices.
 */
class LicensesDialog private constructor(
	private val context: Context,
	private val title: String,
	@StyleRes private val themeResId: Int,
	@ColorInt private val customTabPrimaryColor: Int,
	private val alwaysExpandLicenses: Boolean,
	private val isDarkTheme: Boolean,
	private val notices: List<Notice>
) {

	companion object {
		private val LIBRARY_NOTICE = Notice(
			"Licenses Dialog",
			ApacheLicense20,
			"github.com/colinrtwhite/LicensesDialog",
			Copyright("Colin White", 2020)
		)
	}

	class Builder(private val context: Context) {

		private var title = context.getString(R.string.default_title)
		@StyleRes private var themeResId = -1
		@ColorInt private var customTabPrimaryColor = -1
		private var alwaysExpandLicenses = false
		private var includeOwnLicense = true
		private var isDarkTheme = false
		private var notices: List<Notice> = emptyList()

		fun setTitle(@StringRes titleResId: Int) = apply {
			this.title = context.getString(titleResId)
		}

		fun setTitle(title: String) = apply {
			this.title = title
		}

		fun setStyle(@StyleRes themeResId: Int) = apply {
			this.themeResId = themeResId
		}

		fun setCustomTabPrimaryColor(@ColorInt primaryColor: Int) = apply {
			this.customTabPrimaryColor = primaryColor
		}

		fun setNotices(notices: List<Notice>) = apply {
			this.notices = notices
		}

		/**
		 * If true, always show expanded license text.
		 */
		fun setAlwaysExpandLicenses(alwaysExpandLicenses: Boolean) = apply {
			this.alwaysExpandLicenses = alwaysExpandLicenses
		}

		/**
		 * If true, add this library's license automatically to the list of licenses.
		 */
		fun setIncludeOwnLicense(includeOwnLicense: Boolean) = apply {
			this.includeOwnLicense = includeOwnLicense
		}

		/**
		 * If true, use light dividers + license text background
		 * (to contrast the dark alert dialog background).
		 */
		fun setIsDarkTheme(isDarkTheme: Boolean) = apply {
			this.isDarkTheme = isDarkTheme
		}

		fun create(): LicensesDialog {
			// Defensive copy the list.
			val notices = notices.toMutableList()
			if (includeOwnLicense) {
				notices += LIBRARY_NOTICE
			}

			return LicensesDialog(
				context = context,
				title = title,
				themeResId = themeResId,
				customTabPrimaryColor = customTabPrimaryColor,
				alwaysExpandLicenses = alwaysExpandLicenses,
				isDarkTheme = isDarkTheme,
				notices = notices
			)
		}

		@MainThread
		fun show(): LicensesDialog {
			return create().apply { show() }
		}
	}

	@MainThread
	fun show() {
		val builder = if (themeResId != -1) {
			MaterialAlertDialogBuilder(context, themeResId)
		} else {
			MaterialAlertDialogBuilder(context)
		}

		// Use the AlertDialog builder's context, since it is automatically themed.
		builder
			.setTitle(title)
			.setView(buildView(builder.context))
			.setNegativeButton(R.string.close, null)
			.show()
	}

	@MainThread
	private fun buildView(context: Context): View {
		val customTabColor = customTabPrimaryColor.takeIf { it != -1 } ?: context.primaryColor
		val customTabData = CustomTabData(customTabColor)

		val dividerColorResId = if (isDarkTheme) R.color.divider_dark else R.color.divider_light
		val dividerColor = ContextCompat.getColor(context, dividerColorResId)

		val view = View.inflate(context, R.layout.license_dialog_list, null)
		view.findViewById<RecyclerView>(R.id.list).apply {
			adapter = LicensesListAdapter(this, notices, customTabData, dividerColor, alwaysExpandLicenses)
			layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
		}
		view.findViewById<View>(R.id.divider_top).setBackgroundColor(dividerColor)
		view.findViewById<View>(R.id.divider_bottom).setBackgroundColor(dividerColor)

		return view
	}
}