package com.colinrtwhite.licensesdialog.model


class Notice @JvmOverloads constructor(
	val title: String,
	val license: License,
	val url: URL? = null,
	val copyright: Copyright? = null
) {

	@JvmOverloads constructor(
		title: String,
		license: License,
		url: String,
		copyright: Copyright? = null
	) : this(title, license, URL(url), copyright)

	// If there is a copyright header, format it. Else, just show the license text.
	val licenseText = run {
		if (copyright != null) {
			"${license.copyrightPrefix} ${copyright.year} ${copyright.author}\n\n${license.text}"
		} else {
			license.text
		}
	}
}