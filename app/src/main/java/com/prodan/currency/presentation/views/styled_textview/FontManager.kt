package com.prodan.currency.presentation.views.styled_textview

import android.content.Context
import android.graphics.Typeface
import java.util.*

object FontManager {
    private val fontCache = HashMap<String, Typeface>()

    fun getTypeface(fontName: String, context: Context): Typeface? {
        var typeFace: Typeface? = fontCache[fontName]
        if (typeFace == null) {
            typeFace = try {
                Typeface.createFromAsset(context.assets, fontName)
            } catch (e: Exception) {
                println("Missing font asset")
                return null
            }
            fontCache[fontName] =  typeFace
        }
        return typeFace
    }
}