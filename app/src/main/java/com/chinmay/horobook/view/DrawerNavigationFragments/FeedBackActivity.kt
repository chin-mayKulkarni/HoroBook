package com.chinmay.horobook.view.DrawerNavigationFragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.chinmay.horobook.R
import com.chinmay.horobook.model.Feedback
import com.chinmay.horobook.model.SongData
import com.chinmay.horobook.model.SongsApiService
import com.chinmay.horobook.util.SharedPreferencesHelper
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_feed_back.*
import kotlinx.android.synthetic.main.activity_web.*
import org.json.JSONObject


class FeedBackActivity : AppCompatActivity() {

    private val songsService = SongsApiService()
    private val disposable = CompositeDisposable()

    private var deviceID = ""
    private lateinit var prefHelper: SharedPreferencesHelper
    lateinit var nameTxt: String
    lateinit var phNo: String
    lateinit var emailTxt: String
    lateinit var feedTxt: String


    private val mTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {}
        override fun afterTextChanged(editable: Editable) {
            // check Fields For Empty Values
            checkFieldsForEmptyValues()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed_back)

        txt_name.addTextChangedListener(mTextWatcher)
        txt_email.addTextChangedListener(mTextWatcher)
        txt_phone.addTextChangedListener(mTextWatcher)
        txt_feedback.addTextChangedListener(mTextWatcher)

        //Loading Ad
        MobileAds.initialize(applicationContext){}
        val adRequest = AdRequest.Builder().build()
        feedbackBanner.loadAd(adRequest)

        checkFieldsForEmptyValues()

        toolbarFeed.title = "Feedback"

        toolbarFeed.setNavigationOnClickListener(View.OnClickListener {
            // back button pressed
            this.onBackPressed()
        })

        btn_submit.setOnClickListener {
            validateInput()
            if (errorText.visibility != View.VISIBLE) {

                prefHelper = SharedPreferencesHelper(applicationContext)

                deviceID = prefHelper?.getAuthKey().toString()
                Log.d("DeviceID", "Device Id is: " + deviceID)


                    var jsonObj = JSONObject()
                    jsonObj.put("device", deviceID)
                    jsonObj.put("email", emailTxt)
                    jsonObj.put("feedback_message", feedTxt)
                    jsonObj.put("mobile", phNo)



                disposable.add(
                    songsService.submitFeedback(jsonObj)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object : DisposableSingleObserver<Feedback>() {

                            override fun onSuccess(t: Feedback) {
                                Log.d("response", "Auth Response is: " + t)
                                showDialog("Thank You!!", "You have submitted the feedback")
                            }

                            override fun onError(e: Throwable) {
                                /*songsLoadError.value = true
                                loading.value = false*/
                                Log.d("Error", "POST Response is: " + e)
                                e.printStackTrace()
                            }
                        })
                )




                clearAllFields()
            }
        }


    }


    private fun showDialog(title: String, msg: String) {
        val builder = AlertDialog.Builder(this)
        //set title for alert dialog
        builder.setTitle(title)
        //set message for alert dialog
        builder.setMessage(msg)
        //builder.setIcon(android.R.drawable.ic_input_add)

        //performing positive action
        builder.setPositiveButton("OK") { dialogInterface, which ->
            this.onBackPressed()
        }
        /*//performing cancel action
        builder.setNeutralButton("Cancel") { dialogInterface, which ->
            Toast.makeText(
                applicationContext,
                "clicked cancel\n operation cancel",
                Toast.LENGTH_LONG
            ).show()
        }
        //performing negative action
        builder.setNegativeButton("No") { dialogInterface, which ->
            Toast.makeText(applicationContext, "clicked No", Toast.LENGTH_LONG).show()
        }*/
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun clearAllFields() {
        txt_name.text?.clear()
        txt_email.text?.clear()
        txt_phone.text?.clear()
        txt_feedback!!.text?.clear()
        btn_submit.isEnabled = false
    }


    private fun validateInput() {
        /*if (!txt_email.getText().toString().contains("@") || !txt_email.getText().toString()
                .endsWith(".com")
        ) {
            errorText.setVisibility(View.VISIBLE)
            errorText.setText("Please enter valid emailID")
        }*/

        if (txt_phone.getText()!!.length !== 10) {
            errorText.setVisibility(View.VISIBLE)
            errorText.setText("Please enter valid Phone number")
        } else errorText.setVisibility(View.GONE)
    }


    fun checkFieldsForEmptyValues() {
        nameTxt = txt_name.text.toString()
        feedTxt = txt_feedback.text.toString()
        phNo = txt_phone.text.toString()
        emailTxt = txt_email.text.toString()
        if (nameTxt == "" || feedTxt == "" /*|| (phoneNumber.getText().toString().length() != 10)*/) {
            btn_submit.setEnabled(false)
        } else {
            btn_submit.setEnabled(true)
            // btn_submit.setBackgroundColor(resources.getColor(R.color.colorPrimaryDark))
        }
    }


}
