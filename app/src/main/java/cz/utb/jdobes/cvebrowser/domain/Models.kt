package cz.utb.jdobes.cvebrowser.domain

data class Cve(val synopsis: String,
               val description: String,
               val impact: String,
               val publicDate: String,
               val cvss3Score: String?,
               val cvss2Score: String?)