package cz.utb.jdobes.cvebrowser.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VmaasFilter (
    @Json(name = "cve_list") val cveList: List<String> = listOf(".*"),
    @Json(name = "page_size") val pageSize: Int = 20
)