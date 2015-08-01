package com.suh.itboy.auctionsystem.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.suh.itboy.auctionsystem.R;
import com.suh.itboy.auctionsystem.Utils.Validate;


public class RegisterFragment extends Fragment {
    EditText name;
    EditText email;
    EditText pass;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        email = (EditText)view.findViewById(R.id.input_email);
        pass = (EditText)view.findViewById(R.id.input_password);
        name = (EditText)view.findViewById(R.id.input_name);
        Button signup = (Button)view.findViewById(R.id.btn_signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });

        return view;
    }


    private void Register(){
        if (ValidateRegister()){
            return;
        }
    }



    private boolean ValidateRegister(){
        if (!Validate.full_name(name.getText().toString())){
            name.setError("Invalid Name letters and space allowed.");
            return false;
        }

        if (!Validate.email(email.getText().toString())){
            email.setError("Invalid Email!");
            return false;
        }

        if (!Validate.password(pass.getText().toString(), 5, 15)) {
            pass.setError("Invalid Password should be (5-15)");
            return false;
        }

        return true;
    }
}
