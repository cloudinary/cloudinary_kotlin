package com.cloudinary

import com.cloudinary.transformation.Rotate
import com.cloudinary.transformation.Transformation
import com.cloudinary.transformation.effect.Effect
import org.junit.Assert.*
import org.junit.Test

private const val DEFAULT_ROOT_PATH = "https://res.cloudinary.com/test123/"
private const val DEFAULT_UPLOAD_PATH = DEFAULT_ROOT_PATH + "image/upload/"

class UrlTest {
    private val cloudinary = Cloudinary("cloudinary://a:b@test123")

    @Test
    fun testBasicFunctionality() {
        val transformation = Transformation().effect(Effect.sepia { level(90) })
        val url =
            cloudinary.url(
                format = "jpg",
                type = "upload",
                resourceType = "image",
                transformation = transformation,
                publicId = "sample"
            )

        assertEquals("https://res.cloudinary.com/test123/image/upload/e_sepia:90/sample.jpg", url.generate())
    }

    @Test
    fun testUrlSuffix() {
        cloudinary.url()
    }

    @Test
    fun testUrlSuffixWithDotOrSlash() {
        val errors = BooleanArray(4)
        try {
            cloudinary.url(urlSuffix = "dsfdfd.adsfad").generate("publicId")
        } catch (e: IllegalArgumentException) {
            errors[0] = true
        }

        try {
            cloudinary.url(urlSuffix = "dsfdfd/adsfad").generate("publicId")
        } catch (e: IllegalArgumentException) {
            errors[1] = true
        }

        try {
            cloudinary.url(urlSuffix = "dsfd.fd/adsfad").generate("publicId")
        } catch (e: IllegalArgumentException) {
            errors[2] = true
        }

        try {
            cloudinary.url(urlSuffix = "dsfdfdaddsfad").generate("publicId")
        } catch (e: IllegalArgumentException) {
            errors[3] = true
        }

        assertTrue(errors[0])
        assertTrue(errors[1])
        assertTrue(errors[2])
        assertFalse(errors[3])
    }

    @Test
    fun testCloudName() {
        // should use cloud_name from config
        val result = cloudinary.url().generate("test")
        assertEquals(DEFAULT_UPLOAD_PATH + "test", result)
    }

    @Test
    fun testCloudNameOptions() {
        // should allow overriding cloud_name in options
        val result = cloudinary.url(cloudName = "test321").generate("test")
        assertEquals("https://res.cloudinary.com/test321/image/upload/test", result)
    }

    @Test
    fun testSecureDistribution() {
        // should use default secure distribution if secure=TRUE
        val result = cloudinary.url().generate("test")
        assertEquals("https://res.cloudinary.com/test123/image/upload/test", result)
    }

    @Test
    fun testSecureDistributionOverwrite() {
        // should allow overwriting secure distribution if secure=TRUE
        val result = cloudinary.url(secureDistribution = "something.else.com").generate("test")
        assertEquals("https://something.else.com/test123/image/upload/test", result)
    }

    @Test
    fun testSecureDistributionFromConfig() {
        // should take secure distribution from config if secure=TRUE
        val cld =
            Cloudinary(cloudinary.config.copy(urlConfig = cloudinary.config.urlConfig.copy(secureDistribution = "config.secure.distribution.com")))
        val result = cld.url().generate("test")
        assertEquals("https://config.secure.distribution.com/test123/image/upload/test", result)
    }

    @Test
    fun testSecureAkamai() {
        // should default to akamai if secure is given with private_cdn and no
        // secure_distribution
        val cld = Cloudinary(cloudinary.config.copy(urlConfig = cloudinary.config.urlConfig.copy(privateCdn = true)))
        val result = cld.url().generate("test")
        assertEquals("https://test123-res.cloudinary.com/image/upload/test", result)
    }

    @Test
    fun testSecureNonAkamai() {
        // should not add cloud_name if private_cdn and secure non akamai
        // secure_distribution
        val cld = Cloudinary(
            cloudinary.config.copy(
                urlConfig = cloudinary.config.urlConfig.copy(
                    privateCdn = true,
                    secureDistribution = "something.cloudfront.net"
                )
            )
        )
        val result = cld.url().generate("test")
        assertEquals("https://something.cloudfront.net/image/upload/test", result)
    }

    @Test
    fun testFormat() {
        // should use format from options
        val result = cloudinary.url(format = "jpg").generate("test")
        assertEquals(DEFAULT_UPLOAD_PATH + "test.jpg", result)
    }

    @Test
    fun testType() {
        // should use type from options
        val result = cloudinary.url(type = "facebook").generate("test")
        assertEquals("https://res.cloudinary.com/test123/image/facebook/test", result)
    }

    @Test
    fun testResourceType() {
        // should use resource_type from options
        val result = cloudinary.url(resourceType = "raw").generate("test")
        assertEquals("https://res.cloudinary.com/test123/raw/upload/test", result)
    }

    @Test
    fun testIgnoreHttp() {
        // should ignore http links only if type is not given or is asset
        var result = cloudinary.url().generate("https://test")
        assertEquals("https://test", result)
        result = cloudinary.url(type = "asset").generate("https://test")
        assertEquals("https://test", result)
        result = cloudinary.url(type = "fetch").generate("https://test")
        assertEquals("https://res.cloudinary.com/test123/image/fetch/https://test", result)
    }


    @Test
    fun testFetch() {
        // should escape fetch urls
        val result = cloudinary.url(type = "fetch").generate("https://blah.com/hello?a=b")
        assertEquals("https://res.cloudinary.com/test123/image/fetch/https://blah.com/hello%3Fa%3Db", result)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testDisallowUrlSuffixInNonUploadTypes() {
        cloudinary.url(urlSuffix = "hello", privateCdn = true, type = "facebook").generate("test")

    }

    @Test(expected = IllegalArgumentException::class)
    fun testDisallowUrlSuffixWithSlash() {
        cloudinary.url(urlSuffix = "hello/world", privateCdn = true).generate("test")
    }

    @Test(expected = IllegalArgumentException::class)
    fun testDisallowUrlSuffixWithDot() {
        cloudinary.url(urlSuffix = "hello.world", privateCdn = true).generate("test")
    }

    @Test
    fun testSupportUrlSuffixForPrivateCdn() {
        var actual = cloudinary.url(urlSuffix = "hello", privateCdn = true).generate("test")
        assertEquals("https://test123-res.cloudinary.com/images/test/hello", actual)

        actual =
            cloudinary.url(
                urlSuffix = "hello", privateCdn = true, transformation =
                Transformation().rotate { angle(0) }
            ).generate("test")
        assertEquals("https://test123-res.cloudinary.com/images/a_0/test/hello", actual)
    }

    @Test
    fun testPutFormatAfterUrlSuffix() {
        val actual = cloudinary.url(urlSuffix = "hello", privateCdn = true, format = "jpg").generate("test")
        assertEquals("https://test123-res.cloudinary.com/images/test/hello.jpg", actual)
    }

    @Test
    fun testSupportUrlSuffixForRawUploads() {
        val actual = cloudinary.url(urlSuffix = "hello", privateCdn = true, resourceType = "raw").generate("test")
        assertEquals("https://test123-res.cloudinary.com/files/test/hello", actual)
    }

    @Test
    fun testSupportUrlSuffixForVideoUploads() {
        val actual = cloudinary.url(urlSuffix = "hello", privateCdn = true, resourceType = "video").generate("test")
        assertEquals("https://test123-res.cloudinary.com/videos/test/hello", actual)
    }

    @Test
    fun testSupportUrlSuffixForAuthenticatedImages() {
        val actual =
            cloudinary.url(urlSuffix = "hello", privateCdn = true, resourceType = "image", type = "authenticated")
                .generate("test")
        assertEquals("https://test123-res.cloudinary.com/authenticated_images/test/hello", actual)
    }

    @Test
    fun testSupportUrlSuffixForPrivateImages() {
        val actual =
            cloudinary.url(urlSuffix = "hello", privateCdn = true, resourceType = "image", type = "private")
                .generate("test")
        assertEquals("https://test123-res.cloudinary.com/private_images/test/hello", actual)
    }

    @Test
    fun testSupportUseRootPathForPrivateCdn() {

        Transformation().rotate(Rotate.Builder().angle(12).mode(Rotate.Mode.IGNORE).build())
        Transformation().rotate {
            angle(40)
            mode(Rotate.Mode.HFLIP)
            angle(30)
        }

        var actual = cloudinary.url(privateCdn = true, useRootPath = true).generate("test")
        assertEquals("https://test123-res.cloudinary.com/test", actual)

        actual = cloudinary.url(
            privateCdn = true,
            transformation = Transformation().rotate { angle(0) },
            useRootPath = true
        )
            .generate("test")
        assertEquals("https://test123-res.cloudinary.com/a_0/test", actual)
    }

    @Test
    fun testSupportUseRootPathTogetherWithUrlSuffixForPrivateCdn() {

        val actual = cloudinary.url(privateCdn = true, urlSuffix = "hello", useRootPath = true).generate("test")
        assertEquals("https://test123-res.cloudinary.com/test/hello", actual)

    }

    @Test(expected = IllegalArgumentException::class)
    fun testDisllowUseRootPathIfNotImageUploadForFacebook() {
        cloudinary.url(useRootPath = true, privateCdn = true, type = "facebook").generate("test")
    }

    @Test(expected = IllegalArgumentException::class)
    fun testDisllowUseRootPathIfNotImageUploadForRaw() {
        cloudinary.url(useRootPath = true, privateCdn = true, resourceType = "raw").generate("test")
    }

    @Test
    fun testFoldersWithVersion() {
        // should not add version if public_id contains version already
        val result = cloudinary.url().generate("v1234/test")
        assertEquals(DEFAULT_UPLOAD_PATH + "v1234/test", result)
    }

    @Test
    fun testShorten() {
        // should allow to shorted image/upload urls
        val result = cloudinary.url(shorten = true).generate("test")
        assertEquals("https://res.cloudinary.com/test123/iu/test", result)
    }

    @Test
    fun testEscapePublicId() {
        // should escape public_ids
        val tests = mapOf("a b" to "a%20b", "a+b" to "a%2Bb", "a%20b" to "a%20b", "a-b" to "a-b", "a??b" to "a%3F%3Fb")
        for (entry in tests.entries) {
            val result = cloudinary.url().generate(entry.key)
            assertEquals(DEFAULT_UPLOAD_PATH + "" + entry.value, result)
        }
    }

}