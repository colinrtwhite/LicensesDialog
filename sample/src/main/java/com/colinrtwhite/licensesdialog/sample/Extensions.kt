package com.colinrtwhite.licensesdialog.sample

import android.view.View

fun View.setOnClickListener(onClick: (() -> Unit)?) {
	if (onClick != null) {
		setOnClickListener { onClick() }
	} else {
		setOnClickListener(null)
	}
}