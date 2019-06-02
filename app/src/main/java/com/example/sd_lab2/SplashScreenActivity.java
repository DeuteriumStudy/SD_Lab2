package com.example.sd_lab2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SplashScreenActivity extends AppCompatActivity {
    public static final String TAG = "SplashTag";
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        requestQueue = Volley.newRequestQueue(this);

        final String url = "https://raw.githubusercontent.com/wesleywerner/ancient-tech/master/src/data/techs.ruleset.json";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ObjectList objectList = ObjectList.getInstance();

                        objectList.clear();
                        for(int i = 1; i < response.length(); i++) {
                            try {
                                JSONObject obj = (JSONObject) response.get(i);

                                objectList.addData(new ObjectList.Data(
                                        obj.optString("name"),
                                        obj.optString("helptext"),
                                        obj.optString("graphic")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        startMainActivity();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //ErrorActivity
                    }
                });

        jsonArrayRequest.setTag(TAG);
        requestQueue.add(jsonArrayRequest);
    }

    private void startMainActivity(){
        Log.d("mytag", "Intented");
        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStop () {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(TAG);
        }
    }
}
