package com.example.revolutassingment.features.currencies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.revolutassingment.R
import com.example.revolutassingment.domain.entities.Rate
import com.example.revolutassingment.util.CurrencyUtils
import com.example.revolutassingment.util.ext.getDrawableFromName

class CurrencyAdapter(
    private val rates: List<Rate>,
    private val listener: (Rate) -> Unit
) : RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {

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

    inner class CurrencyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val flag = view.findViewById<ImageView>(R.id.flag)
        private val title = view.findViewById<TextView>(R.id.title)
        private val subtitle = view.findViewById<TextView>(R.id.subtitle)
        private val value = view.findViewById<EditText>(R.id.value)

        fun bind(rate: Rate) {
            title.text = rate.symbol
            value.setText(rate.value.toString())
            subtitle.text = CurrencyUtils.getCurrencySymbol(rate.symbol)
            flag.setImageResource(view.getDrawableFromName(CurrencyUtils.normalizeCode(rate.symbol)))

            value.setOnClickListener {
                listener(rate)
            }
        }
    }
}