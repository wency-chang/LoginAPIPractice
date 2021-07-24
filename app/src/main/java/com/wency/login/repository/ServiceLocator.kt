package com.wency.login.repository

import android.content.Context
import com.wency.login.network.DataSource

object ServiceLocator {

    @Volatile
    var repository: Repository? = null

    fun provideTasksRepository(): Repository {
        synchronized(this) {
            return repository
                ?: repository
                ?: createRepository()
        }
    }

    private fun createRepository(): Repository {
        return Repository(DataSource)
    }
}