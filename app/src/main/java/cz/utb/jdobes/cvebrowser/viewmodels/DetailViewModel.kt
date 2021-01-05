package cz.utb.jdobes.cvebrowser.viewmodels

import android.app.Application
import androidx.lifecycle.*
import cz.utb.jdobes.cvebrowser.database.getDatabase
import cz.utb.jdobes.cvebrowser.domain.Cve
import cz.utb.jdobes.cvebrowser.repository.CveRepository

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val cveRepository = CveRepository(getDatabase(application))
    var _cve = MutableLiveData<Cve>(Cve("s", "d", "i", "pd", "c", "c"))
    val cve: LiveData<Cve>
        get() = _cve

    fun loadCve(name: String) {
        _cve = (cveRepository.getCve(name) as MutableLiveData)
    }

}