package com.booxj.tools.crypto.symmetric;

import com.booxj.tools.core.codec.Base64;
import com.booxj.tools.core.utils.CharsetUtil;
import com.booxj.tools.crypto.CryptoException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.security.Key;

public abstract class SymmetricCrypto {

    private Cipher cipher;
    private Key key;

    public SymmetricCrypto(String algorithm) {
        try {
            key = toKey(initKey());
            this.cipher = Cipher.getInstance(algorithm);
        } catch (Exception e) {
            throw new CryptoException(e);
        }
    }

    protected abstract SecretKey initKey() throws CryptoException;

    protected abstract Key toKey(SecretKey secretKey) throws CryptoException;

    public byte[] encrypt(byte[] data) {
        try {
            this.cipher.init(Cipher.ENCRYPT_MODE, key);
            return this.cipher.doFinal(data);
        } catch (Exception e) {
            throw new CryptoException(e);
        }
    }

    public byte[] encrypt(String data) {
        return encrypt(data.getBytes(CharsetUtil.CHARSET_UTF_8));
    }

    public String encryptToStr(String data) {
        byte[] bytes = encrypt(data);
        // 字符串和字节数组之间相互转换会导致字符增减，使用base64过渡，保证字符长度不变
        return Base64.encodeToString(bytes);
    }

    public byte[] decrypt(byte[] data) {
        try {
            this.cipher.init(Cipher.DECRYPT_MODE, key);
            return this.cipher.doFinal(data);
        } catch (Exception e) {
            throw new CryptoException(e);
        }
    }

    public byte[] decrypt(String data) {
        // 字符串和字节数组之间相互转换会导致字符增减，使用base64过渡，保证字符长度不变
        return decrypt(Base64.decode(data));
    }

    public String decryptToStr(String data) {
        byte[] bytes = decrypt(data);
        return new String(bytes, CharsetUtil.CHARSET_UTF_8);
    }

}
