package tech.mooo.tcp_rest_proxy.modules.proxy.rest;

import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tech.mooo.tcp_rest_proxy.modules.proxy.ProxyService;
import tech.mooo.tcp_rest_proxy.modules.proxy.core.Call;

@Path("proxy")
@RequestScoped
public class ProxyEndpoint {

    @Inject
    private ProxyService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response send(Call call) throws IOException {
        return Response.ok(service.send(call)).build();
    }

}
