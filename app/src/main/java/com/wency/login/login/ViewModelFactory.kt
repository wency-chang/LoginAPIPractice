package com.wency.login.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wency.login.repository.Repository
import com.wency.login.timezone.TimezoneViewModel

class ViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return LoginViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}