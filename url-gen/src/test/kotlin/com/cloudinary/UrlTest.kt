package com.cloudinary

import com.cloudinary.asset.Asset
import com.cloudinary.config.CloudinaryConfig
import com.cloudinary.config.UrlConfig
import com.cloudinary.transformation.Format
import com.cloudinary.transformation.Rotate
import com.cloudinary.transformation.gravity.AutoFocus
import com.cloudinary.transformation.gravity.FocusOn
import com.cloudinary.transformation.gravity.Gravity
import com.cloudinary.transformation.resize.Resize
import org.junit.Assert.*
import org.junit.Test
import java.util.regex.Pattern

private const val DEFAULT_ROOT_PATH = "https://res.cloudinary.com/test123/"
private const val DEFAULT_UPLOAD_PATH = DEFAULT_ROOT_PATH + "image/upload/"

class UrlTest {
    private val cloudinary = Cloudinary("cloudinary://a:b@test123?analytics=false")
    private val cloudinaryPrivateCdn = Cloudinary(
        cloudinary.config.copy(
            urlConfig = cloudinary.config.urlConfig.copy(
                privateCdn = true
            )
        )
    )

    private val cloudinaryPrivateCdnUseRootPath = Cloudinary(
        cloudinary.config.copy(
            urlConfig = cloudinary.config.urlConfig.copy(
                privateCdn = true, useRootPath = true
            )
        )
    )

    private val cloudinaryPrivateCdnSignUrl = Cloudinary(
        cloudinary.config.copy(
            urlConfig = cloudinary.config.urlConfig.copy(
                privateCdn = true, signUrl = true
            )
        )
    )

    private val cloudinarySignedUrl =
        Cloudinary(cloudinary.config.copy(urlConfig = cloudinary.config.urlConfig.copy(signUrl = true)))
    private val cloudinaryLongSignedUrl =
        Cloudinary(cloudinary.config.copy(urlConfig = cloudinary.config.urlConfig.copy(secure = false, signUrl = true, signatureAlgorithm = "SHA-256")))


    @Test
    fun testConfigValues() {

        val urlConfig = UrlConfig(
            secureDistribution = "secure.api.com",
            privateCdn = true,
            shorten = true,
            secureCdnSubdomain = true,
            useRootPath = true,
            cname = "my.domain.org",
            secure = true,
            analytics = false
        )

        val cloudConfig = cloudinary.config.cloudConfig

        assertEquals("https://secure.api.com/sample", Asset(cloudConfig, urlConfig).generate("sample"))
        assertEquals(
            "http://my.domain.org/sample",
            Asset(cloudConfig, urlConfig.copy(secure = false)).generate("sample")
        )
    }

    @Test
    fun testUrlWithAnalytics() {
        val cloudinaryWithAnalytics =
            Cloudinary(cloudinary.config.copy(urlConfig = cloudinary.config.urlConfig.copy(analytics = true)))

        val result = cloudinaryWithAnalytics.image().generate("test")

        // note: This test validates the concatenation of analytics query param to the url.
        // This is not meant to test the string generation - This is tested separately in its own test.
        val expectedAnalytics = generateAnalyticsSignature()

        assertEquals("https://res.cloudinary.com/test123/image/upload/test?_a=$expectedAnalytics", result)

    }

    @Test
    fun testUrlSuffixWithDotOrSlash() {
        val errors = arrayOfNulls<Boolean>(4)
        try {
            cloudinary.image {
                urlSuffix("dsfdfd.adsfad")
            }.generate("publicId")
        } catch (e: IllegalArgumentException) {
            errors[0] = true
        }
        try {
            cloudinary.image {
                urlSuffix("dsfdfd/adsfad")
            }.generate("publicId")
        } catch (e: IllegalArgumentException) {
            errors[1] = true
        }
        try {
            cloudinary.image {
                urlSuffix("dsfd.fd/adsfad")
            }.generate("publicId")
        } catch (e: IllegalArgumentException) {
            errors[2] = true
        }
        try {
            cloudinary.image {
                urlSuffix("dsfdfdaddsfad")
            }.generate("publicId")
        } catch (e: IllegalArgumentException) {
            errors[3] = true
        }
        assertTrue(errors[0]!!)
        assertTrue(errors[1]!!)
        assertTrue(errors[2]!!)
        assertNull(errors[3])
    }

    @Test
    fun testCloudName() { // should use cloud_name from config
        val result = cloudinary.image().generate("test")
        assertEquals(DEFAULT_UPLOAD_PATH + "test", result)
    }

    @Test
    fun testCloudNameOptions() { // should allow overriding cloud_name in options
        val cloudinaryDifferentCloud =
            Cloudinary(cloudinary.config.copy(cloudConfig = cloudinary.config.cloudConfig.copy(cloudName = "test321")))
        val result = cloudinaryDifferentCloud.image {
        }.generate("test")
        assertEquals("https://res.cloudinary.com/test321/image/upload/test", result)
    }

    @Test
    fun testSecureDistribution() { // should use default secure distribution if secure=TRUE
        val cloudinarySecureFalse =
            Cloudinary(cloudinary.config.copy(urlConfig = cloudinary.config.urlConfig.copy(secure = false)))

        val result = cloudinarySecureFalse.image().generate("test")
        assertEquals("http://res.cloudinary.com/test123/image/upload/test", result)

        // should take secure distribution from config if secure=TRUE
        val newConfig =
            cloudinary.config.copy(urlConfig = cloudinary.config.urlConfig.copy(secureDistribution = "config.secure.distribution.com"))

        val result2 = Cloudinary(newConfig).image().generate("test")
        assertEquals("https://config.secure.distribution.com/test123/image/upload/test", result2)
    }

    @Test
    fun testSecureDistributionOverwrite() { // should allow overwriting secure distribution if secure=TRUE
        val cloudinarySecureDistribution =
            Cloudinary(cloudinary.config.copy(urlConfig = cloudinary.config.urlConfig.copy(secureDistribution = "something.else.com")))

        val result =
            cloudinarySecureDistribution.image().generate("test")
        assertEquals("https://something.else.com/test123/image/upload/test", result)
    }

    @Test
    fun testSecureAkamai() { // should default to akamai if secure is given with private_cdn and no
// secure_distribution
        val urlConfig = cloudinary.config.urlConfig.copy(secure = true, privateCdn = true)
        val config = cloudinary.config.copy(urlConfig = urlConfig)
        val result = Cloudinary(config).image().generate("test")
        assertEquals("https://test123-res.cloudinary.com/image/upload/test", result)
    }

    @Test
    fun testSecureNonAkamai() { // should not add cloud_name if private_cdn and secure non akamai
// secure_distribution
        val urlConfig = cloudinary.config.urlConfig.copy(
            secure = true,
            privateCdn = true,
            secureDistribution = "something.cloudfront.net"
        )
        val config = cloudinary.config.copy(urlConfig = urlConfig)
        val result = Cloudinary(config).image().generate("test")
        assertEquals("https://something.cloudfront.net/image/upload/test", result)
    }

    @Test
    fun testHttpPrivateCdn() { // should not add cloud_name if private_cdn and not secure
        val urlConfig = cloudinary.config.urlConfig.copy(privateCdn = true)
        val config = cloudinary.config.copy(urlConfig = urlConfig)
        val result = Cloudinary(config).image().generate("test")

        assertEquals("https://test123-res.cloudinary.com/image/upload/test", result)
    }

    @Test
    fun testExtension() { // should use format from options
        val result = cloudinary.image {
            extension(Format.jpg())
        }.generate("test")
        assertEquals(DEFAULT_UPLOAD_PATH + "test.jpg", result)
    }

    @Test
    fun testType() { // should use type from options
        var result = cloudinary.image {
            storageType("facebook")
        }.generate("test")
        assertEquals("https://res.cloudinary.com/test123/image/facebook/test", result)

        result = cloudinary.image {
            deliveryType("facebook")
        }.generate("test")
        assertEquals("https://res.cloudinary.com/test123/image/facebook/test", result)
    }

    @Test
    fun testResourceType() { // should use resource_type from options
        val result = cloudinary.raw().generate("test")
        assertEquals("https://res.cloudinary.com/test123/raw/upload/test", result)
    }

    @Test
    fun testIgnoreHttp() { // should ignore http links only if type is not given or is asset
        var result = cloudinary.image().generate("http://test")
        assertEquals("http://test", result)
        result = cloudinary.image {
            deliveryType("asset")
        }.generate("http://test")
        assertEquals("http://test", result)
        result = cloudinary.image {
            deliveryType("fetch")
        }.generate("http://test")
        assertEquals("https://res.cloudinary.com/test123/image/fetch/http://test", result)
    }

    @Test
    fun testFetch() { // should escape fetch urls
        var result = cloudinary.image {
            deliveryType("fetch")
        }.generate("http://blah.com/hello?a=b")
        assertEquals(
            "https://res.cloudinary.com/test123/image/fetch/http://blah.com/hello%3Fa%3Db",
            result
        )
    }

    @Test
    fun testCname() { // should support external cname
        val cloudinarySecureFalseWithCname = Cloudinary(
            cloudinary.config.copy(
                urlConfig = cloudinary.config.urlConfig.copy(
                    secure = false,
                    cname = "hello.com"
                )
            )
        )

        val result = cloudinarySecureFalseWithCname.image().generate("test")
        assertEquals("http://hello.com/test123/image/upload/test", result)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testDisallowUrlSuffixInNonUploadTypes() {
        cloudinaryPrivateCdn.image {
            urlSuffix("hello")
            deliveryType("facebook")
        }.generate("test")
    }

    @Test(expected = IllegalArgumentException::class)
    fun testDisallowUrlSuffixWithSlash() {
        cloudinaryPrivateCdn.image {
            urlSuffix("hello/world")
        }.generate("test")
    }

    @Test(expected = IllegalArgumentException::class)
    fun testDisallowUrlSuffixWithDot() {
        cloudinaryPrivateCdn.image {
            urlSuffix("hello.world")
        }.generate("test")
    }

    @Test
    fun testSupportUrlSuffixForPrivateCdn() {
        var actual = cloudinaryPrivateCdn.image {
            urlSuffix("hello")
        }.generate("test")
        assertEquals("https://test123-res.cloudinary.com/images/test/hello", actual)
        actual =
            cloudinaryPrivateCdn.image {
                urlSuffix("hello")
                rotate(Rotate.byAngle(0))
            }
                .generate("test")
        assertEquals("https://test123-res.cloudinary.com/images/a_0/test/hello", actual)
    }

    @Test
    fun testPutFormatAfterUrlSuffix() {
        val actual =
            cloudinaryPrivateCdn.image {
                urlSuffix("hello")
                extension(Format.jpg())
            }.generate("test")
        assertEquals("https://test123-res.cloudinary.com/images/test/hello.jpg", actual)
    }

    @Test
    fun testNotSignTheUrlSuffix() {
        val pattern = Pattern.compile("s--[0-9A-Za-z_-]{8}--")
        var url = cloudinarySignedUrl.image {
            extension(Format.jpg())
        }.generate("test")!!
        var matcher = pattern.matcher(url)
        matcher.find()
        var expectedSignature = url.substring(matcher.start(), matcher.end())

        var actual =
            cloudinaryPrivateCdnSignUrl.image {
                extension(Format.jpg())
                urlSuffix("hello")
            }.generate("test")

        assertEquals(
            "https://test123-res.cloudinary.com/images/$expectedSignature/test/hello.jpg",
            actual
        )

        url = cloudinarySignedUrl.image {
            extension(Format.jpg())
            rotate(Rotate.byAngle(0))
        }.generate("test")!!
        matcher = pattern.matcher(url)
        matcher.find()
        expectedSignature = url.substring(matcher.start(), matcher.end())
        actual = cloudinaryPrivateCdnSignUrl.image {
            extension(Format.jpg())
            urlSuffix("hello")
            rotate(Rotate.byAngle(0))
        }.generate("test")
        assertEquals(
            "https://test123-res.cloudinary.com/images/$expectedSignature/a_0/test/hello.jpg",
            actual
        )
    }

    @Test
    fun testSupportUrlSuffixForRawUploads() {
        val actual =
            cloudinaryPrivateCdn.raw {
                urlSuffix("hello")
            }.generate("test")
        assertEquals("https://test123-res.cloudinary.com/files/test/hello", actual)
    }

    @Test
    fun testSupportUrlSuffixForVideoUploads() {
        val actual =
            cloudinaryPrivateCdn.video {
                urlSuffix("hello")
            }.generate("test")
        assertEquals("https://test123-res.cloudinary.com/videos/test/hello", actual)
    }

    @Test
    fun testSupportUrlSuffixForAuthenticatedImages() {
        var actual =
            cloudinaryPrivateCdn.image {
                urlSuffix("hello")
                deliveryType("authenticated")
            }
                .generate("test")
        assertEquals("https://test123-res.cloudinary.com/authenticated_images/test/hello", actual)
    }

    @Test
    fun testSupportUrlSuffixForPrivateImages() {
        var actual =
            cloudinaryPrivateCdn.image {
                urlSuffix("hello")
                deliveryType("private")
            }
                .generate("test")
        assertEquals("https://test123-res.cloudinary.com/private_images/test/hello", actual)
    }

    @Test
    fun testSupportUseRootPathForPrivateCdn() {
        var actual = cloudinaryPrivateCdnUseRootPath.image().generate("test")
        assertEquals("https://test123-res.cloudinary.com/test", actual)
        actual =
            cloudinaryPrivateCdnUseRootPath.image {
                rotate(Rotate.byAngle(0))
            }.generate("test")
        assertEquals("https://test123-res.cloudinary.com/a_0/test", actual)
    }

    @Test
    fun testSupportUseRootPathTogetherWithUrlSuffixForPrivateCdn() {
        val actual = cloudinaryPrivateCdnUseRootPath.image {
            urlSuffix("hello")
        }.generate("test")
        assertEquals("https://test123-res.cloudinary.com/test/hello", actual)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testDisallowUseRootPathIfNotImageUploadForFacebook() {
        cloudinaryPrivateCdnUseRootPath.image {
            deliveryType("facebook")
        }.generate("test")
    }

    @Test(expected = IllegalArgumentException::class)
    fun testDisallowUseRootPathIfNotImageUploadForRaw() {
        cloudinaryPrivateCdnUseRootPath.raw().generate("test")
    }

    @Test
    fun testHttpEscape() { // should escape http urls
        var result =
            cloudinary.image {
                deliveryType("youtube")
            }.generate("http://www.youtube.com/watch?v=d9NF2edxy-M")
        assertEquals(
            "https://res.cloudinary.com/test123/image/youtube/http://www.youtube.com/watch%3Fv%3Dd9NF2edxy-M",
            result
        )
    }

    @Test
    fun testFolders() { // should add version if public_id contains /
        var result = cloudinary.image().generate("folder/test")
        assertEquals(DEFAULT_UPLOAD_PATH + "v1/folder/test", result)
        result = cloudinary.image {
            version("123")
        }.generate("folder/test")
        assertEquals(
            DEFAULT_UPLOAD_PATH + "v123/folder/test",
            result
        )
    }

    @Test
    fun testFoldersWithExcludeVersion() { // should not add version if the user turned off forceVersion
        val cloudinaryForceVersionFalse =
            Cloudinary(cloudinary.config.copy(urlConfig = cloudinary.config.urlConfig.copy(forceVersion = false)))
        var result = cloudinaryForceVersionFalse.image {
        }.generate("folder/test")
        assertEquals(DEFAULT_UPLOAD_PATH + "folder/test", result)
        // should still show explicit version if passed by the user
        result = cloudinaryForceVersionFalse.image {
            version("1234")
        }.generate("folder/test")
        assertEquals(
            DEFAULT_UPLOAD_PATH + "v1234/folder/test",
            result
        )
        // should add version if no value specified for forceVersion:
        result = cloudinary.image().generate("folder/test")
        assertEquals(DEFAULT_UPLOAD_PATH + "v1/folder/test", result)
        // should not use v1 if explicit version is passed
        result = cloudinaryForceVersionFalse.image {
            version("1234")
        }.generate("folder/test")
        assertEquals(
            DEFAULT_UPLOAD_PATH + "v1234/folder/test",
            result
        )
    }

    @Test
    fun testFoldersWithVersion() { // should not add version if public_id contains version already
        val result = cloudinary.image().generate("v1234/test")
        assertEquals(DEFAULT_UPLOAD_PATH + "v1234/test", result)
    }

    @Test
    fun testShorten() { // should allow to shorted image/upload urls
        val cloudinaryShortenTrue =
            Cloudinary(cloudinary.config.copy(urlConfig = cloudinary.config.urlConfig.copy(shorten = true)))

        val result = cloudinaryShortenTrue.image().generate("test")
        assertEquals("https://res.cloudinary.com/test123/iu/test", result)
    }

    @Test
    fun testEscapePublicId() { // should escape public_ids
        val tests = mapOf("a b" to "a%20b", "a+b" to "a%2Bb", "a%20b" to "a%20b", "a-b" to "a-b", "a??b" to "a%3F%3Fb")
        for ((key, value) in tests) {
            val result = cloudinary.image().generate(key)
            assertEquals(
                DEFAULT_UPLOAD_PATH + "" + value,
                result
            )
        }
    }

    @Test
    fun testSignedUrl() { // should correctly sign a url
        var expected: String =
            DEFAULT_UPLOAD_PATH + "s--Ai4Znfl3--/c_crop,h_20,w_10/v1234/image.jpg"

        var actual =
            cloudinarySignedUrl.image {
                version("1234")
                resize(Resize.crop { width(10); height(20) })
            }.generate("image.jpg")

        assertEquals(expected, actual)

        expected = DEFAULT_UPLOAD_PATH + "s----SjmNDA--/v1234/image.jpg"

        actual = cloudinarySignedUrl.image {
            version("1234")
        }.generate("image.jpg")

        assertEquals(expected, actual)

        expected = DEFAULT_UPLOAD_PATH + "s--Ai4Znfl3--/c_crop,h_20,w_10/image.jpg"

        actual = cloudinarySignedUrl.image {
            resize(Resize.crop { width(10); height(20) })
        }.generate("image.jpg")

        assertEquals(expected, actual)
        expected = "http://res.cloudinary.com/test123/image/upload/s--2hbrSMPO--/sample.jpg"

        actual =
            cloudinaryLongSignedUrl.image {
            }.generate("sample.jpg")
        assertEquals(expected, actual)
    }

    @Test
    fun testCloudinaryUrlValidScheme() {
        val cloudinaryUrl = "cloudinary://123456789012345:ALKJdjklLJAjhkKJ45hBK92baj3@test"
        CloudinaryConfig.fromUri(cloudinaryUrl)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testCloudinaryUrlInvalidScheme() {
        val cloudinaryUrl = "https://123456789012345:ALKJdjklLJAjhkKJ45hBK92baj3@test"
        CloudinaryConfig.fromUri(cloudinaryUrl)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testCloudinaryUrlEmptyScheme() {
        val cloudinaryUrl = " "
        CloudinaryConfig.fromUri(cloudinaryUrl)
    }

    @Test
    fun testOCR() {
        val tAsset = cloudinary.image {
            publicId("sample")
            resize(Resize.crop() {
                gravity(
                    Gravity.autoGravity() {
                        autoFocus(
                            AutoFocus.focusOn(
                                FocusOn.ocr()
                            )
                        )
                    })
            })
        }.generate()
        assertNotNull(tAsset)
        tAsset?.let { cldAssert(it, "https://res.cloudinary.com/test123/image/upload/c_crop,g_auto:ocr_text/sample") }
    }
}