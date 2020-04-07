package com.prodan.currency.presentation.currencies.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.prodan.currency.base.extentions.loadWithGlideCircle
import com.prodan.currency.base.viewholders.ChangePayload
import com.prodan.currency.base.viewholders.RowActionListener
import com.prodan.currency.base.viewholders.RowViewHolderPayload
import com.prodan.currency.domain.models.RowCurrencyData
import kotlinx.android.synthetic.main.row_currency.view.*
import java.text.DecimalFormat


class ViewHolderCurrency(view: View) : RowViewHolderPayload(view) {
    var rowListener: ICurrencyActions? = null
    private var isBind = false

    override fun setDataOnView(rowItem: Any) {
        isBind = true
        val row = rowItem as RowCurrencyData
        rowListener = listener as ICurrencyActions?

        setupFlag(row)
        setupCode(row)
        setupName(row)
        setupRateValue(row)
        setActions(row)

        itemView.currencyEdit.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus)
                rowListener?.onItemClick(adapterPosition)
        }
        isBind = false
    }

    override fun setDataWithPayload(payload: Any) {
        isBind = true
        if (payload is ChangePayload<*>) {
            val oldData = payload.oldData
            val newData = payload.newData
            if (oldData is RowCurrencyData && newData is RowCurrencyData) {
                if (oldData.currency.flag != newData.currency.flag)
                    setupFlag(newData)
                if (oldData.currency.code != newData.currency.code)
                    setupCode(newData)
                if (oldData.currency.name != newData.currency.name)
                    setupName(newData)
                if (oldData.multipliedRate() != newData.multipliedRate())
                    setupRateValue(newData)
                setActions(newData)
            }
        }
        isBind = false
    }

    private fun setupFlag(rowItem: RowCurrencyData) {
        itemView.currencyFlag.loadWithGlideCircle(rowItem.currency.flag, itemView.context)
    }

    private fun setupName(rowItem: RowCurrencyData) {
        itemView.currencyName.text = rowItem.currency.name
    }

    private fun setupCode(rowItem: RowCurrencyData) {
        itemView.currencyCode.text = rowItem.currency.code
    }

    private fun setupRateValue(rowItem: RowCurrencyData) {
        val rate = if (adapterPosition == 0) {
            rowItem.multiplier
        } else {
            rowItem.multipliedRate()
        }
        if (!itemView.currencyEdit.hasFocus()){
            itemView.currencyEdit.setText(String.format("%.2f", rate))
        }
    }

    private fun setActions(rowItem: RowCurrencyData) {
        itemView.rowContainer.setOnClickListener { rowListener?.onItemClick(adapterPosition) }
        itemView.currencyEdit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!isBind) {
                    rowListener?.onItemTextChanged(s.toString())
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    interface ICurrencyActions : RowActionListener {
        fun onItemClick(position: Int)
        fun onItemTextChanged(inputText: String)
    }
}