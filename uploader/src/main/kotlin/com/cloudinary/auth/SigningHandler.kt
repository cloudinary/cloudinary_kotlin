package com.cloudinary.auth

interface SigningHandler {
    fun sign(map: Map<String, String?>): Signature
}