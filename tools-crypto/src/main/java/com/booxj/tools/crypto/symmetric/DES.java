package com.booxj.tools.crypto.symmetric;

import com.booxj.tools.crypto.CryptoException;
import com.booxj.tools.crypto.SecurityHelper;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.Key;

public class DES extends SymmetricCrypto {

    private static String DES_ALGORITHM = "DES/ECB/PKCS5Padding";

    public DES() {
        super(DES_ALGORITHM);
    }


    @Override
    protected SecretKey initKey() throws CryptoException {
        try {
            KeyGenerator kg = KeyGenerator.getInstance(SecurityHelper.getAlgorithm(DES_ALGORITHM));
            kg.init(56);
            return kg.generateKey();
        } catch (Exception e) {
            throw new CryptoException(e);
        }
    }

    @Override
    protected Key toKey(SecretKey secretKey) throws CryptoException {
        try {
            DESKeySpec dks = new DESKeySpec(secretKey.getEncoded());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(SecurityHelper.getAlgorithm(DES_ALGORITHM));
            return keyFactory.generateSecret(dks);
        } catch (Exception e) {
            throw new CryptoException(e);
        }
    }

}
