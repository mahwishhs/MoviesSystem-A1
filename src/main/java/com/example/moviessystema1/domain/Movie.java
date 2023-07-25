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
    private List<Genre> genres;

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

    public List<Actor> getActors() {
        return actors;
    }

    public List<Director> getDirectors() {
        return directors;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void addActor(Actor actor, String role) {
        actors.add(new Actor(actor.getActorId(), actor.getFirstName(), actor.getLastName(), actor.getDateOfBirth(), actor.getNationality()));
    }

    public void addDirector(Director director) {
        directors.add(new Director(director.getDirectorId(), director.getFirstName(), director.getLastName(), director.getDateOfBirth(), director.getNationality()));
    }

    public void addGenre(Genre genre) {
        genres.add(new Genre(genre.getGenreId(), genre.getName()));
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
                ", actors=" + actors +
                ", directors=" + directors +
                ", genres=" + genres +
                '}';
    }
}
