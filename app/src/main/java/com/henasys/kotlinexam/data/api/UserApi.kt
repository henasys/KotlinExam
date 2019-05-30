package com.henasys.kotlinexam.data.api

import androidx.annotation.CheckResult
import com.henasys.kotlinexam.data.api.response.UserLogin
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface UserApi {
    @POST("api/login")
    @FormUrlEncoded
    @CheckResult
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Single<UserLogin>
}