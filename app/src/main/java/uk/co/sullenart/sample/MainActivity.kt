package uk.co.sullenart.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import uk.co.sullenart.sample.countries.CountriesScreen
import uk.co.sullenart.sample.countries.CountriesViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewmodel = CountriesViewModel()
        setContent {
            CountriesScreen(viewmodel.countries)
        }
    }
}