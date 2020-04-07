package com.prodan.currency.domain.models

import com.prodan.currency.base.viewholders.Row
import com.prodan.currency.data.models.db.CurrencyLocal

data class RowCurrencyData(var currency : CurrencyLocal, var multiplier : Float) : Row {
    fun multipliedRate() : Float = currency.rate * multiplier
}