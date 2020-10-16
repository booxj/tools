package com.booxj.tools.core.codec;

import com.booxj.tools.core.utils.CharsetUtil;

import java.io.UnsupportedEncodingException;

public class Base64 {

    private static String DEFAULT_CHARSET = CharsetUtil.UTF_8;

    private static java.util.Base64.Encoder encoder = java.util.Base64.getEncoder();
    private static java.util.Base64.Decoder decoder = java.util.Base64.getDecoder();

    // ----------------------------------------------------------------------------------------------- encode

    public static byte[] encode(byte[] bytes) {
        return encoder.encode(bytes);
    }

    public static byte[] encode(String str) throws UnsupportedEncodingException {
        return encoder.encode(str.getBytes(DEFAULT_CHARSET));
    }

    public static String encodeToString(byte[] bytes) throws UnsupportedEncodingException {
        byte[] bytes1 = encoder.encode(bytes);
        return new String(bytes1, DEFAULT_CHARSET);
    }

    public static String encodeToString(String str, String charset) {
        try {
            return encoder.encodeToString(str.getBytes(charset));
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    // ----------------------------------------------------------------------------------------------- decode

    public static byte[] decode(byte[] bytes) {
        return decoder.decode(bytes);
    }

    public static byte[] decode(String str) throws UnsupportedEncodingException {
        return decoder.decode(str.getBytes(DEFAULT_CHARSET));
    }

    public static String decodeToString(byte[] bytes) throws UnsupportedEncodingException {
        return new String(bytes, DEFAULT_CHARSET);
    }

    public static String decodeToString(String str, String charset) {
        try {
            return new String(decoder.decode(str), charset);
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        }
    }
}
