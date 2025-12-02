package com.ms.oauth.core.application.port.in.client;

public interface EncodeClientPasswordPort {

    String encodePassword(String rawPassword);

    boolean matched(String encodedPassword, String rawPassword);
}
