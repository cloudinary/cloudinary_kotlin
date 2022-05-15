
1.4.0 / 2022-05-11
==================

  * Fix `Resize.fill` variables names
  * Add support to disable b-frames for h265 video codec
  * Add `format` qualifier to `fl_waveform`
  * Support text style and color as user variables
  * Add support for `g_ocr_text`
  * Add support for underlay, resize fill, `Source.image`
  * Add support for video overlay in `config.js`
  * Add support for tint effect

1.3.0 / 2022-05-01
==================

New functionality
-----------------
  * Add tags as array to `addTag`, `removeTag`, `replaceTag`
  * Add verification for SHA-256 signature
  * Add `AudioFrequencyType` original support

Other changes
-------------
  * Deprecate `storageType` and replace with with `deliveryType`
  * Verify normalize expression

1.2.0 / 2022-04-05
==================

New functionality
-----------------
  * Add support for `USDZ` file format
  * Add ignore mask channels qualifier
  * Add `accessibilityAnalysis` upload parameter
  * Add `filename_override` parameter to upload API

Other changes
-------------
  * Fix expression normalization for user variables

1.1.0 / 2022-02-23
==================

Other changes
-------------
  * Add level to anti_removal on overlay
  * Fix expression normalization
  * Remove jcenter dependency
  * Update README.md
  * Fix OCR text variable

1.0.0 / 2021-02-25
==================

New functionality and features
------------------------------
 * 1.0.0 major release
 * Uses Cloudinary’s new SDK 2 principles with enhanced code autocomplete and action based syntax.
 * Actions and transformations are immutable, for easier and safer code reuse.
 * Makes use of Type-Safe Builders to create a Cloudinary DSL layer. The transformation syntax is therefore simpler and more human-readable when compared with the existing Java or Android SDKs.
