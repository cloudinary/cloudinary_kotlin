Cloudinary Kotlin SDK
=========================
[![Build Status](https://api.travis-ci.com/cloudinary/cloudinary_kotlin.svg?branch=master)](https://app.travis-ci.com/github/cloudinary/cloudinary_kotlin)
## About
The Cloudinary iOS SDK allows you to quickly and easily integrate your application with Cloudinary.
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
implementation 'com.cloudinary:kotlin-url-gen:1.0.0'
```

**Using Maven**:

Add the Cloudinary Kotlin SDK to the list of dependencies in your `pom.xml` file.
```xml
<dependency>
  <groupId>com.cloudinary</groupId>
  <artifactId>kotlin-url-gen</artifactId>
  <version>1.0.0</version>
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
