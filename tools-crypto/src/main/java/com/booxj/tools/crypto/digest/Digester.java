package com.booxj.tools.crypto.digest;

import com.booxj.tools.core.utils.ArrayUtil;
import com.booxj.tools.core.utils.CharsetUtil;
import com.booxj.tools.core.utils.HexUtil;
import com.booxj.tools.core.utils.StringUtil;
import com.booxj.tools.crypto.SecureUtil;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.security.MessageDigest;

/**
 * 摘要算法<br>
 */
public class Digester implements Serializable {

    private static final long serialVersionUID = 1L;

    private MessageDigest digest;
    /**
     * 盐值
     */
    protected byte[] salt;
    /**
     * 加盐位置，即将盐值字符串放置在数据的index数，默认0
     */
    protected int saltPosition;
    /**
     * 散列次数
     */
    protected int digestCount;


    public Digester(String algorithm) {
        this.digest = SecureUtil.createMessageDigest(algorithm);
    }

    public Digester algorithm(String algorithm) {
        this.digest = SecureUtil.createMessageDigest(algorithm);
        return this;
    }

    public Digester salt(byte[] salt) {
        this.salt = salt;
        return this;
    }

    public Digester saltPosition(int saltPosition) {
        this.saltPosition = saltPosition;
        return this;
    }

    public Digester digestCount(int digestCount) {
        this.digestCount = digestCount;
        return this;
    }

    /**
     * 生成摘要，并转为16进制字符串<br>
     *
     * @param data 被摘要数据
     * @return 摘要
     */
    public String digestHex(byte[] data) {
        return HexUtil.encodeHexStr(digest(data));
    }

    /**
     * 生成文件摘要
     *
     * @param data 被摘要数据
     * @return 摘要
     */
    public String digestHex(String data) {
        return digestHex(data, CharsetUtil.CHARSET_UTF_8);
    }

    /**
     * 生成文件摘要，并转为16进制字符串
     *
     * @param data    被摘要数据
     * @param charset 编码
     * @return 摘要
     */
    public String digestHex(String data, Charset charset) {
        return HexUtil.encodeHexStr(digest(data, charset));
    }

    /**
     * 生成文件摘要
     *
     * @param data 被摘要数据
     * @return 摘要
     */
    public byte[] digest(String data, Charset charset) {
        return digest(StringUtil.bytes(data, charset));
    }

    /**
     * 生成摘要，考虑加盐和重复摘要次数
     *
     * @param data 数据bytes
     * @return 摘要bytes
     */
    public byte[] digest(byte[] data) {
        byte[] result;
        if (this.saltPosition <= 0) {
            // 加盐在开头，自动忽略空盐值
            result = doDigest(this.salt, data);
        } else if (this.saltPosition >= data.length) {
            // 加盐在末尾，自动忽略空盐值
            result = doDigest(data, this.salt);
        } else if (ArrayUtil.isNotEmpty(this.salt)) {
            // 加盐在中间
            this.digest.update(data, 0, this.saltPosition);
            this.digest.update(this.salt);
            this.digest.update(data, this.saltPosition, data.length - this.saltPosition);
            result = this.digest.digest();
        } else {
            // 无加盐
            result = doDigest(data);
        }
        return resetAndRepeatDigest(result);
    }

    /**
     * 生成摘要
     *
     * @param datas 数据bytes
     * @return 摘要bytes
     */
    private byte[] doDigest(byte[]... datas) {
        for (byte[] data : datas) {
            if (null != data) {
                this.digest.update(data);
            }
        }
        return this.digest.digest();
    }

    /**
     * 重复计算摘要，取决于{@link #digestCount} 值<br>
     * 每次计算摘要前都会重置{@link #digest}
     *
     * @param digestData 第一次摘要过的数据
     * @return 摘要
     */
    private byte[] resetAndRepeatDigest(byte[] digestData) {
        final int digestCount = Math.max(1, this.digestCount);
        reset();
        for (int i = 0; i < digestCount - 1; i++) {
            digestData = doDigest(digestData);
            reset();
        }
        return digestData;
    }

    public Digester reset() {
        this.digest.reset();
        return this;
    }
}
