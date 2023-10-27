package org.peachSpring.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class PasswordEncoderImpl extends BCryptPasswordEncoder {

    public PasswordEncoderImpl() {
        super();
    }

    public PasswordEncoderImpl(int strength) {
        super(strength);
    }

    public PasswordEncoderImpl(BCryptVersion version) {
        super(version);
    }

    public PasswordEncoderImpl(BCryptVersion version, SecureRandom random) {
        super(version, random);
    }

    public PasswordEncoderImpl(int strength, SecureRandom random) {
        super(strength, random);
    }

    public PasswordEncoderImpl(BCryptVersion version, int strength) {
        super(version, strength);
    }

    public PasswordEncoderImpl(BCryptVersion version, int strength, SecureRandom random) {
        super(version, strength, random);
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return super.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return super.matches(rawPassword, encodedPassword);
    }

    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        return super.upgradeEncoding(encodedPassword);
    }
}
