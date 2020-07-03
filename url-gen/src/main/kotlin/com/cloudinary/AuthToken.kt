package com.cloudinary

import com.cloudinary.config.IAuthTokenConfig
import com.cloudinary.util.cldHexStringToByteArray
import com.cloudinary.util.cldUrlEncode
import com.cloudinary.util.toHex
import java.nio.charset.Charset
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.util.*
import java.util.regex.Pattern
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

val NULL_AUTH_TOKEN = AuthToken(key = "", isNullToken = true)
const val AUTH_TOKEN_NAME = "__cld_token__"
val UNSAFE_URL_CHARS_PATTERN =
    Pattern.compile("[ \"#%&'/:;<=>?@\\[\\\\\\]^`{|}~]")!!

data class AuthToken(
    private val tokenName: String? = AUTH_TOKEN_NAME,
    private val key: String,
    val startTime: Long = 0,
    private val expiration: Long = 0,
    private val ip: String? = null,
    val acl: String? = null,
    val duration: Long = 0,
    private val isNullToken: Boolean = false
) {
    constructor(config: IAuthTokenConfig) : this(
        key = config.key,
        ip = config.ip,
        acl = config.acl,
        startTime = config.startTime ?: 0,
        expiration = config.expiration ?: 0,
        duration = config.duration ?: 0
    )

    /**
     * Generate a URL token for the given URL.
     *
     * @param url the URL to be authorized
     * @return a URL token
     */
    fun generate(url: String? = null): String {
        var expiration = expiration
        if (expiration == 0L) {
            expiration = if (duration > 0) {
                val start =
                    if (startTime > 0) startTime else Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis / 1000L
                start + duration
            } else {
                throw IllegalArgumentException("Must provide either expiration or duration")
            }
        }
        val tokenParts = ArrayList<String>()
        if (ip != null) {
            tokenParts.add("ip=$ip")
        }
        if (startTime > 0) {
            tokenParts.add("st=$startTime")
        }
        tokenParts.add("exp=$expiration")
        if (acl != null) {
            tokenParts.add("acl=" + escapeToLower(acl))
        }
        val toSign = ArrayList(tokenParts)
        if (url != null && acl == null) {
            toSign.add("url=" + escapeToLower(url))
        }
        val auth = digest(toSign.joinToString("~"))
        tokenParts.add("hmac=$auth")
        return tokenName + "=" + tokenParts.joinToString("~")
    }

    /**
     * Escape url using lowercase hex code
     *
     * @param url a url string
     * @return escaped url
     */
    private fun escapeToLower(url: String): String {
        return url.cldUrlEncode(UNSAFE_URL_CHARS_PATTERN, Charset.forName("UTF-8"))
    }

    private fun digest(message: String): String {
        val binKey: ByteArray = key.cldHexStringToByteArray()
        return try {
            val hmac = Mac.getInstance("HmacSHA256")
            val secret = SecretKeySpec(binKey, "HmacSHA256")
            hmac.init(secret)
            val bytes = message.toByteArray()
            hmac.doFinal(bytes).toHex().toLowerCase(Locale.US)
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException("Cannot create authorization token.", e)
        } catch (e: InvalidKeyException) {
            throw RuntimeException("Cannot create authorization token.", e)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AuthToken) return false

        if (tokenName != other.tokenName) return false
        if (key != other.key) return false
        if (startTime != other.startTime) return false
        if (expiration != other.expiration) return false
        if (ip != other.ip) return false
        if (acl != other.acl) return false
        if (duration != other.duration) return false
        if (isNullToken != other.isNullToken) return false

        return true
    }

    override fun hashCode(): Int {
        if (isNullToken) return 0

        var result = tokenName?.hashCode() ?: 0
        result = 31 * result + key.hashCode()
        result = 31 * result + startTime.hashCode()
        result = 31 * result + expiration.hashCode()
        result = 31 * result + (ip?.hashCode() ?: 0)
        result = 31 * result + (acl?.hashCode() ?: 0)
        result = 31 * result + duration.hashCode()
        return result
    }
}