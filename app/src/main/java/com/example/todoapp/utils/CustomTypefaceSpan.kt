package com.example.todoapp.utils

import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.MetricAffectingSpan

class CustomTypefaceSpan(private val typeface: Typeface) : MetricAffectingSpan() {

    override fun updateMeasureState(p: TextPaint) {
        p.typeface = typeface
        p.flags = p.flags or Paint.SUBPIXEL_TEXT_FLAG
    }

    override fun updateDrawState(tp: TextPaint) {
        tp.typeface = typeface
        tp.flags = tp.flags or Paint.SUBPIXEL_TEXT_FLAG
    }
}