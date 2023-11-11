package com.example.shop.common.utils;

import java.util.Random;

//随机生成用户名
public class GeneratorCodeUtils {
    /**
     * 生成6位随机数
     * @return
     */
    public static String generateCode() {
        int n = 6;
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            code.append(Integer.valueOf(random.nextInt(10)).toString());
        }
        return code.toString();
    }
}
