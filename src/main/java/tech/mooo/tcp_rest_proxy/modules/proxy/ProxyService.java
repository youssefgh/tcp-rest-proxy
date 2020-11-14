package tech.mooo.tcp_rest_proxy.modules.proxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import tech.mooo.tcp_rest_proxy.modules.proxy.core.Call;

@RequestScoped
public class ProxyService {

    @Inject
    @ConfigProperty(name = "electrum.server")
    private String electrumServer;

    @Inject
    @ConfigProperty(name = "electrum.port")
    private Integer electrumPort;

    public List<String> send(Call call) throws IOException {
        try (var socket = new Socket(electrumServer, electrumPort);) {
            socket.setSoTimeout(10000);
            var printWriter = new PrintWriter(socket.getOutputStream());
            call.procedureList.forEach(printWriter::println);
            printWriter.flush();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            List<String> resultList = new ArrayList<>();
            for (int i = 0; i < call.procedureList.size(); i++) {
                resultList.add(bufferedReader.readLine());
            }
            return resultList;
        }
    }

}
