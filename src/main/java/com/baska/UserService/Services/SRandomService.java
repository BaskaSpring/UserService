package com.baska.UserService.Services;

import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class SRandomService {

    public String getSalt(int countByte, int lengthStr)    {
        String str ="";
        while (true) {
            SecureRandom sr = null;
            try {
                sr = SecureRandom.getInstance("SHA1PRNG");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            byte[] salt = new byte[countByte];
            sr.nextBytes(salt);
            Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
            str = encoder.encodeToString(salt);
            if (str.length()<=lengthStr){
                break;
            }
        }
        return str;
    }

    public byte[] strToByte(String str){
        Base64.Decoder decoder = Base64.getUrlDecoder();
        byte[] b = decoder.decode(str);
        return  b;
    }
}
