package com.example.revolutassingment.features.currencies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.setListener(this)
        currencies.adapter = adapter

        viewModel.ratesViewState.observe(viewLifecycleOwner, Observer { renderRates(it) })
        viewModel.loadingViewState.observe(viewLifecycleOwner, Observer { renderLoading(it) })
        viewModel.errorViewState.observe(viewLifecycleOwner, Observer { renderError() })
    }

    private fun renderRates(rates: MutableList<Rate>) {
        renderLoading(false)
        adapter.setRates(rates)
    }

    private fun renderLoading(isLoading: Boolean) {
        if (isLoading) {
            loading.visibility = View.VISIBLE
        } else {
            loading.visibility = View.GONE
        }
    }

    private fun renderError() {
        AlertDialog.Builder(ContextThemeWrapper(context, R.style.AlertDialogTheme))
            .setCancelable(false)
            .setTitle(getString(R.string.error_title))
            .setMessage(getString(R.string.error_message))
            .setPositiveButton(getString(R.string.try_again)) { _, _ -> viewModel.getRates() }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ -> dialog.dismiss() }
            .show()
    }

    override fun onChanged(currency: String, value: Double) {
        viewModel.getRates(currency, value)
    }
}













