package url.genchi.password;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Created by mac on 2016/6/3.
 */
public class Password {
    private static final int workload = 12;
    public static String encrypt(String plaintext_password) {
        String salt = BCrypt.gensalt(workload);
        String hashed_password = BCrypt.hashpw(plaintext_password, salt);
        return(hashed_password);
    }
    public static boolean isValidatePassword(String password, String hashPassword) {
        return BCrypt.checkpw(password, hashPassword);
    }
    public static void main(String[] args) {
        String tmp = "b9134034";
        encrypt(tmp);
    }
}
