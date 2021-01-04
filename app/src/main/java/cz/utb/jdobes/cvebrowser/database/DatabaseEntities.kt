package cz.utb.jdobes.cvebrowser.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import cz.utb.jdobes.cvebrowser.domain.Cve

@Entity
data class CveDbItem constructor(
    @PrimaryKey
    val synopsis: String,
    val description: String,
    val impact: String,
    val publicDate: String,
    val cvss3Score: String?,
    val cvss2Score: String?)

fun List<CveDbItem>.asDomainModel(): List<Cve> {
    return map {
        Cve(
            synopsis = it.synopsis,
            description = it.description,
            impact = it.impact,
            publicDate = it.publicDate,
            cvss3Score = it.cvss3Score,
            cvss2Score = it.cvss2Score
        )
    }
}