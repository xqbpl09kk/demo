package me.pl09kk.demoapp

import android.util.Log


interface IClickable {
    fun onItemClick(): Boolean
}

class ParentItem : IClickable {

    private val childItem = ChildItem()

    override fun onItemClick(): Boolean {
        return childItem.onItemClick()
    }

}

class ChildItem : IClickable {
    override fun onItemClick(): Boolean {
        Log.e("TestBY", "this is in child Item")
        return true
    }
}


class ReplaceItem(val childItem: ChildItem) : IClickable by childItem {

    val childItem2 = ChildItem()

    private fun doSomething() {

    }

}


fun test() {
    val replaceItem = ReplaceItem(ChildItem());
    replaceItem.onItemClick()
}
