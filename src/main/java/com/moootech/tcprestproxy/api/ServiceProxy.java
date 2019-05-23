/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moootech.tcprestproxy.api;

import com.moootech.tcprestproxy.core.Call;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author youssef
 */
@Path("proxy")
@RequestScoped
public class ServiceProxy {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response submit(Call call) throws IOException {
        if (call.getPort() < 50001 || call.getPort() > 50200) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        Socket socket = new Socket("172.17.0.1", call.getPort());
        socket.setSoTimeout(10000);
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
        for (String procedure : call.getProcedureList()) {
            printWriter.println(procedure);
        }
        printWriter.flush();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        List<String> resultList = new ArrayList<>();
        String s;
        for (int i = 0; i < call.getProcedureList().size(); i++) {
            resultList.add(bufferedReader.readLine());
        }
        return Response.ok(resultList).build();
    }

}
