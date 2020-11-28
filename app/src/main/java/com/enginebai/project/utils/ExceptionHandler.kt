package com.enginebai.project.utils

import android.app.Application
import com.enginebai.base.R
import io.reactivex.exceptions.OnErrorNotImplementedException
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.functions.Consumer
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.subjects.PublishSubject
import retrofit2.HttpException
import timber.log.Timber
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ExceptionHandler : Consumer<Throwable> {

    val errorMessageToDisplay = PublishSubject.create<String>()
    val handledException = PublishSubject.create<Throwable>()

    init {
        RxJavaPlugins.setErrorHandler(this)
    }

    override fun accept(t: Throwable) {
        val cause = parseCause(t)
        Timber.d("Handle exception: $cause from $t")
        when (cause) {
            is SocketTimeoutException, is ConnectException, is UnknownHostException, is SocketException -> {
                val errorMessage = "Network failed: $cause"
                Timber.w(errorMessage)
                errorMessageToDisplay.onNext(errorMessage)
            }
            is HttpException -> {
                val url = cause.response()?.raw()?.request?.url
                errorMessageToDisplay.onNext("HTTP ${cause.code()} of $url")
            }
            is ApiException -> {
                errorMessageToDisplay.onNext(cause.toString())
                handledException.onNext(cause)
            }
            else -> {
                Timber.e(cause)
                throw cause
            }
        }
    }

    private fun parseCause(t: Throwable): Throwable {
        when (t) {
            is OnErrorNotImplementedException, is UndeliverableException, is RuntimeException -> {
                t.cause?.run { return this } ?: throw ParseCauseFailException(t)
            }
        }
        return t
    }
}

class ParseCauseFailException(t: Throwable) : RuntimeException(t)
class ApiException(t: Throwable) : RuntimeException(t)