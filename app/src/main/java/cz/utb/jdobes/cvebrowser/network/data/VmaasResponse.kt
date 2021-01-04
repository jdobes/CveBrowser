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
    val description: String,
    val impact: String,
    @Json(name = "public_date") val publicDate: String,
    @Json(name = "cvss3_score") val cvss3Score: String?,
    @Json(name = "cvss2_score") val cvss2Score: String?
)

fun VmaasResponse.asDatabaseModel(): List<CveDbItem> {
    return cveList.map {
        CveDbItem(
            synopsis = it.attributes.synopsis,
            description = it.attributes.description,
            impact = it.attributes.impact,
            publicDate = it.attributes.publicDate,
            cvss3Score = it.attributes.cvss3Score,
            cvss2Score = it.attributes.cvss2Score)
    }
}