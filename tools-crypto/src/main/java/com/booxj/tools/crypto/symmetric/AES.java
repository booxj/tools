package com.booxj.tools.crypto.symmetric;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.security.spec.AlgorithmParameterSpec;

public class AES {

    private static final long serialVersionUID = 1L;

    /**
     * SecretKey 负责保存对称密钥
     */
    private SecretKey secretKey;
    /**
     * Cipher负责完成加密或解密工作
     */
    private Cipher cipher;
    /**
     * 加密解密参数
     */
    private AlgorithmParameterSpec params;
}
