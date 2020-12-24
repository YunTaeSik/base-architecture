package com.yts.base.presentation.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.yts.base.presentation.base.BaseViewModel

class MainViewModel @ViewModelInject constructor(): BaseViewModel() {
    val query = MutableLiveData<String>() //twoway
}