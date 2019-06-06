package com.henasys.kotlinexam.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.henasys.kotlinexam.data.repository.UserRepository
import com.henasys.kotlinexam.model.User
import com.henasys.kotlinexam.presentation.common.mapper.toResult
import com.henasys.kotlinexam.util.defaultErrorHandler
import com.henasys.kotlinexam.util.ext.toLiveData
import com.henasys.kotlinexam.util.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: UserRepository,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private val mutableIsUserLogin = MutableLiveData<Boolean>()
    val isUserLogin: LiveData<Boolean> = mutableIsUserLogin

    val users: LiveData<Result<List<User>>> by lazy {
        repository.users.toResult(schedulerProvider).toLiveData()
    }

    init {
        checkUserLogin()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun checkUserLogin() {
        Timber.i("checkUserLogin")
        repository.user
            .observeOn(schedulerProvider.ui())
            .subscribeBy(
                onSuccess = {
                    Timber.i("onSuccess: $it")
                    mutableIsUserLogin.value = true
                },
                onError = {
                    Timber.i("onError: $it")
                    mutableIsUserLogin.value = false
                }
            )
            .addTo(compositeDisposable)
    }

    fun login(email: String, password: String) {
        repository.login(email, password)
            .observeOn(schedulerProvider.ui())
            .subscribeBy(
                onSuccess = {
                    Timber.i("onSuccess: $it")
                },
                onError = {
                    Timber.i("onError: $it")
                }
            )
            .addTo(compositeDisposable)
    }

    fun start() {
//        checkUserLogin()

        val email = "eve.holt@reqres.in"
        val password = "pistol"

//        login(email, password)
    }
}