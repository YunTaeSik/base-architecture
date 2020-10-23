package com.yts.base.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<B : ViewDataBinding> : Fragment() {
    protected lateinit var binding: B

    protected abstract fun onLayoutId(): Int
    protected abstract fun observer()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, onLayoutId(), container, false)
        if(::binding.isInitialized){
            binding.lifecycleOwner = this
            observer()
        }
        return binding.root
    }

    /*protected fun addBindingVariable(variableId: Int, value: Any) {
        binding.setVariable(variableId, value)
    }

    protected fun setHoldExitTransition() {
        exitTransition = Hold().apply {
            duration = AnimationDuration.MEDIUM_EXPANDING
        }
    }

    protected fun setSharedElementTransition() {
        sharedElementEnterTransition = MaterialContainerTransform()
        sharedElementReturnTransition = MaterialContainerTransform()
    }*/

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        postponeEnterTransition()
        view?.doOnPreDraw {
            startPostponedEnterTransition()
        }
        observer()
    }

}