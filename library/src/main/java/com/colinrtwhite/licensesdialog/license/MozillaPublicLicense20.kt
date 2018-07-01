package com.colinrtwhite.licensesdialog.license

import com.colinrtwhite.licensesdialog.model.License


object MozillaPublicLicense20 : License {

	override val title = "Mozilla Public License 2.0"

	override val text = run {
		"This Source Code Form is subject to the terms of the Mozilla Public\n" +
		"License, v. 2.0. If a copy of the MPL was not distributed with this\n" +
		"file, You can obtain one at https://mozilla.org/MPL/2.0/."
	}
}