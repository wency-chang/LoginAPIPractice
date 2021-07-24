package com.wency.login.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class LoginResponse(
    var error: String? = null,
    var objectId: String = "",
    var username: String = "",
    val code: String,
    var isVerifiedReportEmail: Boolean = false,
    var reportEmail: String = "",
    var createdAt: String = "",
    var updatedAt: String = "",
    var timezone: Int = 0x00,
    var parameter: Int = 0x00,
    var number: Int = 0x00,
    var phone: String = "",
    var sessionToken: String = "",
): Parcelable
