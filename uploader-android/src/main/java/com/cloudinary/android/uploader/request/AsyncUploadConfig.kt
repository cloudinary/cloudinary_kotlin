package com.cloudinary.android.uploader.request

data class AsyncUploadConfig(
    val requiredNetworkType: NetworkType,
    val requiresCharging: Boolean,
    val requiresIdle: Boolean,
    val requiresBatteryNotLow: Boolean,
    val maxErrorRetries: Int,
    val backoffMillis: Long,
    val backoffPolicy: BackoffPolicy,
    val initialDelay: Long
) {
    companion object {
        fun default() = Builder().build()
    }

    class Builder {
        private var requiredNetworkType: NetworkType = NetworkType.CONNECTED
        private var requiresCharging: Boolean = false
        private var requiresIdle: Boolean = false
        private var requiresBatteryNotLow: Boolean = false
        private var maxErrorRetries: Int = 5
        private var backoffMillis: Long = 120000
        private var backoffPolicy: BackoffPolicy = BackoffPolicy.EXPONENTIAL
        private var initialDelay: Long = 0

        fun networkType(requiredNetworkType: NetworkType) = apply { this.requiredNetworkType = requiredNetworkType }
        fun requiresCharging(requiresCharging: Boolean) = apply { this.requiresCharging = requiresCharging }
        fun requiresBatteryNotLow(requiresBatteryNotLow: Boolean) =
            apply { this.requiresBatteryNotLow = requiresBatteryNotLow }

        fun requiresIdle(requiresIdle: Boolean) = apply { this.requiresIdle = requiresIdle }
        fun maxErrorRetries(maxErrorRetries: Int) = apply { this.maxErrorRetries = maxErrorRetries }
        fun backoffMillis(backoffMillis: Long) = apply { this.backoffMillis = backoffMillis }
        fun backoffPolicy(backoffPolicy: BackoffPolicy) = apply { this.backoffPolicy = backoffPolicy }
        fun initialDelay(initialDelay: Long) = apply { this.initialDelay = initialDelay }

        fun build() = AsyncUploadConfig(
            requiredNetworkType,
            requiresCharging,
            requiresIdle,
            requiresBatteryNotLow,
            maxErrorRetries,
            backoffMillis,
            backoffPolicy,
            initialDelay
        )
    }
}


/**
 * Enum to define requirements for network.
 */
enum class NetworkType {
    /**
     * Any working network connection is required for this work.
     */
    CONNECTED,

    /**
     * An unmetered network connection is required for this work.
     */
    UNMETERED,

    /**
     * A non-roaming network connection is required for this work.
     */
    NOT_ROAMING,

    /**
     * A metered network connection is required for this work.
     */
    METERED
}

/**
 * Enum to define the backoff policy for request rescheduling.
 */
enum class BackoffPolicy {
    /**
     * backoff = numFailures * initial_backoff.
     */
    LINEAR,

    /**
     * backoff = initial_backoff * 2 ^ (numFailures - 1).
     */
    EXPONENTIAL
}