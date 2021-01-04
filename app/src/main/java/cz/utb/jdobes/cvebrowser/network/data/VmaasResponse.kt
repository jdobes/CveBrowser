package cz.utb.jdobes.cvebrowser.network.data

import com.squareup.moshi.Json
import cz.utb.jdobes.cvebrowser.database.CveDbItem

data class VmaasResponse(
    @Json(name = "data") val cveList: List<CveResponseItem>
)

// wrapped
data class CveResponseItem(
    val attributes: CveItem
)

data class CveItem(
    val synopsis: String,
    val description: String
)

fun VmaasResponse.asDatabaseModel(): List<CveDbItem> {
    return cveList.map {
        CveDbItem(
            synopsis = it.attributes.synopsis,
            description = it.attributes.description)
    }
}