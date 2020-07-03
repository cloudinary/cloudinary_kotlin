package com.cloudinary.config

const val KEY = "key"
const val IP = "ip"
const val ACL = "acl"
const val START_TIME = "start_time"
const val EXPIRATION = "expiration"
const val DURATION = "duration"

interface IAuthTokenConfig {
    val key: String
    val ip: String?
    val acl: String?
    val startTime: Long?
    val expiration: Long?
    val duration: Long?
}

data class AuthTokenConfig(
    override val key: String,
    override val ip: String? = null,
    override val acl: String? = null,
    override val startTime: Long? = null,
    override val expiration: Long? = null,
    override val duration: Long? = null
) : IAuthTokenConfig {
    constructor(params: Map<*, *>) : this(
        key = (params[KEY] ?: error("Must provide Auth Token key")).toString(),
        startTime = params[START_TIME]?.toString()?.toLong() ?: 0,
        expiration = params[EXPIRATION]?.toString()?.toLong() ?: 0,
        ip = params[IP]?.toString(),
        acl = params[ACL]?.toString(),
        duration = params[DURATION]?.toString()?.toLong() ?: 0
    )
}
