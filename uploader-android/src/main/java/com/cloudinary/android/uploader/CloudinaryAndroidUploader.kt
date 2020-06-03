package com.cloudinary.android.uploader

import com.cloudinary.Cloudinary
import com.cloudinary.android.uploader.work.AsyncWorkDispatcher
import com.cloudinary.android.uploader.work.WorkManagerAsyncDispatcher

private const val BACKGROUND_WORK_DISPATCHER_EXTENSION_NAME = "cld.AsyncWorkDispatcher"

var Cloudinary.asyncWorkDispatcher: AsyncWorkDispatcher
    get() = getExtension(
        BACKGROUND_WORK_DISPATCHER_EXTENSION_NAME,
        WorkManagerAsyncDispatcher()
    ) as AsyncWorkDispatcher
    set(value) = setExtension(BACKGROUND_WORK_DISPATCHER_EXTENSION_NAME, value)
