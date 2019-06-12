package com.henasys.kotlinexam.presentation.user

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.henasys.kotlinexam.data.repository.UserRepository
import com.henasys.kotlinexam.presentation.Event
import com.henasys.kotlinexam.presentation.common.mapper.toResult
import com.henasys.kotlinexam.util.rx.SchedulerProvider
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val repository: UserRepository,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private val mutableNavigateToLoginDone = MutableLiveData<Event<Any>>()
    val navigateToLoginDone: LiveData<Event<Any>>
        get() = mutableNavigateToLoginDone

    val isLoading = ObservableField<Boolean>()
    val emailError = ObservableField<String>()
    val passwordError = ObservableField<String>()

    init {
//        observeUsers()
    }

    private fun observeUsers() {
        repository.users
            .flatMap { Flowable.just(it.first()) }
            .subscribeOn(schedulerProvider.io())
            .toResult(schedulerProvider)
            .subscribeBy(
                onComplete = {
                    Timber.i("onComplete")
                },
                onNext = {
                    Timber.i("onNext: $it")
//                    mutableUser.value = it
                },
                onError = {
                    Timber.i("onError: $it")
//                    mutableUser.value = Result.failure(it.message ?: "unknown", it)
                }
            )
            .addTo(compositeDisposable)
    }

    fun login(email: String, password: String) {
        repository.login(email, password)
            .observeOn(schedulerProvider.ui())
            .doOnSubscribe { isLoading.set(true) }
            .doFinally { isLoading.set(false) }
            .subscribeBy(
                onSuccess = {
                    Timber.i("onSuccess: $it")
                    mutableNavigateToLoginDone.value = Event(Any())
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