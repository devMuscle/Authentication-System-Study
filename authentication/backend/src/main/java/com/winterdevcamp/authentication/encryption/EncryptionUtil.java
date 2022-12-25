package com.winterdevcamp.authentication.encryption;

import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class EncryptionUtil {

    private final String EncryptAlgorithm = "SHA-256";

    public String getSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] saltByte = new byte[16];

        secureRandom.nextBytes(saltByte);

        String salt = new String(Base64.getEncoder().encode(saltByte));

        return salt;
    }

    public String encryptBySHA245(String password) throws NoSuchAlgorithmException {
        String pwWithSalt = password+getSalt();

        MessageDigest messageDigest = MessageDigest.getInstance(EncryptAlgorithm);

        messageDigest.update(pwWithSalt.getBytes());

        String encryptedPw = String.format("%064x", new BigInteger(1, messageDigest.digest()));

        return encryptedPw;
    }
}
