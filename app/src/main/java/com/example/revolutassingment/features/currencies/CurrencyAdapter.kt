package com.example.revolutassingment.features.currencies

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.revolutassingment.R
import com.example.revolutassingment.domain.entities.Rate
import com.example.revolutassingment.util.CurrencyUtils
import com.example.revolutassingment.util.ext.getDrawableFromName
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CurrencyAdapter @Inject constructor() :
    RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {

    private var rates: MutableList<Rate> = mutableListOf()
    private var listener: OnRateValueChanged? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val inflater = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.currency_list_item, parent, false)

        return CurrencyViewHolder(inflater)
    }

    override fun getItemCount() = rates.count()

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(rates[position])
    }

    fun setListener(listener: OnRateValueChanged) {
        this.listener = listener
    }

    fun setRates(rates: MutableList<Rate>) {
        this.rates = rates
        notifyDataSetChanged()
    }

    inner class CurrencyViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val flag = view.findViewById<ImageView>(R.id.flag)
        private val title = view.findViewById<TextView>(R.id.title)
        private val subtitle = view.findViewById<TextView>(R.id.subtitle)
        private val value = view.findViewById<EditText>(R.id.value)
        private val root = view.findViewById<ConstraintLayout>(R.id.root)

        fun bind(rate: Rate) {
            title.text = rate.symbol
            value.setText(rate.value.toString())
            subtitle.text = CurrencyUtils.getCurrencySymbol(rate.symbol)
            flag.setImageResource(view.getDrawableFromName(CurrencyUtils.normalizeCode(rate.symbol)))

            val textWatcher = object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {}
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(s: CharSequence, p1: Int, p2: Int, p3: Int) {
                    if (adapterPosition == 0) {
                        listener?.onChanged(rate.symbol, CurrencyUtils.normalizeValue(s.toString()))
                    }
                }
            }

            root.setOnClickListener {
                value.requestFocus()
            }

            value.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    moveToTop()
                    value.setSelection(value.text.toString().length)
                    value.addTextChangedListener(textWatcher)
                } else {
                    value.removeTextChangedListener(textWatcher)
                }
            }
        }

        private fun moveToTop() {
            if (layoutPosition > 0) {
                rates.removeAt(layoutPosition).also {
                    rates.add(0, it)
                    listener?.onChanged(it.symbol, it.value)
                }

                notifyItemMoved(layoutPosition, 0)
            }
        }
    }
}