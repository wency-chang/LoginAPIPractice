package com.wency.login.network

import android.util.Log
import com.wency.login.MyApplication
import com.wency.login.R
import com.wency.login.data.LoginResponse
import com.wency.login.data.TimezoneResponse
import com.wency.login.data.TimezoneUpdate
import com.wency.login.data.UserInfo
import java.util.*

object DataSource: DefaultDataSource {
    override suspend fun userLogin(userInfo: UserInfo): LoginResponse{
        return try {
            UserApi.retrofitService.userLogin(
                MyApplication.instance.getString(R.string.parse_id),
                userInfo.userName,
                userInfo.userPassword
            )
        } catch (e: Exception) {
            Log.w("DataSource","[${this::class.simpleName}] exception=${e.localizedMessage}")
            LoginResponse(error = "Invalid username/password.", code = "101")
        }
    }

    override suspend fun updateUser(
        loginResponse: LoginResponse, timezone: TimezoneUpdate
    ): TimezoneResponse{
        return try {
            UserApi.retrofitService.updateUser(
                MyApplication.instance.getString(R.string.parse_id),
                loginResponse.sessionToken,
                "application/json",
                timezone,
                loginResponse.objectId
            )
        } catch (e: Exception) {
            Log.w("DataSource","[${this::class.simpleName}] exception=${e.message}")
            TimezoneResponse(error = e.toString())
        }
    }
}