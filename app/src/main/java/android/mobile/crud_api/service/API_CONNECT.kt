package android.mobile.crud_api.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object API_CONNECT {

    private val BASE_URL = "https://665c82113e4ac90a04d9c1eb.mockapi.io"

    val apiService: API_METHOD = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(API_METHOD::class.java)
}
