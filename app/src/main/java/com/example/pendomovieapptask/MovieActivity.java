package com.example.pendomovieapptask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import static com.example.pendomovieapptask.MovieRecViewAdapter.MOVIE_ID_KEY;

public class MovieActivity extends AppCompatActivity {

    private TextView txtMovieName, txtMovieVoteCount, txtMovieDate, txtMovieOverview;
    private ImageView movieImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        initViews();

        Intent intent = getIntent();
        if (null != intent) {
            int movieId = intent.getIntExtra(MOVIE_ID_KEY, -1);
            if (movieId != -1) {
                Movie incomingMovie = Utils.getInstance(this).getMovieById(movieId, Utils.getInstance(this).getAllMovies());
                if (null != incomingMovie) {
                    setData(incomingMovie);
                }
            }
        }
    }

    private void setData(Movie movie) {
        txtMovieName.setText(movie.getTitle());
        txtMovieVoteCount.setText(movie.getVoteAverage());
        txtMovieDate.setText(movie.getReleaseDate());
        txtMovieOverview.setText(movie.getOverview());

        Glide.with(this)
                .asBitmap()
                .load(Utils.IMAGE_URL_PREFIX + "w500" + movie.getPosterPath())
                .into(movieImage);
    }

    private void initViews() {
        txtMovieName = findViewById(R.id.movie_activity_title);
        txtMovieVoteCount = findViewById(R.id.movie_activity_vote_average);
        txtMovieDate = findViewById(R.id.movie_activity_release_date);
        txtMovieOverview = findViewById(R.id.movie_activity_overview);
        movieImage = findViewById(R.id.movie_activity_poster);
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
        // R.menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/menu directory.
        // If you don't have res/menu, just create a directory named "menu" inside res
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