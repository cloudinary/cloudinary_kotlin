package com.cloudinary.upload.request.params

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
class AccessControlRule(
    @Json(name = "access_type")
    val accessType: AccessType,
    val start: Date?,
    val end: Date?
) {
    /**
     * Access type for an access rule
     */
    enum class AccessType {
        @Json(name = "anonymous")
        Anonymous,

        @Json(name = "token")
        Token
    }

    companion object {
        /**
         * Construct a new token access rule
         * @return The access rule instance
         */
        fun token(): AccessControlRule {
            return AccessControlRule(AccessType.Token, null, null)
        }

        /**
         * Construct a new anonymous access rule
         * @param start The start date for the rule
         * @return The access rule instance
         */
        fun anonymousFrom(start: Date?): AccessControlRule {
            return AccessControlRule(AccessType.Anonymous, start, null)
        }

        /**
         * Construct a new anonymous access rule
         * @param end The end date for the rule
         * @return The access rule instance
         */
        fun anonymousUntil(end: Date?): AccessControlRule {
            return AccessControlRule(AccessType.Anonymous, null, end)
        }

        /**
         * Construct a new anonymous access rule
         * @param start The start date for the rule
         * @param end The end date for the rule
         * @return The access rule instance
         */
        fun anonymous(start: Date?, end: Date?): AccessControlRule {
            return AccessControlRule(AccessType.Anonymous, start, end)
        }
    }
}
