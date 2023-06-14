package com.arc.app.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;
import java.io.InputStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.core.io.ClassPathResource;
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

    public static InputStream getFile(String pathname) {
        try {
            return new ClassPathResource(String.format("classpath:%s", pathname)).getInputStream();
        } catch (IOException e) {
            try {
                return new ClassPathResource(pathname).getInputStream();
            } catch (IOException ex) {
                return null;
            }
        }
    }

    public static String readFile(String pathname) {
        try {
            String content = null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getFile(pathname))));
            content = reader.lines().collect(Collectors.joining("\n"));
            reader.close();
            return content;
        } catch (IOException e) {
            return null;
        }
    }
}
