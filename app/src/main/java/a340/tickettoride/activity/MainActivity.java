package a340.tickettoride.activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import a340.tickettoride.R;
import cs340.TicketToRide.model.User;

public class MainActivity extends AppCompatActivity {
    String mUsername = "";
    String mPassword = "";
    EditText mUsernameField;
    EditText mPasswordField;
    Button mLoginButton = null;
    Button mRegisterButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // login button wire-up & listener
        mLoginButton = findViewById(R.id.loginButton);
        mLoginButton.setEnabled(false);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // register button wire-up & listener
        mRegisterButton = findViewById(R.id.registerButton);
        mRegisterButton.setEnabled(false);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // username field wire-up & listener
        mUsernameField = findViewById(R.id.usernameInput);
        mUsernameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUsername = s.toString();
                if (canLogin()) {
                    mLoginButton.setEnabled(true);
                    mRegisterButton.setEnabled(true);
                } else {
                    mLoginButton.setEnabled(false);
                    mRegisterButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // password field wire-up & listener
        mPasswordField = findViewById(R.id.passwordInput);
        mPasswordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPassword = s.toString();
                if (canLogin()) {
                    mLoginButton.setEnabled(true);
                    mRegisterButton.setEnabled(true);
                } else {
                    mLoginButton.setEnabled(false);
                    mRegisterButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void loggedIn(User user) {

    }

    public void invalid() {

    }

    private boolean canLogin() {
        return (!mUsername.equals("") && !mPassword.equals(""));
    }
}
