package com.colinrtwhite.licensesdialog

import android.content.Context
import android.support.annotation.ColorInt
import android.support.annotation.MainThread
import android.support.annotation.StringRes
import android.support.annotation.StyleRes
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.colinrtwhite.licensesdialog.chrome.CustomTabData
import com.colinrtwhite.licensesdialog.license.ApacheLicense20
import com.colinrtwhite.licensesdialog.model.Copyright
import com.colinrtwhite.licensesdialog.model.Notice
import java.util.Collections.emptyList


/**
 * Use [LicensesDialog.Builder] to create and show a list of open source software notices.
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
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
		private val LIBRARY_NOTICE by lazy(LazyThreadSafetyMode.NONE) {
			Notice(
				"Licenses Dialog",
				ApacheLicense20,
				"github.com/colinrtwhite/LicensesDialog",
				Copyright("Colin White", 2018)
			)
		}
	}

	class Builder(private val context: Context) {

		private var title = context.getString(R.string.default_title)
		@StyleRes private var themeResId = -1
		@ColorInt private var customTabPrimaryColor = -1
		private var alwaysExpandLicenses = false
		private var includeOwnLicense = true
		private var isDarkTheme = false
		private var notices: List<Notice> = emptyList()

		fun setTitle(@StringRes titleResId: Int): Builder {
			return setTitle(context.getString(titleResId))
		}

		fun setTitle(title: String): Builder {
			this.title = title
			return this
		}

		fun setStyle(@StyleRes themeResId: Int): Builder {
			this.themeResId = themeResId
			return this
		}

		fun setCustomTabPrimaryColor(@ColorInt primaryColor: Int): Builder {
			this.customTabPrimaryColor = primaryColor
			return this
		}

		fun setNotices(notices: List<Notice>): Builder {
			this.notices = notices
			return this
		}

		/**
		 * If true, always show expanded license text.
		 */
		fun setAlwaysExpandLicenses(alwaysExpandLicenses: Boolean): Builder {
			this.alwaysExpandLicenses = alwaysExpandLicenses
			return this
		}

		/**
		 * If true, add this library's license automatically to the list of licenses.
		 */
		fun setIncludeOwnLicense(includeOwnLicense: Boolean): Builder {
			this.includeOwnLicense = includeOwnLicense
			return this
		}

		/**
		 * If true, use light dividers + license text background
		 * (to contrast the dark alert dialog background).
		 */
		fun setIsDarkTheme(isDarkTheme: Boolean): Builder {
			this.isDarkTheme = isDarkTheme
			return this
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
			val dialog = create()
			dialog.show()
			return dialog
		}
	}

	@MainThread
	fun show() {
		val builder = if (themeResId != -1) {
			AlertDialog.Builder(context, themeResId)
		} else {
			AlertDialog.Builder(context)
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
			layoutManager = LinearLayoutManager(context)
		}
		view.findViewById<View>(R.id.divider_top).setBackgroundColor(dividerColor)
		view.findViewById<View>(R.id.divider_bottom).setBackgroundColor(dividerColor)

		return view
	}
}