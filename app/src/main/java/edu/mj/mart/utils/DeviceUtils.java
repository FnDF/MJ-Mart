package edu.mj.mart.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeviceUtils {

    public static boolean validateEmailAddress(String emailAddress) {
        Pattern regexPattern = Pattern.compile("^[(a-zA-Z-0-9-_+.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$");
        Matcher regMatcher = regexPattern.matcher(emailAddress);
        return regMatcher.matches();
    }

    public static boolean validPhoneNumber(String phone) {
        if (TextUtils.isEmpty(phone)) return false;
        if (phone.length() < 9) return false;
        try {
            Integer.parseInt(phone);
        } catch (Exception exception) {
            return false;
        }
        return true;
    }
}
