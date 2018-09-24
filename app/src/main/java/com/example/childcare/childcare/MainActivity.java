package com.example.childcare.childcare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.childcare.childcare.helpers.InputValidation;
import com.example.childcare.childcare.sql.DatabaseHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = MainActivity.this;

    private EditText editTextEmail;
    private EditText editTextPassword;

    private Button login;
    private TextView signup;
    private TextView admin;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();
    }

    /**
     * This method is to initialize views
     */
    private void initViews() {

        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);

        login = (Button) findViewById(R.id.login);

        signup = (TextView) findViewById(R.id.signup);
        admin = (TextView) findViewById(R.id.admin);

    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        login.setOnClickListener(this);
        signup.setOnClickListener(this);
        admin.setOnClickListener(this);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);

    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                verifyFromSQLite();
                break;
            case R.id.signup:
                // Navigate to RegisterActivity
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
                break;
            case R.id.admin:
                // Navigate to RegisterActivity
                Intent intentAdmin = new Intent(getApplicationContext(), ALoginActivity.class);
                startActivity(intentAdmin);
                break;
        }
    }

    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */
    private void verifyFromSQLite() {
        if (!inputValidation.isInputEditTextFilled(editTextEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(editTextEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(editTextPassword, getString(R.string.error_message_password))) {
            return;
        }

        if (databaseHelper.checkUser(editTextEmail.getText().toString().trim()
                , editTextPassword.getText().toString().trim())) {


            Intent home = new Intent(activity, HomeActivity.class);
            home.putExtra("USER_ID", databaseHelper.GetUserID(editTextEmail.getText().toString().trim()));
            home.putExtra("ROLE", "Parent");
            emptyInputEditText();
            startActivity(home);


        } else {
            // Snack Bar to show success message that record is wrong
            Toast.makeText(this, getString(R.string.error_valid_email_password), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        editTextEmail.setText(null);
        editTextPassword.setText(null);
    }
}
