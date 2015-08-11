package com.suh.itboy.auctionsystem.Fragments.Account;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.suh.itboy.auctionsystem.Activities.AccountActivity;
import com.suh.itboy.auctionsystem.Activities.ActivityManager;
import com.suh.itboy.auctionsystem.Adapters.Database.ProfileDBAdapter;
import com.suh.itboy.auctionsystem.Adapters.Database.UserDBAdapter;
import com.suh.itboy.auctionsystem.Adapters.ViewPagerAdapter;
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
    TextView createAccountText;
    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_login, container, false);
        email = (EditText) view.findViewById(R.id.input_email);
        pass = (EditText) view.findViewById(R.id.input_password);
        createAccountText = (TextView) view.findViewById(R.id.link_signup);

        Button sign_in_btn = (Button) view.findViewById(R.id.btn_login);
        sign_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogin();
            }
        });

/*
        Bundle bundle = getArguments();
        if ( bundle != null && bundle.containsKey("email")){
            this.setEmail(bundle.getString("email"));
        }
*/

        return view;
    }

    private void onLogin() {
        if (!ValidateLogin()) {
            AccountActivity.showMsg("Invalid Username or Password.", null, null);
            return;
        }

        //Show progress dialog
        AccountActivity.showProgressDialog("Authenticating...");

        if (Login(email.getText().toString(), pass.getText().toString())) {
            AccountActivity.closeProgressDialog();
            ActivityManager.startDashboardActivity(getActivity());
        }

        AccountActivity.closeProgressDialog(1000);

    }

    private boolean Login(String email, String pass) {
        UserDBAdapter userDBAdapter = new UserDBAdapter(getActivity());
        ProfileDBAdapter profileDBAdapter = new ProfileDBAdapter(getActivity());

        Cursor userCursor = userDBAdapter.getByEmail(email);
        if (userCursor == null || userCursor.getCount() < 1) {
            AccountActivity.showMsg("Email does not exists.", 1000, "Create!", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewPagerAdapter.SET_EMAIL = true;
                    ((AccountActivity) getActivity()).ChangeToSignUp(v);
                }
            });
            return false;
        }


        userCursor = userDBAdapter.getByEmailAndPass(email, pass);
        if (userCursor == null || userCursor.getCount() < 1) {
            AccountActivity.showMsg("Incorrect Email or Password", 1000, "Forget!", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AccountActivity.showMsg("Not Implemented!", null, null);
                }
            });
            return false;
        }

        UserModel userModel = new UserModel();
        ProfileModel profileModel = new ProfileModel();

        if (!(userModel.mapFromCursor(userCursor))) {
            AccountActivity.showMsg("Unable to map user data.", 1000, null, null);
            return false;
        }
        Cursor profileCursor = profileDBAdapter.getByUserId(userModel.getRowId());

        if (!(profileModel.mapFromCursor(profileCursor))) {
            AccountActivity.showMsg("Unable to map profile data.", 1000, null, null);
            return false;
        }

        UserSessionHelper userSessionHelper = UserSessionHelper.getInstance(getActivity());
        userSessionHelper.createUserLoginSession(profileModel.getName(), userModel.getEmail());

        return true;
    }

    private boolean ValidateLogin() {

        if (!Validate.email(email.getText().toString())) {
            email.setError("Invalid Email!");
            return false;
        }

        if (!Validate.password(pass.getText().toString(), 0, 0)) {
            pass.setError("Enter Valid Password");
            return false;
        }

        return true;
    }

    public String getEmail() {
        return this.email.getText().toString();
    }

    public void setEmail(String email) {
        this.email.setText(email);
    }
}
