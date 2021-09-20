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
      `;

    file += `class CompilationTests_${Date.now() + Math.ceil(Math.random() * 50)} {\n`;
    file += `private val cloudinary = Cloudinary("cloudinary://a:b@test123?analytics=false")\n`;


    file += txs.map((txResult, i) => {
        txResult.transformation = txResult.transformation.replace(/\$/g, "\\$")
        let test = '@Test\n';
        test += `fun testSomething_${i}() {\n`
        test += `// ${txResult.transformation}\n`
        test += `val tAsset = ${txResult.codeSnippet}`;

        if (txResult.transformation.startsWith('http')) {
            // For URLS, If not a demo cloud, we do not support the compilation test.
            if (!txResult.transformation.includes('/demo/')) {
                throw `Unsupported URL: ${txResult.transformation}`;
            }

            test += `\n// Skipping deeper TX test (part in parts...) because the transformation is a URL`
            //
            // test += `tAsset.setCloudConfig({cloudName: 'demo'});`;
            // test += `tAsset.setURLConfig({analytics:false});`;
            // test += `assertEquals(tAsset.toURL(), '${txResult.transformation}');`;
        } else {
            test += `\nval parts = "${txResult.transformation}".replace("/", ",").split(",")\n\n`;
            test += `
                    for (part in parts) {
                          cldAssertContains(tAsset.toString(), part)
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
