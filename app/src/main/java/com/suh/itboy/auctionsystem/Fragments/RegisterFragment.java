package com.suh.itboy.auctionsystem.Fragments;


import android.database.Cursor;
import android.database.SQLException;
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
import com.suh.itboy.auctionsystem.Helper.DatabaseHelper;
import com.suh.itboy.auctionsystem.Helper.UserSessionHelper;
import com.suh.itboy.auctionsystem.Models.Database.ProfileModel;
import com.suh.itboy.auctionsystem.Models.Database.UserModel;
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
        Button sign_up_btn = (Button)view.findViewById(R.id.btn_signup);
        sign_up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRegister();
            }
        });

        return view;
    }


    private void onRegister(){
        if (!ValidateRegister()){
            return;
        }

        AccountActivity.showProgressDialog("Registering...");
        if(Register()){
            AccountActivity.showMsg("Registered Successfully!",2000);
            ActivityManager.startHomeActivity(getActivity());
        }
        else{
            AccountActivity.showMsg("Registration Failed!",2000);
        }
        AccountActivity.closeProgressDialog(2000);
    }


    private boolean Register(){
        UserDBAdapter userDBAdapter = new UserDBAdapter(getActivity());
        ProfileDBAdapter profileDBAdapter = new ProfileDBAdapter(getActivity());

        DatabaseHelper.beginTransaction();

        try{
            long userId = userDBAdapter.createUser(email.getText().toString(),pass.getText().toString());
            //TODO: avatar and gender hardcoded
            long profileId = profileDBAdapter.createProfile(userId,name.getText().toString(),"","male");
        }
        catch (SQLException exception){
            DatabaseHelper.rollbackTransaction();
            return false;
        }

        DatabaseHelper.commitTransaction();

        //UserModel userModel = new UserModel(userId,email.getText().toString(),pass.getText().toString());

        UserSessionHelper userSessionHelper = UserSessionHelper.getInstance(getActivity());
        return  userSessionHelper.createUserLoginSession(name.getText().toString(), email.getText().toString());
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
