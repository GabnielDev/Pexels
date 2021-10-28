package com.example.pexels.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pexels.data.VideosItem
import com.example.pexels.network.ApiClient
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class VideoViewModel: ViewModel() {

    private val loading = MutableLiveData<Boolean>()
    private val status = MutableLiveData<Int>()
    private val message = MutableLiveData<String>()

    fun getVideo(page: Int): LiveData<ArrayList<VideosItem?>> {
        val video = MutableLiveData<ArrayList<VideosItem?>>()
        loading.value = true
        viewModelScope.launch {
            try {
                val data = ApiClient.getClient().getVideo(page)
                if (data.isSuccessful) {
                    video.value = data.body()?.videos
                } else {
                    status.value = data.code()
                }
                loading.value = false
            } catch (t: Throwable) {
                when(t) {
                    is IOException -> message.value = t.message.toString()
                    is HttpException -> message.value = t.message().toString()
                    else -> message.value = "Unknow Error"
                }
                loading.value = false
            }
        }
        return video
    }

    fun getSearchVideo(query: String, page: Int): LiveData<ArrayList<VideosItem?>> {
        val searchvideo = MutableLiveData<ArrayList<VideosItem?>>()
        loading.value = true
        viewModelScope.launch {
            try {
                val data = ApiClient.getClient().getSearchVideo(query, page)
                if (data.isSuccessful) {
                    searchvideo.value = data.body()?.videos
                } else {
                    status.value = data.code()
                }
                loading.value = false
            } catch (t: Throwable) {
                when(t) {
                    is IOException -> message.value = t.message.toString()
                    is HttpException -> message.value = t.message().toString()
                    else -> message.value = "Unknow Error"
                }
                loading.value = false
            }
        }
        return searchvideo
    }

    fun getLoading(): LiveData<Boolean> = loading
    fun getStatus(): LiveData<Int> = status
    fun getMessage(): LiveData<String> = message

}