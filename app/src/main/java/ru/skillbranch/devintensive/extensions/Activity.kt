package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard() {
    hideKeyboard(if (currentFocus == null) View(this) else currentFocus)
}

fun Activity.isKeyboardOpen(): Boolean {
    val visibleBounds = Rect()
    val rootView = findViewById<View>(android.R.id.content)
    rootView.getWindowVisibleDisplayFrame(visibleBounds)

    val heightDiff = rootView.height - visibleBounds.height()
    return heightDiff > rootView.rootView.height / 4
}

fun Activity.isKeyboardClosed(): Boolean {
    return this.isKeyboardOpen().not()
}

fun Context.hideKeyboard(view: View?) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
}