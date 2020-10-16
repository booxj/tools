package com.booxj.tools.crypto.digest;

import com.booxj.tools.core.utils.ArrayUtil;
import com.booxj.tools.core.utils.CharsetUtil;
import com.booxj.tools.core.utils.HexUtil;
import com.booxj.tools.crypto.CryptoException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 摘要算法
 */
public class Digester {

    private MessageDigest digest;
    /** 盐值 */
    protected byte[] salt;
    /** 加盐位置，即将盐值字符串放置在数据的index数，默认0 */
    protected int saltPosition;

    protected Digester(String algorithm, String salt, int saltPosition) {
        try {
            this.digest = MessageDigest.getInstance(algorithm);
            this.salt = salt.getBytes(CharsetUtil.CHARSET_UTF_8);
            this.saltPosition = saltPosition;
        } catch (NoSuchAlgorithmException e) {
            throw new CryptoException(e);
        }
    }

    protected Digester(String algorithm) {
        this(algorithm, "", 0);
    }

    protected String digest(String data) {
        byte[] bytes = digest(data.getBytes(CharsetUtil.CHARSET_UTF_8));
        return HexUtil.encodeHexStr(bytes);
    }

    /**
     * 生成摘要，考虑加盐和重复摘要次数
     *
     * @param data 数据bytes
     * @return 摘要bytes
     */
    protected byte[] digest(byte[] data) {
        byte[] result;
        if (ArrayUtil.isEmpty(this.salt)) {
            this.digest.update(data);
            return this.digest.digest();
        } else {
            if (this.saltPosition <= 0) {    // 加盐在开头，自动忽略空盐值
                this.digest.update(salt);
                this.digest.update(data);
                result = this.digest.digest();
            } else if (this.saltPosition >= data.length) {  // 加盐在末尾，自动忽略空盐值
                this.digest.update(data);
                this.digest.update(salt);
                result = this.digest.digest();
            } else {
                this.digest.update(data, 0, this.saltPosition);
                this.digest.update(this.salt);
                this.digest.update(data, this.saltPosition, data.length - this.saltPosition);
                result = this.digest.digest();
            }
        }
        return result;
    }

}
