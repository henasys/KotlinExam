package com.henasys.kotlinexam.data.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.henasys.kotlinexam.data.api.response.UserLogin
import com.henasys.kotlinexam.di.DaggerTestAppComponent
import io.reactivex.observers.TestObserver
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import javax.inject.Inject

class MockUserApiTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Inject
    lateinit var mockWebServer: MockWebServer

    @Inject
    lateinit var userApi: UserApi

    @Before
    fun setUp() {
        DaggerTestAppComponent.builder().build().inject(this)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun login_not_ok() {
        val email = "tester@test.org"
        val password = "password1"

        println("email: $email")

        userApi.login(email, password)
            .subscribeOn(Schedulers.trampoline())
            .observeOn(Schedulers.trampoline())
            .subscribeBy(
                onSuccess = {println("onSuccess: $it")},
                onError = {println("onError: $it")}
            )
    }

    @Test
    fun login_ok() {
        // Given
        val token = "this_is_a_token"
        val path = "/api/login"
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("{\"token\": \"${token}\"}")
        mockWebServer.enqueue(mockResponse)

        val email = "eve.holt@reqres.in"
        val password = "pistol"

        println("email: $email")

        // When
        val testObserver = TestObserver<UserLogin>()
        userApi.login(email, password).subscribe(testObserver)

        // Then
        testObserver.assertNoErrors()
        testObserver.assertValueCount(1)
        testObserver.assertValue {
            it.token == token
        }

        val request = mockWebServer.takeRequest()
        Assert.assertEquals(path, request.path)
    }
}