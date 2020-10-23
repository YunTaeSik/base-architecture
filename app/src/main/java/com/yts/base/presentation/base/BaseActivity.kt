package  com.yts.base.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity() {
    protected lateinit var binding: B

    abstract fun onLayoutId(): Int
    abstract fun observer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, onLayoutId())
        if(::binding.isInitialized){
            binding.lifecycleOwner = this
            observer()
        }
    }
}
