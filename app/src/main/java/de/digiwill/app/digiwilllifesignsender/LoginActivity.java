package de.digiwill.app.digiwilllifesignsender;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    // UI references.
    private AutoCompleteTextView txtEmail;
    private EditText txtPassword;
    private Button btSignIn;
    Intent intent;
    private final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = new Intent(LoginActivity.this, SendActivity.class);
        setContentView(R.layout.activity_login);
        btSignIn = findViewById(R.id.sign_in_button);
        txtEmail = findViewById(R.id.email);
        txtPassword = findViewById(R.id.password);
        btSignIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String username = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();
                boolean required = false;
                if (username.equals("")) {
                    txtEmail.setError(getString(R.string.error_field_required));
                    required = true;
                } else if(!username.matches(EMAIL_REGEX)) {
                    txtEmail.setError(getString(R.string.error_invalid_email));
                    required = true;
                }
                if (password.equals("")) {
                    txtPassword.setError(getString(R.string.error_field_required));
                    required = true;
                }
                if (!required) {
                    intent.putExtra("username", username);
                    intent.putExtra("password", password);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(LoginActivity.this,
                getString(R.string.error_login_failed), Toast.LENGTH_LONG).show();
    }
}

