package com.example.pexels.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pexels.data.VideosItem
import com.example.pexels.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val loading = MutableLiveData<Boolean>()
    private val status = MutableLiveData<Int>()
    private val message = MutableLiveData<String>()

    fun getVideo(page: Int): LiveData<ArrayList<VideosItem?>> {
        val video = MutableLiveData<ArrayList<VideosItem?>>()
        loading.value = true
        viewModelScope.launch {
            repository.getVideo(page).let {
                try {
                    val data = it.body()?.videos
                    video.value = data
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
        }
        return video
    }

    fun getSearchVideo(query: String, page: Int): LiveData<ArrayList<VideosItem?>> {
        val searchVideo = MutableLiveData<ArrayList<VideosItem?>>()
        loading.value = true
        viewModelScope.launch {
            repository.getSearchVideo(query, page).let {
                try {
                    val data = it.body()?.videos
                    searchVideo.value = data
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
        }
        return searchVideo
    }


    fun getLoading(): LiveData<Boolean> = loading
    fun getStatus(): LiveData<Int> = status
    fun getMessage(): LiveData<String> = message

}