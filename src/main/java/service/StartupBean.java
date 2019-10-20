/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;

/**
 *
 * @author youssef
 */
@ApplicationScoped
public class StartupBean {

    private String server;
    private int port;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
        var serverArg = System.getenv("server");
        var portArg = System.getenv("port");
        if (serverArg != null && portArg != null) {
            this.server = serverArg;
            this.port = Integer.parseInt(serverArg);
        } else {
            this.server = "localhost";
            this.port = 50001;
        }
        Logger.getLogger(StartupBean.class.getName()).log(Level.INFO, "Using {0}:{1} as destination", new Object[]{this.server, this.port});
    }

    public String getServer() {
        return server;
    }

    public int getPort() {
        return port;
    }

}
