package me.tang.bindingcollectionadapter.expandablelistview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import me.tang.bindingcollectionadapter.BindingExpandableCollectionAdapter
import me.tang.bindingcollectionadapter.ItemBinding

class BindingExpandableListViewAdapter<T, T2> : BaseExpandableListAdapter(),
    BindingExpandableCollectionAdapter<T, T2> {

    private var inflater: LayoutInflater? = null

    private lateinit var _groups: List<T>
    private lateinit var _groupBinding: ItemBinding<T>

    private lateinit var _childrens: List<List<T2>>
    private lateinit var _childBinding: ItemBinding<T2>


    override fun getGroupCount(): Int {
        return _groups.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return _childrens[groupPosition].size
    }

    override fun getGroup(groupPosition: Int): T {
        return _groups[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): T2 {
        return _childrens[groupPosition][childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        inflater ?: LayoutInflater.from(parent!!.context).also { inflater = it }

        val item = getGroup(groupPosition)

        var binding: ViewDataBinding? = null
        if (convertView == null)
        {
            _groupBinding.onItemBind(groupPosition, item)
            binding = onCreateBinding(inflater!!, _groupBinding.layoutRes, parent!!)
        }
        else
        {
            binding = DataBindingUtil.findBinding(convertView)
        }

        onGroupBindBinding(binding!!, _groupBinding.variableId, _groupBinding.layoutRes, groupPosition, item);

        return binding!!.root
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        inflater ?: LayoutInflater.from(parent!!.context).also { inflater = it }

        val item = getChild(groupPosition, childPosition)

        var binding: ViewDataBinding? = null
        if (convertView == null)
        {
            _childBinding.onItemBind(groupPosition, item)
            binding = onCreateBinding(inflater!!, _childBinding.layoutRes, parent!!)
        }
        else
        {
            binding = DataBindingUtil.findBinding(convertView)
        }

        onChildBindBinding(binding!!, _childBinding.variableId, _childBinding.layoutRes, groupPosition, childPosition, item);

        return binding!!.root
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun setGroupBinding(itemBinding: ItemBinding<T>) {
        _groupBinding = itemBinding
        notifyDataSetChanged()
    }

    override fun getGroupBinding(): ItemBinding<T> {
        return _groupBinding
    }

    override fun setGroups(items: List<T>) {
        _groups = items
        notifyDataSetChanged()
    }

    override fun getAdapterGroup(groupPosition: Int): T {
        return _groups[groupPosition]
    }

    override fun setChildBinding(itemBinding: ItemBinding<T2>) {
        _childBinding = itemBinding
    }

    override fun getChildBinding(): ItemBinding<T2> {
        return _childBinding
    }

    override fun setChilds(items: List<List<T2>>) {
        _childrens = items
    }

    override fun getAdapterChild(groupPosition: Int, childPosition: Int): T2 {
        return _childrens[groupPosition][childPosition]
    }

    override fun onCreateBinding(
        inflater: LayoutInflater,
        layoutRes: Int,
        viewGroup: ViewGroup
    ): ViewDataBinding {
        return DataBindingUtil.inflate(inflater, layoutRes, viewGroup, false)
    }

    override fun onGroupBindBinding(
        binding: ViewDataBinding,
        variableId: Int,
        layoutRes: Int,
        groupPosition: Int,
        item: T
    ) {
        if (_groupBinding.bind(binding, groupPosition, item)) {
            binding.executePendingBindings()
        }
    }

    override fun onChildBindBinding(
        binding: ViewDataBinding,
        variableId: Int,
        layoutRes: Int,
        groupPosition: Int,
        childPosition: Int,
        item: T2
    ) {
        if (_childBinding.bind(binding, childPosition, item)) {
            binding.executePendingBindings()
        }
    }

}