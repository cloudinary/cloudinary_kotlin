package com.cloudinary

import com.cloudinary.config.ApiConfig
import com.cloudinary.config.CloudConfig
import com.cloudinary.config.Configuration
import com.cloudinary.config.UrlConfig
import org.junit.Test
import kotlin.test.assertEquals

class ConfigTest {
    @Test
    fun testConfigFromUrl() {
        val cloudinaryUrl =
            "cloudinary://123456123456123:3Sf3FAdasa2easdFGDS3afADFS2@cloudname?shorten=true&cname=custom.domain.com&chunk_size=5000"

        val config = Configuration.fromUri(cloudinaryUrl)

        assertEquals("cloudname", config.cloudName)
        assertEquals("123456123456123", config.apiKey)
        assertEquals("3Sf3FAdasa2easdFGDS3afADFS2", config.apiSecret)
        assertEquals("custom.domain.com", config.cname)
        assertEquals(5000, config.chunkSize)
        assertEquals(true, config.shorten)

    }

    @Test
    fun testApiConfigDefaults() {
        val config = ApiConfig()

        assertEquals(null, config.uploadPrefix)
        assertEquals(20_000_000, config.chunkSize)
        assertEquals(0, config.readTimeout)
        assertEquals(0, config.connectTimeout)
    }

    @Test
    fun testApiConfig() {
        val config = ApiConfig(
            uploadPrefix = "http://prefix.api.com",
            chunkSize = 5000,
            connectTimeout = 100,
            readTimeout = 200
        )

        assertEquals("http://prefix.api.com", config.uploadPrefix)
        assertEquals(5000, config.chunkSize)
        assertEquals(100, config.connectTimeout)
        assertEquals(200, config.readTimeout)

        val copy = config.copy(readTimeout = 500)

        assertEquals("http://prefix.api.com", copy.uploadPrefix)
        assertEquals(5000, copy.chunkSize)
        assertEquals(500, copy.readTimeout)
        assertEquals(100, copy.connectTimeout)
    }

    @Test
    fun testCloudConfig() {
        val cloudName = "my_cloud"
        val apiKey = "abcdefghijklmnop"
        val apiSecret = "1234567890"

        val config = CloudConfig(
            cloudName = cloudName,
            apiKey = apiKey,
            apiSecret = apiSecret
        )

        assertEquals(cloudName, config.cloudName)
        assertEquals(apiKey, config.apiKey)
        assertEquals(apiSecret, config.apiSecret)

        val copy = config.copy(cloudName = "different_cloud")

        assertEquals("different_cloud", copy.cloudName)
        assertEquals(apiKey, copy.apiKey)
        assertEquals(apiSecret, copy.apiSecret)
    }

    @Test
    fun testUrlConfigDefaults() {
        val config = UrlConfig()

        assertEquals(null, config.secureDistribution)
        assertEquals(false, config.privateCdn)
        assertEquals(false, config.cdnSubdomain)
        assertEquals(false, config.shorten)
        assertEquals(false, config.secureCdnSubdomain)
        assertEquals(false, config.useRootPath)
        assertEquals(null, config.cname)
        assertEquals(false, config.secure)
        assertEquals(null, config.authToken)
    }

    @Test
    fun testUrlConfig() {
        val authToken = AuthToken(key = "auth_token_key")
        val secureDistribution = "secure.api.com"
        val cname = "my.domain.com"

        val config = UrlConfig(
            secureDistribution = secureDistribution,
            privateCdn = true,
            cdnSubdomain = true,
            shorten = true,
            secureCdnSubdomain = true,
            useRootPath = true,
            cname = cname,
            secure = true,
            authToken = authToken
        )

        assertEquals(secureDistribution, config.secureDistribution)
        assertEquals(true, config.privateCdn)
        assertEquals(true, config.cdnSubdomain)
        assertEquals(true, config.shorten)
        assertEquals(true, config.secureCdnSubdomain)
        assertEquals(true, config.useRootPath)
        assertEquals(cname, config.cname)
        assertEquals(true, config.secure)
        assertEquals(authToken, config.authToken)

        val copy = config.copy(
            secureDistribution = "copy.secure.distribution",
            shorten = false
        )

        assertEquals("copy.secure.distribution", copy.secureDistribution)
        assertEquals(true, copy.privateCdn)
        assertEquals(true, copy.cdnSubdomain)
        assertEquals(false, copy.shorten)
        assertEquals(true, copy.secureCdnSubdomain)
        assertEquals(true, copy.useRootPath)
        assertEquals(cname, copy.cname)
        assertEquals(true, copy.secure)
        assertEquals(authToken, copy.authToken)
    }
}