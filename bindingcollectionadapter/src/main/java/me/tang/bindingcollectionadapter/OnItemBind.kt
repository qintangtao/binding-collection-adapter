package me.tang.bindingcollectionadapter


interface OnItemBind<T> {

    fun onItemBind(
        itemBinding: ItemBinding<T>,
        position: Int,
        item: T)

}