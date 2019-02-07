package a340.tickettoride.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import a340.tickettoride.R;
import a340.tickettoride.presenter.IMainPresenter;
import a340.tickettoride.presenter.MainPresenter;

public class MainActivity extends AppCompatActivity implements MainPresenter.View {
    private IMainPresenter presenter;
    private Button mLoginButton;
    private Button mRegisterButton;
    private EditText mUsernameField;
    private EditText mPasswordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter(this);
        findViews();
        setButtonListeners();
        setTextListeners();
    }

    private void findViews() {
        mLoginButton = findViewById(R.id.loginButton);
        mRegisterButton = findViewById(R.id.registerButton);
        mUsernameField = findViewById(R.id.usernameInput);
        mPasswordField = findViewById(R.id.passwordInput);
    }

    private void setButtonListeners() {
        mLoginButton.setEnabled(false);
        mRegisterButton.setEnabled(false);
        final String usernameStr = mUsernameField.getText().toString();
        final String passwordStr = mPasswordField.getText().toString();

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    presenter.login(usernameStr, passwordStr);
                } catch (Exception ex) {
                    // handle exception (should this be handled in presenter?)
                }
            }
        });

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    presenter.register(usernameStr, passwordStr);
                } catch (Exception ex) {
                    // handle exception (should this be handled in presenter?)
                }
            }
        });

    }

    private void setTextListeners() {
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateButtons();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        mUsernameField.addTextChangedListener(watcher);
        mPasswordField.addTextChangedListener(watcher);
    }

    public void loggedIn() {
        Intent intent = new Intent(MainActivity.this, LobbyActivity.class);
        startActivity(intent);
    }

    public void invalid(String errorMessage) {
        showMessage(errorMessage); // toast indicating what went wrong
    }

    private void showMessage(String message) {
        Toast.makeText(this,
                message,
                Toast.LENGTH_SHORT).show();
    }

    private boolean canLogin() {
        final String usernameStr = mUsernameField.getText().toString();
        final String passwordStr = mPasswordField.getText().toString();
        return (!usernameStr.equals("") && !passwordStr.equals(""));
    }

    private void updateButtons() {
        if (canLogin()) {
            mLoginButton.setEnabled(true);
            mRegisterButton.setEnabled(true);
        } else {
            mLoginButton.setEnabled(false);
            mRegisterButton.setEnabled(false);
        }
    }
}
