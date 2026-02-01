package com.sicredi.votacao.application.utils;

public class CpfValidator {
    public static boolean isValid(String cpf) {
        if (cpf == null) return false;
        String num = cpf.replaceAll("\\D", "");
        if (num.length() != 11) return false;
        if (num.chars().distinct().count() == 1) return false;
        try {
            int d1 = 0, d2 = 0;
            for (int i = 0; i < 9; i++) {
                int dig = num.charAt(i) - '0';
                d1 += dig * (10 - i);
                d2 += dig * (11 - i);
            }
            d1 = 11 - (d1 % 11);
            if (d1 >= 10) d1 = 0;
            d2 += d1 * 2;
            d2 = 11 - (d2 % 11);
            if (d2 >= 10) d2 = 0;
            return d1 == (num.charAt(9) - '0') && d2 == (num.charAt(10) - '0');
        } catch (Exception e) {
            return false;
        }
    }
}