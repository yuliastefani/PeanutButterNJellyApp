package com.example.mobprogprojectlec.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mobprogprojectlec.Database.UserHelper;
import com.example.mobprogprojectlec.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etRgUsername, etRgEmail, etRgPassword;
    TextView tvRgLogin;
    Button btnRegister;
    UserHelper userHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etRgUsername = findViewById(R.id.etRgUsername);
        etRgEmail = findViewById(R.id.etRgEmail);
        etRgPassword = findViewById(R.id.etRgPassword);
        tvRgLogin = findViewById(R.id.tvRgLogin);
        btnRegister = findViewById(R.id.btnRegister);

        tvRgLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        userHelper = new UserHelper(this);


    }

    @Override
    public void onClick(View v) {
        Intent intent;
        if (v == tvRgLogin) {
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        else if(v.getId()==R.id.btnRegister){
            if(validate()==true){
                userHelper.open();
                userHelper.insertUser(etRgUsername.getText().toString(),etRgEmail.getText().toString(),etRgPassword.getText().toString());
                userHelper.close();
                intent = new Intent(this,MainActivity.class);
                startActivity(intent);
            }
        }
    }

    private boolean validate() {
        if (etRgUsername.getText().toString().isEmpty() || etRgPassword.getText().toString().isEmpty() || etRgEmail.getText().toString().isEmpty()) {
            etRgUsername.setError("Username must be filled!");
            etRgPassword.setError("Password must be filled!");
            etRgEmail.setError("Email must be filled!");
            return false;
        }
        else if(!etRgUsername.getText().toString().matches("^[a-zA-Z0-9]*$")){
            etRgUsername.setError("Username must be alphanumeric!");
            return false;
        }

        userHelper.open();
        Boolean checkUsername = userHelper.validateUsername(etRgUsername.getText().toString());
        userHelper.close();
        if (checkUsername == true){
            etRgUsername.setError("Username already exists!");
            return false;
        }

        else if(!etRgEmail.getText().toString().matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")){
            etRgEmail.setError("Email must be valid!");
            return false;
        }
        else if(etRgPassword.getText().toString().length()<8){
            etRgPassword.setError("Password must be at least 8 characters!");
            return false;
        }
        else{
            return true;
        }
    }
}