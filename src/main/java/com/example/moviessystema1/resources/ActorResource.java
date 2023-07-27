package com.example.moviessystema1.resources;

import com.example.moviessystema1.domain.Actor;
import com.example.moviessystema1.services.ActorServices;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/actors")
public class ActorResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addActor(Actor actor) {
        try {
            ActorServices.addActor(actor);
            return Response.status(Response.Status.CREATED).entity("Actor added successfully.").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to add actor.").build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateActor(@PathParam("id") int id, Actor updatedActor) {
        try {
            updatedActor.setActorId(id); // Set the actor ID to the provided path parameter
            ActorServices.updateActor(updatedActor);
            return Response.status(Response.Status.NO_CONTENT).entity("Actor updated successfully.").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to update the actor.").build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteActor(@PathParam("id") int id) {
        try {
            ActorServices.deleteActor(id);
            return Response.status(Response.Status.NO_CONTENT).entity("Actor deleted successfully.").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to delete the actor.").build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public static Response getAllActors() {
        List<Actor> actors = ActorServices.getAllActors();
        return Response.ok(actors).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getActorById(@PathParam("id") int id) {
        Actor actor = ActorServices.getActorById(id);
        if (actor != null) {
            return Response.ok(actor).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Actor not found with ID: " + id).build();
        }
    }

    @GET
    @Path("/name/{actorName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getActorsByName(@PathParam("actorName") String actorName) {
        List<Actor> actors = ActorServices.getActorsByName(actorName);
        if (!actors.isEmpty()) {
            return Response.ok(actors).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("No actors found with name: " + actorName).build();
        }
    }
}
