package cz.utb.jdobes.cvebrowser.network.data

import com.squareup.moshi.Json
import cz.utb.jdobes.cvebrowser.database.CveDbItem

data class VmaasResponse(
    @Json(name = "cve_list") val cveList: Map<String, CveResponseItem>
)

data class CveResponseItem(
    val synopsis: String,
    val description: String
)

fun VmaasResponse.asDatabaseModel(): List<CveDbItem> {
    return cveList.values.map {
        CveDbItem(
            synopsis = it.synopsis,
            description = it.description)
    }
}