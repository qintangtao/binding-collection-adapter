package me.tang.bindingcollectionadapter.tagflowlayout

import androidx.databinding.BindingAdapter
import com.zhy.view.flowlayout.TagFlowLayout
import me.tang.bindingcollectionadapter.ItemBinding

object BindingTagFlowLayoutAdapters {

    @BindingAdapter(value = ["itemBinding", "items", "selected"], requireAll = false)
    fun  <T>  setAdapter(layout: TagFlowLayout, itemBinding: ItemBinding<T>, items: List<T>?, selected: Int?) {
        requireNotNull(itemBinding) { "itemBinding must not be null" }
        items?.let {
            val oldAdapter =  layout.adapter
            var adapter = oldAdapter as? BindingTagFlowLayoutAdapter<T> ?: BindingTagFlowLayoutAdapter<T>(it)
            adapter.setItemBinding(itemBinding)
            adapter.setItems(it.toMutableList())
            selected?.let {
                if (selected > -1) layout.adapter?.setSelectedList(selected)
            }
            if (oldAdapter != adapter)
                layout.adapter = adapter
        }
    }

    @BindingAdapter(value = ["onTagClick"], requireAll = false)
    fun setOnTagClickListener(layout: TagFlowLayout, listener: TagFlowLayout.OnTagClickListener) {
        layout.setOnTagClickListener(listener)
    }

}