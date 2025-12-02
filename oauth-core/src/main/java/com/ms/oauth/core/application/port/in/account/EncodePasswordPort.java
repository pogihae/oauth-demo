package com.ms.oauth.core.application.port.in.account;

public interface EncodePasswordPort {

    String encodePassword(String rawPassword);

    boolean matched(String encodedPassword, String rawPassword);
}
