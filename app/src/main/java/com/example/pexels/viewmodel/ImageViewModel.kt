package com.example.pexels.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pexels.data.PhotosItem
import com.example.pexels.network.ApiClient
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class ImageViewModel : ViewModel() {

    private val loading = MutableLiveData<Boolean>()
    private val status = MutableLiveData<Int>()
    private val message = MutableLiveData<String>()

    fun getImage(page: Int): LiveData<ArrayList<PhotosItem?>> {
        val image = MutableLiveData<ArrayList<PhotosItem?>>()
        loading.value = true
        viewModelScope.launch {
            try {
                val data = ApiClient.getClient().getImage(page)
                if (data.isSuccessful) {
                    image.value = data.body()?.photos
                } else {
                    status.value = data.code()
                }
                loading.value = false
            } catch (t: Throwable) {
                when (t) {
                    is IOException -> message.value = t.message.toString()
                    is HttpException -> message.value = t.message().toString()
                    else -> message.value = "Unknow Error"
                }
                loading.value = false
            }
        }

        return image
    }

    fun getSearchImage(query: String, page: Int): LiveData<ArrayList<PhotosItem?>> {
        val searchimage = MutableLiveData<ArrayList<PhotosItem?>>()
        loading.value = true
        viewModelScope.launch {
            try {
                val data = ApiClient.getClient().getSearchImage(query, page)
                if (data.isSuccessful) {
                    searchimage.value = data.body()?.photos
                } else {
                    status.value = data.code()
                }
                loading.value = false
            } catch (t: Throwable) {
                when (t) {
                    is IOException -> message.value = t.message.toString()
                    is HttpException -> message.value = t.message().toString()
                    else -> message.value = "Unknow Error"
                }
                loading.value = false
            }
        }
        return searchimage
    }

    fun getLoading(): LiveData<Boolean> = loading
    fun getStatus(): LiveData<Int> = status
    fun getMessage(): LiveData<String> = message

}