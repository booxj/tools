package com.booxj.tools.crypto;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 安全相关工具类<br>
 * 加密分为三种：<br>
 * 1、对称加密（symmetric），例如：AES、DES等<br>
 * 2、非对称加密（asymmetric），例如：RSA、DSA等<br>
 * 3、摘要加密（digest），例如：MD5、SHA-1、SHA-256、HMAC等<br>
 */
public class SecureUtil {

    /**
     * 创建{@link Cipher}
     *
     * @param algorithm 算法
     * @return {@link Cipher}
     */
    public static Cipher createCipher(String algorithm) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            return cipher;
        } catch (Exception e) {
            throw new CryptoException(e);
        }
    }

    /**
     * 创建{@link MessageDigest}
     *
     * @param algorithm 算法
     * @return {@link MessageDigest}
     */
    public static MessageDigest createMessageDigest(String algorithm) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            return messageDigest;
        } catch (NoSuchAlgorithmException e) {
            throw new CryptoException(e);
        }
    }

    /**
     * 创建{@link Mac}
     *
     * @param algorithm 算法
     * @return {@link Mac}
     */
    public static Mac createMac(String algorithm) {
        try {
            Mac mac = Mac.getInstance(algorithm);
            return mac;
        } catch (NoSuchAlgorithmException e) {
            throw new CryptoException(e);
        }
    }
}
