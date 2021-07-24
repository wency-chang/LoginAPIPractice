package com.wency.login

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.parse.Parse
import com.wency.login.repository.Repository
import com.wency.login.repository.ServiceLocator
import kotlin.properties.Delegates

class MyApplication: Application() {

    val repository: Repository
        get() = ServiceLocator.provideTasksRepository()

    companion object{
        var instance: MyApplication by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}