package com.ms.oauth.core.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SHA256Utils {

    public static MessageDigest messageDigest;

    public static String encode(String raw) {
        byte[] encodedHash = getMessageDigest().digest(raw.getBytes());
        return new String(encodedHash);
    }

    private static MessageDigest getMessageDigest() {
        if (messageDigest == null) {
            try {
                messageDigest = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
        return messageDigest;
    }
}
