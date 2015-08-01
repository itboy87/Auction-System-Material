package com.suh.itboy.auctionsystem.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.suh.itboy.auctionsystem.Activities.AccountActivity;
import com.suh.itboy.auctionsystem.R;
import com.suh.itboy.auctionsystem.Utils.Validate;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    EditText email;
    EditText pass;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_login, container, false);
        email = (EditText)view.findViewById(R.id.input_email);
        pass = (EditText)view.findViewById(R.id.input_password);

        Button signin = (Button)view.findViewById(R.id.btn_login);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogin();
            }
        });
        return view;
    }

    private void onLogin(){
        if (!ValidateLogin()){
            return;
        }
        //Show progress dialog
        AccountActivity.showProgressDialog("Authenticating...");
        Login();

    }
    private void Login(){
    }
    private boolean ValidateLogin(){

        if (!Validate.email(email.getText().toString())){
            email.setError("Invalid Email!");
            return false;
        }

        if (!Validate.password(pass.getText().toString(), 0, 0)) {
            pass.setError("Enter Valid Password");
            return false;
        }

        return true;
    }
}
