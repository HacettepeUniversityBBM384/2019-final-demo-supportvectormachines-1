package com.svms.sepetle.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Encoder {

    private static Encoder passEncoding = new Encoder();
    public BCryptPasswordEncoder passwordEncoder;

    public static Encoder getInstance() {
        if (passEncoding != null)
            return passEncoding;
        return new Encoder();
    }

    private Encoder() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

}
