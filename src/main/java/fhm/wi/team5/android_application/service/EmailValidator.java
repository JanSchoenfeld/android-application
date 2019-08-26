package fhm.wi.team5.android_application.service;

import android.text.TextUtils;

/**
 * Offers a static method to check if an entered email is valid.
 *
 * @author Lucas Nebot
 */

public class EmailValidator {

    /**
     * Checks if a given CharSequence is a valid email address
     *
     * @param target CharSequence of a supposed email address
     * @return boolean value if input is a valid email address or not
     */
    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) &&
                android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
