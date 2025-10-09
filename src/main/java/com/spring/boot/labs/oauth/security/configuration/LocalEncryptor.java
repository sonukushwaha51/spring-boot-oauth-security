package com.spring.boot.labs.oauth.security.configuration;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;

import java.nio.charset.StandardCharsets;

public class LocalEncryptor {
//    public static void main(String[] args) {
//        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
//        encryptor.setAlgorithm("PBEWithMD5AndDES");
//        encryptor.setPassword("JmZV8pnQeUcOovP"); // your encryption password
//        String encrypted = encryptor.encrypt("97b9e34ac9682f43fea6d0fe41b931f2346190b1");
//        String decrypted = encryptor.decrypt(encrypted);
//        System.out.println(decrypted);
//        System.out.println(new String(("ENC("+encrypted+")").getBytes(), StandardCharsets.UTF_8));
//    }
}

