package com.microsservices.country.service.criptografia;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class Encoder_Decoder {
    
    public static String enconderURL(String id){
        try {
            return URLEncoder.encode(id, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new IllegalAccessError("Error at enconde");
        }
    }

    public static String deconderURL(String id){
        try {
            return URLDecoder.decode(id, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new IllegalAccessError("Error at deconde");
        }
    }
}
