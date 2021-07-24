package com.wency.login.network

import com.wency.login.data.LoginResponse
import com.wency.login.data.TimezoneResponse
import com.wency.login.data.TimezoneUpdate
import com.wency.login.data.UserInfo
import java.util.*

interface DefaultDataSource {
    suspend fun userLogin(userInfo: UserInfo): LoginResponse

    suspend fun updateUser(loginResponse: LoginResponse, timezone: TimezoneUpdate): TimezoneResponse
}