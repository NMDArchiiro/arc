package com.arc.app.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;

import java.text.DecimalFormat;

public abstract class ConvertUtils {
    public static String getHashPassword(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    public static Double roundTwoNumber(Double value){
        DecimalFormat df = new DecimalFormat("###.##");
        return ObjectUtils.isEmpty(value) ? 0d :  Double.parseDouble(df.format(value));
    }

    public static Double divAndRoundTwoNumber(Long numerator, Long denominator){
        DecimalFormat df = new DecimalFormat("###.##");
        if(denominator == null || denominator == 0 || numerator == null || numerator == 0) return  0d;
        return ObjectUtils.isEmpty(denominator) ? 0d :  Double.parseDouble(df.format((double)numerator/(double)denominator * 100));
    }
}
