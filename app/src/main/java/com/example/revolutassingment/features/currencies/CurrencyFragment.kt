package com.example.revolutassingment.features.currencies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.revolutassingment.R
import com.example.revolutassingment.domain.entities.Rate
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_currency.currencies
import kotlinx.android.synthetic.main.fragment_currency.loading
import javax.inject.Inject

class CurrencyFragment : DaggerFragment(), OnRateValueChanged {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var adapter: CurrencyAdapter

    private val viewModel by viewModels<CurrencyViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_currency,
            container,
            false
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getRates()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()

        viewModel.ratesViewState.observe(viewLifecycleOwner, Observer { renderRates(it) })
        viewModel.errorViewState.observe(viewLifecycleOwner, Observer { renderError() })
    }

    private fun setupAdapter() {
        adapter.setListener(this)
        currencies.adapter = adapter
    }

    private fun renderRates(rates: MutableList<Rate>) {
        loading.visibility = View.GONE
        adapter.submitList(rates)
    }

    private fun renderError() {
        Toast.makeText(requireContext(), getString(R.string.error), Toast.LENGTH_LONG).show()
    }

    override fun onRateChanged(currency: String, value: Double) {
        viewModel.updateRates(currency, value)
    }

    override fun onBaseCurrencyChanged(currency: String, value: Double, currentList: MutableList<Rate>) {
        viewModel.updateBaseCurrency(currency, value, currentList)
    }
}