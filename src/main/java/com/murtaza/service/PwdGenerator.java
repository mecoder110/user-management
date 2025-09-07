package com.murtaza.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@Log
public class PwdGenerator {

    public String pwdGen() {
        String chars = "0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        int length = 4; // password length

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }

        String randomPwd = sb.toString();
        log.info("Generated password: " + randomPwd);
        return randomPwd;
    }

}