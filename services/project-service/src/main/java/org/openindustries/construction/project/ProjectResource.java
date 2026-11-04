package org.openindustries.construction.project;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.UUID;

@Path("/api/v1/projects")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Projects", description = "Infrastructure project registry.")
public class ProjectResource {

    @Inject
    ProjectService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a project")
    public Response create(@Valid CreateProjectRequest request) {
        return Response.status(Response.Status.CREATED).entity(service.create(request)).build();
    }

    @GET
    @Operation(summary = "List all")
    public List<Project> list() {
        return service.list();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one by id")
    public Project get(@PathParam("id") UUID id) {
        return service.get(id);
    }

    @POST
    @Path("/{id}/start")
    @Operation(summary = "Start this project (lifecycle transition, no body)")
    public Project start(@PathParam("id") UUID id) {
        return service.start(id);
    }

    @POST
    @Path("/{id}/complete")
    @Operation(summary = "Complete this project (lifecycle transition, no body)")
    public Project complete(@PathParam("id") UUID id) {
        return service.complete(id);
    }
}
