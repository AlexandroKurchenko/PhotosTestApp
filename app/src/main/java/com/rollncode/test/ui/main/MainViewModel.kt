package com.rollncode.test.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rollncode.test.repoository.PhotosRepository
import com.rollncode.test.repoository.db.model.PhotoItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val repo: PhotosRepository) : ViewModel() {

    private val compositeDisposable by lazy { CompositeDisposable() }
    private val _data by lazy { MutableLiveData<List<PhotoItem>>() }
    val photosLiveData: LiveData<List<PhotoItem>>
        get() = _data

    private val _isInProgress by lazy { MutableLiveData<Boolean>() }
    val isInProgress: LiveData<Boolean>
        get() = _isInProgress

    fun getAllPhotos() {
        _isInProgress.postValue(true)
        compositeDisposable.add(
            repo.getAllPhotos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ photoItems -> processItems(photoItems) }, { error -> processError(error) })
        )
    }

    fun fetchAllPhotos() {
        _isInProgress.postValue(true)
        compositeDisposable.add(
            repo.fetchAllPhotos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ photoItems -> processItems(photoItems) }, { error -> processError(error) })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    private fun processItems(photoItems: List<PhotoItem>) {
        _data.postValue(photoItems)
        _isInProgress.postValue(false)
    }

    private fun processError(error: Throwable) {
       showError(error)
        _isInProgress.postValue(false)
    }

    private fun showError(error: Throwable) {
        val errorMessage = error.message ?: "Unexpected case while get error message "
        Log.e(MainViewModel::class.java.canonicalName, errorMessage)
    }
}