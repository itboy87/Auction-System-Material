package com.suh.itboy.auctionsystem.Fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.suh.itboy.auctionsystem.Activities.AccountActivity;
import com.suh.itboy.auctionsystem.Activities.ActivityManager;
import com.suh.itboy.auctionsystem.Adapters.Database.UserDBAdapter;
import com.suh.itboy.auctionsystem.Helper.UserSessionHelper;
import com.suh.itboy.auctionsystem.Models.Database.ProfileModel;
import com.suh.itboy.auctionsystem.Models.Database.UserModel;
import com.suh.itboy.auctionsystem.R;
import com.suh.itboy.auctionsystem.Utils.App;
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
            showMsg("Invalid Username or Password.");
            return;
        }

        //Show progress dialog
        AccountActivity.showProgressDialog("Authenticating...");
        if (Login(email.getText().toString(),pass.getText().toString())){
            ActivityManager.startHomeActivity(getActivity());
        }
        else {
            showMsg("Incorrect Email or Password.");
        }
        AccountActivity.closeProgressDialog();

    }

    private boolean Login(String email, String pass){
        UserDBAdapter userDBAdapter = new UserDBAdapter(getActivity());

        Cursor userCursor = userDBAdapter.getByEmailAndPass(email, pass);

        UserModel userModel = new UserModel();
        ProfileModel profileModel = new ProfileModel();

        if (userCursor == null){
            return false;
        }

        if (!(userModel.mapFromCursor(userCursor) && profileModel.mapFromCursor(userCursor))){
            return false;
        }

        UserSessionHelper userSessionHelper = UserSessionHelper.getInstance(getActivity());
        userSessionHelper.createUserLoginSession(profileModel.getName(),userModel.getEmail());

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

    /**
     * Wrap for App.ShowMsg
     * @param value
     */
    private void showMsg(String value){
        App.ShowMsg(getView(),value);
    }
}
