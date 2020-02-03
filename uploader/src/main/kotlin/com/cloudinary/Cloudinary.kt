package com.cloudinary

import com.cloudinary.http.HttpClientFactory
import com.cloudinary.upload.Uploader

fun Cloudinary.uploader() = Uploader(this)
fun Cloudinary.uploader(clientFactory: HttpClientFactory) = Uploader(this, clientFactory)
