package me.tang.bindingcollectionadapter

import android.util.SparseArray
import androidx.annotation.LayoutRes
import androidx.core.util.forEach
import androidx.databinding.ViewDataBinding


class ItemBinding<T> private constructor(
    private var onItemBind: OnItemBind<T>? = null
) {

    companion object {
        val VAR_NONE = 0
        private val VAR_INVALID = -1
        private val LAYOUT_NONE = 0

        fun <T> of(
            variableId: Int,
            positionId: Int,
            @LayoutRes layoutRes: Int
        ): ItemBinding<T> {
            return ItemBinding<T>().set(variableId, positionId, layoutRes)
        }

        fun <T> of(variableId: Int, @LayoutRes layoutRes: Int): ItemBinding<T> {
            return ItemBinding<T>().set(variableId, layoutRes)
        }

        fun <T> of(onItemBind: OnItemBind<T>): ItemBinding<T> {
            requireNotNull(onItemBind) { "onItemBind == null" }
            return ItemBinding(onItemBind)
        }
    }

    private var _variableId = 0
    val variableId get() = _variableId

    private var _positionId: Int = 0
    val positionId get() = _positionId

    @LayoutRes
    private var _layoutRes = 0
    val layoutRes get() = _layoutRes

    private var _extraBindings: SparseArray<Any>? = null

    fun set(variableId: Int, positionId: Int, @LayoutRes layoutRes: Int): ItemBinding<T> {
        _variableId = variableId
        _positionId = positionId
        _layoutRes = layoutRes
        return this
    }

    fun set(variableId: Int, @LayoutRes layoutRes: Int): ItemBinding<T> {
        return set(variableId, ItemBinding.VAR_NONE, layoutRes)
    }

    fun variableId(variableId: Int): ItemBinding<T> {
        _variableId = variableId
        return this
    }

    fun layoutRes(@LayoutRes layoutRes: Int): ItemBinding<T> {
        _layoutRes = layoutRes
        return this
    }

    fun bindExtra(variableId: Int, value: Any?): ItemBinding<T> {
        _extraBindings ?: SparseArray<Any>(1).also { _extraBindings = it }
        _extraBindings?.put(variableId, value)
        return this
    }

    fun clearExtras(): ItemBinding<T> {
        _extraBindings?.clear()
        return this
    }

    fun removeExtra(variableId: Int): ItemBinding<T> {
        _extraBindings?.remove(variableId)
        return this
    }

    fun extraBinding(variableId: Int): Any? {
        return _extraBindings?.get(variableId)
    }

    fun onItemBind(position: Int, item: T) {
        onItemBind?.let {
            _variableId = VAR_INVALID
            _layoutRes = LAYOUT_NONE
            it.onItemBind(this, position, item)
            check(variableId != ItemBinding.VAR_INVALID) { "variableId not set in onItemBind()" }
            check(layoutRes != ItemBinding.LAYOUT_NONE) { "layoutRes not set in onItemBind()" }
        }
    }

    fun bind(binding: ViewDataBinding, position: Int, item: T): Boolean {
        if (variableId == VAR_NONE) {
            return false
        }
        val result = binding.setVariable(variableId, item)
        if (!result) {
            Utils.throwMissingVariable(binding, variableId, layoutRes)
        }

        if (positionId != ItemBinding.VAR_NONE) {
            binding.setVariable(positionId, position)
        }

        _extraBindings?.forEach { key, value ->
            if (key != VAR_NONE) {
                binding.setVariable(key, value)
            }
        }
        return true
    }
}