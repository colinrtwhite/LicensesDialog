package com.colinrtwhite.licensesdialog.model


interface License {

	val title: String
	val text: String

	// Allow alternate variations of copyright prefix.
	val copyrightPrefix: String
		get() = "Copyright"
}