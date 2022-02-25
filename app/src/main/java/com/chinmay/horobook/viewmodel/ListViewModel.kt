package com.chinmay.horobook.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chinmay.horobook.model.SongData
import com.chinmay.horobook.model.SongsApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ListViewModel : ViewModel() {

    private val songsService = SongsApiService()
    private val disposable = CompositeDisposable()


    val songs = MutableLiveData<List<SongData>>()
    val songsLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        fetchFromRemote()
    }

    private fun fetchFromRemote() {
        loading.value = true

        disposable.add(
            songsService.getSongs()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<SongData>>() {
                    override fun onSuccess(t: List<SongData>) {
                        songs.value = t
                        songsLoadError.value = false
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        songsLoadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }

                })
        )

    }

    override fun onCleared() {
        super.onCleared()

        disposable.clear()
    }
}