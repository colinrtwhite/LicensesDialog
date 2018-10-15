package com.colinrtwhite.licensesdialog.chrome

import android.os.Parcelable
import androidx.annotation.ColorInt
import kotlinx.android.parcel.Parcelize

/**
 * Currently, this only supports toolbar colour, but could support more in the future.
 */
@Parcelize
data class CustomTabData(
	@ColorInt val toolbarColor: Int
) : Parcelable