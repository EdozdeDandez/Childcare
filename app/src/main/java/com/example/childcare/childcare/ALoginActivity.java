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


public class ALoginActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = ALoginActivity.this;

    private EditText editTextUsername;
    private EditText editTextPassword;

    private Button alogin;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alogin);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();
    }

    /**
     * This method is to initialize views
     */
    private void initViews() {

        editTextUsername = (EditText) findViewById(R.id.username);
        editTextPassword = (EditText) findViewById(R.id.password3);

        alogin = (Button) findViewById(R.id.alogin);

    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        alogin.setOnClickListener(this);
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
            case R.id.alogin:
                verifyFromSQLite();
                break;
        }
    }

    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */
    private void verifyFromSQLite() {
        if (!inputValidation.isInputEditTextFilled(editTextUsername, getString(R.string.error_message_username))) {
            return;
        }

        if (!inputValidation.isInputEditTextFilled(editTextPassword, getString(R.string.error_message_password))) {
            return;
        }

        if (databaseHelper.checkAdmin(editTextUsername.getText().toString().trim()
                , editTextPassword.getText().toString().trim())) {


            Intent home = new Intent(activity, HomeActivity.class);
            home.putExtra("ROLE", "Admin");
            emptyInputEditText();
            startActivity(home);


        } else {
            // Snack Bar to show success message that record is wrong
            Toast.makeText(this, getString(R.string.error_valid_username_password), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        editTextUsername.setText(null);
        editTextPassword.setText(null);
    }
}
