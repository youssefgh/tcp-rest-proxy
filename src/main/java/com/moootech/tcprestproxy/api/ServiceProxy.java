/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moootech.tcprestproxy.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.moootech.tcprestproxy.core.Call;

import service.StartupBean;

/**
 *
 * @author youssef
 */
@Path("proxy")
@RequestScoped
public class ServiceProxy {

    @Inject
    private StartupBean startupBean;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response submit(Call call) throws IOException {
        var socket = new Socket(startupBean.getServer(), startupBean.getPort());
        socket.setSoTimeout(10000);
        var printWriter = new PrintWriter(socket.getOutputStream());
        call.getProcedureList().forEach((procedure) -> {
            printWriter.println(procedure);
        });
        printWriter.flush();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        List<String> resultList = new ArrayList<>();
        for (int i = 0; i < call.getProcedureList().size(); i++) {
            resultList.add(bufferedReader.readLine());
        }
        return Response.ok(resultList).build();
    }

}
