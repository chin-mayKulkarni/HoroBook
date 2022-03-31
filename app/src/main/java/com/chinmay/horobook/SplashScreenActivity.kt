package com.chinmay.horobook

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chinmay.horobook.model.Authentication
import com.chinmay.horobook.model.SongsApiService
import com.chinmay.horobook.util.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.util.*

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var prefHelper: SharedPreferencesHelper

    private val songsService = SongsApiService()
    private val disposable = CompositeDisposable()
    private var deviceID = ""

    lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        prefHelper = SharedPreferencesHelper(applicationContext)

        deviceID = prefHelper?.getAuthKey().toString()
        Log.d("DeviceID", "Device Id is: " + deviceID)
        if (deviceID.equals(" ")) {
            deviceID = generateDeviceId()
        }
        disposable.add(
            songsService.authenticate(deviceID)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Authentication>() {
                    override fun onSuccess(t: Authentication) {
                        //TODO: Store response in shared preferences

                        Log.d("response", "Auth Response is: " + t)
                        //Toast.makeText(getApplication(), "Retrieved from Remote", Toast.LENGTH_LONG).show()

                    }

                    override fun onError(e: Throwable) {
                        /*songsLoadError.value = true
                        loading.value = false*/
                        e.printStackTrace()
                    }

                })
        )
        handler = Handler()
        handler.postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)

    }

    private fun generateDeviceId(): String {
        val random = Random()
        val generatedInt = random.nextInt(99999999 - 11111111)
        var generatedString = generatedInt.toString()
        generatedString = generatedString + generatedString
        Log.d("Device ID", "This is device id:$generatedString")
        prefHelper.storeDataInSharedPref(generatedString)
        return generatedString
    }
}