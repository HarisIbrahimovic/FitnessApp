package com.example.myfitness.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfitness.R;
import com.example.myfitness.ViewModel.MainViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private TextView loginTextView;
    private TextView signUpTextView;
    private EditText userNameEditText;
    private EditText userEmailEditText;
    private EditText userWeightEditText;
    private EditText userHeightEditText;
    private EditText userPasswordEditText;
    private Button loginButton;
    private MainViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkUser();
        setUpView();
        onClicks();
        observe();
    }

    private void checkUser() {
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MenuActivity.class));
            finish();
        }
    }

    private void observe() {
        viewModel.getLoginState().observe(this, integer -> {
            if(integer==1){
                setUpForLogin();
            }else if(integer==0){
                setUpForSignUp();
            }
        });
        viewModel.getAcceptLogin().observe(this, aBoolean -> {
            if(aBoolean){
                viewModel.fenchData(getApplication());
                startActivity(new Intent(getApplicationContext(),MenuActivity.class));
                finish();
            }
        });
        viewModel.getToastMessage().observe(this, s -> Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show());
    }

    private void setUpForSignUp() {
        userNameEditText.setVisibility(View.VISIBLE);
        userWeightEditText.setVisibility(View.VISIBLE);
        userHeightEditText.setVisibility(View.VISIBLE);
        loginTextView.setTextColor(getResources().getColor(R.color.white));
        signUpTextView.setTextColor(getResources().getColor(R.color.myBlue));
        loginButton.setText("Sign up");
        emptyTextFields();
    }

    private void emptyTextFields() {
        userEmailEditText.setText("");
        userWeightEditText.setText("");
        userHeightEditText.setText("");
        userNameEditText.setText("");
        userPasswordEditText.setText("");
    }

    private void setUpForLogin() {
        userNameEditText.setVisibility(View.GONE);
        userWeightEditText.setVisibility(View.GONE);
        userHeightEditText.setVisibility(View.GONE);
        loginTextView.setTextColor(getResources().getColor(R.color.myBlue));
        signUpTextView.setTextColor(getResources().getColor(R.color.white));
        loginButton.setText("Login");
        emptyTextFields();
    }

    private void onClicks() {
        loginTextView.setOnClickListener(v -> viewModel.setLoginState(1));

        signUpTextView.setOnClickListener(v -> viewModel.setLoginState(0));

        loginButton.setOnClickListener(v -> signUser());
    }

    private void signUser() {
        String UserName = userNameEditText.getText().toString().trim();
        String Email = userEmailEditText.getText().toString().trim();
        String Password = userPasswordEditText.getText().toString().trim();
        String Weight = userWeightEditText.getText().toString().trim();
        String Height = userHeightEditText.getText().toString().trim();
        String buttonText = loginButton.getText().toString();
        viewModel.singUser(UserName,Email,Password,Weight,Height,buttonText);
    }

    private void setUpView() {
        getWindow().setNavigationBarColor(getResources().getColor(R.color.myPurple));
        loginTextView = findViewById(R.id.loginTextMain);
        signUpTextView = findViewById(R.id.SignUpTextMain);
        userNameEditText = findViewById(R.id.userNameLogin);
        userEmailEditText = findViewById(R.id.emailLoginMain);
        userWeightEditText = findViewById(R.id.weightLogin);
        userHeightEditText = findViewById(R.id.heightLogin);
        userPasswordEditText = findViewById(R.id.passwordLogin);
        loginButton = findViewById(R.id.loginButton);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.init();

    }
}