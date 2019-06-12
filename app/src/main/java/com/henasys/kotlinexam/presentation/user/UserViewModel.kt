package com.henasys.kotlinexam.presentation.user

import android.text.Editable
import android.util.Patterns
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.henasys.kotlinexam.R
import com.henasys.kotlinexam.data.repository.UserRepository
import com.henasys.kotlinexam.presentation.Event
import com.henasys.kotlinexam.util.rx.SchedulerProvider
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

    val isLoading = ObservableBoolean()
    val buttonEnabled = ObservableBoolean(false)
    val email = ObservableField<String>()
    val password = ObservableField<String>()

    val emailError = ObservableInt()
    val passwordError = ObservableInt()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
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

    fun onClick(v: View) {
        Timber.i("onClick")
        Timber.i("email: ${email.get()}, password: ${password.get()}")

        if (isValid()) {
            login(email.get().toString(), password.get().toString())
        }
    }

    fun afterTextChangedForEmail(s: Editable?) {
        Timber.i("afterTextChangedForEmail: ${s.toString()}")
        if (!isEmailValid(s.toString())) {
            emailError.set(R.string.invalid_email)
        }

        buttonEnabled.set(isValid())
    }

    fun afterTextChangedForPassword(s: Editable?) {
        Timber.i("afterTextChangedForPassword: ${s.toString()}")
        if (!isPasswordValid(s.toString())) {
            passwordError.set(R.string.invalid_password)
        }

        buttonEnabled.set(isValid())
    }

    private fun isValid(): Boolean {
        val emailValue = email.get().toString()
        val passwordValue = password.get().toString()

        return isEmailValid(emailValue) && isPasswordValid(passwordValue)
    }


    // A placeholder email validation check
    private fun isEmailValid(email: String?): Boolean {
        if (email == null) {
            return false
        }
        return if (email.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(email).matches()
        } else false

    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String?): Boolean {
        return password != null && password.trim { it <= ' ' }.length >= 4
    }
}