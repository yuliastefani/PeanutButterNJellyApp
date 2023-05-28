package com.example.mobprogprojectlec.UI;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobprogprojectlec.Database.UserHelper;
import com.example.mobprogprojectlec.Model.User;
import com.example.mobprogprojectlec.R;


public class AccountFragment extends Fragment {

    Button editAccBtn;
    TextView accUsername, accEmail, accPassword;
    EditText accUsernameEdt, accEmailEdt, accPasswordEdt;
    ImageView closeDialog;
    User user;
    UserHelper userHelper;
    Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View accountFragment = inflater.inflate(R.layout.fragment_account, container, false);

        editAccBtn = accountFragment.findViewById(R.id.editAccBtn);
        accUsername = accountFragment.findViewById(R.id.accUsername);
        accEmail = accountFragment.findViewById(R.id.accEmail);
        accPassword = accountFragment.findViewById(R.id.accPassword);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("username",getActivity().MODE_PRIVATE);
        String username = sharedPreferences.getString("username","");
        userHelper = new UserHelper(getActivity());
        userHelper.open();
        user = userHelper.getUser(username);
        userHelper.close();

        accUsername.setText(user.getUsername());
        accEmail.setText(user.getEmail());
        accPassword.setText(user.getPassword());

        editAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.edit_profile);

                accUsernameEdt = dialog.findViewById(R.id.accUsernameEdt);
                accEmailEdt = dialog.findViewById(R.id.accEmailEdt);
                accPasswordEdt = dialog.findViewById(R.id.accPasswordEdt);
                Button updAccBtn = dialog.findViewById(R.id.updAccBtn);
                closeDialog = dialog.findViewById(R.id.closeDialog);

                closeDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                updAccBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (validate()) {
                            userHelper.open();
                            userHelper.updateUser(String.valueOf(user.getId()),accUsernameEdt.getText().toString(),accEmailEdt.getText().toString(),accPasswordEdt.getText().toString());
                            userHelper.close();
                            dialog.dismiss();
//                            accUsername.setText(accUsernameEdt.getText().toString());
//                            accEmail.setText(accEmailEdt.getText().toString());
//                            accPassword.setText(accPasswordEdt.getText().toString());
                        }

//                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

                dialog.show();
                
            }
        });

        return accountFragment;
    }

    private void notifyDataSetChanged() {
    }

    private boolean validate() {
        if (accUsernameEdt.getText().toString().isEmpty() || accEmailEdt.getText().toString().isEmpty() || accPasswordEdt.getText().toString().isEmpty()) {
            accUsernameEdt.setError("Username must be filled!");
            accPasswordEdt.setError("Password must be filled!");
            accEmailEdt.setError("Email must be filled!");
            return false;
        }
        else if(!accUsernameEdt.getText().toString().matches("^[a-zA-Z0-9]*$")){
            accUsernameEdt.setError("Username must be alphanumeric!");
            return false;
        }

        userHelper.open();
        Boolean checkUsername = userHelper.validateUsername(accUsernameEdt.getText().toString());
        userHelper.close();
        if (checkUsername == true){
            accUsernameEdt.setError("Username already exists!");
            return false;
        }

        else if(!accEmailEdt.getText().toString().matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")){
            accEmailEdt.setError("Email must be valid!");
            return false;
        }
        else if(accPasswordEdt.getText().toString().length()<8){
            accPasswordEdt.setError("Password must be at least 8 characters!");
            return false;
        }
        else{
            return true;
        }
    }

}