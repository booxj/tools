package com.booxj.tools.core.codec;

import com.booxj.tools.core.utils.CharsetUtil;

import java.io.UnsupportedEncodingException;

public class Base64 {

    private static String DEFAULT_CHARSET = CharsetUtil.UTF_8;

    private static java.util.Base64.Encoder encoder = java.util.Base64.getEncoder();
    private static java.util.Base64.Decoder decoder = java.util.Base64.getDecoder();


    public static byte[] encode(byte[] bytes) {
        return encoder.encode(bytes);
    }

    public static String encode(String str) {
        return encode(str, DEFAULT_CHARSET);
    }

    public static String encode(String str, String charset) {
        try {
            return encoder.encodeToString(str.getBytes(charset));
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public static byte[] decode(byte[] bytes) {
        return decoder.decode(bytes);
    }

    public static String decode(String str) {
        return decode(str, DEFAULT_CHARSET);
    }

    public static String decode(String str, String charset) {
        try {
            return new String(decoder.decode(str), charset);
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        }
    }
}
