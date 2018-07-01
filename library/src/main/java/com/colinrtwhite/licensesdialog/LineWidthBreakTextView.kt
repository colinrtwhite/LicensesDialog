package com.colinrtwhite.licensesdialog

import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import kotlin.math.ceil


/**
 * A TextView which wraps its width to the length of its longest line.
 */
internal class LineWidthBreakTextView @JvmOverloads constructor(
	context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

	override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec)

		layout?.let {
			val width = compoundPaddingLeft + computeMaxLineWidth() + compoundPaddingRight
			setMeasuredDimension(Math.max(width, minimumWidth), measuredHeight)
		}
	}

	private fun computeMaxLineWidth(): Int {
		var maxWidth = 0.0f
		for (i in 0 until layout.lineCount) {
			maxWidth = maxOf(maxWidth, layout.getLineWidth(i))
		}
		return ceil(maxWidth.toDouble()).toInt()
	}
}