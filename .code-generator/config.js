function CanonicalColorQualifier(payload) {
  const {qualifierDTO, langConfig} = payload;
  // This case supports two types of qualifiers
  // TODO this structure needs to be aligned


  if (qualifierDTO.qualifiers && qualifierDTO.name === 'rgb') {
    return [
      formatClassOrEnum(qualifierDTO.group, langConfig),
      langConfig.groupDelimiter,
      formatMethod(qualifierDTO.name, langConfig),
      '(',
      `'${qualifierDTO.qualifiers[0].value}'`.replace('#', ''),
      ')'
    ].join('');
  } else {
    const simpleColorName = qualifierDTO.qualifiers ?  qualifierDTO.qualifiers[0].name : qualifierDTO.name;
    return [
      formatClassOrEnum(qualifierDTO.group, langConfig),
      langConfig.groupDelimiter,
      simpleColorName.toUpperCase()
    ].join('');
  }
}


module.exports = {
  "SDKSpecVersion": "master",
  "langConfig": {
    lang: 'Kotlin',
    methodDelimiter: ' ',
    groupDelimiter: '.',
    hideActionGroups: false,
    openQualifiersChar : ' {',
    closeQualifiersChar : ' }',
    openActionChar: '(',
    closeActionChar: ')',
    unsupportedTxParams: ['fl_waveform', 'fl_animated', 'e_tint', 'u_', 'e_theme', 'l_fetch', 'l_text', 'u_text', 'af_'],
    unsupportedCode: ['.stroke(', '.textFit(', 'Animated.edit', '.RoundCorners(', 'getVideoFrame', 'Source.image', '.transcode('],
    mainTransformationString: {
      openSyntaxString: {
        image: 'cloudinary.image { publicId("#publicID")',
        video: 'cloudinary.video { publicId("#publicID")',
        media: 'cloudinary.media { publicId("#publicID")'
      },
      closeSyntaxString : ' }.generate()',
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
        open: 'MediaTransformation {',
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
      },
      color: CanonicalColorQualifier // TODO Color this functionality should be embeded in the framework itself
    }
  }
}
