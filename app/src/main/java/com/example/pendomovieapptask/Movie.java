package com.example.pendomovieapptask;

public class Movie {
    private int id;
    private String title;
    private String poster_path;
    private String popularity;
    private String release_date;
    private String overview;
    private String vote_average;

    public Movie(int id, String title, String poster_path, String popularity, String release_date, String overview, String vote_average) {
        this.id = id;
        this.title = title;
        this.poster_path = poster_path;
        this.popularity = popularity;
        this.release_date = release_date;
        this.overview = overview;
        this.vote_average = vote_average;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return poster_path;
    }

    public void setPosterPath(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getReleaseDate() {
        return release_date;
    }

    public void setReleaseDate(String release_date) {
        this.release_date = release_date;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getVoteAverage() {
        return vote_average;
    }

    public void setVoteAverage(String vote_average) {
        this.vote_average = vote_average;
    }
}
