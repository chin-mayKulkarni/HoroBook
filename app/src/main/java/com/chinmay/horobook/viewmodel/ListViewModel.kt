package com.chinmay.horobook.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.chinmay.horobook.model.SongData
import com.chinmay.horobook.model.SongDataBase
import com.chinmay.horobook.model.SongsApiService
import com.chinmay.horobook.util.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class ListViewModel(application: Application) : BaseViewModel(application) {

    private val refreshTime = 0.5 * 60 * 1000 * 1000 * 1000L

    private var prefHelper = SharedPreferencesHelper(getApplication())
    private val songsService = SongsApiService()
    private val disposable = CompositeDisposable()


    val songs = MutableLiveData<List<SongData>>()
    val songsLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        val updatedTime = prefHelper.getUpdatedTime()
        if (updatedTime != null && updatedTime != 0L && System.nanoTime() - updatedTime < refreshTime ){
            fetchFromDatabase()
        } else
        fetchFromRemote()
    }

    private fun fetchFromDatabase() {
        loading.value = true
        launch {
            val songs = SongDataBase(getApplication()).songDao().getAllSongs()
            songsRetrieved(songs)
            Toast.makeText(getApplication(), "Retrieved from DB", Toast.LENGTH_LONG).show()
        }
    }

    private fun fetchFromRemote() {
        loading.value = true

        disposable.add(
            songsService.getSongs(prefHelper.getAuthKey().toString())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<SongData>>() {
                    override fun onSuccess(t: List<SongData>) {
                        storeSongsLocally(t)
                        Toast.makeText(getApplication(), "Retrieved from Remote", Toast.LENGTH_LONG).show()

                    }

                    override fun onError(e: Throwable) {
                        songsLoadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }

                })
        )

    }

    private fun storeSongsLocally(list: List<SongData>) {
        launch {
            SongDataBase(getApplication()).songDao().deleteAllSongs()
            val result = SongDataBase(getApplication()).songDao().insertAll(*list.toTypedArray())
            var i = 0
            while(i< list.size){
                list[i].uuid = result[i].toInt()
                ++i
            }
            songsRetrieved(list)
        }
        prefHelper.saveUpdatedTime(System.nanoTime())

    }

    private fun songsRetrieved(songList: List<SongData>){
        songs.value = songList
        songsLoadError.value = false
        loading.value = false
    }


    override fun onCleared() {
        super.onCleared()

        disposable.clear()
    }
}