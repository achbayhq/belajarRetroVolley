package com.abayhq.belajarretrovolley.volley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class volleyRequestHandler {
        private static final String TAG = volleyRequestHandler.class.getSimpleName();
        private RequestQueue requestQueue;
        private Context context;

        public volleyRequestHandler(Context context) {
            this.context = context;
            requestQueue = Volley.newRequestQueue(context);
        }

        public void registerUser(final String nama, final String email, final String password, final ResponseListener listener) {
            String url = "http://192.168.0.110/WSRetrovolley-master/User_Registration.php"; // Ganti dengan URL server PHP Anda.

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("nama", nama);
                jsonObject.put("email", email);
                jsonObject.put("password", password);
            } catch (Exception e) {
                e.printStackTrace();
            }

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Response sukses dari server
                            listener.onResponse(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Response error dari server
                            listener.onError(error.getMessage());
                        }
                    });

            requestQueue.add(request);
        }
    public interface ResponseListener {
        void onResponse(JSONObject response);
        void onError(String error);
    }

}
