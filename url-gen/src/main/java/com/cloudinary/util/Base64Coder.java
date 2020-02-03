//Copyright 2003-2010 Christian d'Heureuse, Inventec Informatik AG, Zurich, Switzerland
//www.source-code.biz, www.inventec.ch/chdh
//
//This module is multi-licensed and may be used under the terms
//of any of the following licenses:
//
//EPL, Eclipse Public License, V1.0 or later, http://www.eclipse.org/legal
//LGPL, GNU Lesser General Public License, V2.1 or later, http://www.gnu.org/licenses/lgpl.html
//GPL, GNU General Public License, V2 or later, http://www.gnu.org/licenses/gpl.html
//AGPL, GNU Affero General Public License V3 or later, http://www.gnu.org/licenses/agpl.html
//AL, Apache License, V2.0 or later, http://www.apache.org/licenses
//BSD, BSD License, http://www.opensource.org/licenses/bsd-license.php
//MIT, MIT License, http://www.opensource.org/licenses/MIT
//
//Please contact the author if you need another license.
//This module is provided "as is", without warranties of any kind.
package com.cloudinary.util;

/**
 * A Base64 encoder/decoder.
 * <p>
 * <p>
 * This class is used to encode and decode data in Base64 format as described in
 * RFC 1521.
 *
 * @author Christian d'Heureuse, Inventec Informatik AG, Zurich, Switzerland,
 * www.source-code.biz
 */
@SuppressWarnings({"UnusedAssignment"})
public class Base64Coder {

    // Mapping table from 6-bit nibbles to Base64 characters.
    private static final char[] map1 = new char[64];
    // Mapping table from Base64 characters to 6-bit nibbles.

    static {
        int i = 0;
        for (char c = 'A'; c <= 'Z'; c++)
            map1[i++] = c;
        for (char c = 'a'; c <= 'z'; c++)
            map1[i++] = c;
        for (char c = '0'; c <= '9'; c++)
            map1[i++] = c;
        map1[i++] = '+';
        map1[i++] = '/';
    }

    private Base64Coder() {
    }

    /**
     * Encodes a string into Base64 format. No blanks or line breaks are
     * inserted.
     *
     * @param s A String to be encoded.
     * @return A String containing the Base64 encoded data.
     */
    public static String encodeString(String s) {
        return new String(encode(s.getBytes()));
    }

    /**
     * Encodes a byte array into Base64 format. No blanks or line breaks are
     * inserted in the output.
     *
     * @param in An array containing the data bytes to be encoded.
     * @return A character array containing the Base64 encoded data.
     */
    public static char[] encode(byte[] in) {
        return encode(in, 0, in.length);
    }

    /**
     * Encodes a byte array into Base64 format. No blanks or line breaks are
     * inserted in the output.
     *
     * @param in   An array containing the data bytes to be encoded.
     * @param iOff Offset of the first byte in {@code in} to be processed.
     * @param iLen Number of bytes to process in {@code in}, starting at
     *             {@code iOff}.
     * @return A character array containing the Base64 encoded data.
     */
    public static char[] encode(byte[] in, int iOff, int iLen) {
        int oDataLen = (iLen * 4 + 2) / 3; // output length without padding
        int oLen = ((iLen + 2) / 3) * 4; // output length including padding
        char[] out = new char[oLen];
        int ip = iOff;
        int iEnd = iOff + iLen;
        int op = 0;
        while (ip < iEnd) {
            int i0 = in[ip++] & 0xff;
            int i1 = ip < iEnd ? in[ip++] & 0xff : 0;
            int i2 = ip < iEnd ? in[ip++] & 0xff : 0;
            int o0 = i0 >>> 2;
            int o1 = ((i0 & 3) << 4) | (i1 >>> 4);
            int o2 = ((i1 & 0xf) << 2) | (i2 >>> 6);
            int o3 = i2 & 0x3F;
            out[op++] = map1[o0];
            out[op++] = map1[o1];
            out[op] = op < oDataLen ? map1[o2] : '=';
            op++;
            out[op] = op < oDataLen ? map1[o3] : '=';
            op++;
        }
        return out;
    }

    public static String encodeURLSafeString(String s) {
        return encodeURLSafeString(s.getBytes());
    }

    public static String encodeURLSafeString(byte[] digest) {
        char[] encode = encode(digest);
        for (int i = 0; i < encode.length; i++) {
            if (encode[i] == '+') {
                encode[i] = '-';
            } else if (encode[i] == '/') {
                encode[i] = '_';
            }
        }
        return new String(encode);
    }

}