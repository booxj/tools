package com.booxj.tools.crypto.digest;

public class MD5 extends Digester {

    public MD5() {
        super("MD5");
    }

    public MD5(String salt, int saltPosition) {
        super("MD5", salt, saltPosition);
    }

    public String digestHex16(String data) {
        return digest(data).toUpperCase().substring(8, 24);
    }

    public String digestHex32(String data) {
        return digest(data).toUpperCase();
    }

}
