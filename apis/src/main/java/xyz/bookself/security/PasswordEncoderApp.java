package xyz.bookself.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderApp {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("No argument");
            return;
        }
        var encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode(args[0]));
    }

}
