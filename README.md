Cloudinary Kotlin SDK
=========================
[![Build Status](https://api.travis-ci.com/cloudinary/cloudinary_kotlin.svg?branch=master)](https://app.travis-ci.com/github/cloudinary/cloudinary_kotlin)
## About
The Cloudinary Kotlin SDK allows you to quickly and easily integrate your application with Cloudinary.
Effortlessly optimize and transform your cloud's assets.

### Additional documentation
This Readme provides basic installation and usage information.
For the complete documentation, see the [Kotlin SDK Guide](https://cloudinary.com/documentation/kotlin_integration).

## Table of Contents
- [Key Features](#key-features)
- [Version Support](#Version-Support)
- [Installation](#installation)
- [Usage](#usage)
    - [Setup](#Setup)
    - [Transform and Optimize Assets](#Transform-and-Optimize-Assets)
    - [Uploading Assets](#Uploading-Assets)

## Key Features
- [Transform](https://cloudinary.com/documentation/kotlin_media_transformations) and [optimize](https://cloudinary.com/documentation/kotlin_media_transformations#image_optimizations) assets.

## Version Support
| SDK Version   |  Kotlin Version  |
|---------------|------------------|
|      1.x      |       > 1.0      | 

## Installation
Add the SDK to your project as a dependency, we recommend using a build management tool such as Maven or Gradle to do this.

**Using Gradle**:

Add the Cloudinary Kotlin SDK to the dependencies section of your `build.gradle` file.

```
implementation 'com.cloudinary:kotlin-url-gen:1.5.1'
```

**Using Maven**:

Add the Cloudinary Kotlin SDK to the list of dependencies in your `pom.xml` file.
```xml
<dependency>
  <groupId>com.cloudinary</groupId>
  <artifactId>kotlin-url-gen</artifactId>
  <version>1.5.1</version>
  <type>pom</type>
</dependency>
```

## Usage
### Setup
The `Cloudinary` class is the main entry point for using the library. Your `cloud_name` is required to create an instance of this class. Your `api_key` and `api_secret` are also needed to perform secure API calls to Cloudinary (e.g., image and video uploads). Setting the configuration parameters can be done either programmatically using an appropriate constructor of the Cloudinary class or globally using an environment variable. You can find your account-specific configuration parameters in the **Dashboard** page of your [account console](https://cloudinary.com/console).

Here's an example of setting configuration parameters in your Kotlin application:

```kotlin
import com.cloudinary.*

...
private val cloudinary = Cloudinary("cloudinary://<your-api-key>:<your-api-secret>@<your-cloud-name>")
```
### Transform and Optimize Assets

Generate a Cloudinary URL using the `cloudinary.media` helper method and pass this to your image or video view:

For example, to generate a url for an image called `sample` on the `demo` account, and resize it using the fill method:

A transformation is also added to the image - cropping and using the sepia effect:

```kotlin
 val cloudinary = Cloudinary("cloudinary://@demo")
 val url = cloudinary.image {
   publicId("sample")
   resize(Resize.fill {
     width(100)
     height(150)
   })
 }
 println(url.generate())
```

This will output the following url:
`https://res.cloudinary.com/demo/image/upload/c_fill,h_150,w_100/sample.jpg`

### Uploading Assets

The entry point for upload operations is the `cloudinary.uploader().upload()` call. All upload operations are dispatched to a background queue.

The following example performs an unsigned upload of a `File` using the default settings, a request upload callback, and an upload preset (required for unsigned uploads):

```kotlin
cloudinary.uploader().upload(imageFile) { 
    params {
      uploadPreset = "sample_preset"
    }
    options {
      unsigned = true
    }
}
```

The uploaded image is assigned a randomly generated public ID, which is returned as part of the response object.
The image is immediately available for download through a CDN:

    cloudinary.image().generate("generatedPublicId")
      
    http://res.cloudinary.com/<your cloud>/image/upload/generatedPublicId.jpg

You can also specify your own public ID:

```kotlin
cloudinary.uploader().upload(uri) {
        params {
          uploadPreset = "sample_preset"
          publicId = "sample_remote" 
        }
        options {
            unsigned = true
        }
}
```

For security reasons, mobile applications cannot contain the full account credentials, and so they cannot freely upload resources to the cloud.
Cloudinary provides two different mechanisms to enable end-users to upload resources without providing full credentials.

##### 1. Unsigned uploads using [Upload Presets.](https://cloudinary.com/documentation/upload_presets)
You can create an upload preset in your Cloudinary account console, defining rules that limit the formats, transformations, dimensions and more.
Once the preset is defined, it's name is supplied when calling upload. An upload call will only succeed if the preset name is used and the resource is within the preset's pre-defined limits.

The following example uploads a local resource, available as a Uri, assuming a preset named 'sample_preset' already exists in the account:

```kotlin
cloudinary.uploader().upload(uri) {
        params {
          uploadPreset = "sample_preset"
        }
        options {
          unsigned = true
        }
}
```

##### 2. Signed uploads with server-generated signature
Another way to upload without including credentials is using signed uploads.
You should generate the upload authentication signature on the server side, where it's safe to store the `api_secret`.

The Cloudinary Kotlin SDK allows you to provide a server-generated signature and any additional parameters that were generated on the server side (instead of signing using `api_secret` locally).

Your server can use any Cloudinary libraries (Ruby on Rails, PHP, Python & Django, Java, Perl, .Net, etc.) for generating the signature. The following JSON in an example of a response of an upload authorization request to your server:
```json
	{
	  "signature": "sgjfdoigfjdgfdogidf9g87df98gfdb8f7d6gfdg7gfd8",
	  "public_id": "abdbasdasda76asd7sa789",
	  "timestamp": 1346925631,
	  "api_key": "123456789012345"
	}
```

Use the signature field to put the signature you got from your server, when using signature api key is required as well as part of the Cloudinary initialization.

```kotlin
        val response = uploader.upload(remoteTestImageUrl) {
            params {
                signature = <your signature>
            }
        }
```

## Contributions
See [contributing guidelines](/CONTRIBUTING.md).

## Get Help
If you run into an issue or have a question, you can either:
- [Open a Github issue](https://github.com/cloudinary/cloudinary_kotlin/issues) (for issues related to the SDK)
- [Open a support ticket](https://cloudinary.com/contact) (for issues related to your account)

## About Cloudinary
Cloudinary is a powerful media API for websites and mobile apps alike, Cloudinary enables developers to efficiently manage, transform, optimize, and deliver images and videos through multiple CDNs. Ultimately, viewers enjoy responsive and personalized visual-media experiences—irrespective of the viewing device.

## Additional Resources
- [Cloudinary Transformation and REST API References](https://cloudinary.com/documentation/cloudinary_references): Comprehensive references, including syntax and examples for all SDKs.
- [MediaJams.dev](https://mediajams.dev/): Bite-size use-case tutorials written by and for Cloudinary Developers
- [DevJams](https://www.youtube.com/playlist?list=PL8dVGjLA2oMr09amgERARsZyrOz_sPvqw): Cloudinary developer podcasts on YouTube.
- [Cloudinary Academy](https://training.cloudinary.com/): Free self-paced courses, instructor-led virtual courses, and on-site courses.
- [Code Explorers and Feature Demos](https://cloudinary.com/documentation/code_explorers_demos_index): A one-stop shop for all code explorers, Postman collections, and feature demos found in the docs.
- [Cloudinary Roadmap](https://cloudinary.com/roadmap): Your chance to follow, vote, or suggest what Cloudinary should develop next.
- [Cloudinary Facebook Community](https://www.facebook.com/groups/CloudinaryCommunity): Learn from and offer help to other Cloudinary developers.
- [Cloudinary Account Registration](https://cloudinary.com/users/register/free): Free Cloudinary account registration.
- [Cloudinary Website](https://cloudinary.com)

## Licence
Released under the MIT license.
