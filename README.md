# Cloudinary Kotlin SDK Beta

Cloudinary is a cloud service that offers a solution to a web application's entire image management pipeline.

Easily upload images to the cloud. Automatically perform smart image resizing, cropping and conversion without installing any complex software. Integrate Facebook or Twitter profile image extraction in a snap, in any dimension and style to match your mobile applications's graphics requirements. Images are seamlessly delivered through a fast CDN, and much much more.

Cloudinary provides URL and HTTP based APIs that can be easily integrated with mobile applications. For Kotlin, Cloudinary provides an extension for simplifying the integration even further.


## Installation

Add the SDK to your project as a dependency, we recommend using a build management tool such as Maven or Gradle to do this.

**Using Gradle**:

Add the Cloudinary Kotlin SDK to the dependencies section of your `build.gradle` file.

```
implementation 'com.cloudinary:kotlin-url-gen:0.0.1-beta.6'
```

**Using Maven**:

Add the Cloudinary Kotlin SDK to the list of dependencies in your `pom.xml` file.
```xml
<dependency>
  <groupId>com.cloudinary</groupId>
  <artifactId>kotlin-url-gen</artifactId>
  <version>0.0.1-beta.6</version>
  <type>pom</type>
</dependency>
```

## Configuration

The `Cloudinary` class is the main entry point for using the library. Your `cloud_name` is required to create an instance of this class. Your `api_key` and `api_secret` are also needed to perform secure API calls to Cloudinary (e.g., image and video uploads). Setting the configuration parameters can be done either programmatically using an appropriate constructor of the Cloudinary class or globally using an environment variable. You can find your account-specific configuration parameters in the **Dashboard** page of your [account console](https://cloudinary.com/console).

Here's an example of setting configuration parameters in your Kotlin application:

```kotlin
import com.cloudinary.*

...
private val cloudinary = Cloudinary("cloudinary://<your-api-key>:<your-api-secret>@<your-cloud-name>")
```

#### Usage

Generate a Cloudinary URL using the `cloudinary.media` helper method and pass this to your image or video view:

For example, to generate a url for an image called `sample` on the `demo` account, and resize it using the fill method:
 
A transformation is also added to the image - cropping and using the sepia effect:

```kotlin
 val cloudinary = Cloudinary("cloudinary://@demo")
 val url = cloudinary.media{ 
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
