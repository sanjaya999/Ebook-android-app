package com.ebook.np;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.ebook.np.api.Api;
import com.ebook.np.api.ApiService;
import com.ebook.np.dto.Request.RegisterRequest;
import com.ebook.np.dto.Response.RegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterActivity extends AppCompatActivity {

    private ApiService apiService;
    private EditText FullName , Email , Password;
    private AppCompatButton register;
    private TextView alreadyLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findView();

        alreadyLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this , LoginActivity.class);
                startActivity(i);
            }
        });
        try{
            apiService = Api.getClient().create(ApiService.class);
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Error initializing network service. Please restart the app.", Toast.LENGTH_LONG).show();
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (apiService != null) {
                    register();
                } else {
                    Toast.makeText(RegisterActivity.this, "Network service not available.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private  void findView(){
        FullName = findViewById(R.id.fullName);
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        register = findViewById(R.id.registerButton);
        alreadyLogin = findViewById(R.id.alreadyLogin);
    }

    private void register(){
        String email = Email.getText().toString();
        String password = Password.getText().toString();
        String fullName = FullName.getText().toString();

        if(email.isEmpty() || password.isEmpty() || fullName.isEmpty()){
            Toast.makeText(RegisterActivity.this, "Email or Password or Full Name cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        RegisterRequest registerRequest = new RegisterRequest(fullName , email , password);

        apiService.register(registerRequest).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

                if (response.isSuccessful() && response.body() != null){
                    RegisterResponse registerResponse = response.body();

                    if (registerResponse.isSuccess()){
                        Toast.makeText(RegisterActivity.this , "Register success" , Toast.LENGTH_SHORT).show();


                    }else {
                        Toast.makeText(RegisterActivity.this , "Register failed" , Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this , "Network Error: Could not connect to server.", Toast.LENGTH_LONG).show();
            }
        });


    }
}