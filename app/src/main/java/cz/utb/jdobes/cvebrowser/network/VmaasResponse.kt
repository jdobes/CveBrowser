package cz.utb.jdobes.cvebrowser.network

import com.squareup.moshi.Json

data class VmaasResponse(
    @Json(name = "cve_list") val cveList: Map<String, Cve>
)

data class Cve(
    val synopsis: String,
    val description: String
)