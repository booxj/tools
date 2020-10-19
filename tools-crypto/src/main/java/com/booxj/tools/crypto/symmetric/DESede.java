package com.booxj.tools.crypto.symmetric;

import com.booxj.tools.crypto.CryptoException;
import com.booxj.tools.crypto.SecurityHelper;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.security.Key;

public class DESede extends SymmetricCrypto {

    private static String DESede_ALGORITHM = "DESede/ECB/PKCS5Padding";

    public DESede() {
        super(DESede_ALGORITHM);
    }

    @Override
    protected SecretKey initKey() throws CryptoException {
        try {
            KeyGenerator kg = KeyGenerator.getInstance(SecurityHelper.getAlgorithm(DESede_ALGORITHM));
            kg.init(168);
            return kg.generateKey();
        } catch (Exception e) {
            throw new CryptoException(e);
        }
    }

    @Override
    protected Key toKey(SecretKey secretKey) throws CryptoException {
        try {
            DESedeKeySpec dks = new DESedeKeySpec(secretKey.getEncoded());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(SecurityHelper.getAlgorithm(DESede_ALGORITHM));
            return keyFactory.generateSecret(dks);
        } catch (Exception e) {
            throw new CryptoException(e);
        }
    }
}
