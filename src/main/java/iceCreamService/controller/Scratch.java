package iceCreamService.controller;

import org.mindrot.jbcrypt.BCrypt;

public class Scratch {
    public static void main(String[] args) {
        String ishukapassword = "ishukapassword";
        hashPassowrd(ishukapassword);
        String passowrd = hashPassowrd(ishukapassword);

        System.out.println(BCrypt.checkpw(ishukapassword, passowrd));
    }

    private static String hashPassowrd(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }
}
