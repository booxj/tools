package com.booxj.tools.crypto;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.Key;

public class DESCoder {

    public static final String KEY_ALGORITHM = "DES";

    /**
     * 加密/解密算法/工作模式/填充方式
     */
    public static final String CIPHER_ALGORITHM = "DES/ECB/PKCS5Padding";

    /**
     * 转换密钥
     *
     * @param key 二进制密钥
     * @return Key密钥
     * @throws Exception
     */
    private static Key toKey(byte[] key) throws Exception {
        //实例化DES密钥材料
        DESKeySpec dks = new DESKeySpec(key);
        //实例化秘密密钥工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
        //生成秘密密钥
        SecretKey secretKey = keyFactory.generateSecret(dks);
        return secretKey;
    }

    /**
     * 解密
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return byte[]解密数据
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        //还原密钥
        Key k = toKey(key);
        //实例化
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        //初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, k);
        //执行操作
        return cipher.doFinal(data);
    }

    /**
     * 加密
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return byte[]加密数据
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        //还原密钥
        Key k = toKey(key);
        //实例化
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        //初始化，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, k);
        //执行操作
        return cipher.doFinal(data);
    }

    /**
     * 　　 生成密钥 <br>
     * 　　 @return byte[] 二进制密钥
     * 　　 @throws Exception
     */
    public static SecretKey initKey() throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        kg.init(56);
        //生成秘密密钥
        return kg.generateKey();
    }

    public static void main(String[] args) throws Exception {

        String str = "test string";
        SecretKey secretKey = initKey();
        Key key = toKey(secretKey.getEncoded());

        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] en = encrypt(str.getBytes(), key.getEncoded());
        System.out.println(new String(en));
        cipher.init(Cipher.DECRYPT_MODE, key);
        System.out.println(new String(decrypt(en,key.getEncoded())));

//        String key = Base64.decodeToString(toKey(null).getEncoded());
//        byte[] en = encrypt(str.getBytes(), key.getBytes());
//        System.out.println(new String(en));
//        System.out.println(new String(decrypt(en,key.getBytes())));

//        Key key = toKey(null);
//        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
//        cipher.init(Cipher.DECRYPT_MODE, key);
//        byte[] bytes = cipher.doFinal(str.getBytes());

//        byte[] bytes = cipher.doFinal(str.getBytes());
//        System.out.println(new String(bytes));
    }
}
