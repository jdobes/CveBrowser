package cz.utb.jdobes.cvebrowser.fragments.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.utb.jdobes.cvebrowser.network.VmaasApi
import cz.utb.jdobes.cvebrowser.network.VmaasFilter
import kotlinx.coroutines.launch

class ListViewModel : ViewModel() {

    // The internal MutableLiveData String that stores the most recent response
    private val _response = MutableLiveData<String>()

    // The external immutable LiveData for the response String
    val response: LiveData<String>
        get() = _response

    /**
     * Call getCveList() on init so we can display status immediately.
     */
    init {
        getCveList()
    }

    /**
     * Sets the value of the status LiveData to the VMaas API status.
     */
    private fun getCveList() {
        viewModelScope.launch {
            try {
                val result = VmaasApi.retrofitService.getCveList(VmaasFilter())
                _response.value = "Success: ${result.cveList.size} CVEs retrieved"
            } catch (e: Exception) {
                _response.value = "Failure: ${e.message}"
            }
        }
    }
}