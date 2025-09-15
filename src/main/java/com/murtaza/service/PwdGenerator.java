package com.murtaza.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;

@Service
@Log
public class PwdGenerator {

    private Random random = new Random();

    public String pwdGen(int pwdSize) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < pwdSize; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }

        String randomPwd = sb.toString();
        log.info("Generated password: " + randomPwd);
        return randomPwd;
    }

}