package com.wency.login.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TimezoneUpdate(
    @Json(name = "timezone") val timezone: Int
): Parcelable
