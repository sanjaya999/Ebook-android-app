package com.ebook.np;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.ebook.np.session.SessionManager;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class ProfileActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView name , email , id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SessionManager sessionManager = new SessionManager(this);

        imageView = findViewById(R.id.profileImage);
        name = findViewById(R.id.profileName);
        email = findViewById(R.id.profileEmail);
        id = findViewById(R.id.profileId);

        String name = sessionManager.getUserName();
        String email = sessionManager.getUserEmail();
        String id = sessionManager.getUserId();
        String imageUrl = sessionManager.getUserProfileUrl();

        Picasso.get().load(imageUrl).into(imageView);
        this.name.setText(name);
        this.email.setText(email);
        this.id.setText(id);



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        MenuItem profileMenuItem = menu.findItem(R.id.profile);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
         if (id == R.id.logout) {
               SessionManager sessionManager = new SessionManager(this);
             sessionManager.logoutUser();
            Intent i = new Intent(ProfileActivity.this , LoginActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}