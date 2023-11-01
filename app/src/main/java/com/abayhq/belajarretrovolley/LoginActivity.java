package com.abayhq.belajarretrovolley;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.abayhq.belajarretrovolley.volley.volleyRequestHandler;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private String email;
    private String password;
    private EditText etEmail, etPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.txtEmailLogin);
        etPass = findViewById(R.id.txtPassLogin);
    }

    public void keDas(View view) {
        email = etEmail.getText().toString().trim();
        password = etPass.getText().toString().trim();

        volleyRequestHandler volleyRequestHandler = new volleyRequestHandler(this);
        volleyRequestHandler.loginUser(email, password, new volleyRequestHandler.ResponseListener() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    int code = response.getInt("code");
                    if (code == 200){
                        Intent intent = new Intent(LoginActivity.this, DasActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (code == 401) {
                        Toast.makeText(LoginActivity.this, "Email atau Password Salah", Toast.LENGTH_SHORT).show();
                    } else if (code == 404) {
                        Toast.makeText(LoginActivity.this, "User Tidak Ditemukan, Silahkan Register", Toast.LENGTH_SHORT).show();
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }
}