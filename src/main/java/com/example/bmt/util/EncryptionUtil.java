package com.example.bmt.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class EncryptionUtil {
  // Must be 16, 24, or 32 characters
  private static final String KEY = "BMTSecretKey1234"; // 16 chars

  public static String encrypt(String plain) {
    try {
      SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(), "AES");
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      cipher.init(Cipher.ENCRYPT_MODE, secretKey);
      return Base64.getEncoder().encodeToString(cipher.doFinal(plain.getBytes()));
    } catch (Exception e) { throw new RuntimeException(e); }
  }

  public static String decrypt(String encrypted) {
    try {
      SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(), "AES");
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      cipher.init(Cipher.DECRYPT_MODE, secretKey);
      return new String(cipher.doFinal(Base64.getDecoder().decode(encrypted)));
    } catch (Exception e) { throw new RuntimeException(e); }
  }
}
