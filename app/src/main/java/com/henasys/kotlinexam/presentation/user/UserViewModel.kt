package com.henasys.kotlinexam.presentation.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.henasys.kotlinexam.data.repository.UserRepository
import com.henasys.kotlinexam.model.User
import com.henasys.kotlinexam.presentation.Result
import com.henasys.kotlinexam.presentation.common.mapper.toResult
import com.henasys.kotlinexam.util.ext.map
import com.henasys.kotlinexam.util.ext.toLiveData
import com.henasys.kotlinexam.util.rx.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import org.reactivestreams.Publisher
import timber.log.Timber
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val repository: UserRepository,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private val mutableUser = MutableLiveData<Result<User>>()
    val user: LiveData<Result<User>> = mutableUser

    val isLoading: LiveData<Boolean> by lazy {
        user.map {it.inProgress}
    }

    init {
        observeUser()
    }

    fun observeUser() {
        Timber.i("observeUser")
        repository.user
            .observeOn(schedulerProvider.ui())
            .toResult(schedulerProvider)
            .subscribeBy(
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

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun start() {
    }

}