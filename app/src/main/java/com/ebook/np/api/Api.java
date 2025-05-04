package com.ebook.np.api;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.ebook.np.session.SessionManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {
    private static  final String Base_URL = "https://ebook-project-rho.vercel.app/";
    private  static Retrofit retrofitPublic = null;
    private static Retrofit retrofitAuth = null;
    private static final String TAG = "ApiClient";


    public  static Retrofit getClient(){
        if(retrofitPublic == null){
            retrofitPublic = new Retrofit.Builder()
                    .baseUrl(Base_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitPublic;
    }

    public static Retrofit getClientAuth(Context context) {
        // Use existing instance if available
        if (retrofitAuth == null) {
            if (context == null) {
                Log.e(TAG, "Context cannot be null for authenticated client");
                return null; // Or throw exception
            }
            Context appContext = context.getApplicationContext();

            // Optional: Logging Interceptor for authenticated calls
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY); // Use NONE for production

            // Create the Auth Interceptor (only tries to add token)
            Interceptor authInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request originalRequest = chain.request();

                    // Get token from SessionManager
                    SessionManager sessionManager = new SessionManager(appContext);
                    String token = sessionManager.getAccessToken();

                    // If token exists, add the Authorization header
                    if (token != null && !token.isEmpty()) {
                        Log.d(TAG, "AuthClient: Adding Authorization header.");
                        Request modifiedRequest = originalRequest.newBuilder()
                                .header("Authorization", "Bearer " + token)
                                .build();
                        return chain.proceed(modifiedRequest);
                    } else {
                        // No token found. Proceed without header.
                        // Server should handle unauthorized access for protected endpoints.
                        Log.w(TAG, "AuthClient: No token found. Proceeding without Auth header.");
                        return chain.proceed(originalRequest);
                    }
                }
            };

            // Build OkHttpClient with the auth interceptor
            OkHttpClient okHttpClientAuth = new OkHttpClient.Builder()
                    .addInterceptor(authInterceptor) // Add our auth interceptor
                    .addInterceptor(loggingInterceptor) // Add logging
                    .build();

            // Build Retrofit for authenticated calls
            retrofitAuth = new Retrofit.Builder()
                    .baseUrl(Base_URL)
                    .client(okHttpClientAuth) // Use the authenticated client
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Log.d(TAG, "Authenticated Retrofit client created.");
        }
        return retrofitAuth;
    }

    // Optional: Method to clear singleton instances, e.g., on logout
    // Call this if you need to ensure fresh instances are created next time
    public static void clearClientInstances() {
        retrofitPublic = null;
        retrofitAuth = null;
        Log.d(TAG, "Cleared Retrofit client instances.");
    }

}
