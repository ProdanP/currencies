package com.prodan.currency.base.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView


abstract class RowViewHolder(view : View) : RecyclerView.ViewHolder(view), RowInterface {
    var listener : RowActionListener? = null
}