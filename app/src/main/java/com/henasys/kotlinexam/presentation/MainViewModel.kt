package com.henasys.kotlinexam.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.henasys.kotlinexam.data.repository.UserRepository
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

    private val mutableNavigateToLogoutDone = MutableLiveData<Event<Any>>()
    val navigateToLogoutDone: LiveData<Event<Any>>
        get() = mutableNavigateToLogoutDone

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun logout() {
        repository.logout()
            .observeOn(schedulerProvider.ui())
            .subscribeBy(
                onComplete = {
                    Timber.i("onComplete")
                    mutableNavigateToLogoutDone.value = Event(Any())
                },
                onError = {
                    Timber.i("onError: $it")
                }
            )
            .addTo(compositeDisposable)
    }

    fun start() {
    }
}