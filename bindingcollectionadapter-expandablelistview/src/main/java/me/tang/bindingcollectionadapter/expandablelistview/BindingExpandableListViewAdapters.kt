package me.tang.bindingcollectionadapter.expandablelistview

import android.widget.ExpandableListView
import androidx.databinding.BindingAdapter
import me.tang.bindingcollectionadapter.ItemBinding

object BindingExpandableListViewAdapters {

    @JvmStatic
    @BindingAdapter(
        value = ["groupBinding", "groups", "childBinding", "childrens"],
        requireAll = false
    )
    fun <T, T2> setAdapter(
        view: ExpandableListView,
        groupBinding: ItemBinding<T>,
        groups: List<T>?,
        childBinding: ItemBinding<T2>,
        childrens: List<List<T2>>?
    ) {
        requireNotNull(groupBinding) { "groupBinding must not be null" }
        requireNotNull(childBinding) { "childBinding must not be null" }

        if (groups == null || childrens == null)
            return

        val oldAdapter = view.expandableListAdapter

        val adapter = oldAdapter as? BindingExpandableListViewAdapter<T, T2>
            ?: BindingExpandableListViewAdapter<T, T2>()

        adapter.apply {
            setGroupBinding(groupBinding)
            setGroups(groups!!)
            setChildBinding(childBinding)
            setChilds(childrens!!)
        }

        if (oldAdapter != adapter)
            view.setAdapter(adapter)
    }
}