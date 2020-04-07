package com.prodan.currency.presentation.views.styled_textview

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.prodan.currency.R

class TextViewStyled  @JvmOverloads constructor(
    context: Context,
    var attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {
    private var fontName : String? = null
    init {
        initAttributes()
    }

    private fun initAttributes(){
        val a = context.obtainStyledAttributes(attrs, R.styleable.TextViewStyled)
        for (i in 0 until a.indexCount) {
            val attr = a.getIndex(i)
            if (attr == R.styleable.TextViewStyled_fontName_text) {
                fontName = a.getString(attr)
                fontName?.let {
                    applyStyledFont(it, context)
                }
            }
        }
        includeFontPadding = false
        a.recycle()
    }

    private fun applyStyledFont(
        fontName: String,
        context: Context
    ) {
        val customFont = selectTypeface(context, fontName)
        typeface = customFont
        includeFontPadding = false
    }

    private fun selectTypeface(
        context: Context,
        fName: String
    ): Typeface? {
        return FontManager.getTypeface("$fName.ttf", context)
    }
}