package com.colinrtwhite.licensesdialog.sample

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.colinrtwhite.licensesdialog.LicensesDialog
import com.colinrtwhite.licensesdialog.license.ApacheLicense20
import com.colinrtwhite.licensesdialog.license.GnuGeneralPublicLicense30
import com.colinrtwhite.licensesdialog.license.MitLicense
import com.colinrtwhite.licensesdialog.model.Copyright
import com.colinrtwhite.licensesdialog.model.Notice

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		findViewById<Button>(R.id.licenses_button).setOnClickListener(this::onLicensesButtonClick)
	}

	private fun onLicensesButtonClick() {
		val notices = listOf(
			Notice("Library #1", ApacheLicense20, "www.google.com", Copyright("Somebody", 2017)),
			Notice("Library #2", MitLicense, "www.bbc.com", Copyright("Anybody", 2016)),
			Notice("Library #3", GnuGeneralPublicLicense30),
			Notice("Library #4", ApacheLicense20, "www.github.com", Copyright("Somebody", 2017)),
			Notice("Library #5", MitLicense, "www.wikipedia.org", Copyright("Anybody", 2016)),
			Notice("Library #6", GnuGeneralPublicLicense30, "www.youtube.com/watch?v=dQw4w9WgXcQ")
		)

		LicensesDialog.Builder(this)
			.setTitle("Open source licenses")
			.setNotices(notices)
			.setCustomTabPrimaryColor(ContextCompat.getColor(this, R.color.colorAccent))
			.show()
	}
}
