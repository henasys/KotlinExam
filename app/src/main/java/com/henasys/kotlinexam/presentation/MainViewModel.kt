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
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: UserRepository,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private val mutableUser = MutableLiveData<User>()
    val user: LiveData<User> = mutableUser

    init {
        observeUser()
        observeUsers()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun observeUser() {
        Timber.i("observeUser")
        repository.user
            .observeOn(schedulerProvider.ui())
            .subscribeBy(
                onComplete = {
                    Timber.i("onComplete")
                },
                onNext = {
                    Timber.i("onNext: $it")
                    mutableUser.value = it
                },
                onError = {
                    Timber.i("onError: $it")
                    mutableUser.value = null
                }
            )
            .addTo(compositeDisposable)
    }

    fun observeUsers() {
        Timber.i("observeUsers")
        repository.users
            .observeOn(schedulerProvider.ui())
            .subscribeBy(
                onComplete = {
                    Timber.i("onComplete")
                },
                onNext = {
                    Timber.i("onNext: $it")
                },
                onError = {
                    Timber.i("onError: $it")
                }
            )
            .addTo(compositeDisposable)
    }

    fun start() {
        repository.deleteAll()
    }
}