package study.signUpUsingJDBC.util;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Component
public class Sha256Util {
    /**
     * SHA-256으로 해싱하는 메소드
     */
    public String makeSalt() {
        return UUID.randomUUID().toString();
    }

    public String sha256(String msg, String salt) throws NoSuchAlgorithmException {
        msg += salt;
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(msg.getBytes());

        return bytesToHex(md.digest());
    }

    /**
     * 바이트를 헥스값으로 변환한다.
     */
    public String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b: bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
}
