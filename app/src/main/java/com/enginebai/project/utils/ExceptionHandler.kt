package com.enginebai.project.utils

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

/**
 * The exception handler for the application, it will handle RxJava exception automatically,
 *  and for non-RxJava you call `exceptionHandler.accept(e)`, and add the new branch condition
 *  for your custom exception you want to handle.
 *
 *  If you want to just display error message, call `errorMessageToDisplay.onNext(error message)`.
 *  If you want to propagate the exception, call `handledException.onNext(e)`.
 *
 *  To display the error message, you need to subscribe the `errorMessageToDisplay` subject.
 *  To handle the propagated exception, you need to subscribe the `handledException` subject.
 */
class ExceptionHandler : Consumer<Throwable> {

    // Emit strings if you handle the exception and just display error message.
    val errorMessageToDisplay = PublishSubject.create<String>()
    // Emit the exception if you'd like to propagate the exception and handle in other modules or layers
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
                handledException.onNext(cause)
            }
            // TODO: add case to handle your custom exception
            else -> {
                // For the case you can't catch or handle
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

// TODO: you can create the custom exception for your logic
// Such as UsernameNotFoundException(), PasswordIncorrectException()