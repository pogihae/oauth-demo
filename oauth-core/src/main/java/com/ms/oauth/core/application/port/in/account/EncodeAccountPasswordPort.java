package com.ms.oauth.core.application.port.in.account;

public interface EncodeAccountPasswordPort {

    String encodePassword(String rawPassword);

    boolean matched(String encodedPassword, String rawPassword);
}
