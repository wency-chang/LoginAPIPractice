package com.wency.login.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import com.wency.login.MyApplication
import com.wency.login.data.LoginResponse
import com.wency.login.data.TimezoneResponse
import com.wency.login.data.TimezoneUpdate
import com.wency.login.data.UserInfo
import com.wency.login.network.DataSource
import com.wency.login.network.DefaultDataSource
import java.util.*

class Repository(private val remoteDataSource: DefaultDataSource) {

    private fun isInternetConnected(): Boolean {
        val cm = MyApplication.instance
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    suspend fun userLogin(userInfo: UserInfo): LoginResponse {
        return if (isInternetConnected()){
            remoteDataSource.userLogin(userInfo)
        } else {
            LoginResponse(code = "45", error = "No internet")
        }

    }

    suspend fun updateUser(userToken: LoginResponse, timezone: TimezoneUpdate): TimezoneResponse{
        return if (isInternetConnected()){
            remoteDataSource.updateUser(userToken, timezone)
        }else {
            TimezoneResponse(error = "No internet")
        }
    }
}