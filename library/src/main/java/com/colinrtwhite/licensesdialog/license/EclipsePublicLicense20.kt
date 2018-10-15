package com.colinrtwhite.licensesdialog.license

import com.colinrtwhite.licensesdialog.model.License

object EclipsePublicLicense20 : License {

	override val title = "Eclipse Public License 2.0"

	override val text = run {
		"This program and the accompanying materials are made available under the\n" +
		"3 terms of the Eclipse Public License 2.0 which is available at\n" +
		"https://www.eclipse.org/legal/epl-2.0\n" +
		"\n" +
		"SPDX-License-Identifier: EPL-2.0"
	}
}