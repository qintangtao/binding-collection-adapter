package me.tang.bindingcollectionadapter

import android.content.Context
import android.content.res.Resources
import android.os.Looper
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.MainThread
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner


object Utils {

    fun throwMissingVariable(
        binding: ViewDataBinding,
        bindingVariable: Int,
        @LayoutRes layoutRes: Int
    ) {
        val context: Context = binding.root.context
        val resources: Resources = context.resources
        val layoutName: String = resources.getResourceName(layoutRes)
        val bindingVariableName = DataBindingUtil.convertBrIdToString(bindingVariable)
        throw IllegalStateException("Could not bind variable '$bindingVariableName' in layout '$layoutName'")
    }

    @MainThread
    fun findLifecycleOwner(view: View): LifecycleOwner? {
        val binding: ViewDataBinding? = DataBindingUtil.findBinding(view)
        var lifecycleOwner: LifecycleOwner? = binding?.lifecycleOwner
        if (lifecycleOwner == null) {
            val ctx: Context = view.getContext()
            if (ctx is LifecycleOwner)
                lifecycleOwner = ctx
        }
        return lifecycleOwner
    }

    fun ensureChangeOnMainThread() {
        check(!(Thread.currentThread() !== Looper.getMainLooper().thread)) { "You must only modify the ObservableList on the main thread." }
    }
}