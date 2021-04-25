package com.enginebai.project

import com.enginebai.base.view.BaseActivity
import com.enginebai.project.utils.ExceptionHandler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit

// TODO: 4. rename the package for your project
class MainActivity : BaseActivity() {

    private val exceptionHandler: ExceptionHandler by inject()
    private var exceptionHandlerDisposable: Disposable? = null

    override fun onStart() {
        super.onStart()
        // You will handle the error message at single activity.
        if (null == exceptionHandlerDisposable || false == exceptionHandlerDisposable?.isDisposed) {
            exceptionHandlerDisposable = exceptionHandler.errorMessageToDisplay
                .filter { it.isNotBlank() }
                .throttleFirst(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { handleErrorMessage(it) }
                .subscribe()
        }
    }

    override fun onStop() {
        exceptionHandlerDisposable?.dispose()
        super.onStop()
    }

    override fun getLayoutId() = R.layout.activity_main

    override fun handleErrorMessage(message: String) {
        TODO("Not yet implemented")
    }
}