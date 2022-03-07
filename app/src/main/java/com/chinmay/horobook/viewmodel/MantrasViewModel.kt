package com.chinmay.horobook.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.chinmay.horobook.model.SongData
import com.chinmay.horobook.model.SongsApiService
import com.chinmay.horobook.model.SongsListData
import com.chinmay.horobook.util.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MantrasViewModel(application: Application) : BaseViewModel(application) {

    private var prefHelper = SharedPreferencesHelper(getApplication())
    private val songsService = SongsApiService()
    private val disposable = CompositeDisposable()


    val mantras = MutableLiveData<List<SongData>>()
    val mantrasLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    val mantrasList = MutableLiveData<List<SongsListData>>()
    val mantrasListLoadError = MutableLiveData<Boolean>()
    val loadingMantras = MutableLiveData<Boolean>()



    fun refreshMantrasList(albumId: String) {

        loadingMantras.value = true
        val auth_key = prefHelper.getAuthKey().toString()

        disposable.add(
            songsService.getSongsList(auth_key, albumId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<SongsListData>>(){
                    override fun onSuccess(t: List<SongsListData>) {
                        songsListRetrieved(t)
                        Log.d("SongsList", "SongsList response :" + t)
                    }

                    override fun onError(e: Throwable) {
                        mantrasListLoadError.value =true
                        loadingMantras.value = false
                        e.printStackTrace()
                    }

                })

        )

    }



    fun refreshMantras() {
        loading.value = true

        disposable.add(
            songsService.getSongsAlbumList(prefHelper.getAuthKey().toString())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<SongData>>() {
                    override fun onSuccess(t: List<SongData>) {
                        //storeSongsLocally(t)
                        songsRetrieved(t)
                        Toast.makeText(getApplication(), "Retrieved from Remote", Toast.LENGTH_LONG).show()

                    }

                    override fun onError(e: Throwable) {
                        mantrasLoadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }

                })
        )

    }

    private fun songsRetrieved(songList: List<SongData>){
        mantras.value = songList
        mantrasLoadError.value = false
        loading.value = false
    }

    private fun songsListRetrieved(songList: List<SongsListData>){
        mantrasList.value = songList
        mantrasListLoadError.value = false
        loadingMantras.value = false
    }


    override fun onCleared() {
        super.onCleared()

        disposable.clear()
    }
}