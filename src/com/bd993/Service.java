package com.bd993;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.glassfish.jersey.server.mvc.Viewable;

import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/")
public class Service {
  public static final String JSON_UTF_8 = "application/json;charset=UTF-8";
  
  private static Response toJson(final Object object) {
    return Response.ok(new StreamingOutput() {  
      @Override
      public void write(OutputStream output) throws IOException, WebApplicationException {
        new ObjectMapper().writeValue(output, object);
      }
    }).build();
  }
  
  @GET
  @Produces(MediaType.TEXT_HTML)
  public Viewable getLogin() {
    return new Viewable("/index.jsp");
  }
  
  @GET
  @Path("/api/v1/items")
  @Produces(JSON_UTF_8)
  public Response getList() {
    return toJson(TodoItems.getInstance().getAll());
  }
  
  @POST
  @Path("/api/v1/item")
  @Produces(JSON_UTF_8)
  public Response addItem(@Context HttpServletRequest request) {
    try {
      TodoItem item = new ObjectMapper().readValue(request.getInputStream(), TodoItem.class);
      TodoItems.getInstance().add(item);
    } catch (Exception e) {
      e.printStackTrace();
      return Response.status(400).entity("Invalid request").build();
    }
    return Response.ok().build();
  }
  
  @GET
  @Path("/api/v1/item/{id}")
  @Produces(JSON_UTF_8)
  public Response getItem(@PathParam("id") String id) {
    return toJson(TodoItems.getInstance().get(id));
  }
  
  @PUT
  @Path("/api/v1/item/{id}")
  @Produces(JSON_UTF_8)
  public Response updateItem(@Context HttpServletRequest request, @PathParam("id") String id) {
    TodoItem newItem;
    try {
      TodoItem item = new ObjectMapper().readValue(request.getInputStream(), TodoItem.class);
      newItem = TodoItems.getInstance().set(item);
    } catch (Exception e) {
      e.printStackTrace();
      return Response.status(400).entity("Invalid request").build();
    }
    return toJson(newItem);
  }
  
  @DELETE
  @Path("/api/v1/item/{id}")
  @Produces(JSON_UTF_8)
  public Response deleteItem(@PathParam("id") String id) {
    TodoItems.getInstance().delete(id);
    return Response.ok().build();
  }
}
