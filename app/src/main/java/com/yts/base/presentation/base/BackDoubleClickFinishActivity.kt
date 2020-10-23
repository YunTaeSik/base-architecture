package com.yts.base.presentation.base

import android.os.Bundle
import androidx.activity.addCallback
import androidx.databinding.ViewDataBinding
import com.yts.base.R
import com.yts.base.extension.makeToast

abstract class BackDoubleClickFinishActivity<B : ViewDataBinding> : BaseActivity<B>() {
    private val TIME_INTERVAL = 2000
    private var mBackPressed: Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setOnBackDoublePressedFinish()
    }

    private fun setOnBackDoublePressedFinish() {
        onBackPressedDispatcher.addCallback {
            if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
                finish()
            } else {
                makeToast(R.string.msg_quit)
            }
            mBackPressed = System.currentTimeMillis()
        }
    }
}