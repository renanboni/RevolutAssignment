package com.example.revolutassingment.features.currencies

import androidx.recyclerview.widget.DiffUtil
import com.example.revolutassingment.domain.entities.Rate
import javax.inject.Inject

class CurrencyDiffCallback @Inject constructor() : DiffUtil.ItemCallback<Rate>() {
    override fun areItemsTheSame(oldItem: Rate, newItem: Rate) = oldItem.symbol == newItem.symbol
    override fun areContentsTheSame(oldItem: Rate, newItem: Rate) = oldItem.value == newItem.value
    override fun getChangePayload(oldItem: Rate, newItem: Rate) =
        if (oldItem.value != newItem.value) {
            newItem.value
        } else {
            null
        }
}