package cz.utb.jdobes.cvebrowser.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://webapp-vmaas-prod.apps.crcp01ue1.o9m8.p1.openshiftapps.com/api/v1/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface VmaasApiService {
    @GET("cves/CVE-1999-0002")
    fun getCves():
            Call<String>
}

object VmaasApi {
    val retrofitService : VmaasApiService by lazy {
        retrofit.create(VmaasApiService::class.java) }
}