package com.suh.itboy.auctionsystem.Fragments.Account;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.suh.itboy.auctionsystem.Activities.AccountActivity;
import com.suh.itboy.auctionsystem.Activities.ActivityManager;
import com.suh.itboy.auctionsystem.Adapters.Database.ProfileDBAdapter;
import com.suh.itboy.auctionsystem.Adapters.Database.UserDBAdapter;
import com.suh.itboy.auctionsystem.Helper.UserSessionHelper;
import com.suh.itboy.auctionsystem.Models.Database.ProfileModel;
import com.suh.itboy.auctionsystem.Models.Database.UserModel;
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

        Button sign_in_btn = (Button)view.findViewById(R.id.btn_login);
        sign_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogin();
            }
        });
        return view;
    }

    private void onLogin(){
        if (!ValidateLogin()){
            AccountActivity.showMsg("Invalid Username or Password.");
            return;
        }

        //Show progress dialog
        AccountActivity.showProgressDialog("Authenticating...");

        if (Login(email.getText().toString(),pass.getText().toString())){
            AccountActivity.closeProgressDialog();
            ActivityManager.startHomeActivity(getActivity());
        }
        else {
            AccountActivity.showMsg("Incorrect Email or Password.", 1000);
        }

        AccountActivity.closeProgressDialog(1000);

    }

    private boolean Login(String email, String pass){
        UserDBAdapter userDBAdapter = new UserDBAdapter(getActivity());
        ProfileDBAdapter profileDBAdapter = new ProfileDBAdapter(getActivity());

        Cursor userCursor = userDBAdapter.getByEmailAndPass(email, pass);
        if (userCursor == null){
            return false;
        }

        UserModel userModel = new UserModel();
        ProfileModel profileModel = new ProfileModel();

        if (!(userModel.mapFromCursor(userCursor))){
            return false;
        }
        Cursor profileCursor = profileDBAdapter.getByUserId(userModel.getRowId());

        if (!(profileModel.mapFromCursor(profileCursor))){
            return false;
        }

        UserSessionHelper userSessionHelper = UserSessionHelper.getInstance(getActivity());
        userSessionHelper.createUserLoginSession(profileModel.getName(), userModel.getEmail());

        return true;
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
