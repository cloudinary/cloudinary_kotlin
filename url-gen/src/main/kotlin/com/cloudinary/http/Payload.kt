package com.cloudinary.http

import java.io.InputStream

interface Payload<T> {
    val value: T
    val name: String
    val length: Long

    fun asInputStream(): InputStream
}