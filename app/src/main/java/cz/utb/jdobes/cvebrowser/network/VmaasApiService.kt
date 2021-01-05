package cz.utb.jdobes.cvebrowser.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import cz.utb.jdobes.cvebrowser.network.data.VmaasResponse
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

private const val BASE_URL = "https://cloud.redhat.com/api/vulnerability/v1/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface VmaasApiService {
    @Headers("Authorization: Basic dXRiLWFibXRlLWRlbW86a2Y0X0dRMXo=") // utb-abmte-demo account
    @GET("vulnerabilities/cves")
    suspend fun getCveList(@Query("affecting") affecting: String = "true,true",
                           @Query("sort") sort: String = "-public_date",
                           @Query("page") page: Int = 1,
                           @Query("page_size") pageSize: Int = 50): VmaasResponse
}

object VmaasApi {
    val retrofitService : VmaasApiService by lazy {
        retrofit.create(VmaasApiService::class.java) }
}