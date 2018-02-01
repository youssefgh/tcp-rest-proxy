/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.moootech.tcprestproxy.core;

import java.util.List;

/**
 *
 * @author youssef
 */
public class Call {

    private String server;
    private Integer port;
    private List<String> procedureList;

    public String getServer() {
        return server;
    }

    public Integer getPort() {
        return port;
    }

    public List<String> getProcedureList() {
        return procedureList;
    }

}
