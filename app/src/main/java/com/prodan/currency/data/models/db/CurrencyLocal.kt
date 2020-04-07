package com.prodan.currency.data.models.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.prodan.currency.base.viewholders.Row
import com.prodan.currency.domain.models.RowCurrencyData

@Entity(tableName = "currency_local")
data class CurrencyLocal(
    @PrimaryKey var code: String, var name: String,
    var flag: String,
    var rate: Float,
    var isReference: Boolean = false
)