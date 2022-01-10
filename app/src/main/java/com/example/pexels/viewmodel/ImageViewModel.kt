package com.example.pexels.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pexels.data.PhotosItem
import com.example.pexels.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val loading = MutableLiveData<Boolean>()
    private val status = MutableLiveData<Int>()
    private val message = MutableLiveData<String>()

    fun getImage(page: Int): LiveData<ArrayList<PhotosItem?>?> {
        val image = MutableLiveData<ArrayList<PhotosItem?>?>()
        loading.value = true
        viewModelScope.launch {
            repository.getImage(page).let {
                try {
                    val data = it.body()?.photos
                    image.value = data
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
        return image
    }

    fun getSearchImage(query: String, page: Int): LiveData<ArrayList<PhotosItem?>?> {
        val searchImage = MutableLiveData<ArrayList<PhotosItem?>?>()
        loading.value = true
        viewModelScope.launch {
            repository.getSearchImage(query, page).let {
                try {
                    val data = it.body()?.photos
                    searchImage.value = data
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
        return searchImage
    }


    fun getLoading(): LiveData<Boolean> = loading
    fun getStatus(): LiveData<Int> = status
    fun getMessage(): LiveData<String> = message

}