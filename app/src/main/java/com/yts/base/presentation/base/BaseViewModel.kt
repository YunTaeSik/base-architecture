package com.yts.base.presentation.base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yts.base.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    protected var _isLoading = MutableLiveData<Boolean>()
    protected var _toastMessageId = MutableLiveData<Int>()

    val isLoading: LiveData<Boolean> get() = _isLoading
    val toastMessageId: LiveData<Int> get() = _toastMessageId


    fun <T> addDisposable(
        observable: Observable<T>,
        onSuccess: ((t: T) -> Unit),
        onError: (Throwable) -> Unit
    ): Disposable {
        val disposable = observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                _isLoading.value = true
            }.doAfterNext {
                _isLoading.value = false
            }.doAfterTerminate {
                _isLoading.value = false
            }.subscribe(onSuccess, onError)

        disposable?.let {
            compositeDisposable.add(it)
        }
        return disposable
    }

    fun commonError(throwable: Throwable) {
        Log.e("test", "test")
        if (throwable is SocketTimeoutException || throwable is ConnectException) {
            _toastMessageId.postValue(R.string.error_socket_timeout)
        } else if (throwable is UnknownHostException) {
            _toastMessageId.postValue(R.string.error_https)
        } else {
            _toastMessageId.postValue(R.string.error_default)
        }
    }

    private fun clear() {
        compositeDisposable.clear()
    }

    override fun onCleared() {
        clear()
        super.onCleared()
    }
}