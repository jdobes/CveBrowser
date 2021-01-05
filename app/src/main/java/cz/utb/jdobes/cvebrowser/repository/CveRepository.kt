package cz.utb.jdobes.cvebrowser.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import cz.utb.jdobes.cvebrowser.database.CveDatabase
import cz.utb.jdobes.cvebrowser.database.asDomainModel
import cz.utb.jdobes.cvebrowser.domain.Cve
import cz.utb.jdobes.cvebrowser.network.VmaasApi
import cz.utb.jdobes.cvebrowser.network.data.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CveRepository(private val database: CveDatabase) {

    val cves: LiveData<List<Cve>> = Transformations.map(database.cveDao.getCves()) {
        it.asDomainModel()
    }

    fun getCve(name: String): LiveData<Cve> {
        val cve = Transformations.map(database.cveDao.getCve(name)) {
            it.asDomainModel()
        }
        return cve
    }

    suspend fun refreshCves(page: Int = 1, pageSize: Int = 50, init: Boolean = false) {
        withContext(Dispatchers.IO) {
            val cves = VmaasApi.retrofitService.getCveList(page = page, pageSize = pageSize)
            if (init) {
                database.cveDao.clearCves()
            }
            database.cveDao.insertAll(cves.asDatabaseModel())
        }
    }
}