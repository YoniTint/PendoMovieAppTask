package com.example.pendomovieapptask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

public class SearchedMoviesActivity extends AppCompatActivity {

    public static final String SEARCH_API_PREFIX = "/search/movie?api_key=" + MainActivity.API_KEY + "&query=";

    private RecyclerView moviesRecView;
    private TextView txtKeyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_movies);

        txtKeyword = findViewById(R.id.searched_movies_activity_keyword);
        moviesRecView = findViewById(R.id.moviesRecView);

        moviesRecView.setHasFixedSize(true);
        moviesRecView.setItemViewCacheSize(20);
        moviesRecView.setDrawingCacheEnabled(true);
        moviesRecView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        MovieRecViewAdapter adapter = new MovieRecViewAdapter(this);

        Intent intent = getIntent();
        if (null != intent) {
            int keywordId = intent.getIntExtra(KeywordsRecViewAdapter.KEYWORD_ID_KEY, -1);
            if (keywordId != -1) {
                Keyword incomingKeyword = Utils.getInstance(this).getKeywordById(keywordId, Utils.getInstance(this).getKeywords());
                if (null != incomingKeyword) {
                    txtKeyword.setText(incomingKeyword.getName());

                    //Search Movies Request Declaration
                    StringRequest searchMoviesRequest = new StringRequest(Request.Method.GET, MainActivity.API_PREFIX + SEARCH_API_PREFIX + incomingKeyword.getName(),
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Gson gson = new Gson();
                                    JsonObject json = gson.fromJson(response, JsonObject.class);
                                    JsonElement temp = json.get("results");
                                    JsonArray jsonMovieArray = temp.getAsJsonArray();

                                    Type type = new TypeToken<ArrayList<Movie>>() {}.getType();
                                    ArrayList<Movie> movies = gson.fromJson(jsonMovieArray, type);

                                    Utils.getInstance(SearchedMoviesActivity.this).setSearchedMovies(movies);

                                    adapter.setMovies(Utils.getInstance(SearchedMoviesActivity.this).getSearchedMovies());
                                    moviesRecView.setAdapter(adapter);
                                    moviesRecView.setLayoutManager(new LinearLayoutManager(SearchedMoviesActivity.this));
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    System.out.println("Something went wrong yoni!!");
                                }
                            });

                    RequestQueue queue = MySingleton.getInstance(SearchedMoviesActivity.this.getApplicationContext()).
                            getRequestQueue();

                    MySingleton.getInstance(SearchedMoviesActivity.this).addToRequestQueue(searchMoviesRequest);
                }
            }
        }
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