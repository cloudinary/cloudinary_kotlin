module.exports = {
  "SDKSpecVersion": "master",
  "langConfig": {
    lang: 'Kotlin',
    methodDelimiter: ' ',
    groupDelimiter: '.',
    hideActionGroups: false,
    specialCharacterMapping: [
      ['$', '\$']
    ],
    openQualifiersChar : ' {',
    closeQualifiersChar : ' }',
    openActionChar: '(',
    closeActionChar: ')',
    unsupportedTxParams: ['if_', 'palette_f00', '$overlaywidth_$mainvideowidth_div_3'],
    unsupportedSyntaxList: ['getVideoFrame'],
    mainTransformationString: {
      openSyntaxString: {
        image: 'cloudinary.image {\n\tpublicId("#publicID")',
        video: 'cloudinary.video {\n\tpublicId("#publicID")',
        media: 'cloudinary.media {\n\tpublicId("#publicID")'
      },
      closeSyntaxString : ' \n}.generate()',
    },
    closeTransformationChar: ' }', // Note how this is needed because the openSyntaxString contains an open transformation {
    overwritePreset: 'kotlin',
    newInstanceSyntax: '#name(#req)#optional',
    arrayOpen: 'listOf(',
    arrayClose: ')',
    arraySeparator: ', ',
    formats: {
      formatClassOrEnum: 'PascalCase',
      formatMethod: 'camelCase',
      formatFloat: (f) => {
        if (!f.toString().includes('.')) {
          return `${f}.0F` // In JS world, 1.0 is 1, so we make sure 1.0 stays 1.0
        } else {
          return `${f}F`
        }
      }
    },
    methodNameMap: {},
    classNameMap: {},
    childTransformations: {
      image: {
        open: 'ImageTransformation {',
        close: ' }',
      },
      video: {
        open: 'VideoTransformation {',
        close: ' }',
      },
      media: {
        open: 'Transformation {',
        close: ' }',
      }
    }
  },
  "overwrites": {
    qualifiers: {
      color_override: (payload) => {
        const {qualifierDTO} = payload;
        const colorName = qualifierDTO.qualifiers[0].name;
        const group = qualifierDTO.qualifiers[0].group;

        // TODO Color - this should be streamlined with how we deal with color.
        return `.colorOverride(Color.${colorName.toUpperCase()})`
      }
    }
  }
}
