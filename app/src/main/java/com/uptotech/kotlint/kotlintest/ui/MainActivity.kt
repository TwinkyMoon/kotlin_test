package com.uptotech.kotlint.kotlintest.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.uptotech.kotlint.kotlintest.App
import com.uptotech.kotlint.kotlintest.R
import com.uptotech.kotlint.kotlintest.api.ConfBookingApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var confBookingApi: ConfBookingApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as App).appComponent().inject(this)

        setContentView(R.layout.activity_main)

        confBookingApi.getConfBookingData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                // .doOnTerminate(() -> {})
                .subscribe(
                        { response ->
                            run {
                                Log.e("Diana", "response " + response.toString())
                            }
                        },
                        { error ->
                            run {
                                Log.e("Diana", "error " + error)
                            }
                        })
    }
}
