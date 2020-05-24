package com.cloudinary.android.downloader

fun String.isRemoteUrl(): Boolean {
    return this.matches("ftp:.*|https?:.*|s3:.*|gs:.*|data:([\\w-]+/[\\w-]+)?(;[\\w-]+=[\\w-]+)*;base64,([a-zA-Z0-9/+\n=]+)".toRegex());
}