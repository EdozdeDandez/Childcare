package com.example.childcare.childcare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.childcare.childcare.helpers.InputValidation;
import com.example.childcare.childcare.model.Child;
import com.example.childcare.childcare.sql.DatabaseHelper;

public class EditChildActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = EditChildActivity.this;

    int child_id;

    private EditText editTextName1;
    private EditText editTextAge1;
    private EditText editTextDob1;
    private EditText editTextAddress1;
    private EditText editTextSchedule1;

    private Button save1;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private Child child;
    private Child child1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_child);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        initObjects();
        initViews();
        initListeners();

        editTextName1.setText(child1.getName());
        editTextAddress1.setText(child1.getAddress());
        editTextAge1.setText(Integer.toString(child1.getAge()));
        editTextDob1.setText(child1.getDob());
        editTextSchedule1.setText(child1.getSchedule());

    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        editTextName1 = (EditText) findViewById(R.id.cname1);
        editTextAge1 = (EditText) findViewById(R.id.cage1);
        editTextAddress1 = (EditText) findViewById(R.id.caddress1);
        editTextSchedule1 = (EditText) findViewById(R.id.cschedule1);
        editTextDob1 = (EditText) findViewById(R.id.cdob1);

        save1 = (Button) findViewById(R.id.csave1);

        child_id = getIntent().getIntExtra("CHILD", 0);

        child1 = databaseHelper.getChild(child_id);
    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        save1.setOnClickListener(this);
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

            case R.id.csave1:
                postDataToSQLite();
                break;
        }
    }

    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private void postDataToSQLite() {
        if (!inputValidation.isInputEditTextFilled(editTextName1, getString(R.string.empty))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(editTextAge1, getString(R.string.empty))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(editTextAddress1, getString(R.string.empty))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(editTextSchedule1, getString(R.string.empty))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(editTextDob1, getString(R.string.empty))) {
            return;
        }
        child.setId(child1.getId());
        child.setName(editTextName1.getText().toString().trim());
        child.setAge(Integer.parseInt(editTextAge1.getText().toString().trim()));
        child.setSchedule(editTextSchedule1.getText().toString().trim());
        child.setDob(editTextDob1.getText().toString().trim());
        child.setAddress(editTextAddress1.getText().toString().trim());
        child.setUser_id(child1.getUser_id());

        databaseHelper.updateChild(child);

        // Snack Bar to show success message that record saved successfully
        Toast.makeText(this, getString(R.string.success_message), Toast.LENGTH_LONG).show();
        Intent achild = new Intent(activity, ChildActivity.class);
        achild.putExtra("CHILD", child.getId());
        achild.putExtra("ROLE", "Parent");
        achild.putExtra("USER_ID", child.getUser_id());
        startActivity(achild);
        finish();
        emptyInputEditText();

    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        editTextName1.setText(null);
        editTextAge1.setText(null);
        editTextSchedule1.setText(null);
        editTextDob1.setText(null);
        editTextAddress1.setText(null);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        this.finish();
    }
}
