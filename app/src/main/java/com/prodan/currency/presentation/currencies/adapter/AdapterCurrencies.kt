package com.prodan.currency.presentation.currencies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.prodan.currency.R
import com.prodan.currency.base.viewholders.ChangePayload
import com.prodan.currency.base.viewholders.Row
import com.prodan.currency.base.viewholders.RowViewHolderPayload
import com.prodan.currency.base.viewholders.createCombinedPayload
import com.prodan.currency.data.models.db.CurrencyLocal
import com.prodan.currency.domain.models.RowCurrencyData

class AdapterCurrencies : RecyclerView.Adapter<RowViewHolderPayload>() {
    val rows = mutableListOf<Row>()
    var listener : ViewHolderCurrency.ICurrencyActions? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowViewHolderPayload {
        return ViewHolderCurrency(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_currency,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = rows.size

    override fun onBindViewHolder(holder: RowViewHolderPayload, position: Int) {
        holder.listener = listener
        holder.setDataOnView(rows[position])
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(
        holder: RowViewHolderPayload,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val combinedChange =
                createCombinedPayload<Row>(payloads as List<ChangePayload<Row>>)
            holder.setDataWithPayload(combinedChange)
        }
    }

    fun notifyChanges(newList: List<Row>) {
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldItem = rows[oldItemPosition]
                val newItem = newList[newItemPosition]

                return if (oldItem is RowCurrencyData && newItem is RowCurrencyData) {
                    oldItem.currency.code == newItem.currency.code
                } else {
                    false
                }
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldItem = rows[oldItemPosition]
                val newItem = newList[newItemPosition]

                return if (oldItem is RowCurrencyData && newItem is RowCurrencyData) {
                   oldItem == newItem
                } else {
                    false
                }
            }

            override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
                return ChangePayload(rows[oldItemPosition], newList[newItemPosition])
            }

            override fun getOldListSize() = rows.size

            override fun getNewListSize() = newList.size
        })

        diff.dispatchUpdatesTo(this)
        rows.clear()
        rows.addAll(newList)
    }
}