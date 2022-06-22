package me.tang.bindingcollectionadapter.tagflowlayout

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import me.tang.bindingcollectionadapter.BindingCollectionAdapter
import me.tang.bindingcollectionadapter.ItemBinding

class BindingTagFlowLayoutAdapter<T>(datas: List<T>) : TagAdapter<T>(datas), BindingCollectionAdapter<T> {

    private var inflater: LayoutInflater? = null
    private lateinit var items: List<T>
    private lateinit var itemBinding: ItemBinding<T>

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): T {
        return items.get(position)
    }

    override fun getView(parent: FlowLayout?, position: Int, t: T): View {
        inflater ?: LayoutInflater.from(parent!!.context).also { inflater = it }

        val item = getItem(position)
        itemBinding.onItemBind(position, item)

        val binding = onCreateBinding(inflater!!, itemBinding.layoutRes, parent!!)
        onBindBinding(binding, itemBinding.variableId, itemBinding.layoutRes, position, item)
        return binding.root
    }

    override fun setItemBinding(itemBinding: ItemBinding<T>) {
        this.itemBinding = itemBinding
    }

    override fun getItemBinding(): ItemBinding<T> {
        return itemBinding
    }

    override fun setItems(items: List<T>) {
        if (this::items.isInitialized && this.items === items) {
            return
        }
        this.items = items
        notifyDataChanged()
    }

    override fun getAdapterItem(position: Int): T {
        return getItem(position)
    }

    override fun onCreateBinding(
        inflater: LayoutInflater,
        layoutRes: Int,
        viewGroup: ViewGroup
    ): ViewDataBinding {
        return DataBindingUtil.inflate(inflater, layoutRes, viewGroup, false)
    }

    override fun onBindBinding(
        binding: ViewDataBinding,
        variableId: Int,
        layoutRes: Int,
        position: Int,
        item: T
    ) {
        if (itemBinding.bind(binding, position, item)) {
            binding.executePendingBindings()
        }
    }
}