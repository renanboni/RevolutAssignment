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

class CurrencyFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

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
        viewModel.ratesViewState.observe(viewLifecycleOwner, Observer { renderRates(it) })
        viewModel.loadingViewState.observe(viewLifecycleOwner, Observer { renderLoading(it) })
        viewModel.errorViewState.observe(viewLifecycleOwner, Observer { renderError(it) })
    }

    private fun renderRates(rates: List<Rate>) {
        currencies.adapter = CurrencyAdapter(rates) {

        }
    }

    private fun renderLoading(isLoading: Boolean) {

    }

    private fun renderError(msg: String) {

    }
}













