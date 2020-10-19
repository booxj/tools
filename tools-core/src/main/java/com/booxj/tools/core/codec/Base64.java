package com.booxj.tools.core.codec;

import com.booxj.tools.core.utils.CharsetUtil;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class Base64 {

    private static Charset DEFAULT_CHARSET = CharsetUtil.CHARSET_UTF_8;

    private static java.util.Base64.Encoder encoder = java.util.Base64.getEncoder();
    private static java.util.Base64.Decoder decoder = java.util.Base64.getDecoder();

    // ----------------------------------------------------------------------------------------------- encode

    public static byte[] encode(byte[] bytes) {
        return encoder.encode(bytes);
    }

    public static byte[] encode(String str) {
        return encoder.encode(str.getBytes(DEFAULT_CHARSET));
    }

    public static String encodeToString(byte[] bytes) {
        byte[] bytes1 = encode(bytes);
        return new String(bytes1, DEFAULT_CHARSET);
    }

    public static String encodeToString(String str) {
        byte[] bytes1 = encode(str.getBytes(DEFAULT_CHARSET));
        return new String(bytes1, DEFAULT_CHARSET);
    }

    public static String encodeToString(String str, String charset) {
        try {
            return encodeToString(str.getBytes(charset));
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    // ----------------------------------------------------------------------------------------------- decode

    public static byte[] decode(byte[] bytes) {
        return decoder.decode(bytes);
    }

    public static byte[] decode(String str) {
        return decode(str.getBytes(DEFAULT_CHARSET));
    }

    public static String decodeToString(byte[] bytes) {
        return new String(decode(bytes), DEFAULT_CHARSET);
    }

    public static String decodeToString(String str) {
        return decodeToString(str.getBytes(DEFAULT_CHARSET));
    }

    public static String decodeToString(String str, String charset) {
        try {
            return new String(decode(str), charset);
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

}
