package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ActivityCountryDetailBinding
import com.example.myapplication.data.model.Country
import com.example.myapplication.ui.detail.CountryDetailViewModel
import com.example.myapplication.ui.detail.CountryDetailUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CountryDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCountryDetailBinding
    private val viewModel: CountryDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        observeUiState()
    }

    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun observeUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is CountryDetailUiState.Loading -> showLoading()
                        is CountryDetailUiState.Success -> showCountry(state.country)
                        is CountryDetailUiState.Error -> showError(state.message)
                    }
                }
            }
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.content.visibility = View.GONE
        binding.errorText.visibility = View.GONE
    }

    private fun showCountry(country: Country) {
        binding.progressBar.visibility = View.GONE
        binding.content.visibility = View.VISIBLE
        binding.errorText.visibility = View.GONE

        binding.apply {
            toolbar.title = country.name
            tvCapital.text = country.capital
            tvRegion.text = country.region
            tvPopulation.text = country.population.toString()
            tvLanguages.text = country.languages.joinToString(", ")
            tvCurrencies.text = country.currencies.joinToString(", ")

            Glide.with(ivFlag)
                .load(country.flagUrl)
                .into(ivFlag)
        }
    }

    private fun showError(message: String) {
        binding.progressBar.visibility = View.GONE
        binding.content.visibility = View.GONE
        binding.errorText.visibility = View.VISIBLE
        binding.errorText.text = message
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    companion object {
        private const val EXTRA_COUNTRY_NAME = "extra_country_name"

        fun newIntent(context: Context, countryName: String): Intent {
            return Intent(context, CountryDetailActivity::class.java).apply {
                putExtra(EXTRA_COUNTRY_NAME, countryName)
            }
        }
    }
} 