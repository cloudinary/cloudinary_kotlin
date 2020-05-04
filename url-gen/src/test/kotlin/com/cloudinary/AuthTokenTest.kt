package com.cloudinary

import com.cloudinary.config.Configuration
import com.cloudinary.transformation.resize.Resize
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.*
import java.util.regex.Pattern

class AuthTokenTest {
    private val authToken = AuthToken(key = KEY, duration = 300, startTime = 11111111)
    private val tempConfig = Configuration.fromUri("cloudinary://a:b@test123")
    private val cloudinary = Cloudinary(tempConfig.copy(urlConfig = tempConfig.urlConfig.copy(authToken = authToken)))

    @Test
    @Throws(Exception::class)
    fun generateWithStartAndDuration() {
        val t = AuthToken(key = KEY, startTime = 1111111111, acl = "/image/*", duration = 300)
        assertEquals(
            "should generate an authorization token with startTime and duration",
            "__cld_token__=st=1111111111~exp=1111111411~acl=%2fimage%2f*~hmac=1751370bcc6cfe9e03f30dd1a9722ba0f2cdca283fa3e6df3342a00a7528cc51",
            t.generate()
        )
    }

    @Test
    @Throws(Exception::class)
    fun generateWithDuration() {
        val firstExp = Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis / 1000L + 300
        Thread.sleep(1200)
        val token: String =
            AuthToken(key = KEY, acl = "*", duration = 300).generate()
        Thread.sleep(1200)
        val secondExp = Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis / 1000L + 300
        val m = Pattern.compile("exp=(\\d+)").matcher(token)
        assertTrue(m.find())
        val expString = m.group(1)
        val actual = expString.toLong()
        assertTrue(actual >= firstExp)
        assertTrue(actual <= secondExp)
        assertEquals(
            token,
            AuthToken(key = KEY, acl = "*", expiration = actual).generate()
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun testMustProvideExpirationOrDuration() {
        AuthToken(key = KEY, acl = "*").generate()
    }

    @Test
    fun testAuthenticatedUrl() {
        val cloudinary =
            Cloudinary(cloudinary.config.copy(urlConfig = cloudinary.config.urlConfig.copy(privateCdn = true)))
        var message = "should add token if authToken is globally set and signed = true"
        var url =
            cloudinary.url {
                signUrl(true)
                resourceType("image")
                type("authenticated")
                version("1486020273")
            }.generate("sample.jpg")
        assertEquals(
            message,
            "http://test123-res.cloudinary.com/image/authenticated/v1486020273/sample.jpg?__cld_token__=st=11111111~exp=11111411~hmac=8db0d753ee7bbb9e2eaf8698ca3797436ba4c20e31f44527e43b6a6e995cfdb3",
            url
        )
        message = "should add token for 'public' resource"
        url = cloudinary.url {
            signUrl(true)
            resourceType("image")
            type("public")
            version("1486020273")
        }.generate("sample.jpg")
        assertEquals(
            message,
            "http://test123-res.cloudinary.com/image/public/v1486020273/sample.jpg?__cld_token__=st=11111111~exp=11111411~hmac=c2b77d9f81be6d89b5d0ebc67b671557e88a40bcf03dd4a6997ff4b994ceb80e",
            url
        )
        message = "should not add token if signed is false"
        url =
            cloudinary.url {
                resourceType("image")
                type("authenticated")
                version("1486020273")
            }
                .generate("sample.jpg")
        assertEquals(
            message,
            "http://test123-res.cloudinary.com/image/authenticated/v1486020273/sample.jpg",
            url
        )
        message =
            "should not add token if authToken is globally set but null auth token is explicitly set and signed = true"
        url = cloudinary.url {
            authToken(NULL_AUTH_TOKEN)
            signUrl(true)
            resourceType("image")
            type("authenticated")
            version("1486020273")
        }.generate("sample.jpg")
        assertEquals(
            message,
            "http://test123-res.cloudinary.com/image/authenticated/s--v2fTPYTu--/v1486020273/sample.jpg",
            url
        )
        message = "explicit authToken should override global setting"
        url = cloudinary.url {
            signUrl(true)
            authToken(AuthToken(key = ALT_KEY, startTime = 222222222, duration = 100))
            resourceType("image")
            type("authenticated")
            resize(Resize.scale {
                width(300)
            })
        }.generate("sample.jpg")
        assertEquals(
            message,
            "http://test123-res.cloudinary.com/image/authenticated/c_scale,w_300/sample.jpg?__cld_token__=st=222222222~exp=222222322~hmac=55cfe516530461213fe3b3606014533b1eca8ff60aeab79d1bb84c9322eebc1f",
            url
        )
        message = "should compute expiration as start time + duration"
        url = cloudinary.url {
            signUrl(true)
            authToken(AuthToken(key = KEY, startTime = 11111111, duration = 300))
            type("authenticated")
            version("1486020273")
        }.generate("sample.jpg")
        assertEquals(
            message,
            "http://test123-res.cloudinary.com/image/authenticated/v1486020273/sample.jpg?__cld_token__=st=11111111~exp=11111411~hmac=8db0d753ee7bbb9e2eaf8698ca3797436ba4c20e31f44527e43b6a6e995cfdb3",
            url
        )
    }

    @Test
    fun testConfiguration() {
        val cloudinary =
            Cloudinary("cloudinary://a:b@test123?load_strategies=false&auth_token[key]=aabbcc112233&auth_token[duration]=200")
        assertEquals(cloudinary.config.authToken, AuthToken(key = "aabbcc112233", duration = 200))
    }

    @Test
    fun testTokenGeneration() {
        val user = "foobar" // username taken from elsewhere
        val token = AuthToken(key = KEY, duration = 300, acl = "/*/t_$user", startTime = 222222222)

        val cookieToken = token.generate()
        assertEquals(
            "__cld_token__=st=222222222~exp=222222522~acl=%2f*%2ft_foobar~hmac=8e39600cc18cec339b21fe2b05fcb64b98de373355f8ce732c35710d8b10259f",
            cookieToken
        )
    }

    @Test
    fun testIgnoreUrlIfAclIsProvided() {
        val user = "foobar" // username taken from elsewhere
        val token =
            AuthToken(key = KEY, duration = 300, acl = "/*/t_$user", startTime = 222222222)
        val cookieToken = token.generate()
        val aclToken =
            AuthToken(key = KEY, duration = 300, acl = "/*/t_$user", startTime = 222222222)
        val cookieAclToken =
            aclToken.generate("http://res.cloudinary.com/test123/image/upload/v1486020273/sample.jpg")
        assertEquals(cookieToken, cookieAclToken)
    }

    companion object {
        const val KEY = "00112233FF99"
        const val ALT_KEY = "CCBB2233FF00"
    }
}