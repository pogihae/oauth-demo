package com.ms.oauth.core.application.service;

import com.ms.oauth.core.application.port.in.account.EncodeAccountPasswordPort;
import com.ms.oauth.core.application.port.in.client.EncodeClientPasswordPort;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class EncodePasswordService implements EncodeAccountPasswordPort, EncodeClientPasswordPort {

    private static MessageDigest digest;

    @Override
    public String encodePassword(String rawPassword) {
        if (rawPassword == null) {
            return null;
        }

        byte[] encodedHash = getDigest().digest(rawPassword.getBytes());
        return new String(encodedHash);
    }

    @Override
    public boolean matched(String encodedPassword, String rawPassword) {
        if (encodedPassword == null || rawPassword == null) {
            return false;
        }

        String rawEncodedPassword = encodePassword(rawPassword);
        return encodedPassword.equals(rawEncodedPassword);
    }

    private MessageDigest getDigest() {
        if (digest == null) {
            try {
                digest = MessageDigest.getInstance("SHA-256");
                return digest;
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
        return digest;
    }
}
