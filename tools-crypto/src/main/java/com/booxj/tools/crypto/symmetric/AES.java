package com.booxj.tools.crypto.symmetric;

import com.booxj.tools.crypto.CryptoException;
import com.booxj.tools.crypto.SecurityHelper;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class AES extends SymmetricCrypto{

    private static String AES_ALGORITHM ="AES/ECB/PKCS5Padding";

    public AES() {
        super(AES_ALGORITHM);
    }

    @Override
    protected SecretKey initKey() {
        try {
            KeyGenerator kg = KeyGenerator.getInstance(SecurityHelper.getAlgorithm(AES_ALGORITHM));
            kg.init(128);
            return kg.generateKey();
        } catch (Exception e) {
            throw new CryptoException(e);
        }
    }

    @Override
    protected Key toKey(SecretKey secretKey) {
       return new SecretKeySpec(secretKey.getEncoded(), SecurityHelper.getAlgorithm(AES_ALGORITHM));
    }

}
