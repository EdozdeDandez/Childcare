package com.example.childcare.childcare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.childcare.childcare.helpers.InputValidation;
import com.example.childcare.childcare.model.Child;
import com.example.childcare.childcare.sql.DatabaseHelper;

public class NewChildActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = NewChildActivity.this;

    int user_id;

    private EditText editTextName;
    private EditText editTextAge;
    private EditText editTextDob;
    private EditText editTextAddress;
    private EditText editTextSchedule;

    private Button save;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private Child child;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("New applicant");
        setContentView(R.layout.activity_new_child);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        initViews();
        initListeners();
        initObjects();
    }


    /**
     * This method is to initialize views
     */
    private void initViews() {
        editTextName = (EditText) findViewById(R.id.cname);
        editTextAge = (EditText) findViewById(R.id.cage);
        editTextAddress = (EditText) findViewById(R.id.caddress);
        editTextSchedule = (EditText) findViewById(R.id.cschedule);
        editTextDob = (EditText) findViewById(R.id.cdob);

        save = (Button) findViewById(R.id.csave);

        user_id = getIntent().getIntExtra("USER_ID", 0);
    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        save.setOnClickListener(this);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);
        child = new Child();

    }


    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.csave:
                postDataToSQLite();
                break;
        }
    }

    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private void postDataToSQLite() {
        if (!inputValidation.isInputEditTextFilled(editTextName, getString(R.string.empty))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(editTextAge, getString(R.string.empty))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(editTextAddress, getString(R.string.empty))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(editTextSchedule, getString(R.string.empty))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(editTextDob, getString(R.string.empty))) {
            return;
        }
        child.setName(editTextName.getText().toString().trim());
        child.setAge(Integer.parseInt(editTextAge.getText().toString().trim()));
        child.setSchedule(editTextSchedule.getText().toString().trim());
        child.setDob(editTextDob.getText().toString().trim());
        child.setAddress(editTextAddress.getText().toString().trim());
        child.setUser_id(user_id);

        databaseHelper.addChild(child);

        // Snack Bar to show success message that record saved successfully
        Toast.makeText(this, getString(R.string.success_message), Toast.LENGTH_LONG).show();
        Intent home = new Intent(activity, HomeActivity.class);
        home.putExtra("USER_ID", user_id);
        home.putExtra("ROLE", "Parent");
        emptyInputEditText();

        startActivity(home);


    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        editTextName.setText(null);
        editTextAge.setText(null);
        editTextSchedule.setText(null);
        editTextDob.setText(null);
        editTextAddress.setText(null);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        this.finish();
    }

//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        int itemID = item.getItemId();
//
//        switch (itemID ) {
//            case android.R.id.home:
//                this.finish();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }item
}
