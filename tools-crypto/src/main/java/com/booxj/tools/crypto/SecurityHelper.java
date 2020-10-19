package com.booxj.tools.crypto;

public class SecurityHelper {

    public static String getAlgorithm(String algorithm) {
        int end = algorithm.indexOf("/");
        if (end != -1) {
            return algorithm.substring(0, end);
        } else {
            return algorithm;
        }
    }

}
