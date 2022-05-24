package jboster.poshe.one.utils

import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESKeySpec
import javax.crypto.spec.IvParameterSpec
import android.util.Base64
import java.nio.charset.Charset

/** 加密KEY  */
private val KEY = "7;9Ku7;:84VG*B78".toByteArray()

/** 算法  */
private const val ALGORITHM = "DES"

/** IV  */
private val IV = "sHjrydLq".toByteArray()

/** TRANSFORMATION  */
private const val TRANSFORMATION = "DES/CBC/PKCS5Padding"

private const val code = 0

/**
 * 将字符串进行DES加密
 * @param source 未加密源字符串
 * @return 加密后字符串
 */
fun encrypt(source: String): String? {
    var retByte: ByteArray? = null

    // Create SecretKey object
    var dks: DESKeySpec? = null
    try {
        dks = DESKeySpec(KEY)
        val keyFactory: SecretKeyFactory = SecretKeyFactory.getInstance(ALGORITHM)
        val securekey: SecretKey = keyFactory.generateSecret(dks)

        // Create IvParameterSpec object with initialization vector
        val spec = IvParameterSpec(IV)

        // Create Cipter object
        val cipher: Cipher = Cipher.getInstance(TRANSFORMATION)

        // Initialize Cipher object
        cipher.init(Cipher.ENCRYPT_MODE, securekey, spec)

        // Decrypting data
        retByte = cipher.doFinal(source.toByteArray())
        var result = ""
        result = if (code == 0) {
            String(retByte, Charset.forName("ISO-8859-1"))
        } else if (code == 1) {
            Base64.encodeToString(retByte, 2)
        } else {
            String(retByte)
        }
        return result
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}