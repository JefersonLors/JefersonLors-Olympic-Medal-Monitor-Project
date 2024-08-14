package com.microsservices.country.criptografia;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;

@Service
public class CriptografiaAES {
    // @Value("${api.secret.key}")
    private String base64SecretKey = "8Gzo8wuSb9YyJ8l33rOhBfiqwhVUg+3VjF9ZlquPwmo=";
    private static final String ALGORITHM = "AES";

    private SecretKey getSecretKey() {
        byte[] keyBytes = Base64.getDecoder().decode(base64SecretKey);
        return new SecretKeySpec(keyBytes, ALGORITHM);
    }

    public String encrypt(String plainText) throws Exception {
        SecretKey key = getSecretKey();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decrypt(String encryptedText) throws Exception {
        SecretKey key = getSecretKey();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }

    //ALGORITIMO PARA GERAR CHAVES BASE64 NO TAMANHO CORRETO
    // private static SecretKey generateKey() throws Exception {
    //     KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
    //     keyGen.init(256); // Tamanho da chave em bits
    //     SecretKey secretKey = keyGen.generateKey();
    //     String base64Key = Base64.getEncoder().encodeToString(secretKey.getEncoded());
    //     System.out.println("Base64 Secret Key: " + base64Key);
    //     return secretKey;
    // }
}

