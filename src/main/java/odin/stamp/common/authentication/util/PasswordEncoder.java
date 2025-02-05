package odin.stamp.common.authentication.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Component
public class PasswordEncoder {

    @Value("${password.salt}")
    private String salt;

    public String getEncrypt(String pwd){
        String result = "";

        try{

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update((pwd+salt).getBytes());
            byte[] pwdSalt = md.digest();

            StringBuffer sb = new StringBuffer();
            for(byte b : pwdSalt){
                sb.append(String.format("%02x", b));
            }

            result = sb.toString();

        } catch (NoSuchAlgorithmException e){
            log.error("잘못된 알고리즘 사용");
        }
        return result;
    }
}
