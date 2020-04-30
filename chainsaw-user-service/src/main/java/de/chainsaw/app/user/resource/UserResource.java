package de.chainsaw.app.user.resource;

import de.chainsaw.app.user.model.User;
import de.chainsaw.app.user.service.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/user")
public class UserResource {

    private UserService userService;

    UserResource(final UserService userService) {
        this.userService = userService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> all() {
        return userService.listAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User find(@PathParam("id") Long id) {
        return userService.findById(id);
    }

    @POST
    @Path("/{name}")
    public void create(@PathParam("name") String name) {
        final User user = new User();
        user.name = name;
        userService.persist(user);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void delete(@PathParam("id") Long id) {
        userService.delete(id);
    }
}
