package com.ebook.np;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ebook.np.api.Api;
import com.ebook.np.api.ApiService;
import com.ebook.np.dto.Request.LoginRequest;
import com.ebook.np.dto.Response.LoginData;
import com.ebook.np.dto.Response.LoginResponse;
import com.ebook.np.model.User;
import com.ebook.np.session.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText Email , Password;
    private AppCompatButton Login;
    private ApiService apiService;
    private ProgressBar loadingProgressBar;
    private static final String TAG = "LoginActivity";
    private SessionManager sessionManager;
    private TextView signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sessionManager = new SessionManager(this);
        findView();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this , RegisterActivity.class);
                startActivity(i);

            }
        });

        try{
            apiService = Api.getClient().create(ApiService.class);
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Error initializing network service. Please restart the app.", Toast.LENGTH_LONG).show();
        }
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (apiService != null) {
                    attemptLogin();
                } else {
                    Toast.makeText(LoginActivity.this, "Network service not available.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void findView(){
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        Login = findViewById(R.id.loginButton);
        signUp = findViewById(R.id.signUp);
    }

    private void attemptLogin(){
        String email = Email.getText().toString();
        String password = Password.getText().toString();

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(LoginActivity.this, "Email or Password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        LoginRequest loginRequest = new LoginRequest(email , password);

        Log.d("LoginRequest" , loginRequest.toString());

        apiService.login(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                    LoginResponse loginResponse = response.body();

                    if (loginResponse.isSuccess()){
                        Toast.makeText(LoginActivity.this , "Login success" , Toast.LENGTH_SHORT).show();

                        LoginData data = loginResponse.getData();
                        if (data != null){
                            String accessToken = data.getAccessToken();
                            User user = data.getUser();
                            if (accessToken != null && user != null){
                                String userId = user.getId();
                                String userName = user.getFullName();
                                String userEmail = user.getEmail();
                                String userProfile = user.getProfile();

                                sessionManager.createLoginSession(accessToken , user);
                                Intent i = new Intent(LoginActivity.this , BookRecyclerViewActivity.class);
                                startActivity(i);

                                Toast.makeText(LoginActivity.this , "data" + userId + userName + userEmail + userProfile , Toast.LENGTH_SHORT).show();


                            }else {
                                Toast.makeText(LoginActivity.this , "Login failed accesstokens are null" , Toast.LENGTH_SHORT).show();
                            }

                            }else {
                            Toast.makeText(LoginActivity.this , "Login failed whole data null" , Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this , "Login failed isFailed" , Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(LoginActivity.this , "Login failed request body null" , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this , "Network Error: Could not connect to server.", Toast.LENGTH_LONG).show();
                Log.e(TAG, "Network request failed: " + t.getMessage(), t);
            }
        });
    }

    private void setLoadingState(boolean isLoading){
        if (isLoading){
            Login.setEnabled(false);
            if (loadingProgressBar != null) {
                loadingProgressBar.setVisibility(View.VISIBLE);
            }
        }else{
            Login.setEnabled(true);
            if (loadingProgressBar != null) {
                loadingProgressBar.setVisibility(View.GONE);
            }
        }
    }
}