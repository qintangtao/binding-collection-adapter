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
        groups: List<T>,
        childBinding: ItemBinding<T2>,
        childrens: List<List<T2>>
    ) {
        requireNotNull(groupBinding) { "groupBinding must not be null" }
        requireNotNull(childBinding) { "childBinding must not be null" }
        requireNotNull(groups) { "groups must not be null" }
        requireNotNull(childrens) { "childrens must not be null" }

        val oldAdapter = view.expandableListAdapter
        var adapter = oldAdapter as? BindingExpandableListViewAdapter<T, T2>
            ?: BindingExpandableListViewAdapter<T, T2>()
        adapter.setGroupBinding(groupBinding)
        adapter.setGroups(groups)
        adapter.setChildBinding(childBinding)
        adapter.setChilds(childrens)
        if (oldAdapter != adapter)
            view.setAdapter(adapter)
    }
}