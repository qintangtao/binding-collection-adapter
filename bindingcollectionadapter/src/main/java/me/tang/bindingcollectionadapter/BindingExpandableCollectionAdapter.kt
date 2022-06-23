package me.tang.bindingcollectionadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding

interface BindingExpandableCollectionAdapter<T, T2> {

    fun setGroupBinding(itemBinding: ItemBinding<T>)

    fun getGroupBinding(): ItemBinding<T>

    fun setGroups(items: List<T>)

    fun getAdapterGroup(groupPosition: Int): T

    fun setChildBinding(itemBinding: ItemBinding<T2>)

    fun getChildBinding(): ItemBinding<T2>

    fun setChilds(items: List<List<T2>>)

    fun getAdapterChild(groupPosition: Int, childPosition: Int): T2

    fun onCreateBinding(
        inflater: LayoutInflater,
        @LayoutRes layoutRes: Int,
        viewGroup: ViewGroup
    ): ViewDataBinding

    fun onGroupBindBinding(
        binding: ViewDataBinding,
        variableId: Int,
        @LayoutRes layoutRes: Int,
        groupPosition: Int,
        item: T
    )

    fun onChildBindBinding(
        binding: ViewDataBinding,
        variableId: Int,
        @LayoutRes layoutRes: Int,
        groupPosition: Int,
        childPosition: Int,
        item: T2
    )

}