package com.henasys.kotlinexam.presentation.user

import android.text.Editable
import android.util.Patterns
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    private val mutableEmailError = MutableLiveData<Boolean>()
    val emailError: LiveData<Boolean>
        get() = mutableEmailError

    private val mutablePasswordError = MutableLiveData<Boolean>()
    val passwordError: LiveData<Boolean>
        get() = mutablePasswordError

    val isLoading = ObservableBoolean()
    val buttonEnabled = ObservableBoolean(false)
    val email = ObservableField<String>()
    val password = ObservableField<String>()

    init {
        Timber.i("init")
        mutableEmailError.value = false
        mutablePasswordError.value = false

//        testLogin()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    private fun testLogin() {
        email.set("eve.holt@reqres.in")
        password.set("pistol")
    }

    fun login() {
        if (isValid()) {
            login(email.get().toString(), password.get().toString())
        }
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

    fun onClickForLogin(v: View) {
        Timber.i("onClickForLogin")
        Timber.i("email: ${email.get()}, password: ${password.get()}")

        login()
    }

    fun onClickForRegistration(v: View) {
        Timber.i("onClickForRegistration")
        Timber.i("email: ${email.get()}, password: ${password.get()}")
    }

    fun onClickForResetPassword(v: View) {
        Timber.i("onClickForResetPassword")
        Timber.i("email: ${email.get()}")
    }

    fun afterTextChangedForEmail(s: Editable?) {
        Timber.i("afterTextChangedForEmail: ${s.toString()}")
        mutableEmailError.value = !isEmailValid(s.toString())
        buttonEnabled.set(isValid())
    }

    fun afterTextChangedForPassword(s: Editable?) {
        Timber.i("afterTextChangedForPassword: ${s.toString()}")
        mutablePasswordError.value = !isPasswordValid(s.toString())
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