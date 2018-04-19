package com.uptotech.kotlint.kotlintest.api

import com.uptotech.kotlint.kotlintest.model.Location
import io.reactivex.Observable
import retrofit2.http.GET

interface ConfBookingApi {
    @GET("getfake")
    fun getConfBookingData(): Observable<Location>
}