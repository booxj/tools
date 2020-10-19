package com.booxj.tools.crypto.asymmetric;

import com.booxj.tools.core.codec.Base64;
import com.booxj.tools.core.utils.CharsetUtil;
import com.booxj.tools.crypto.CryptoException;
import com.booxj.tools.crypto.SecurityHelper;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public abstract class AsymmetricCrypto {

    /** Cipher负责完成加密或解密工作 */
    protected Cipher cipher;
    protected PublicKey publicKey;
    protected PrivateKey privateKey;

    public AsymmetricCrypto(String algorithm, int size) {
        try {
            KeyPair keyPair = generateKeyPair(algorithm, size);
            this.publicKey = keyPair.getPublic();
            this.privateKey = keyPair.getPrivate();

            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            this.cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        } catch (Exception e) {
            throw new CryptoException(e);
        }
    }

    public AsymmetricCrypto(String algorithm, KeyPair keyPair) {
        try {
            this.publicKey = keyPair.getPublic();
            this.privateKey = keyPair.getPrivate();

            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            this.cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        } catch (Exception e) {
            throw new CryptoException(e);
        }
    }

    public AsymmetricCrypto(String algorithm, String publicKey, String privateKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(SecurityHelper.getAlgorithm(algorithm));

            // publicKey
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKey.getBytes(CharsetUtil.CHARSET_UTF_8));
            this.publicKey = keyFactory.generatePublic(x509KeySpec);

            // privateKey
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey.getBytes(CharsetUtil.CHARSET_UTF_8));
            this.privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
            this.cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        } catch (Exception e) {
            throw new CryptoException(e);
        }
    }

    public byte[] encrypt(byte[] data) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new CryptoException(e);
        }
    }

    public String encryptToString(String data) {
        byte[] bytes = encrypt(data.getBytes(CharsetUtil.CHARSET_UTF_8));
        return Base64.encodeToString(bytes);
    }

    public byte[] decrypt(byte[] data) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new CryptoException(e);
        }
    }

    public String decryptToString(String data) {
        byte[] bytes = decrypt(Base64.decode(data));
        return new String(bytes, CharsetUtil.CHARSET_UTF_8);
    }

    protected KeyPair generateKeyPair(String algorithm, int size) {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(algorithm);
            keyPairGen.initialize(size);
            return keyPairGen.generateKeyPair();
        } catch (Exception e) {
            throw new CryptoException(e);
        }
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

}
