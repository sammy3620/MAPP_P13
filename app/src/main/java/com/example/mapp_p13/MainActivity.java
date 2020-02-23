package com.example.mapp_p13;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    final String TAG ="MainActivity";
    RecyclerView recyclerView;
    ProductListAdapter mAdapter;
    JSONObject items = new JSONObject();
    public static RequestQueue queue;


    void connectToInternet(){
// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://mapp2020-e0cf2.firebaseio.com/products.json";
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
// Display the first 500 characters of the response string.
                       // Log.d(TAG, "Response is: "+ response.substring(0,500));
                        Log.d(TAG, "Response is: " + response);
                        parseData(response);
                    }
                }, new Response.ErrorListener() {@Override
        public void onErrorResponse(VolleyError error) {
            Log.e(TAG, "That didn't work!");
        }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    void parseData(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
// once data is loaded, update adapter for RecyclerView
            mAdapter.setItems(jsonObject);
            mAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queue = Volley.newRequestQueue(this);

        setupRecyler();
        connectToInternet();
    }
    void setupRecyler() {
        recyclerView = findViewById(R.id.recyclerview);
        mAdapter = new ProductListAdapter(this,items);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void launchaddstuff(View view) {
        Intent intent = new Intent(this, AddStuff.class);
        startActivity(intent);
    }




}
