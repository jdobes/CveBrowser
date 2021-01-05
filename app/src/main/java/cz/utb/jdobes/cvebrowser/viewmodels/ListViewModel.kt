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

    private val pageSize = 50
    var page = 1
    private var _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    var filter = ""

    init {
        fetchDataFromRepository(init = true)
    }

    fun fetchDataFromRepository(init: Boolean = false) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                cveRepository.refreshCves(page = page, pageSize = pageSize, init = init, filter = filter)
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false
                page++;

            } catch (networkError: IOException) {
                // Show a Toast error message and hide the progress bar.
                if(cves.value.isNullOrEmpty())
                    _eventNetworkError.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }
}