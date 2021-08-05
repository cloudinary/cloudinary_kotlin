/* eslint-disable @typescript-eslint/no-var-requires */
/* eslint-disable require-jsdoc */

import {ISanityGeneratorResponse} from "sdk-sanity-generator";


const fs = require('fs');
const sanityGenerator = require('sdk-sanity-generator').sanityGenerator;
const createTestFile = require('./createTestFile');


function filterNonImplemented(tx: { transformation:string }) {
    if (tx.transformation.includes('dl_')) {
        return false;
    }

    if (tx.transformation.includes('e_preview')) {
        return false;
    }

    return true;
}

// Uncomment this to read from the cache and skip the fetching from the server
// let data = JSON.parse(fs.readFileSync(`${__dirname}/results.json`));
// data = data.filter(filterNonImplemented);
// fs.writeFileSync(`${__dirname}/../url-gen/src/test/kotlin/com/cloudinary/CompilationTest.kt`, createTestFile(data));
//
// If this is block is uncommented, everything below should be commented



sanityGenerator({
    framework: 'kotlin',
    txList: ['c_lpad,w_480,h_320,vs_2,q_70,br_1400k,vc_h264:baseline:3.0', 'e_sepia', 'w_100', 'c_crop,g_west', 'f_auto', 'q_auto', 'bo_15px_solid_green', 'l_sample', 'u_foo'],
    requestSpreading: 70,
    codeSnippetsEndpointURL: 'http://localhost:8000/v1/generate-code'
}).then((res: ISanityGeneratorResponse) => {
    console.log(`Successful transformations: ${res.success.length}`);
    console.log(`Failed transformations: ${res.error.length}`);
    // Uncomment this create a cache of all the results
    // fs.writeFileSync(`${__dirname}/results.json`, JSON.stringify(res.success));
    console.log(process.cwd())
    fs.writeFileSync(`${__dirname}/../url-gen/src/test/kotlin/com/cloudinary/CompilationTest.kt`, createTestFile(res.success));
});
