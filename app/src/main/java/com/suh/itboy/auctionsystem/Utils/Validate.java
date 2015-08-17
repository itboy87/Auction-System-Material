package com.suh.itboy.auctionsystem.Utils;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by itboy on 8/1/2015.
 */
public class Validate {

    public static boolean isEmpty(String $value) {

        //String.isEmpty() throws exception on null
        return $value != null && $value.trim().isEmpty();
    }

    public static boolean email(String email) {

        return !isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email.toString()).matches();
    }

    public static boolean password(String pass, int min, int max) {
        if (isEmpty(pass))
            return false;

        int length = pass.length();

        if (min != 0) {
            if (length < min)
                return false;
        }

        if (max != 0) {
            if (length > max)
                return false;
        }

        return true;
    }

    public static boolean isInt(String value) {
        return TextUtils.isDigitsOnly(value);
    }

    public static boolean full_name(String name) {
        String regx = "^[\\p{L} .'-]+$";
        Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(name);

        return matcher.find();
    }


    public static abstract class TextValidator implements TextWatcher {
        private final TextView textView;

        public TextValidator(TextView textView) {
            this.textView = textView;
        }

        public abstract void validate(TextView textView, String text);

        @Override
        final public void afterTextChanged(Editable s) {
            String text = textView.getText().toString();
            validate(textView, text);
        }

        @Override
        final public void beforeTextChanged(CharSequence s, int start, int count, int after) { /* Don't care */ }

        @Override
        final public void onTextChanged(CharSequence s, int start, int before, int count) { /* Don't care */ }
    }
}
