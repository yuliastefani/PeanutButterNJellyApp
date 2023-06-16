package com.example.mobprogprojectlec.UI.Landing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mobprogprojectlec.Database.UserHelper;
import com.example.mobprogprojectlec.R;
import com.example.mobprogprojectlec.UI.Landing.RegisterActivity;
import com.example.mobprogprojectlec.UI.MainActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etLgUsername, etLgPassword;
    TextView tvLgRegister;
    Button btnLogin;
    UserHelper userHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etLgUsername = findViewById(R.id.etLgUsername);
        etLgPassword = findViewById(R.id.etLgPassword);
        tvLgRegister = findViewById(R.id.tvLgRegister);
        btnLogin = findViewById(R.id.btnLogin);

        tvLgRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        userHelper = new UserHelper(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        if (v == tvLgRegister) {
            intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }
        else if(v.getId()==R.id.btnLogin){
            if(validate() == true){
//                userHelper.open();
//                userHelper.getUser(etLgUsername.getText().toString());
//                userHelper.close();

                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                SharedPreferences sharedPreferences = getSharedPreferences("username",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username",etLgUsername.getText().toString());
                editor.commit();
            }
        }
    }

    private boolean validate() {
        if (etLgUsername.getText().toString().isEmpty() || etLgPassword.getText().toString().isEmpty()) {
            etLgUsername.setError("Username must be filled!");
            etLgPassword.setError("Password must be filled!");
            return false;
        }

        userHelper.open();
        Boolean checkUser = userHelper.validateUser(etLgUsername.getText().toString(), etLgPassword.getText().toString());
        Boolean checkUsername = userHelper.validateUsername(etLgUsername.getText().toString());
        userHelper.close();

        if (checkUsername == true && checkUser == false){
            etLgPassword.setError("Wrong Password!");
            return false;
        }

        if (checkUser == false){
            etLgUsername.setError("Username and Password are not found, Please Register First!");
            etLgPassword.setError("Username and Password are not found, Please Register First!");
            return false;
        }
        return true;
    }
}