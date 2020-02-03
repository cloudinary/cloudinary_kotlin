package com.cloudinary

import com.cloudinary.http.ApacheHttpClient45Factory
import com.cloudinary.http.HttpUrlConnectionFactory
import com.cloudinary.http.OkHttpClientFactory
import com.cloudinary.transformation.EagerTransformation
import com.cloudinary.transformation.Transformation
import com.cloudinary.transformation.effect.Effect
import com.cloudinary.transformation.resize.Resize
import com.cloudinary.transformation.resize.Resize.Companion.scale
import com.cloudinary.upload.Uploader
import com.cloudinary.upload.request.params.AccessControlRule
import com.cloudinary.upload.request.params.Coordinates
import com.cloudinary.upload.request.params.Rectangle
import com.cloudinary.upload.request.params.ResponsiveBreakpoint
import com.cloudinary.upload.response.UploadResult
import com.cloudinary.upload.response.UploaderResponse
import com.cloudinary.util.apiSignRequest
import com.cloudinary.util.asContextParam
import com.cloudinary.util.cldIsRemoteUrl
import com.cloudinary.util.randomPublicId
import org.junit.AfterClass
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.BeforeClass
import java.io.FileInputStream
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

private val contextForTest = mapOf("caption" to "some cäption", "alt" to "alternativè")
private val suffix = System.getenv("TRAVIS_JOB_ID") ?: Random().nextInt(99999)
private val contextTag = "context_tag_$suffix"
private val sdkTestTag = "cloudinary_kotlin_test_$suffix"
private val archiveTag = "${sdkTestTag}_archive"
private val uploaderTag = "${sdkTestTag}_uploader"
private val defaultTags = listOf(sdkTestTag, uploaderTag)
const val remoteTestImageUrl = "http://cloudinary.com/images/old_logo.png"

private val srcTestImage = UploaderTest::class.java.getResource("/old_logo.png").file
const val SRC_TEST_IMAGE_W = 241
const val SRC_TEST_IMAGE_H = 51


class UploaderTest {

    private val cloudinary = Cloudinary()
    private val uploader = cloudinary.uploader()

    companion object {
        @BeforeClass
        @JvmStatic
        fun setUpClass() {
            val cloudinary = Cloudinary()

            if (cloudinary.config.apiSecret == null) {
                System.err.println("Please setup environment for Upload test to run")
            }

            val setupTags = listOf(sdkTestTag, uploaderTag, archiveTag)

            cloudinary.uploader().upload(srcTestImage) {
                params {
                    tags = setupTags
                }
            }

            cloudinary.uploader().upload(srcTestImage) {
                params {
                    tags = setupTags
                }
                options {
                    resourceType = "raw"
                }
            }

            cloudinary.uploader().upload(srcTestImage) {
                params {
                    tags = setupTags
                    transformation { resize(scale { width(10) }) }
                }
            }
        }

        @AfterClass
        @JvmStatic
        fun tearDownClass() {
            // TODO cleanup w/o admin API
        }
    }

    @Test
    fun testUtf8Upload() {
        val response = cloudinary.uploader().upload(srcTestImage) {
            params {
                colors = true
                tags = defaultTags
                publicId = "aåßéƒ"
            }
        }

        Assert.assertNotNull(response.data)
        val result = response.data!!

        Assert.assertNotNull(result.publicId)
        assertEquals(result.width, SRC_TEST_IMAGE_W)
        assertEquals(result.height, SRC_TEST_IMAGE_H)
        Assert.assertNotNull(result.colors)
        Assert.assertNotNull(result.predominant)

        validateSignature(result)
    }

    @Test
    fun testUpload() {
        val response = cloudinary.uploader().upload(srcTestImage) {
            params {
                colors = true
                tags = defaultTags
            }
        }
        val result = response.data!!

        assertEquals(result.width, SRC_TEST_IMAGE_W)
        assertEquals(result.height, SRC_TEST_IMAGE_H)
        Assert.assertNotNull(result.colors)
        Assert.assertNotNull(result.predominant)
        validateSignature(result)
    }

    @Test
    fun testIsRemoteUrl() {
        val urls = arrayOf(
            "ftp://ftp.cloudinary.com/images/old_logo.png",
            "http://cloudinary.com/images/old_logo.png",
            "https://cloudinary.com/images/old_logo.png",
            "s3://s3-us-west-2.amazonaws.com/cloudinary/images/old_logo.png",
            "gs://cloudinary/images/old_logo.png",
            "data:image/gif;charset=utf8;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7",
            "data:image/gif;param1=value1;param2=value2;base64," +
                    "R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7"
        )

        for (url in urls) {
            Assert.assertTrue(url.cldIsRemoteUrl())
        }
        val invalidUrls = arrayOf("adsadasdasdasd", "     ", "")
        for (url in invalidUrls) {
            assertFalse(url.cldIsRemoteUrl())
        }
    }

    @Test
    fun testUploadUrl() {
        val response = cloudinary.uploader().upload(remoteTestImageUrl) {
            params {
                colors = true
                tags = defaultTags
            }
        }

        val result = response.data!!
        assertEquals(result.width, SRC_TEST_IMAGE_W)
    }

    @Test
    fun testUploadLargeUrl() {
        val response = cloudinary.uploader().uploadLarge(remoteTestImageUrl) {
            params {
                tags = defaultTags
            }
        }

        val result = response.data!!
        assertEquals(result.width, SRC_TEST_IMAGE_W)
        assertEquals(result.height, SRC_TEST_IMAGE_H)
        validateSignature(result)
    }

    @Test
    fun testUploadDataUri() {
        val data =
            "data:image/png;base64,iVBORw0KGgoAA\nAANSUhEUgAAABAAAAAQAQMAAAAlPW0iAAAABlBMVEUAAAD///+l2Z/dAAAAM0l\nEQVR4nGP4/5/h/1+G/58ZDrAz3D/McH8yw83NDDeNGe4Ug9C9zwz3gVLMDA/A6\nP9/AFGGFyjOXZtQAAAAAElFTkSuQmCC"

        val response = cloudinary.uploader().upload(data) {
            params {
                tags = defaultTags
            }
        }

        val result = response.data!!

        assertEquals(result.width, 16)
        assertEquals(result.height, 16)
        validateSignature(result)
    }

    @Test
    fun testUploadUTF8() {
        val response = cloudinary.uploader().upload(srcTestImage) {
            params {
                publicId = "Plattenkreiss_ñg-é"
                tags = defaultTags
            }
        }

        assertEquals(response.data?.publicId, "Plattenkreiss_ñg-é")
    }

    @Test
    fun testUniqueFilename() {
        var response = cloudinary.uploader().upload(srcTestImage) {
            params {
                useFilename = true
                tags = defaultTags
            }
        }

        var result = response.data!!
        assertTrue(result.publicId!!.matches("old_logo_[a-z0-9]{6}".toRegex()))

        response = cloudinary.uploader().upload(srcTestImage) {
            params {
                useFilename = true
                uniqueFilename = false
                tags = defaultTags
            }
        }

        result = response.data!!
        assertEquals(result.publicId, "old_logo")
    }

    @Test
    fun testEager() {
        val transformation = Transformation().resize(scale { width(2.0) })
        val response = cloudinary.uploader().upload(srcTestImage) {
            params {
                eager = listOf(EagerTransformation(transformation, "png"))
                tags = defaultTags
            }
        }

        val result = response.data!!
        val eager = result.eager?.first()
        assertNotNull(eager)
        assertEquals("png", eager.format)
        assertEquals("c_scale,w_2.0/png", eager.transformation)
    }

    @Test
    fun testUploadAsync() {
        val transformation = Transformation().resize(scale { width(2.0) })

        val response = cloudinary.uploader().upload(srcTestImage) {
            params {
                this.transformation = transformation
                async = true
                tags = defaultTags
            }
        }
        val result = response.data!!

        assertEquals(result.status, "pending")
    }

    @Test
    fun testHeaders() {
        // TODO remove? this test is not relevant anymore
        cloudinary.uploader().upload(srcTestImage) {
            params {
                tags = defaultTags
            }
            options {
                headers = mapOf("Link" to "1")
            }
        }
    }

    @Test
    fun testAllowedFormats() {
        //should allow whitelisted formats if allowed_formats
        val formats = listOf("png")
        val response = cloudinary.uploader().upload(srcTestImage) {
            params {
                allowedFormats = formats
                tags = defaultTags
            }
        }

        val result = response.data!!
        assertEquals(result.format, "png")
    }

    @Test
    fun testAllowedFormatsWithIllegalFormat() {
        //should prevent non whitelisted formats from being uploaded if allowed_formats is specified
        val formats = listOf("jpg")

        val response = cloudinary.uploader().upload(srcTestImage) {
            params {
                allowedFormats = formats
                tags = defaultTags
            }
        }

        assertNull(response.data)
        assertNotNull(response.error)
    }

    @Test
    fun testAllowedFormatsWithFormat() {
        //should allow non whitelisted formats if type is specified and convert to that type
        val formats = listOf("jpg")

        val response = cloudinary.uploader().upload(srcTestImage) {
            params {
                allowedFormats = formats
                format = "jpg"
                tags = defaultTags
            }
        }

        val result = response.data!!
        assertEquals("jpg", result.format)
    }

    @Test
    fun testFaceCoordinates() { //should allow sending face coordinates
        val rect1 = Rectangle(121, 31, 110, 51)
        val rect2 = Rectangle(120, 30, 109, 51)
        val coordinates = Coordinates(rect1, rect2)
        val response = cloudinary.uploader().upload(srcTestImage) {
            params {
                faceCoordinates = coordinates
                faces = true
                tags = defaultTags
            }
        }

        val result = response.data!!

        val resultFaces = result.faces!!
        assertEquals(2, resultFaces.coordinates.size)
        assertEquals(rect1, resultFaces.coordinates[0])
        assertEquals(rect2, resultFaces.coordinates[1])
    }

    @Test
    fun testCustomCoordinates() {
        //should allow sending face coordinates
        val coordinates = Coordinates("121,31,300,151")
        val response = cloudinary.uploader().upload(srcTestImage) {
            params {
                customCoordinates = coordinates
                tags = defaultTags
            }
        }

        val result = response.data!!
        assertEquals(
            Coordinates(Rectangle(121, 31, SRC_TEST_IMAGE_W, SRC_TEST_IMAGE_H)),
            result.coordinates?.values?.first()
        )
    }

    @Test
    fun testModerationRequest() {
        //should support requesting manual moderation
        val response = cloudinary.uploader().upload(srcTestImage) {
            params {
                moderation = "manual"
                tags = defaultTags
            }
        }

        val result = response.data!!

        assertEquals("manual", result.moderation?.first()?.kind)
        assertEquals("pending", result.moderation?.first()?.status)
    }

    @Test
    fun testRawConvertRequest() {
        //should support requesting raw conversion
        val response = cloudinary.uploader().upload(srcTestImage) {
            params {
                rawConvert = "illegal"
                tags = defaultTags
            }
        }

        assertErrorMessage("Raw convert is invalid", response)
    }

    @Test
    fun testCategorizationRequest() {
        //should support requesting categorization
        val response = cloudinary.uploader().upload(srcTestImage) {
            params {
                categorization = "illegal"
                tags = defaultTags
            }
        }

        assertErrorMessage("Categorization item illegal is not valid", response)
    }

    @Test
    fun testDetectionRequest() {
        //should support requesting detection
        val response = cloudinary.uploader().upload(srcTestImage) {
            params {
                detection = "illegal"
                tags = defaultTags
            }
        }

        assertErrorMessage("Detection is invalid", response)
    }

    @Test
    fun testUploadLarge() {
        // support uploading large files
        val temp = generateFile()
        assertEquals(5880138, temp.length())

        val uploadLargeTags = listOf("upload_large_tag_$suffix", sdkTestTag, uploaderTag)

        var response = cloudinary.uploader().uploadLarge(temp) {
            params {
                useFilename = true
                tags = uploadLargeTags
            }
            options {
                chunkSize = 5243000
                resourceType = "raw"
            }
        }

        var result = response.data!!

        assertEquals(uploadLargeTags, result.tags)

        assertEquals("raw", result.resourceType)
        Assert.assertTrue(result.publicId?.startsWith("cldupload") ?: false)

        response = cloudinary.uploader().uploadLarge(FileInputStream(temp)) {
            params {
                tags = uploadLargeTags
            }
            options {
                chunkSize = 5243000
            }
        }

        result = response.data!!
        assertEquals(uploadLargeTags, result.tags)
        assertEquals("image", result.resourceType)
        assertEquals(1400, result.width)
        assertEquals(1400, result.height)

        response = cloudinary.uploader().uploadLarge(temp) {
            params {
                tags = uploadLargeTags
            }
            options {
                chunkSize = 5880138
            }
        }

        result = response.data!!

        assertEquals(uploadLargeTags, result.tags)

        assertEquals(uploadLargeTags, result.tags)
        assertEquals("image", result.resourceType)
        assertEquals(1400, result.width)
        assertEquals(1400, result.height)

        response = cloudinary.uploader().uploadLarge(temp) {
            params {
                tags = uploadLargeTags
            }
            options {
                chunkSize = 5880138
            }
        }

        result = response.data!!

        assertEquals(uploadLargeTags, result.tags)

        assertEquals(uploadLargeTags, result.tags)
        assertEquals("image", result.resourceType)
        assertEquals(1400, result.width)
        assertEquals(1400, result.height)
    }

    @Test
    fun testUnsignedUpload() {
        // should support unsigned uploading using presets
        val response = cloudinary.uploader().upload(srcTestImage) {
            params {
                uploadPreset = "sdk-test-upload-preset"
                tags = defaultTags
            }
            options {
                unsigned = true
            }
        }
        val result = response.data!!

        assertTrue(result.publicId?.matches("^upload_folder/[a-z0-9]+$".toRegex()) ?: false)
    }

    @Test
    fun testFilenameOption() {
        val response = cloudinary.uploader().upload(srcTestImage) {
            params {
                tags = defaultTags
            }
            options {
                filename = "emanelif"
            }
        }

        val result = response.data!!
        assertEquals("emanelif", result.originalFilename)
    }

    @Test
    fun testResponsiveBreakpoints() {
        val breakpoint = ResponsiveBreakpoint(
            createDerived = true,
            maxImages = 2,
            format = "gif",
            transformation = Transformation().effect(
                Effect.sepia()
            )
        )

        // A single breakpoint
        val response = cloudinary.uploader().upload(srcTestImage) {
            params {
                responsiveBreakpoints = listOf(breakpoint)
                tags = defaultTags
            }
        }

        val result = response.data!!
        val breakpointsResponse = result.responsiveBreakpoints!!
        val br = breakpointsResponse.first()

        assertTrue(br.breakpoints.first().url.endsWith("gif"))
        assertEquals(2, br.breakpoints.size)
        assertEquals("e_sepia", br.transformation)
    }

    @Test
    fun testCreateArchive() {
        var response = cloudinary.uploader()
            .createArchive {
                params {
                    tags = listOf(archiveTag)
                }
            }

        assertEquals(2, response.data!!.fileCount)
        response = cloudinary.uploader()
            .createArchive {
                params {
                    tags = listOf(archiveTag)
                    transformations = listOf(
                        EagerTransformation(Transformation().resize(scale { width(0.5f) })),
                        EagerTransformation(Transformation().resize(scale { width(2.0f) }))
                    )
                }
            }

        assertEquals(4, response.data!!.fileCount)
    }

    @Test
    fun testCreateArchiveRaw() {

        val response = cloudinary.uploader()
            .createArchive {
                params {
                    tags = listOf(archiveTag)
                }
                options {
                    resourceType = "raw"
                }
            }

        assertEquals(1, response.data!!.fileCount)
        assertEquals(1, response.data!!.fileCount)
    }

    @Test
    fun testAccessControl() {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z")
        val start = simpleDateFormat.parse("2019-02-22 16:20:57 +0200")
        val token = AccessControlRule.token()
        val acl = AccessControlRule.anonymous(start, null)

        val response = cloudinary.uploader().upload(srcTestImage) {
            params {
                accessControl = listOf(acl, token)
                tags = defaultTags
            }
        }

        val result = response.data!!

        val accessControlResponse = result.accessControl!!

        assertEquals(2, accessControlResponse.size)
        var acr = accessControlResponse[0]
        assertEquals(AccessControlRule.AccessType.Anonymous, acr.accessType)
        assertEquals(start, acr.start)
        assertNull(acr.end)
        acr = accessControlResponse[1]
        assertEquals(AccessControlRule.AccessType.Token, acr.accessType)
        assertNull(acr.start)
        assertNull(acr.end)
    }

    @Test
    fun testQualityAnalysis() {
        val response = cloudinary.uploader().upload(srcTestImage) {
            params {
                qualityAnalysis = true
                tags = defaultTags
            }
        }

        val result = response.data!!
        Assert.assertNotNull(result.qualityAnalysis)
    }

    @Test
    fun testCinemagraphAnalysisUpload() {
        val response = cloudinary.uploader().upload(srcTestImage) {
            params {
                cinemagraphAnalysis = true
                tags = defaultTags
            }
        }

        val result = response.data!!
        Assert.assertNotNull(result.cinemagraphAnalysis)
    }

    @Test
    fun testDestroy() {
        val uploadResponse = uploader.upload(srcTestImage) {
            params {
                tags = defaultTags
            }
        }

        val publicId = uploadResponse.data!!.publicId!!

        val response = uploader.destroy(publicId)
        assertEquals("ok", response.data?.result)
    }

    @Test
    fun testRename() {
        val uploadResponse = uploader.upload(srcTestImage) {
            params {
                tags = defaultTags
            }
        }

        val publicId = uploadResponse.data!!.publicId!!

        val toPublicId = "rename_${publicId}_${suffix}"
        val response = uploader.rename(publicId, toPublicId)
        assertEquals(toPublicId, response.data?.publicId)
    }

    @Test
    fun testDeleteByToken() {
        val uploadResponse = uploader.upload(srcTestImage) {
            params {
                tags = defaultTags
                returnDeleteToken = true
            }
        }

        val token = uploadResponse.data!!.deleteToken!!

        val response = uploader.deleteByToken(token) {
            options {
                this.filename = "asd"
            }

        }
        assertEquals("ok", response.data?.result)
    }

    @Test
    fun testExplicit() {
        val uploadResponse = uploader.upload(srcTestImage) {
            params {
                tags = defaultTags
            }
        }

        val publicId = uploadResponse.data!!.publicId!!

        val transformation = Transformation().resize(scale {
            width(2.0)
        })
        val response = uploader.explicit(publicId) {
            params {
                eager = listOf(EagerTransformation(transformation))
                type = "upload"
                moderation = "manual"
            }
        }

        val explicitData = response.data!!

        val url = cloudinary.url(
            transformation = transformation,
            format = "png",
            version = explicitData.version!!.toString()
        ).generate(publicId)!!

        val eagerUrl = explicitData.eager!!.first().secureUrl!!
        val cloudName = cloudinary.config.cloudName
        assertEquals(
            eagerUrl.substring(eagerUrl.indexOf(cloudName)),
            url.substring(url.indexOf(cloudName))
        )
    }

    @Test
    fun testSprite() {
        val spriteTestTag = String.format("sprite_test_tag_%d", Date().time)
        cloudinary.uploader().upload(srcTestImage) {
            params {
                tags = defaultTags + spriteTestTag
                publicId = "sprite_test_tag_1$suffix"
            }
        }

        cloudinary.uploader().upload(srcTestImage) {
            params {
                tags = defaultTags + spriteTestTag
                publicId = "sprite_test_tag_2$suffix"
            }
        }

        var response = uploader.generateSprite(spriteTestTag)
        var result = response.data!!
        assertEquals(2, result.imageInfos.size)

        response = uploader.generateSprite(spriteTestTag) {
            transformation {
                resize(scale {
                    width(100)
                })
            }
        }

        result = response.data!!
        assertTrue(result.cssUrl!!.contains("c_scale,w_100"))


        response = uploader.generateSprite(spriteTestTag) {
            transformation {
                resize(scale {
                    width(100)
                })
            }
            format = "jpg"
        }
        result = response.data!!

        assertTrue(result.cssUrl!!.contains("c_scale,w_100/f_jpg"))
    }

    @Test
    fun testMulti() {
        val multiTestTag = "multi_test_tag$suffix"
        val multiTestTags = defaultTags + multiTestTag

        cloudinary.uploader().upload(srcTestImage) { params { tags = multiTestTags } }
        cloudinary.uploader().upload(srcTestImage) { params { tags = multiTestTags } }

        val response = uploader.multi(multiTestTag) {
            transformation {
                resize(Resize.crop {
                    width(0.5)
                })
            }
        }

        val result = response.data!!

        val pdfResponse = uploader.multi(multiTestTag) {
            transformation = Transformation().resize(scale { width(111) })
            format = "pdf"
        }

        val pdfResult = pdfResponse.data!!

        assertEquals(true, result.url?.endsWith(".gif"))
        assertEquals(true, result.url?.contains("w_0.5"))
        assertEquals(true, pdfResult.url?.endsWith(".pdf"))
        assertEquals(true, pdfResult.url?.contains("w_111"))
    }

    @Test
    fun testTags() {
        var uploaderResponse = cloudinary.uploader().upload(srcTestImage)
        var uploadResult = uploaderResponse.data!!
        val publicId1 = uploadResult.publicId!!

        uploaderResponse = cloudinary.uploader().upload(srcTestImage)
        uploadResult = uploaderResponse.data!!
        val publicId2 = uploadResult.publicId!!

        val tag1 = randomPublicId()
        val tag2 = randomPublicId()
        val tag3 = randomPublicId()
        val tag4 = randomPublicId()

        cloudinary.uploader().addTag(tag1, listOf(publicId1, publicId2))
        cloudinary.uploader().addTag(tag2, listOf(publicId1, publicId2))
        cloudinary.uploader().addTag(tag3, listOf(publicId1, publicId2))

        cloudinary.uploader().removeTag(tag2, listOf(publicId2))

        var url = URL(cloudinary.url(publicId = "$tag1.json", type = "list").generate())
        var jsonUrl = HttpUrlConnectionFactory(cloudinary).getClient().get(url)?.content!!
        assertTrue(jsonUrl.contains(publicId1))
        assertTrue(jsonUrl.contains(publicId2))

        url = URL(cloudinary.url(publicId = "$tag2.json", type = "list").generate())
        jsonUrl = HttpUrlConnectionFactory(cloudinary).getClient().get(url)?.content!!

        assertTrue(jsonUrl.contains(publicId1))
        assertFalse(jsonUrl.contains(publicId2))

        cloudinary.uploader().removeAllTags(listOf(publicId2))

        url = URL(cloudinary.url(publicId = "$tag3.json", type = "list").generate())
        jsonUrl = HttpUrlConnectionFactory(cloudinary).getClient().get(url)?.content!!

        assertTrue(jsonUrl.contains(publicId1))
        assertFalse(jsonUrl.contains(publicId2))

        cloudinary.uploader().replaceTag(tag4, listOf(publicId1))
        url = URL(cloudinary.url(publicId = "$tag4.json", type = "list").generate())
        jsonUrl = HttpUrlConnectionFactory(cloudinary).getClient().get(url)?.content!!

        assertTrue(jsonUrl.contains(publicId1))
    }

    @Test
    fun testEncodeContext() {
        val context = mapOf("caption" to "different = caption", "alt2" to "alt|alternative")
        val result: String = context.asContextParam()
        Assert.assertTrue("caption=different \\= caption|alt2=alt\\|alternative" == result || "alt2=alt\\|alternative|caption=different \\= caption" == result)
    }

    @Test
    fun testExplicitContext() {
        val publicId = "explicit_id$suffix"
        uploadResource(publicId)

        //should allow sending context
        val differentContext = mapOf("caption" to "different = caption", "alt2" to "alt|alternative alternative")
        val response = uploader.explicit(publicId) {
            params {
                type = "upload"
                context = differentContext
            }
        }

        assertMapsHaveSameEntries(differentContext, response.data!!.context?.custom)
    }

    @Test
    @Throws(Exception::class)
    fun testAddContext() {
        val publicId = "add_context_id$suffix"
        uploadResource(publicId)
        val response = uploader.addContext(listOf(publicId, "no-such-id")) {
            context("caption" to "new caption")
        }

        val result = response.data!!

        Assert.assertTrue(
            "addContext should return a list of modified public IDs",
            result.publicIds.contains(publicId)
        )
    }

    @Test
    fun testRemoveAllContext() {
        val publicId = "remove_context_id$suffix"
        uploadResource(publicId)
        val response = uploader.removeAllContext(listOf(publicId, "no-such-id"))
        val result = response.data!!

        assertTrue(result.publicIds.contains(publicId))
    }

    @Test
    fun testAdapters() {
        val uploaderUrlConnection =
            Uploader(cloudinary, HttpUrlConnectionFactory(cloudinary))
        val uploaderOkHttp = Uploader(cloudinary, OkHttpClientFactory(cloudinary))
        val uploaderApache45 =
            Uploader(cloudinary, ApacheHttpClient45Factory(cloudinary))

        verifySuccess(uploaderUrlConnection)
        verifyError(uploaderUrlConnection, cloudinary)

        verifySuccess(uploaderOkHttp)
        verifyError(uploaderOkHttp, cloudinary)

        verifySuccess(uploaderApache45)
        verifyError(uploaderApache45, cloudinary)
    }

    private fun uploadResource(publicId: String): UploadResult {
        return Cloudinary().uploader().upload(srcTestImage) {
            params {
                this.publicId = publicId
                tags = listOf(sdkTestTag, contextTag)
                context = contextForTest
                transformation = Transformation().resize(scale { width(10) })
            }
        }.data!!
    }

    private fun validateSignature(result: UploadResult) {
        val toSign: MutableMap<String, Any> = HashMap()
        toSign["public_id"] = result.publicId!!
        toSign["version"] = result.version.toString()

        val expectedSignature: String =
            apiSignRequest(toSign, cloudinary.config.apiSecret!!)
        assertEquals(result.signature, expectedSignature)
    }

    private fun verifySuccess(uploader: Uploader) {
        val res = uploader.upload(srcTestImage)
        assertNotNull(res.data)
        assertNull(res.error)
    }

    private fun verifyError(uploader: Uploader, cld: Cloudinary) {
        val res = uploader.upload(srcTestImage) {
            configuration(
                cld.config.copy(
                    accountConfig = cld.config.accountConfig.copy(
                        apiKey = "bad_secret"
                    )
                )
            )
        }

        assertNull(res.data)
        assertNotNull(res.error)
    }

    private fun assertErrorMessage(
        expectedMessage: String,
        response: UploaderResponse<*>
    ) {
        assertNull(response.data)
        assertNotNull(response.error)
        Assert.assertTrue(
            response.error?.error?.message?.contains(expectedMessage) ?: false
        )
    }
}
