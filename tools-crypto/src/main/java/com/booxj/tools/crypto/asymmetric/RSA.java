package com.booxj.tools.crypto.asymmetric;

import java.security.KeyPair;

public class RSA extends AsymmetricCrypto {

    private static String RSA_ALGORITHM = "RSA";
    private static int RSA_ALGORITHM_SIZE = 512;

    public RSA() {
        this(RSA_ALGORITHM_SIZE);
    }

    public RSA(int size) {
        super(RSA_ALGORITHM, size);
    }

    public RSA(KeyPair keyPair) {
        super(RSA_ALGORITHM, keyPair);
    }

}
