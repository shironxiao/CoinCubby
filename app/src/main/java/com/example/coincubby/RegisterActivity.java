package com.example.coincubby;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    // ── Supabase credentials ──────────────────────────────────────────────────
    // LoginActivity.java & RegisterActivity.java — change this line:
    private static final String SUPABASE_URL = "https://cjuimxgxovdmijuenagr.supabase.co";

    // And update the anon key:
    private static final String SUPABASE_ANON = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImNqdWlteGd4b3ZkbWlqdWVuYWdyIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NzY0MzQ0OTEsImV4cCI6MjA5MjAxMDQ5MX0.t6ixuFiD2iYzrNZsc1QjG3gpdTdBuMY37qTKzwxdg18";

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    // ── Views ─────────────────────────────────────────────────────────────────
    private EditText        etFirstName;
    private EditText        etLastName;
    private EditText        etEmail;
    private EditText        etPassword;
    private EditText        etConfirmPassword;
    private AppCompatButton btnRegister;
    private TextView        tvBackToLogin;

    private final OkHttpClient http = new OkHttpClient();

    // ─────────────────────────────────────────────────────────────────────────

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.register);   // make sure your layout file is named register.xml

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(android.R.id.content), (v, insets) -> {
                    Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    v.setPadding(bars.left, bars.top, bars.right, bars.bottom);
                    return insets;
                });

        bindViews();
        setListeners();
    }

    // ── View binding ──────────────────────────────────────────────────────────

    private void bindViews() {
        etFirstName       = findViewById(R.id.etFirstName);
        etLastName        = findViewById(R.id.etLastName);
        etEmail           = findViewById(R.id.etEmailRegister);
        etPassword        = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnRegister       = findViewById(R.id.btnRegister);
        tvBackToLogin     = findViewById(R.id.tvBackToLogin);
    }

    // ── Listeners ─────────────────────────────────────────────────────────────

    private void setListeners() {
        btnRegister.setOnClickListener(v -> attemptRegister());

        tvBackToLogin.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });
    }

    // ── Registration logic ────────────────────────────────────────────────────

    private void attemptRegister() {
        String firstName  = etFirstName.getText().toString().trim();
        String lastName   = etLastName.getText().toString().trim();
        String email      = etEmail.getText().toString().trim();
        String password   = etPassword.getText().toString();
        String confirmPwd = etConfirmPassword.getText().toString();

        // ── local validation ─────────────────────────────────────────────────
        if (TextUtils.isEmpty(firstName)) {
            etFirstName.setError("First name is required");
            etFirstName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(lastName)) {
            etLastName.setError("Last name is required");
            etLastName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email is required");
            etEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Enter a valid email address");
            etEmail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            etPassword.setError("Password must be at least 6 characters");
            etPassword.requestFocus();
            return;
        }
        if (!password.equals(confirmPwd)) {
            etConfirmPassword.setError("Passwords do not match");
            etConfirmPassword.requestFocus();
            return;
        }

        setLoading(true);

        // ── build JSON body ──────────────────────────────────────────────────
        // We pass first_name + last_name inside the `data` object so they land
        // in auth.users.raw_user_meta_data and can later be synced to `customers`.
        JSONObject body = new JSONObject();
        try {
            body.put("email",    email);
            body.put("password", password);

            JSONObject meta = new JSONObject();
            meta.put("first_name", firstName);
            meta.put("last_name",  lastName);
            meta.put("full_name",  firstName + " " + lastName);
            meta.put("role",       "resident");   // default role

            body.put("data", meta);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Request request = new Request.Builder()
                .url(SUPABASE_URL + "/auth/v1/signup")
                .addHeader("apikey",       SUPABASE_ANON)
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(body.toString(), JSON))
                .build();

        http.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> {
                    setLoading(false);
                    Toast.makeText(RegisterActivity.this,
                            "Network error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = response.body() != null ? response.body().string() : "";
                runOnUiThread(() -> {
                    setLoading(false);
                    if (response.isSuccessful()) {
                        onRegistrationSuccess(responseBody);
                    } else {
                        handleAuthError(responseBody);
                    }
                });
            }
        });
    }

    // ── Post-registration ─────────────────────────────────────────────────────

    /**
     * Called after a successful /auth/v1/signup response.
     *
     * If "email confirmations" are enabled in Supabase the response will NOT
     * contain an access_token yet — the user must click the confirmation email.
     * If they are disabled, the token is returned immediately.
     */
    private void onRegistrationSuccess(String responseBody) {
        try {
            JSONObject json        = new JSONObject(responseBody);
            String     accessToken = json.optString("access_token", "");

            if (accessToken.isEmpty()) {
                // Email confirmation required
                Toast.makeText(this,
                        "Account created! Please check your email to confirm your account.",
                        Toast.LENGTH_LONG).show();
            } else {
                // Confirmed immediately — you can persist the token here if needed
                Toast.makeText(this,
                        "Account created successfully! Welcome aboard.",
                        Toast.LENGTH_LONG).show();
            }

            // Navigate back to Login either way
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();

        } catch (JSONException e) {
            // Shouldn't happen on a 2xx, but handle gracefully
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private void handleAuthError(String responseBody) {
        try {
            JSONObject json = new JSONObject(responseBody);
            String msg = json.optString("error_description",
                    json.optString("msg",
                            json.optString("message", "Registration failed. Try again.")));
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            Toast.makeText(this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private void setLoading(boolean loading) {
        btnRegister.setEnabled(!loading);
        btnRegister.setText(loading ? "Creating account…" : "Create Account");
    }
}