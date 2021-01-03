package cz.utb.jdobes.cvebrowser.fragments.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cz.utb.jdobes.cvebrowser.network.VmaasApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        VmaasApi.retrofitService.getCves().enqueue(
            object: Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    _response.value = "Failure: " + t.message
                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    _response.value = response.body()
                }
            })
    }
}