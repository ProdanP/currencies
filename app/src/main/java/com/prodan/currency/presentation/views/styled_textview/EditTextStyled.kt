package com.prodan.currency.presentation.views.styled_textview

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.prodan.currency.R


class EditTextStyled  @JvmOverloads constructor(
    context: Context,
    var attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatEditText(context, attrs, defStyleAttr) {
    private var fontName : String? = null
    private var mEnabled = false

    init {
        initAttributes()
    }

    private fun initAttributes(){
        val a = context.obtainStyledAttributes(attrs, R.styleable.EditTextStyled)
        for (i in 0 until a.indexCount) {
            val attr = a.getIndex(i)
            if (attr == R.styleable.EditTextStyled_edit_fontName_text) {
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

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        try {
            if (!mEnabled) return
            super.setEnabled(false)
            super.setEnabled(mEnabled)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun setEnabled(enabled: Boolean) {
        mEnabled = enabled;
        super.setEnabled(enabled)

    }

}