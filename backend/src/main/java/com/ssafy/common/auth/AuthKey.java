package com.ssafy.common.auth;

import java.util.Random;

public class AuthKey {
    private int size;

    public String getKey(int size) {
        this.size = size;
        return init();
    }

    private String init() {
        Random rand = new Random();
        StringBuffer sb = new StringBuffer();

        int num = 0;

        do {
            num = rand.nextInt(75) + 48;

            if ((num >= 48 && num <= 57) || (num >= 65 && num <= 90) || (num >= 97 && num <= 122)) {
                sb.append((char) num);
            } else {
                continue;
            }
        } while (sb.length() < size);

        return sb.toString();
    }
}
