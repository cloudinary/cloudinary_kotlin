/* eslint-disable @typescript-eslint/no-var-requires */
/* eslint-disable require-jsdoc */


import {IFrameworkResponse} from "sdk-sanity-generator/dist/interfaces";

const prettier = require('prettier');

function createTestFile(txs: IFrameworkResponse[]) {
    let file = `
        package com.cloudinary
        
        import kotlin.test.assertEquals
        import org.junit.Test
        import com.cloudinary.*
        import com.cloudinary.transformation.*
        import com.cloudinary.transformation.effect.*
        import com.cloudinary.transformation.gravity.*
        import com.cloudinary.transformation.resize.*
        import com.cloudinary.transformation.delivery.*
        import com.cloudinary.transformation.layer.*
        import com.cloudinary.transformation.layer.source.*
        import com.cloudinary.transformation.background.*
        import com.cloudinary.transformation.videoedit.*
        import com.cloudinary.transformation.adjust.*
        import com.cloudinary.transformation.layer.position.*
        import com.cloudinary.transformation.transcode.*
        import com.cloudinary.transformation.reshape.*
        import com.cloudinary.transformation.psdtools.*
        import com.cloudinary.transformation.expression.*
        import com.cloudinary.transformation.extract.*
        
        
        
        
        // import com.cloudinary.transformation.psdtools.PSDTools
        // import com.cloudinary.transformation.layer.Overlay
        
        
        // import com.cloudinary.asset.Asset
        // import com.cloudinary.config.CloudinaryConfig
        // import com.cloudinary.config.UrlConfig
        // import com.cloudinary.transformation.Format
        // import com.cloudinary.transformation.Rotate
        // import com.cloudinary.transformation.resize.Resize
        // import org.junit.Assert.*
        // import java.util.regex.Pattern
        // import com.cloudinary.cldAssert
        // import com.cloudinary.transformation.BackgroundColor.Companion.color
        // import com.cloudinary.transformation.CustomFunction.Companion.wasm
        // import com.cloudinary.transformation.Transformation.Companion.transformation
        // import com.cloudinary.transformation.adjust.Adjust
        // import com.cloudinary.transformation.adjust.Adjust.Companion.opacity
        
        // import com.cloudinary.transformation.effect.Effect
        // import com.cloudinary.transformation.effect.Effect.Companion.sepia
        // import com.cloudinary.transformation.effect.GradientFade
        // import com.cloudinary.transformation.background.Background
        // import com.cloudinary.transformation.expression.Expression
        // import com.cloudinary.transformation.expression.Variable
        // import com.cloudinary.transformation.gravity.Gravity.Companion.east
        // import com.cloudinary.transformation.gravity.Gravity.Companion.west
        // import com.cloudinary.transformation.layer.BlendMode
        // import com.cloudinary.transformation.layer.Overlay
        // import com.cloudinary.transformation.layer.Overlay.Companion.source
        // import com.cloudinary.transformation.layer.Underlay
        // import com.cloudinary.transformation.layer.source.FontHinting
        // import com.cloudinary.transformation.layer.source.FontWeight
        // import com.cloudinary.transformation.layer.source.ImageSource
        // import com.cloudinary.transformation.layer.source.Source.Companion.image
        // import com.cloudinary.transformation.layer.source.Source.Companion.text
        // import com.cloudinary.transformation.reshape.Reshape.Companion.distortArc
        // import com.cloudinary.transformation.transcode.AudioCodec
        // import com.cloudinary.transformation.transcode.Transcode
        // import com.cloudinary.transformation.transcode.Transcode.Companion.videoCodec
        // import com.cloudinary.transformation.videoedit.VideoEdit
        // import com.cloudinary.cldAssert
        // import com.cloudinary.transformation.Color
        // import com.cloudinary.transformation.gravity.FocusOn
        // import com.cloudinary.transformation.gravity.CompassGravity
        // import com.cloudinary.transformation.gravity.FocusOnGravity
        // import com.cloudinary.transformation.gravity.Gravity
        // import com.cloudinary.transformation.layer.BlendMode.Companion.multiply
        // import com.cloudinary.transformation.layer.position.Position
        // import com.cloudinary.transformation.layer.source.Source.Companion.fetch
        // import com.cloudinary.transformation.layer.source.Source.Companion.image
        // import com.cloudinary.transformation.psdtools.PSDTools
        // import com.cloudinary.transformation.delivery.*
        // import com.cloudinary.cldAssert
        // import com.cloudinary.transformation.layer.position.Position
        // import com.cloudinary.transformation.layer.source.Source
        // import com.cloudinary.transformation.reshape.Reshape
        // import com.cloudinary.transformation.videoedit.Concatenate
        // import com.cloudinary.transformation.videoedit.Transition
        // import com.cloudinary.transformation.videoedit.VideoEdit
        // import com.cloudinary.transformation.videoedit.VideoEdit.Companion.concatenate
        // import com.cloudinary.transformation.videoedit.VideoEdit.Companion.trim
        // import com.cloudinary.transformation.videoedit.Volume
        //
        // import com.cloudinary.transformation.*
        // import com.cloudinary.transformation.expression.Expression
        // import com.cloudinary.util.cldEncodePublicId
        // import com.cloudinary.*
  
      `;

    file += `class CompilationTests {\n`;
    file += `private val cloudinary = Cloudinary("cloudinary://a:b@test123?analytics=false")\n`;


    file += txs.map((txResult, i) => {
        let test = '@Test\n';
        test += `fun testSomething_${i}() {\n`
        test += `// ${txResult.transformation}\n`
        test += `var tAsset = ${txResult.code}`;

        if (txResult.transformation.startsWith('http')) {
            // For URLS, If not a demo cloud, we do not support the compilation test.
            if (!txResult.transformation.includes('/demo/')) {
                throw `Unsupported URL: ${txResult.transformation}`;
            }
            //
            // test += `tAsset.setCloudConfig({cloudName: 'demo'});`;
            // test += `tAsset.setURLConfig({analytics:false});`;
            // test += `assertEquals(tAsset.toURL(), '${txResult.transformation}');`;
        } else {
            test += `\nvar parts = "${txResult.transformation}".replace("/", ",").split(",");\n\n`;
            test += `
                    for (part in parts) {
                          assertEquals(true, tAsset.toString().contains(part));
                     }`

            // parts.forEach((part) => { assertEquals(tAsset.toString()).toContain(part)}, true)`;
        }

        test += '\n}\n'; // Close it test

        try {
            return prettier.format(test, {parser: 'babel'});
        } catch (e) {
            return test;
        }
    }).join('\n');

    file += `\n}\n`;

    try {
        return prettier.format(file, {parser: 'babel'});
    } catch (e) {
        return file;
    }
}

module.exports = createTestFile;
