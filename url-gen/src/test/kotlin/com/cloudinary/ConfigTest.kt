package com.cloudinary

import com.cloudinary.config.*
import org.junit.Test
import kotlin.test.assertEquals

class ConfigTest {
    @Test
    fun testConfigFromUrl() {
        val cloudinaryUrl =
            "cloudinary://123456123456123:3Sf3FAdasa2easdFGDS3afADFS2@cloudname?shorten=true&cname=custom.domain.com&chunk_size=5000"

        val config = CloudinaryConfig.fromUri(cloudinaryUrl)

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

        assertEquals(DEFAULT_UPLOAD_PREFIX, config.uploadPrefix)
        assertEquals(20_000_000, config.chunkSize)
        assertEquals(0, config.readTimeout)
        assertEquals(60, config.connectionTimeout)
    }

    @Test
    fun testApiConfig() {
        val config = ApiConfig(
            uploadPrefix = "http://prefix.api.com",
            chunkSize = 5000,
            connectionTimeout = 100,
            readTimeout = 200
        )

        assertEquals("http://prefix.api.com", config.uploadPrefix)
        assertEquals(5000, config.chunkSize)
        assertEquals(100, config.connectionTimeout)
        assertEquals(200, config.readTimeout)

        val copy = config.copy(readTimeout = 500)

        assertEquals("http://prefix.api.com", copy.uploadPrefix)
        assertEquals(5000, copy.chunkSize)
        assertEquals(500, copy.readTimeout)
        assertEquals(100, copy.connectionTimeout)
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
        assertEquals(null, config.secureCname)
        assertEquals(false, config.privateCdn)
        assertEquals(false, config.shorten)
        assertEquals(false, config.secureCdnSubdomain)
        assertEquals(false, config.useRootPath)
        assertEquals(null, config.cname)
        assertEquals(true, config.secure)
    }

    @Test
    fun testUrlConfig() {
        val secureDistribution = "secure.api.com"
        val cname = "my.domain.com"

        val config = UrlConfig(
            secureDistribution = secureDistribution,
            privateCdn = true,
            shorten = true,
            secureCdnSubdomain = true,
            useRootPath = true,
            cname = cname,
            secure = true
        )

        assertEquals(secureDistribution, config.secureDistribution)
        assertEquals(true, config.privateCdn)
        assertEquals(true, config.shorten)
        assertEquals(true, config.secureCdnSubdomain)
        assertEquals(true, config.useRootPath)
        assertEquals(cname, config.cname)
        assertEquals(true, config.secure)

        val copy = config.copy(
            secureDistribution = "copy.secure.distribution",
            shorten = false
        )

        assertEquals("copy.secure.distribution", copy.secureDistribution)
        assertEquals(true, copy.privateCdn)
        assertEquals(false, copy.shorten)
        assertEquals(true, copy.secureCdnSubdomain)
        assertEquals(true, copy.useRootPath)
        assertEquals(cname, copy.cname)
        assertEquals(true, copy.secure)
    }

    @Test
    fun testSecureCname() {
        val secureCname = "secure.api.com"

        val config = UrlConfig(
            secureCname = secureCname
        )

        assertEquals(secureCname, config.secureCname)

        val copy = config.copy(
            secureCname = "copy.secure.distribution",
            shorten = false
        )

        assertEquals("copy.secure.distribution", copy.secureCname)
    }
}