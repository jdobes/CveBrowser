package cz.utb.jdobes.cvebrowser.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import cz.utb.jdobes.cvebrowser.network.data.VmaasFilter
import cz.utb.jdobes.cvebrowser.network.data.VmaasResponse
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

private const val BASE_URL = "https://webapp-vmaas-prod.apps.crcp01ue1.o9m8.p1.openshiftapps.com/api/v1/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface VmaasApiService {
    @Headers("Content-Type: application/json")
    @POST("cves")
    suspend fun getCveList(@Body filter: VmaasFilter): VmaasResponse
}

object VmaasApi {
    val retrofitService : VmaasApiService by lazy {
        retrofit.create(VmaasApiService::class.java) }
}