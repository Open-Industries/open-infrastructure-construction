package org.openindustries.construction.asset;

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

@Path("/api/v1/construction-assets")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Construction Assets", description = "Fleet registry for site equipment.")
public class ConstructionAssetResource {

    @Inject
    ConstructionAssetService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a constructionasset")
    public Response create(@Valid CreateConstructionAssetRequest request) {
        return Response.status(Response.Status.CREATED).entity(service.create(request)).build();
    }

    @GET
    @Operation(summary = "List all")
    public List<ConstructionAsset> list() {
        return service.list();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one by id")
    public ConstructionAsset get(@PathParam("id") UUID id) {
        return service.get(id);
    }

    @POST
    @Path("/{id}/deploy")
    @Operation(summary = "Deploy (lifecycle transition, no body)")
    public ConstructionAsset deploy(@PathParam("id") UUID id) {
        return service.deploy(id);
    }
}
