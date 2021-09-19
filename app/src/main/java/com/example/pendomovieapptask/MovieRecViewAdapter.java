package com.example.pendomovieapptask;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class MovieRecViewAdapter extends RecyclerView.Adapter<MovieRecViewAdapter.ViewHolder>{

    public static final String MOVIE_ID_KEY = "movieId";

    private  ArrayList<Movie> movies = new ArrayList<>();
    private Context context;

    public MovieRecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_movie_item, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(movies.get(position).getTitle());
        holder.release_date.setText(movies.get(position).getReleaseDate());

        String path = movies.get(position).getPosterPath();

        if (path != null) {
            Glide.with(context)
                    .asBitmap()
                    .load(Utils.IMAGE_URL_PREFIX + Utils.IMAGE_WIDTH + path)
                    .into(holder.poster_path);
        }

        holder.movie_item_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MovieActivity.class);
                intent.putExtra(MOVIE_ID_KEY, movies.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView release_date;
        private CardView movie_item_parent;
        private ImageView poster_path;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.movie_item_title);
            release_date = itemView.findViewById(R.id.movie_item_release_date);
            movie_item_parent = itemView.findViewById(R.id.movie_item_parent);
            poster_path = itemView.findViewById(R.id.movie_item_poster);
        }
    }
}
