package com.colinrtwhite.licensesdialog.license

import com.colinrtwhite.licensesdialog.model.License


object EclipsePublicLicense10 : License {

	override val title = "Eclipse Public License 1.0"

	override val text = run {
		"All rights reserved. This program and the accompanying materials\n" +
		"are made available under the terms of the Eclipse Public License v1.0\n" +
		"which is available at\n" +
		"\n" +
		"https://www.eclipse.org/legal/epl-v10.html"
	}
}