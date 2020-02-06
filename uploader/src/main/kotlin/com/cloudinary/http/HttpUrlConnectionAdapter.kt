package com.cloudinary.http

import com.cloudinary.config.ApiConfig
import com.cloudinary.util.randomPublicId
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import java.io.*
import java.net.HttpURLConnection
import java.net.URL


private const val bufferSize = 4096

class HttpUrlConnectionAdapter(private val userAgent: String, private val config: ApiConfig) : HttpClient {
    private val lineFeed = "\r\n"
    private val boundary: String = randomPublicId()

    override fun get(url: URL, headers: Map<String, String>): HttpResponse? {
        val httpConn = (url.openConnection() as HttpURLConnection).apply {
            requestMethod = HttpGet.METHOD_NAME
            readTimeout = config.readTimeout * 1000
            connectTimeout = config.connectTimeout * 1000
            setRequestProperty("User-Agent", userAgent)
            headers.entries.forEach { setRequestProperty(it.key, it.value) }
        }

        httpConn.connect()

        return constructResponse(httpConn)
    }

    override fun post(url: URL, headers: Map<String, String>, entity: MultipartEntity) =
        post(url, headers, entity, null)

    override fun post(
        url: URL,
        headers: Map<String, String>,
        entity: MultipartEntity,
        progressCallback: ProgressCallback?
    ): HttpResponse? {
        val charset = Charsets.UTF_8
        val httpConn = (url.openConnection() as HttpURLConnection).apply {
            requestMethod = HttpPost.METHOD_NAME
            doOutput = true
            doInput = true
            readTimeout = config.readTimeout * 1000
            connectTimeout = config.connectTimeout * 1000
            setChunkedStreamingMode(0)
            setRequestProperty("Content-Type", "multipart/form-data; boundary=$boundary")
            setRequestProperty("User-Agent", userAgent)
            headers.entries.forEach { setRequestProperty(it.key, it.value) }
        }

        httpConn.connect()

        httpConn.outputStream.use { outputStream ->
            PrintWriter(OutputStreamWriter(outputStream, charset), true).use { writer ->
                entity.parts.forEach { addPart(writer, outputStream, it, charset.name()) }
                writer.append("--$boundary--").append(lineFeed)
            }
        }

        return constructResponse(httpConn)
    }

    private fun constructResponse(httpConn: HttpURLConnection): HttpResponse? {
        return with(httpConn) {
            val responseCode = responseCode
            val responseStream: InputStream? = if (responseCode >= 400) errorStream else inputStream
            val responseHeaders = headerFields.entries.associateBy({ it.key }, { it.value.joinToString(",") })
            val stringResponse = responseStream?.bufferedReader().use { it?.readText() }
            HttpResponse(responseCode, stringResponse, responseHeaders)
        }
    }

    private fun addPart(writer: PrintWriter, outputStream: OutputStream, part: MultipartEntity.Part, charset: String) {
        when (part.value) {
            is String -> addFormField(writer, part.name, part.value, charset)
            is File -> FileInputStream(part.value).use { addFilePart(writer, outputStream, "file", it, part.name) }
            is ByteArray -> part.value.inputStream().use { addFilePart(writer, outputStream, "file", it, part.name) }
        }
    }

    /**
     * Adds a form field to the request
     *
     * @param name  field name
     * @param value field value
     */
    private fun addFormField(writer: PrintWriter, name: String, value: String, charset: String) {
        writer.apply {
            append("--$boundary").append(lineFeed)
            append("Content-Disposition: form-data; name=\"$name\"").append(lineFeed)
            append("Content-Type: text/plain; charset=$charset").append(lineFeed)
            append(lineFeed)
            append(value)
            append(lineFeed)
            flush()
        }
    }

    private fun addFilePart(
        writer: PrintWriter,
        outputStream: OutputStream,
        fieldName: String,
        inputStream: InputStream,
        fileName: String
    ) {
        writer.apply {
            append("--$boundary").append(lineFeed)
            append("Content-Disposition: form-data; name=\"$fieldName\"; filename=\"$fileName\"").append(lineFeed)
            append("Content-Type: ").append("application/octet-stream").append(lineFeed)
            append("Content-Transfer-Encoding: binary").append(lineFeed)
            append(lineFeed)
            flush()
        }

        inputStream.copyTo(outputStream, bufferSize)

        outputStream.flush()
        writer.append(lineFeed).flush()
    }
}