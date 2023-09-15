package uk.co.sullenart.sample.service

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import uk.co.sullenart.sample.countries.Country

class Countries {
    @Serializable
    private data class CountryResponse(
        val name: NameResponse? = null,
        val flags: FlagsResponse? = null,
    )

    @Serializable
    private data class NameResponse(
        val common: String? = null,
    )

    @Serializable
    private data class FlagsResponse(
        val png: String? = null,
    )

    private interface Service {
        @GET("/v3.1/all?fields=name,flags")
        suspend fun countries(): List<CountryResponse>
    }

    private val service: Service by lazy {
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
            .build()
        val contentType = "application/json".toMediaType()
        val json = Json { ignoreUnknownKeys = true }
        Retrofit.Builder()
            .baseUrl("https://restcountries.com/")
            .client(client)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(Service::class.java)
    }

    suspend fun getCountries(): List<Country> =
        service.countries().map {
            Country(
                name = it.name?.common.orEmpty(),
                flagUrl = it.flags?.png.orEmpty(),
            )
        }
}