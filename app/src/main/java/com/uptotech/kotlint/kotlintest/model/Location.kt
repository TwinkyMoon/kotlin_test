package com.uptotech.kotlint.kotlintest.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Location {
    @Expose
    var id: Int? = null
    @SerializedName("userName")
    @Expose
    var userName: String? = null
    @SerializedName("reservationNote")
    @Expose
    var reservationNote: String? = null
    @SerializedName("userCompany")
    @Expose
    var userCompany: String? = null
    @SerializedName("userId")
    @Expose
    var userId: String? = null
    @SerializedName("accessCode")
    @Expose
    var accessCode: String? = null

    override fun toString(): String {
        return "Location(id=$id, userName=$userName, reservationNote=$reservationNote, userCompany=$userCompany, userId=$userId, accessCode=$accessCode)"
    }


}