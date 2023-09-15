package uk.co.sullenart.sample.countries

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uk.co.sullenart.sample.service.Countries

class CountriesViewModel : ViewModel() {
    val countries = mutableStateListOf<Country>()

    init {
        viewModelScope.launch {
            countries.addAll(Countries().getCountries())
        }
    }
}