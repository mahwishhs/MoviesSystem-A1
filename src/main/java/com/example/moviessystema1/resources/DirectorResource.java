package com.example.moviessystema1.resources;
import com.example.moviessystema1.domain.Director;
import com.example.moviessystema1.services.DirectorServices;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/directors")
public class DirectorResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createDirector(Director director) {
        try {
            DirectorServices.createDirector(director);
            return Response.status(Response.Status.CREATED).entity("Director saved successfully.").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to add director.").build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateDirector(@PathParam("id") int id, Director updatedDirector) {
        try {
            Director director = DirectorServices.getDirectorById(id);
            if (director == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Director not found with ID: " + id).build();
            }

            updatedDirector.setDirectorId(id);
            DirectorServices.updateDirector(updatedDirector);

            return Response.status(Response.Status.NO_CONTENT).entity("Director updated successfully.").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to update director.").build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteDirector(@PathParam("id") int id) {
        try {
            Director director = DirectorServices.getDirectorById(id);
            if (director == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Director not found with ID: " + id).build();
            }

            DirectorServices.deleteDirector(id);

            return Response.status(Response.Status.NO_CONTENT).entity("Director deleted successfully.").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to delete director.").build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDirectors() {
        try {
            List<Director> directors = DirectorServices.getAllDirectors();
            return Response.ok(directors).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to fetch directors.").build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDirectorById(@PathParam("id") int id) {
        try {
            Director director = DirectorServices.getDirectorById(id);
            if (director != null) {
                return Response.ok(director).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Director not found with ID: " + id).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to fetch director.").build();
        }
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDirectorsByName(@QueryParam("name") String directorName) {
        try {
            List<Director> directors = DirectorServices.getDirectorsByName(directorName);
            if (!directors.isEmpty()) {
                return Response.ok(directors).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("No directors found with name: " + directorName).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to fetch directors.").build();
        }
    }
}

