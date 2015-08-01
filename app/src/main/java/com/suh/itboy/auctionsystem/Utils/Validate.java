package com.suh.itboy.auctionsystem.Utils;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by itboy on 8/1/2015.
 */
public class Validate {
    public static boolean email(String email){
        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email.toString()).matches())
            return true;
        else
            return false;
    }

    public static boolean password(String pass,int min, int max){
        if (pass.isEmpty())
            return false;

        int length = pass.length();

        if (min != 0) {
            if (length < min)
                return false;
        }

        if (max != 0){
            if (length > max)
                return false;
        }

        return true;
    }

    public static boolean full_name(String name){
        String regx = "^[\\p{L} .'-]+$";
        Pattern pattern = Pattern.compile(regx,Pattern.CASE_INSENSITIVE);
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
