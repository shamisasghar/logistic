package com.logistics.hypernym.logistic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.logistics.hypernym.logistic.api.ApiClient;
import com.logistics.hypernym.logistic.api.ApiInterface;
import com.logistics.hypernym.logistic.models.User;
import com.logistics.hypernym.logistic.utils.LoginUtils;
import com.roger.catloadinglibrary.CatLoadingView;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shamis on 19-Dec-17.
 */

public class LoginActivity extends AppCompatActivity {
    Button btn_sign;
    private TextInputEditText edit_username, edit_password;
    private TextInputLayout inputLayout_username,inputLayout_password;
    Snackbar snackbar;

    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_sign = (Button) findViewById(R.id.sign_in);
        edit_username = (TextInputEditText) findViewById(R.id.edittext_username);
        edit_password = (TextInputEditText) findViewById(R.id.edittext_password);
        inputLayout_username = (TextInputLayout) findViewById(R.id.input_layout_username);
        inputLayout_password = (TextInputLayout) findViewById(R.id.input_layout_password);
        edit_username.addTextChangedListener(new MyTextWatcher(edit_username));
        edit_password.addTextChangedListener(new MyTextWatcher(edit_password));
        progressBar = (ProgressBar)findViewById(R.id.spin_kit);
        progressBar.setVisibility(View.GONE);

        if (LoginUtils.isUserLogin(getApplicationContext())) {
            User u = LoginUtils.getUser(getApplicationContext());

            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
            Toast.makeText(this, "User already been logged in", Toast.LENGTH_SHORT).show();
        }
        else

        {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();

            btn_sign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    submit();

                }
            });
        }
    }
    private void submit() {
        if (!validateName()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        String username = edit_username.getText().toString();
        String password = edit_password.getText().toString();
        //    "driver1@kotal.com"
        //    "driver@2017"
        HashMap<String, Object> body = new HashMap<>();
        body.put("email", username);
        body.put("password", password);
        Call<User> call_getlist = apiService.loginUser(body);
        call_getlist.enqueue(new Callback<User>()
        {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                if (user != null && user.getStatus()) {
                    Toast.makeText(LoginActivity.this, user.getResponse().getToken(), Toast.LENGTH_SHORT).show();
                    LoginUtils.saveUserToken(LoginActivity.this, user.getResponse().getToken(),Integer.toString(user.getResponse().getAssociatedEntity()));
                    LoginUtils.userLoggedIn(LoginActivity.this);
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(LoginActivity.this, "user logged in"+Integer.toString(user.getResponse().getAssociatedEntity()), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

                }
                else
                {

                    Snackbar snackbar=Snackbar.make(findViewById(android.R.id.content), "Wrong Email & Password", Snackbar.LENGTH_LONG);
                    View view=snackbar.getView();
                    TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                    view.setBackgroundColor(ContextCompat.getColor(getApplication(),R.color.colorPrimary));
                    tv.setTextColor(ContextCompat.getColor(getApplication(),R.color.colorDialogToolbarText));
                    tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    snackbar.show();
                    progressBar.setVisibility(View.GONE);


                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

                progressBar.setVisibility(View.GONE);
                Snackbar snackbar=Snackbar.make(findViewById(android.R.id.content), "Network Failed!", Snackbar.LENGTH_LONG);
                View view=snackbar.getView();
                TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                view.setBackgroundColor(ContextCompat.getColor(getApplication(),R.color.colorPrimary));
                tv.setTextColor(ContextCompat.getColor(getApplication(),R.color.colorDialogToolbarText));
                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                snackbar.show();
            }
        });
    }

    private boolean validateName() {
        if (edit_username.getText().toString().trim().isEmpty()) {
            inputLayout_username.setError(getString(R.string.err_msg_name));
            requestFocus(edit_username);
            return false;
        } else {
            inputLayout_username.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        if (edit_password.getText().toString().trim().isEmpty()) {
            inputLayout_password.setError(getString(R.string.err_msg_password));
            requestFocus(inputLayout_password);
            return false;
        } else {
            inputLayout_password.setErrorEnabled(false);
        }

        return true;
    }
    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }





    private class MyTextWatcher implements TextWatcher {
        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (view.getId()) {
                case R.id.edittext_username:
                    validateName();
                    break;
                case R.id.edittext_password:
                    validatePassword();
                    break;
            }

        }
    }


}


