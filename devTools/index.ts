/* eslint-disable @typescript-eslint/no-var-requires */
/* eslint-disable require-jsdoc */

import {ISanityGeneratorResponse} from "sdk-sanity-generator";
import {IFrameworkResponse} from "sdk-sanity-generator/dist/interfaces";


const fs = require('fs');
const sanityGenerator = require('sdk-sanity-generator').sanityGenerator;
const filterNonImplemented = require('./utils/filterNonImplemented');
const createTestFile = require('./utils/createTestFile');

sanityGenerator({
    framework: 'kotlin',
    // Uncomment to pass a list of transformations instead of using the default:
    // txList: ['w_150,h_150,c_pad,b_pink'],
    requestSpreading: 50,
    // Uncomment to test against a local endpoint, else use the default staging environment
    // codeSnippetsEndpointURL: 'http://localhost:8000/v1/generate-code',
    filterFn: filterNonImplemented
}).then((res: ISanityGeneratorResponse) => {
    console.log(`Successful transformations: ${res.success.length}`);
    console.log(`Failed transformations: ${res.error.length}`);

    // Store the results
    fs.writeFileSync(`${__dirname}/success.json`, JSON.stringify(res.success));
    fs.writeFileSync(`${__dirname}/error.json`, JSON.stringify(res.error));

    const groups:IFrameworkResponse[][] = [];
    let count = 0;

    // Split the results into files, this helps the compiler to deal with very large files
    res.success.forEach((item: IFrameworkResponse, i) => {
        const GROUP_NUM = Math.floor(count / 100);

        groups[GROUP_NUM] = groups[GROUP_NUM] || [];
        groups[GROUP_NUM].push(item);

        count ++;
    });

    groups.forEach((group,i) => {
        fs.writeFileSync(`${__dirname}/../url-gen/src/test/kotlin/com/cloudinary/CompilationTest_${i}.kt`, createTestFile(group));
    });
});
