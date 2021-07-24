package com.wency.login.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TimezoneResponse(
        @Json(name = "updatedAt")var updatedAt: String = "",
        var error : String? = null
): Parcelable
