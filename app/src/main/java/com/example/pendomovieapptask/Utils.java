package com.example.pendomovieapptask;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.security.Key;
import java.util.ArrayList;

public class Utils {

    private static final String ALL_MOVIES_KEY = "all_movies";
    private static final String SEARCHED_MOVIES_KEY = "searched_movies";
    private static final String KEYWORDS_KEY = "keywords";

    public static final String IMAGE_URL_PREFIX = "https://image.tmdb.org/t/p/";
    public static final String IMAGE_WIDTH = "original";

    private static Utils instance;
    private SharedPreferences sharedPreferences;


    private Utils(Context context) {
        sharedPreferences = context.getSharedPreferences("alternate_db", Context.MODE_PRIVATE);

        if (null == getAllMovies()) {
            initData();
        }
    }

    public static Utils getInstance(Context context) {
        if (null != instance) {
            return instance;
        } else {
            instance = new Utils(context);
            return instance;
        }
    }

    private void initData() {
        ArrayList<Movie> movies = new ArrayList<>();
        ArrayList<Keyword> keywords = new ArrayList<>();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.putString(ALL_MOVIES_KEY, gson.toJson(movies));
        editor.putString(SEARCHED_MOVIES_KEY, gson.toJson(movies));
        editor.putString(KEYWORDS_KEY, gson.toJson(keywords));
        editor.commit();
    }

    public ArrayList<Movie> getAllMovies() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Movie>>(){}.getType();
        ArrayList<Movie> movies = gson.fromJson(sharedPreferences.getString(ALL_MOVIES_KEY, null), type);
        return movies;
    }

    public ArrayList<Movie> getSearchedMovies() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Movie>>(){}.getType();
        ArrayList<Movie> movies = gson.fromJson(sharedPreferences.getString(SEARCHED_MOVIES_KEY, null), type);
        return movies;
    }

    public ArrayList<Keyword> getKeywords() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Keyword>>(){}.getType();
        ArrayList<Keyword> keywords = gson.fromJson(sharedPreferences.getString(KEYWORDS_KEY, null), type);
        return keywords;
    }

    public Movie getMovieById(int id, ArrayList<Movie> movies) {
        if (null != movies) {
            for (Movie m : movies) {
                if (m.getId() == id) {
                    return m;
                }
            }
        }

        return null;
    }

    public Keyword getKeywordById(int id, ArrayList<Keyword> keywords) {
        if (null != keywords) {
            for (Keyword k : keywords) {
                if (k.getId() == id) {
                    return k;
                }
            }
        }

        return null;
    }

    public boolean setMovies(ArrayList<Movie> movies) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.remove(ALL_MOVIES_KEY);
        editor.putString(ALL_MOVIES_KEY, gson.toJson(movies));
        editor.commit();
        return true;
    }

    public boolean setSearchedMovies(ArrayList<Movie> movies) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.remove(SEARCHED_MOVIES_KEY);
        editor.putString(SEARCHED_MOVIES_KEY, gson.toJson(movies));
        editor.commit();
        return true;
    }

    public boolean setKeywords(ArrayList<Keyword> keywords) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.remove(KEYWORDS_KEY);
        editor.putString(KEYWORDS_KEY, gson.toJson(keywords));
        editor.commit();
        return true;
    }
}
