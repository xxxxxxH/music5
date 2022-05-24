package jboster.poshe.one.utils;


import android.util.Base64;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {

    // 采用对称分组密码体制,密钥长度的最少支持为128、192、256
    String key = "abcdefghijklmnop";
    // 初始化向量参数，AES 为16bytes. DES 为8bytes， 16*8=128
    String initVector = "0000000000000000";
    IvParameterSpec iv;
    SecretKeySpec skeySpec;
    Cipher cipher;

    private static class HOLDER {

        private static final AESUtil instance = new AESUtil();
    }

    public static AESUtil getInstance() {
        return HOLDER.instance;
    }

    private AESUtil() {
        try {
            iv = new IvParameterSpec(initVector.getBytes(StandardCharsets.UTF_8));
            skeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
            // 这是CBC模式
            // cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            // 默认就是ECB模式
            cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    //加密
    public String encrypt(String value) {
        try {
            // CBC模式需要传入向量，ECB模式不需要
            // cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.encodeToString(encrypted, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //解密
    public String decrypt(String encrypted) {
        try {
            // CBC模式需要传入向量，ECB模式不需要
            // cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] original = cipher.doFinal(Base64.decode(encrypted, Base64.DEFAULT));
            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
