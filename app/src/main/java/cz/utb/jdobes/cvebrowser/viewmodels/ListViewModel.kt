package cz.utb.jdobes.cvebrowser.viewmodels

import android.app.Application
import androidx.lifecycle.*
import cz.utb.jdobes.cvebrowser.database.getDatabase
import cz.utb.jdobes.cvebrowser.repository.CveRepository
import kotlinx.coroutines.launch
import java.io.IOException

class ListViewModel(application: Application) : AndroidViewModel(application) {

    private val cveRepository = CveRepository(getDatabase(application))
    val cves = cveRepository.cves

    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError
    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    /**
     * Call refreshDataFromRepository() on init so we can display status immediately.
     */
    init {
        refreshDataFromRepository()
    }

    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                cveRepository.refreshCves()
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false

            } catch (networkError: IOException) {
                // Show a Toast error message and hide the progress bar.
                if(cves.value.isNullOrEmpty())
                    _eventNetworkError.value = true
            }
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }
}