package fhm.wi.team5.android_application.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class which offers a static way to check if a passwords meets our requirements
 *
 * @author Jan Schönfeld
 */

public class PasswordValidator {

    public static boolean isValidPassword(final String pw) {
        Pattern pattern;
        Matcher matcher;
        final String PW_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,}$";
        pattern = Pattern.compile(PW_PATTERN);
        matcher = pattern.matcher(pw);
        return matcher.matches();
    }
}
