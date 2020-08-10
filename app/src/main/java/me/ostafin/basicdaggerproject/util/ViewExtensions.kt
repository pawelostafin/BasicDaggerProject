package me.ostafin.basicdaggerproject.util

import android.view.View

fun View.setVisible() {
    this.visibility = View.VISIBLE
}

fun View.setInvisible() {
    this.visibility = View.INVISIBLE
}

fun View.setGone() {
    this.visibility = View.GONE
}

fun View.setVisibleOrInvisible(shouldBeVisible: Boolean) {
    if (shouldBeVisible) setVisible() else setInvisible()
}

var View.isVisible: Boolean
    get() = this.visibility == View.VISIBLE
    set(newValue) {
        if (newValue) {
            this.setVisible()
        } else {
            this.setGone()
        }
    }