package cz.utb.jdobes.cvebrowser.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import cz.utb.jdobes.cvebrowser.domain.Cve

@Entity
data class CveDbItem constructor(
    @PrimaryKey
    val synopsis: String,
    val description: String)

fun List<CveDbItem>.asDomainModel(): List<Cve> {
    return map {
        Cve(
            synopsis = it.synopsis,
            description = it.description)
    }
}