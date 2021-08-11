const unSupportedParams = require('./../../.code-generation/config.js').langConfig.unsupportedTxParams;




/**
 * This function will filter out
 * @param tx
 */
module.exports = function filterNonImplemented(tx: string) {
    const isUnsupported = unSupportedParams.find((unsupportedBit: string) => {
        return tx.includes(unsupportedBit);
    });

    // Specifically ignore this transformation
    if (tx.toLowerCase().includes('palette_f00')) {
        return false;
    }

    // console.log(tx);
    if (isUnsupported) {
        return false;
    }

    return true;
}