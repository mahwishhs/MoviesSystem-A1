package com.example.moviessystema1.resources;
import com.example.moviessystema1.domain.Movie;
import com.example.moviessystema1.services.MovieServices;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/movies")
public class MoviesResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMovies() {
        List<Movie> movies = MovieServices.getAllMovies();
        return Response.ok(movies).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addMovie(Movie movie) {
        try {
            MovieServices.addMovie(movie);
            return Response.status(Response.Status.CREATED).entity("Movie saved successfully.").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to add movie.").build();
        }
    }
//
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovieById(@PathParam("id") int id) {
        Movie movie = MovieServices.getMovieById(id);
        if (movie != null) {
            return Response.ok(movie).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Movie not found with ID: " + id).build();
        }
    }
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateMovie(@PathParam("id") int id, Movie updatedMovie) {
        try {
            updatedMovie.setMovieId(id); // Set the movie ID to the provided path parameter
            MovieServices.updateMovie(id, updatedMovie);
            return Response.status(Response.Status.NO_CONTENT).entity("Movie updated successfully.").build();
        }
        catch (Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to update the movie.").build();

        }
        }

    @DELETE
    @Path("/{id}")
    public Response deleteMovie(@PathParam("id") int id) {
        try {
            MovieServices.deleteMovie(id);
            return Response.status(Response.Status.NO_CONTENT).entity("Movie deleted successfully.").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to update the movie.").build();

        }
    }

    @GET
    @Path("/genre/{genreName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMoviesByGenreName(@PathParam("genreName") String genreName) {
        List<Movie> movies = MovieServices.getMoviesByGenre(genreName);
        if (!movies.isEmpty()) {
            return Response.ok(movies).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No movies found with genre: " + genreName).build();
        }
    }

    @GET
    @Path("/actor")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMoviesByActorName(@QueryParam("actorName") String actorName) {
        List<Movie> movieNames = MovieServices.getMoviesByActorName(actorName);
        if (!movieNames.isEmpty()) {
            return Response.ok(movieNames).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No movies found with actor: " + actorName).build();
        }
    }
    @GET
    @Path("/director")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMoviesByDirectorName(@QueryParam("directorName") String directorName) {
        List<Movie> movies = MovieServices.getMoviesByDirector(directorName);
        if (!movies.isEmpty()) {
            return Response.ok(movies).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No movies found with director: " + directorName).build();
        }
    }

    @GET
    @Path("/actor-director")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMoviesByActorAndDirector(
            @QueryParam("actorName") String actorName,
            @QueryParam("directorName") String directorName) {

        List<Movie> movies = MovieServices.getMoviesByActorAndDirector(actorName, directorName);

        if (!movies.isEmpty()) {
            return Response.ok(movies).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No movies found with actor: " + actorName + " and director: " + directorName).build();
        }
    }



}

