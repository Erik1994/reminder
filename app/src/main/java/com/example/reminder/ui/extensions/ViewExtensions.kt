package com.example.reminder.ui.extensions

import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.graphics.drawable.DrawableCompat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.debounce

private const val DEBOUNCE_DELAY_MS: Long = 300L

@ExperimentalCoroutinesApi
fun View.clicks(): Flow<Unit> = callbackFlow {
    setOnClickListener { safeOffer(Unit) }
    awaitClose { setOnClickListener(null) }
}

@FlowPreview
@ExperimentalCoroutinesApi
fun View.debounceClicks(): Flow<Unit> = callbackFlow {
    setOnClickListener { safeOffer(Unit) }
    awaitClose { setOnClickListener(null) }
}.debounce(DEBOUNCE_DELAY_MS)

@ExperimentalCoroutinesApi
fun <T> ProducerScope<T>.safeOffer(element: T) {
    if (!isClosedForSend) {
        trySend(element)
    }
}

fun Drawable.changeDrawableColor(color: Int): Drawable {
    val wrappedDrawable = DrawableCompat.wrap(this)
    DrawableCompat.setTint(wrappedDrawable, color)
    return wrappedDrawable
}


fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.makeInvisible() {
    this.visibility = View.INVISIBLE
}

inline var View.isVisible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }


fun View.disable() {
    this.isEnabled = false
}

fun View.enable() {
    this.isEnabled = true
}