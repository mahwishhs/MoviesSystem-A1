package com.example.moviessystema1.domain;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private int movieId;
    private String title;
    private String releaseDate;
    private int duration;
    private String plotSummary;
    private String language;
    private double budget;
    private double boxOfficeCollection;
    private List<Actor> actors;
    private List<Director> directors;
    private List<String> genres;
    private String genre;


    public Movie(){}

    public Movie(int movieId, String title, String releaseDate, int duration, String plotSummary,
                 String language, double budget, double boxOfficeCollection) {
        this.movieId = movieId;
        this.title = title;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.plotSummary = plotSummary;
        this.language = language;
        this.budget = budget;
        this.boxOfficeCollection = boxOfficeCollection;
        this.actors = new ArrayList<>();
        this.directors = new ArrayList<>();
        this.genres = new ArrayList<>();
    }


    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

//    public void setDirectors(List<Director> directors) {
//        this.directors = directors;
//    }
//
//    public void setActors(List<Actor> actors) {
//        this.actors = actors;
//    }

    public void setLanguage(String language) {
        this.language = language;
    }
//
//    public void setGenres(List<Genre> genres) {
//        this.genres = genres;
//    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setPlotSummary(String plotSummary) {
        this.plotSummary = plotSummary;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public void setBoxOfficeCollection(double boxOfficeCollection) {
        this.boxOfficeCollection = boxOfficeCollection;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getDuration() {
        return duration;
    }

    public String getPlotSummary() {
        return plotSummary;
    }

    public String getLanguage() {
        return language;
    }

    public double getBudget() {
        return budget;
    }

    public double getBoxOfficeCollection() {
        return boxOfficeCollection;
    }
//
//    public List<Actor> getActors() {
//        return actors;
//    }
//
//    public List<Director> getDirectors() {
//        return directors;
//    }
//
//    public List<Genre> getGenres() {
//        return genres;
//    }


    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public void addActor(Actor actor) {
        actors.add(actor);
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setDirectors(List<Director> directors) {
        this.directors = directors;
    }

    public void addDirector(Director director) {
        directors.add(director);
    }

    public List<Director> getDirectors() {
        return directors;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public void addGenre(String genre) {
        genres.add(genre);
    }


    public void setGenre(String genre) {
        this.genre = genre;
    }
    public List<String> getGenres() {
        return genres;
    }






    @Override
    public String toString() {
        return "Movie{" +
                "movieId=" + movieId +
                ", title='" + title + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", duration=" + duration +
                ", plotSummary='" + plotSummary + '\'' +
                ", language='" + language + '\'' +
                ", budget=" + budget +
                ", boxOfficeCollection=" + boxOfficeCollection +
                ", genres=" + genres +
                '}';
    }


}
