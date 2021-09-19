package com.example.pendomovieapptask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

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

public class MainActivity extends AppCompatActivity {

    public static final String API_KEY = "50071e9c1b3f6a5faee7aed6bf4b84c4";

    public static final String API_PREFIX = "https://api.themoviedb.org/3";

    public static final String DISCOVER_URL_PREFIX = "/discover/movie?api_key=" + API_KEY;

    public static final String DISCOVER_EXAMPLE_SUFFIX = "&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1";

    //Main List of movies container
    private RecyclerView moviesRecView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moviesRecView = findViewById(R.id.moviesRecView);

        moviesRecView.setHasFixedSize(true);
        moviesRecView.setItemViewCacheSize(20);
        moviesRecView.setDrawingCacheEnabled(true);
        moviesRecView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        MovieRecViewAdapter adapter = new MovieRecViewAdapter(this);

        //Discovery Request Declaration
        StringRequest discoverRequest = new StringRequest(Request.Method.GET, API_PREFIX + DISCOVER_URL_PREFIX + DISCOVER_EXAMPLE_SUFFIX,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        JsonObject json = gson.fromJson(response, JsonObject.class);
                        JsonElement temp = json.get("results");
                        JsonArray jsonMovieArray = temp.getAsJsonArray();

                        Type type = new TypeToken<ArrayList<Movie>>() {}.getType();
                        ArrayList<Movie> movies = gson.fromJson(jsonMovieArray, type);

                        Utils.getInstance(MainActivity.this).setMovies(movies);

                        adapter.setMovies(Utils.getInstance(MainActivity.this).getAllMovies());
                        moviesRecView.setAdapter(adapter);
                        moviesRecView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Something went wrong yoni!!");
                    }
                });

        // Discovery Request
        RequestQueue queue = MySingleton.getInstance(this.getApplicationContext()).
                getRequestQueue();

        //Adding Discovery Request to the queue
        MySingleton.getInstance(this).addToRequestQueue(discoverRequest);
    }

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.search_menu_button) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}