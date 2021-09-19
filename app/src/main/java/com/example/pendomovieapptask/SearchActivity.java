package com.example.pendomovieapptask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class SearchActivity extends AppCompatActivity {


    public static final String KEYWORD_API_PREFIX = "/search/keyword?api_key=" + MainActivity.API_KEY + "&query=";

    private SearchView searchField;
    private RecyclerView keywordsRecView;
    private String userKeyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchField = findViewById(R.id.search_activity_field);
        keywordsRecView = findViewById(R.id.keywordsRecView);

        keywordsRecView.setHasFixedSize(true);
        keywordsRecView.setItemViewCacheSize(20);
        keywordsRecView.setDrawingCacheEnabled(true);
        keywordsRecView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        KeywordsRecViewAdapter adapter = new KeywordsRecViewAdapter(this);

        searchField.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                userKeyword = newText;

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (newText.equals(userKeyword)) {

                            StringRequest keywordsRequest = new StringRequest(Request.Method.GET, MainActivity.API_PREFIX + KEYWORD_API_PREFIX + newText,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Gson gson = new Gson();
                                            JsonObject json = gson.fromJson(response, JsonObject.class);
                                            JsonElement temp = json.get("results");
                                            JsonArray jsonKeywordsArray = temp.getAsJsonArray();

                                            Type type = new TypeToken<ArrayList<Keyword>>() {
                                            }.getType();
                                            ArrayList<Keyword> keywords = gson.fromJson(jsonKeywordsArray, type);

                                            Utils.getInstance(SearchActivity.this).setKeywords(keywords);

                                            adapter.setKeywords(Utils.getInstance(SearchActivity.this).getKeywords());
                                            keywordsRecView.setAdapter(adapter);
                                            keywordsRecView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            System.out.println("Something went wrong yoni!!");
                                        }
                                    });

                            RequestQueue queue = MySingleton.getInstance(SearchActivity.this.getApplicationContext()).
                                    getRequestQueue();

                            //Adding Discovery Request to the queue
                            MySingleton.getInstance(SearchActivity.this).addToRequestQueue(keywordsRequest);
                        }
                    }
                }, 2000);

                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.back_to_homepage_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.back_to_homepage_menu_button) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}