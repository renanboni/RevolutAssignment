package com.example.revolutassingment.features.currencies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.revolutassingment.R
import com.example.revolutassingment.domain.entities.Rate
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_currency.*
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

        adapter.setListener(this)
        currencies.adapter = adapter

        viewModel.ratesViewState.observe(viewLifecycleOwner, Observer { renderRates(it) })
        viewModel.loadingViewState.observe(viewLifecycleOwner, Observer { renderLoading(it) })
        viewModel.errorViewState.observe(viewLifecycleOwner, Observer { renderError(it) })
    }

    private fun renderRates(rates: MutableList<Rate>) {
        adapter.setRates(rates)
    }

    private fun renderLoading(isLoading: Boolean) {

    }

    private fun renderError(msg: String) {

    }

    override fun onChanged(currency: String, value: Double) {
        viewModel.getRates(currency, value)
    }
}













