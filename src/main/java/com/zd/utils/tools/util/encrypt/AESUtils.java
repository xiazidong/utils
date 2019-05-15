package com.zd.utils.tools.util.encrypt;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;


/**
 * AES加解密
 */
class AESUtils {
    /**
     * 默认秘钥
     */
	private static final String KEY = "NOPO3nzMD3dndwS0MccuMeXCHgVlGOoYyFwLdS24Im2e7YyhB0wrUsyYf0";

    /**
     * AES加密
     *
     * @param value 待加密内容
     * @param key   秘钥
     */
    public static String aesEncrypt(String value, String key) {
        key = key == null ? AESUtils.KEY : key;
        String result = null;
        try {
            if (value != null && !"".equals(value.trim())) {
                result = base64Encode(aesEncryptToBytes(value, key));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * AES解密
     *
     * @param value 待解密内容
     * @param key   秘钥
     */
    public static String aesDecrypt(String value, String key) {
        key = key == null ? AESUtils.KEY : key;
        String result = null;
        try {
            if (value != null && !"".equals(value.trim())) {
                result = aesDecryptByBytes(base64Decode(value), key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    private static String base64Encode(byte[] bytes){
        return Base64Utils.encode(bytes);
    }

    @SuppressWarnings("static-access")
    private static byte[] base64Decode(String base64Code){
        return base64Code == null ? null : Base64Utils.decode(base64Code);
    }
      
    private static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {  
        KeyGenerator kgen = KeyGenerator.getInstance("AES");  
        kgen.init(128, new SecureRandom(encryptKey.getBytes()));  
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));  
        return cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
    }  
      
    private static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {  
        KeyGenerator kgen = KeyGenerator.getInstance("AES");  
        kgen.init(128, new SecureRandom(decryptKey.getBytes()));  
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));  
        byte[] decryptBytes = cipher.doFinal(encryptBytes);  
        return new String(decryptBytes);
    }  
    
}
