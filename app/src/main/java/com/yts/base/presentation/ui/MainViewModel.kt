package com.yts.base.presentation.ui

import androidx.lifecycle.MutableLiveData
import com.yts.base.presentation.base.BaseViewModel

class MainViewModel : BaseViewModel() {
    val query = MutableLiveData<String>() //twoway
}