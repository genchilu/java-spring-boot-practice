package url.genchi.encrypt;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Created by mac on 2016/6/3.
 */
public class Encrypt {
    private static int workload = 12;
    public static String encrypt(String plaintext_password) {
        String salt = BCrypt.gensalt(workload);
        String hashed_password = BCrypt.hashpw(plaintext_password, salt);
        return(hashed_password);
    }

    public static void main(String[] args) {
        String tmp = "b9134034";
        System.out.println(encrypt(tmp));
    }
}
