package com.henasys.kotlinexam.util

import timber.log.Timber

fun defaultErrorHandler(): (Throwable) -> Unit = { e -> Timber.e(e) }
