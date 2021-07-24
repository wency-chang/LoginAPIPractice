package com.wency.login.timezone

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wency.login.data.LoginResponse
import com.wency.login.repository.Repository

class TimezoneViewModelFactory(
    private val repository: Repository,
    private val userInfo: LoginResponse
    ): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TimezoneViewModel::class.java)){
            return TimezoneViewModel(repository, userInfo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}