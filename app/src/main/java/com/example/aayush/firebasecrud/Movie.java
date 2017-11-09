package com.example.aayush.firebasecrud;

/**
 * Created by Aayush on 11/9/2017.
 */

public class Movie {
    public String movie_id;
    public String moviename;
    public String rating;
    public Movie(){}
    public Movie(String movie_id,String moviename,String rating){

        this.movie_id = movie_id;
        this.moviename = moviename;
        this.rating = rating;
    }

    public String getMovie_id() {
        return movie_id;
    }

    public String getMoviename() {
        return moviename;
    }

    public String getRating() {
        return rating;
    }
}
