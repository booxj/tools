package com.booxj.tools.core.utils;

import com.booxj.tools.core.exceptions.UtilException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class ZipUtil {

    public static final String GZIP_ENCODE_UTF_8 = CharsetUtil.UTF_8;
    public static final String GZIP_ENCODE_ISO_8859_1 = CharsetUtil.ISO_8859_1;

    // ----------------------------------------------------------------------------- Gzip

    /**
     * gzip压缩
     *
     * @param str
     * @param encoding
     * @return
     */
    public static byte[] gzipCompress(String str, String encoding) {
        if (str == null || str.length() == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gout;
        try {
            gout = new GZIPOutputStream(out);
            gout.write(str.getBytes(encoding));
            gout.close();
        } catch (Exception e) {
            throw new UtilException(e);
        }
        return out.toByteArray();
    }

    /**
     * gzip解压缩
     *
     * @param bytes
     * @return
     */
    public static byte[] gzipDecompress(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            GZIPInputStream gin = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = gin.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
        } catch (Exception e) {
            throw new UtilException(e);
        }
        return out.toByteArray();
    }

    /**
     * gzip解压缩
     *
     * @param bytes
     * @return
     */
    public static String gzipDecompressToString(byte[] bytes) {
        byte[] bytes1 = gzipDecompress(bytes);
        return StringUtil.str(bytes1, GZIP_ENCODE_UTF_8);
    }

    // ----------------------------------------------------------------------------- zip

}
