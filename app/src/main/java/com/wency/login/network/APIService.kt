package com.wency.login.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.wency.login.data.LoginResponse
import com.wency.login.data.TimezoneResponse
import com.wency.login.data.TimezoneUpdate
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import java.util.*

private const val BASE_URL = "https://watch-master-staging.herokuapp.com/api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val client = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    })
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(client)
    .build()

interface ApiService {

    @GET("login")
    suspend fun userLogin(@Header("X-Parse-Application-Id")id: String,
                          @Query("username") userName: String,
                          @Query("password") password: String): LoginResponse

    @PUT("users/{userId}")
    suspend fun updateUser(@Header("X-Parse-Application-Id")id: String,
                           @Header("X-Parse-Session-Token") token: String,
                           @Header("Content-Type") type: String,
                           @Body timezone: TimezoneUpdate,
                           @Path("userId") objectId: String): TimezoneResponse
}

object UserApi {
    val retrofitService : ApiService by lazy { retrofit.create(ApiService::class.java) }
}