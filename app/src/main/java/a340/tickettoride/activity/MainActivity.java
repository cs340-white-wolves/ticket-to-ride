package a340.tickettoride.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import a340.tickettoride.R;
import a340.tickettoride.presenter.MainPresenter;
import cs340.TicketToRide.model.User;

public class MainActivity extends AppCompatActivity {
    private MainPresenter presenter;
    private String mUsername = "";
    private String mPassword = "";
    private Button mLoginButton = null;
    private Button mRegisterButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter();

        // login button wire-up & listener
        mLoginButton = findViewById(R.id.loginButton);
        mLoginButton.setEnabled(true);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    presenter.login(mUsername, mPassword);
                } catch (Exception ex) {
                    // handle exception (should this be handled in presenter?)
                }
            }
        });

        // register button wire-up & listener
        mRegisterButton = findViewById(R.id.registerButton);
        mRegisterButton.setEnabled(true);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    presenter.register(mUsername, mPassword);
                } catch (Exception ex) {
                    // handle exception (should this be handled in presenter?)
                }
            }
        });

        // username field wire-up & listener
        EditText mUsernameField = findViewById(R.id.usernameInput);
        mUsernameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUsername = s.toString();
//                if (canLogin()) {
//                    mLoginButton.setEnabled(true);
//                    mRegisterButton.setEnabled(true);
//                } else {
//                    mLoginButton.setEnabled(false);
//                    mRegisterButton.setEnabled(false);
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // password field wire-up & listener
        EditText mPasswordField = findViewById(R.id.passwordInput);
        mPasswordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPassword = s.toString();
//                if (canLogin()) {
//                    mLoginButton.setEnabled(true);
//                    mRegisterButton.setEnabled(true);
//                } else {
//                    mLoginButton.setEnabled(false);
//                    mRegisterButton.setEnabled(false);
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void loggedIn() {
        // start LobbyActivity
        Intent intent = new Intent(MainActivity.this, LobbyActivity.class);
        startActivity(intent);
    }

    public void invalid(String message) {
        showMessage(message); // pop-up indicating what went wrong
    }

    private void showMessage(String message) {
        Toast.makeText(this,
                message,
                Toast.LENGTH_SHORT).show();
    }

//    private boolean canLogin() {
//        return (!mUsername.equals("") && !mPassword.equals(""));
//    }
}
