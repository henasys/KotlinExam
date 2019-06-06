package com.henasys.kotlinexam.presentation.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.henasys.kotlinexam.data.repository.UserRepository
import com.henasys.kotlinexam.model.User
import com.henasys.kotlinexam.presentation.Result
import com.henasys.kotlinexam.presentation.common.mapper.toResult
import com.henasys.kotlinexam.util.ext.toLiveData
import com.henasys.kotlinexam.util.rx.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import org.reactivestreams.Publisher
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val repository: UserRepository,
    private val schedulerProvider: SchedulerProvider
) : ViewModel() {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    val user: LiveData<Result<User>> by lazy {
        repository.user.toResult(schedulerProvider).toLiveData()
    }

    val users: LiveData<Result<List<User>>> by lazy {
        repository.users.toResult(schedulerProvider).toLiveData()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun start() {
    }

}