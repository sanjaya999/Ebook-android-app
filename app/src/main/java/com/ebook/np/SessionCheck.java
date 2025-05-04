package com.ebook.np;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ebook.np.session.SessionManager;

public class SessionCheck extends AppCompatActivity {

    private SessionManager sessionManager;
    private static final String TAG = "SessionCheck";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_check);

       sessionManager = new SessionManager(this);

       checkLoginStatus();



    }

    public void checkLoginStatus(){
        Intent intent;

        if (sessionManager.isLoggedIn()){
            intent = new Intent(this , BookRecyclerViewActivity.class);
            startActivity(intent);
            finish();
        }else {
            intent = new Intent(this , LoginActivity.class);
            startActivity(intent);
        }
    }
}