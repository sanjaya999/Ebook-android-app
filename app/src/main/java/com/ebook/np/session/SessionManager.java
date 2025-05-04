package com.ebook.np.session;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.ebook.np.model.User;

public class SessionManager {

    private static final String PERF_NAME = "UserSessionSimple";
    private static final String KEY_ACCESS_TOKEN = "accessToken";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_USER_NAME = "userName";
    private static final String KEY_USER_EMAIL = "userEmail";
    private static final String KEY_USER_PROFILE_URL = "userProfileUrl";

    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";

    private static final String TAG = "SessionManagerSimple";

    private SharedPreferences sharedPreferences;
    private  SharedPreferences.Editor editor;
    private Context context;


    private static final int PRIVATE_MODE = Context.MODE_PRIVATE;

    public SessionManager(Context context){
        this.context = context.getApplicationContext();
        sharedPreferences = this.context.getSharedPreferences(PERF_NAME , PRIVATE_MODE);
        editor = sharedPreferences.edit();

        if (sharedPreferences == null || editor == null) {
            Log.e(TAG, "Failed to initialize SharedPreferences.");
        }
    }

    public void createLoginSession(String token, User user) {
        if (editor == null || user == null || token == null) {
            Log.e(TAG, "SessionManager not initialized or invalid data provided.");
            return;
        }
        editor.putBoolean(KEY_IS_LOGGED_IN, true); // Set login flag
        editor.putString(KEY_ACCESS_TOKEN, token);
        editor.putString(KEY_USER_ID, user.getId());
        editor.putString(KEY_USER_NAME, user.getFullName());
        editor.putString(KEY_USER_EMAIL, user.getEmail());
        editor.putString(KEY_USER_PROFILE_URL, user.getProfile());
        editor.apply(); // Use apply() for asynchronous saving
        Log.i(TAG, "User session saved successfully (standard SharedPreferences).");
    }

    public String getAccessToken() {
        if (sharedPreferences == null) return null;

        return sharedPreferences.getString(KEY_ACCESS_TOKEN, null);
    }

    public String getUserId() {
        if (sharedPreferences == null) return null;
        return sharedPreferences.getString(KEY_USER_ID, null);
    }

    public String getUserName() {
        if (sharedPreferences == null) return null;
        return sharedPreferences.getString(KEY_USER_NAME, null);
    }

    public String getUserEmail() {
        if (sharedPreferences == null) return null;
        return sharedPreferences.getString(KEY_USER_EMAIL, null);
    }

    public String getUserProfileUrl() {
        if (sharedPreferences == null) return null;
        return sharedPreferences.getString(KEY_USER_PROFILE_URL, null);
    }

    public boolean isLoggedIn() {
        if (sharedPreferences == null) return false;
        // Check the flag instead of just the token for clarity
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void logoutUser() {
        if (editor == null) {
            Log.e(TAG, "SessionManager not initialized.");
            return;
        }
        editor.clear(); // Removes all data from this preference file
        editor.apply();
        Log.i(TAG, "User session cleared (logged out - standard SharedPreferences).");

        // Optional: Redirect to LoginActivity after logout (same as before)
        // Intent i = new Intent(context, LoginActivity.class);
        // i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // context.startActivity(i);
    }
}
