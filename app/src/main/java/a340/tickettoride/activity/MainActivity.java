package a340.tickettoride.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import a340.tickettoride.R;
import a340.tickettoride.presenter.interfaces.IMainPresenter;
import a340.tickettoride.presenter.MainPresenter;

public class MainActivity extends AppCompatActivity implements MainPresenter.View {
    private IMainPresenter presenter;
    private Button mLoginButton;
    private Button mRegisterButton;
    private EditText mUsernameField;
    private EditText mPasswordField;
    private TextView mErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter(this);
        findViews();
        setButtonListeners();
        setTextListeners();
    }

    @Override
    protected void onResume() {
        presenter.startObserving();
        super.onResume();
    }

    @Override
    protected void onPause() {
        presenter.stopObserving();
        super.onPause();
    }

    private void findViews() {
        mLoginButton = findViewById(R.id.loginButton);
        mRegisterButton = findViewById(R.id.registerButton);
        mUsernameField = findViewById(R.id.usernameInput);
        mPasswordField = findViewById(R.id.passwordInput);
        mErrorMessage = findViewById(R.id.errorMessage);
    }

    private void setButtonListeners() {
        mLoginButton.setEnabled(false);
        mRegisterButton.setEnabled(false);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String usernameStr = mUsernameField.getText().toString();
                final String passwordStr = mPasswordField.getText().toString();
                presenter.login(usernameStr, passwordStr);
            }
        });

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainActivity", "In on click");
                final String usernameStr = mUsernameField.getText().toString();
                final String passwordStr = mPasswordField.getText().toString();
                presenter.register(usernameStr, passwordStr);
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

    @Override
    public void onAuthenticated() {
        Intent intent = new Intent(MainActivity.this, LobbyActivity.class);
        startActivity(intent);
    }

    @Override
    public void onInvalid(String errorMessage) {
        mErrorMessage.setText(errorMessage);
        mErrorMessage.setVisibility(View.VISIBLE);
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
