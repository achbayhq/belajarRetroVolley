package com.abayhq.belajarretrovolley;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.abayhq.belajarretrovolley.volley.volleyRequestHandler;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private EditText etNama, etEmail, etPass;
    private Button btnRegister;
    private String URL = "http://192.168.111.127/belajarRetroVolley/register.php";
    private String nama, email, pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNama = findViewById(R.id.txtNama);
        etEmail = findViewById(R.id.txtEmail);
        etPass = findViewById(R.id.txtPass);
        btnRegister = findViewById(R.id.button);

    }

    public void register(View view) {
        nama = etNama.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        pass = etPass.getText().toString().trim();

//        if (!nama.equals("") && !email.equals("") &&!pass.equals("")){
//            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    if (response.equals("berhasil")) {
//                        Intent intent = new Intent(MainActivity.this, DasActivity.class);
//                        startActivity(intent);
//                        finish();
//                    } else if (response.equals("gagal")) {
//                        Toast.makeText(MainActivity.this, "Gagal Register", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Toast.makeText(MainActivity.this, error.toString().trim() , Toast.LENGTH_SHORT).show();
//                    Log.e("Volley conn error", "message: ", error);
//                }
//            }){
//                @Nullable
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String, String> data = new HashMap<>();
//                    data.put("nama", email);
//                    data.put("email", nama);
//                    data.put("password", pass);
//                    return data;
//                }
//            };
//            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//            requestQueue.add(stringRequest);
//        }else{
//            Toast.makeText(MainActivity.this, "Isi Data Secara Lengkap", Toast.LENGTH_SHORT).show();
//        }

        volleyRequestHandler volleyRequestHandler = new volleyRequestHandler(this);
        volleyRequestHandler.registerUser(nama, email, pass, new volleyRequestHandler.ResponseListener() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int code = response.getInt("code");
                    if (code == 201){
                        Intent intent = new Intent(MainActivity.this, DasActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (code == 405) {
                        Toast.makeText(MainActivity.this, "Register Gagal", Toast.LENGTH_SHORT).show();
                    } else if (code == 406) {
                        Toast.makeText(MainActivity.this, "User Sudah Pernah Register", Toast.LENGTH_SHORT).show();
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {
                Toast.makeText(MainActivity.this, error , Toast.LENGTH_SHORT).show();
            }
        });
    }
}