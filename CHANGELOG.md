
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
