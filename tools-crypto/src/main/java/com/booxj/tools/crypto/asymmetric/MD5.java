package com.booxj.tools.crypto.asymmetric;

import com.booxj.tools.crypto.digest.Digester;

import java.nio.charset.Charset;

public class MD5 extends Digester {

    public MD5() {
        super("MD5");
    }

    public MD5(byte[] salt) {
        this();
        this.salt = salt;
        this.saltPosition = 0;
        this.digestCount = 1;
    }

    /**
     * 生成16位MD5摘要
     *
     * @param data 数据
     * @return 16位MD5摘要
     */
    public String digestHex16(byte[] data) {
        return digestHex(data);
    }

    /**
     * 生成16位MD5摘要
     *
     * @param data 数据
     * @return 16位MD5摘要
     */
    public String digestHex16(String data) {
        return digestHex(data).substring(8, 24);
    }

    /**
     * 生成16位MD5摘要
     *
     * @param data    数据
     * @param charset 编码
     * @return 16位MD5摘要
     */
    public String digestHex16(String data, Charset charset) {
        return (digestHex(data, charset)).substring(8, 24);
    }

}
