package com.cloudinary

import com.cloudinary.config.Configuration
import com.cloudinary.transformation.Format
import com.cloudinary.transformation.resize.Resize
import org.junit.Assert.*
import org.junit.Test
import java.util.regex.Pattern

private const val DEFAULT_ROOT_PATH = "http://res.cloudinary.com/test123/"
private const val DEFAULT_UPLOAD_PATH = DEFAULT_ROOT_PATH + "image/upload/"

class UrlTest {
    private val cloudinary = Cloudinary("cloudinary://a:b@test123")


    @Test
    fun testUrlSuffixWithDotOrSlash() {
        val errors = arrayOfNulls<Boolean>(4)
        try {
            cloudinary.url {
                urlSuffix("dsfdfd.adsfad")
            }.generate("publicId")
        } catch (e: IllegalArgumentException) {
            errors[0] = true
        }
        try {
            cloudinary.url {
                urlSuffix("dsfdfd/adsfad")
            }.generate("publicId")
        } catch (e: IllegalArgumentException) {
            errors[1] = true
        }
        try {
            cloudinary.url {
                urlSuffix("dsfd.fd/adsfad")
            }.generate("publicId")
        } catch (e: IllegalArgumentException) {
            errors[2] = true
        }
        try {
            cloudinary.url {
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
        val result = cloudinary.url().generate("test")
        assertEquals(DEFAULT_UPLOAD_PATH + "test", result)
    }

    @Test
    fun testCloudNameOptions() { // should allow overriding cloud_name in options
        val result = cloudinary.url {
            cloudName("test321")
        }.generate("test")
        assertEquals("http://res.cloudinary.com/test321/image/upload/test", result)
    }

    @Test
    fun testSecureDistribution() { // should use default secure distribution if secure=TRUE
        val result = cloudinary.url {
            secure(true)
        }.generate("test")
        assertEquals("https://res.cloudinary.com/test123/image/upload/test", result)
    }

    @Test
    fun testSecureDistributionOverwrite() { // should allow overwriting secure distribution if secure=TRUE
        val result =
            cloudinary.url {
                secure(true)
                secureDistribution("something.else.com")
            }.generate("test")
        assertEquals("https://something.else.com/test123/image/upload/test", result)
    }

    @Test
    fun testSecureDistibution() { // should take secure distribution from config if secure=TRUE
        val newConfig =
            cloudinary.config.copy(urlConfig = cloudinary.config.urlConfig.copy(secureDistribution = "config.secure.distribution.com"))

        val result = Cloudinary(newConfig).url {
            secure(true)
        }.generate("test")
        assertEquals("https://config.secure.distribution.com/test123/image/upload/test", result)
    }

    @Test
    fun testSecureAkamai() { // should default to akamai if secure is given with private_cdn and no
// secure_distribution
        val urlConfig = cloudinary.config.urlConfig.copy(secure = true, privateCdn = true)
        val config = cloudinary.config.copy(urlConfig = urlConfig)
        val result = Cloudinary(config).url().generate("test")
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
        val result = Cloudinary(config).url().generate("test")
        assertEquals("https://something.cloudfront.net/image/upload/test", result)
    }

    @Test
    fun testHttpPrivateCdn() { // should not add cloud_name if private_cdn and not secure
        val urlConfig = cloudinary.config.urlConfig.copy(privateCdn = true)
        val config = cloudinary.config.copy(urlConfig = urlConfig)
        val result = Cloudinary(config).url().generate("test")

        assertEquals("http://test123-res.cloudinary.com/image/upload/test", result)
    }

    @Test
    fun testFormat() { // should use format from options
        val result = cloudinary.url {
            format(Format.jpg())
        }.generate("test")
        assertEquals(DEFAULT_UPLOAD_PATH + "test.jpg", result)
    }

    @Test
    fun testType() { // should use type from options
        val result = cloudinary.url {
            type("facebook")
        }.generate("test")
        assertEquals("http://res.cloudinary.com/test123/image/facebook/test", result)
    }

    @Test
    fun testResourceType() { // should use resource_type from options
        val result = cloudinary.url {
            resourceType("raw")
        }.generate("test")
        assertEquals("http://res.cloudinary.com/test123/raw/upload/test", result)
    }

    @Test
    fun testIgnoreHttp() { // should ignore http links only if type is not given or is asset
        var result = cloudinary.url().generate("http://test")
        assertEquals("http://test", result)
        result = cloudinary.url {
            type("asset")
        }.generate("http://test")
        assertEquals("http://test", result)
        result = cloudinary.url {
            type("fetch")
        }.generate("http://test")
        assertEquals("http://res.cloudinary.com/test123/image/fetch/http://test", result)
    }

    @Test
    fun testFetch() { // should escape fetch urls
        val result = cloudinary.url {
            type("fetch")
        }.generate("http://blah.com/hello?a=b")
        assertEquals(
            "http://res.cloudinary.com/test123/image/fetch/http://blah.com/hello%3Fa%3Db",
            result
        )
    }

    @Test
    fun testCname() { // should support external cname
        val result = cloudinary.url {
            cname("hello.com")
        }.generate("test")
        assertEquals("http://hello.com/test123/image/upload/test", result)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testDisallowUrlSuffixInNonUploadTypes() {
        cloudinary.url {
            urlSuffix("hello")
            privateCdn(true)
            type("facebook")
        }.generate("test")
    }

    @Test(expected = IllegalArgumentException::class)
    fun testDisallowUrlSuffixWithSlash() {
        cloudinary.url {
            urlSuffix("hello/world")
            privateCdn(true)
        }.generate("test")
    }

    @Test(expected = IllegalArgumentException::class)
    fun testDisallowUrlSuffixWithDot() {
        cloudinary.url {
            urlSuffix("hello.world")
            privateCdn(true)
        }.generate("test")
    }

    @Test
    fun testSupportUrlSuffixForPrivateCdn() {
        var actual = cloudinary.url {
            urlSuffix("hello")
            privateCdn(true)
        }.generate("test")
        assertEquals("http://test123-res.cloudinary.com/images/test/hello", actual)
        actual =
            cloudinary.url {
                urlSuffix("hello")
                privateCdn(true)
                rotate { angle(0) }
            }
                .generate("test")
        assertEquals("http://test123-res.cloudinary.com/images/a_0/test/hello", actual)
    }

    @Test
    fun testPutFormatAfterUrlSuffix() {
        val actual =
            cloudinary.url {
                urlSuffix("hello")
                privateCdn(true)
                format(Format.jpg())
            }.generate("test")
        assertEquals("http://test123-res.cloudinary.com/images/test/hello.jpg", actual)
    }

    @Test
    fun testNotSignTheUrlSuffix() {
        val pattern = Pattern.compile("s--[0-9A-Za-z_-]{8}--")
        var url = cloudinary.url {
            format(Format.jpg())
            signUrl(true)
        }.generate("test")!!
        var matcher = pattern.matcher(url)
        matcher.find()
        var expectedSignature = url.substring(matcher.start(), matcher.end())

        var actual =
            cloudinary.url {
                format(Format.jpg())
                privateCdn(true)
                signUrl(true)
                urlSuffix("hello")
            }.generate("test")

        assertEquals(
            "http://test123-res.cloudinary.com/images/$expectedSignature/test/hello.jpg",
            actual
        )

        url = cloudinary.url {
            format(Format.jpg())
            signUrl(true)
            rotate {
                angle(0)
            }
        }.generate("test")!!
        matcher = pattern.matcher(url)
        matcher.find()
        expectedSignature = url.substring(matcher.start(), matcher.end())
        actual = cloudinary.url {
            format(Format.jpg())
            privateCdn(true)
            signUrl(true)
            urlSuffix("hello")
            rotate { angle(0) }
        }.generate("test")
        assertEquals(
            "http://test123-res.cloudinary.com/images/$expectedSignature/a_0/test/hello.jpg",
            actual
        )
    }

    @Test
    fun testSupportUrlSuffixForRawUploads() {
        val actual =
            cloudinary.url {
                urlSuffix("hello")
                privateCdn(true)
                resourceType("raw")
            }.generate("test")
        assertEquals("http://test123-res.cloudinary.com/files/test/hello", actual)
    }

    @Test
    fun testSupportUrlSuffixForVideoUploads() {
        val actual =
            cloudinary.url {
                urlSuffix("hello")
                privateCdn(true)
                resourceType("video")
            }.generate("test")
        assertEquals("http://test123-res.cloudinary.com/videos/test/hello", actual)
    }

    @Test
    fun testSupportUrlSuffixForAuthenticatedImages() {
        val actual =
            cloudinary.url {
                urlSuffix("hello")
                privateCdn(true)
                resourceType("image")
                type("authenticated")
            }
                .generate("test")
        assertEquals("http://test123-res.cloudinary.com/authenticated_images/test/hello", actual)
    }

    @Test
    fun testSupportUrlSuffixForPrivateImages() {
        val actual =
            cloudinary.url {
                urlSuffix("hello")
                privateCdn(true)
                resourceType("image")
                type("private")
            }
                .generate("test")
        assertEquals("http://test123-res.cloudinary.com/private_images/test/hello", actual)
    }

    @Test
    fun testSupportUseRootPathForPrivateCdn() {
        var actual = cloudinary.url {
            privateCdn(true)
            useRootPath(true)
        }.generate("test")
        assertEquals("http://test123-res.cloudinary.com/test", actual)
        actual =
            cloudinary.url {
                privateCdn(true)
                rotate { angle(0) }
                useRootPath(true)
            }.generate("test")
        assertEquals("http://test123-res.cloudinary.com/a_0/test", actual)
    }

    @Test
    fun testSupportUseRootPathTogetherWithUrlSuffixForPrivateCdn() {
        val actual = cloudinary.url {
            privateCdn(true)
            urlSuffix("hello")
            useRootPath(true)
        }.generate("test")
        assertEquals("http://test123-res.cloudinary.com/test/hello", actual)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testDisallowUseRootPathIfNotImageUploadForFacebook() {
        cloudinary.url {
            useRootPath(true)
            privateCdn(true)
            type("facebook")
        }.generate("test")
    }

    @Test(expected = IllegalArgumentException::class)
    fun testDisallowUseRootPathIfNotImageUploadForRaw() {
        cloudinary.url {
            useRootPath(true)
            privateCdn(true)
            resourceType("raw")
        }.generate("test")
    }

    @Test
    fun testHttpEscape() { // should escape http urls
        val result =
            cloudinary.url {
                type("youtube")
            }.generate("http://www.youtube.com/watch?v=d9NF2edxy-M")
        assertEquals(
            "http://res.cloudinary.com/test123/image/youtube/http://www.youtube.com/watch%3Fv%3Dd9NF2edxy-M",
            result
        )
    }

    @Test
    fun testFetchFormat() { // should support format for fetch urls
        val result =
            cloudinary.url {
                format(Format.jpg())
                type("fetch")
            }.generate("http://cloudinary.com/images/old_logo.png")
        assertEquals(
            "http://res.cloudinary.com/test123/image/fetch/f_jpg/http://cloudinary.com/images/old_logo.png",
            result
        )
    }

    @Test
    fun testFolders() { // should add version if public_id contains /
        var result = cloudinary.url().generate("folder/test")
        assertEquals(DEFAULT_UPLOAD_PATH + "v1/folder/test", result)
        result = cloudinary.url {
            version("123")
        }.generate("folder/test")
        assertEquals(
            DEFAULT_UPLOAD_PATH + "v123/folder/test",
            result
        )
    }

    @Test
    fun testFoldersWithExcludeVersion() { // should not add version if the user turned off forceVersion
        var result = cloudinary.url {
            forceVersion(false)
        }.generate("folder/test")
        assertEquals(DEFAULT_UPLOAD_PATH + "folder/test", result)
        // should still show explicit version if passed by the user
        result = cloudinary.url {
            forceVersion(false)
            version("1234")
        }.generate("folder/test")
        assertEquals(
            DEFAULT_UPLOAD_PATH + "v1234/folder/test",
            result
        )
        // should add version if no value specified for forceVersion:
        result = cloudinary.url().generate("folder/test")
        assertEquals(DEFAULT_UPLOAD_PATH + "v1/folder/test", result)
        // should add version if forceVersion is true
        result = cloudinary.url {
            forceVersion(true)
        }.generate("folder/test")
        assertEquals(DEFAULT_UPLOAD_PATH + "v1/folder/test", result)
        // should not use v1 if explicit version is passed
        result = cloudinary.url {
            forceVersion(true)
            version("1234")
        }.generate("folder/test")
        assertEquals(
            DEFAULT_UPLOAD_PATH + "v1234/folder/test",
            result
        )
    }

    @Test
    fun testFoldersWithVersion() { // should not add version if public_id contains version already
        val result = cloudinary.url().generate("v1234/test")
        assertEquals(DEFAULT_UPLOAD_PATH + "v1234/test", result)
    }

    @Test
    fun testShorten() { // should allow to shorted image/upload urls
        val result = cloudinary.url {
            shorten(true)
        }.generate("test")
        assertEquals("http://res.cloudinary.com/test123/iu/test", result)
    }

    @Test
    fun testEscapePublicId() { // should escape public_ids
        val tests = mapOf("a b" to "a%20b", "a+b" to "a%2Bb", "a%20b" to "a%20b", "a-b" to "a-b", "a??b" to "a%3F%3Fb")
        for ((key, value) in tests) {
            val result = cloudinary.url().generate(key)
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
            cloudinary.url {
                version("1234")
                resize(Resize.crop { width(10); height(20) })
                signUrl(true)
            }.generate("image.jpg")
        assertEquals(expected, actual)
        expected = DEFAULT_UPLOAD_PATH + "s----SjmNDA--/v1234/image.jpg"
        actual = cloudinary.url {
            version("1234")
            signUrl(true)
        }.generate("image.jpg")
        assertEquals(expected, actual)
        expected = DEFAULT_UPLOAD_PATH + "s--Ai4Znfl3--/c_crop,h_20,w_10/image.jpg"
        actual = cloudinary.url {
            resize(Resize.crop { width(10); height(20) })
            signUrl(true)
        }.generate("image.jpg")
        assertEquals(expected, actual)
    }

    @Test
    fun testCloudinaryUrlValidScheme() {
        val cloudinaryUrl = "cloudinary://123456789012345:ALKJdjklLJAjhkKJ45hBK92baj3@test"
        Configuration.fromUri(cloudinaryUrl)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testCloudinaryUrlInvalidScheme() {
        val cloudinaryUrl = "https://123456789012345:ALKJdjklLJAjhkKJ45hBK92baj3@test"
        Configuration.fromUri(cloudinaryUrl)
    }

    @Test(expected = IllegalArgumentException::class)
    fun testCloudinaryUrlEmptyScheme() {
        val cloudinaryUrl = " "
        Configuration.fromUri(cloudinaryUrl)
    }
}