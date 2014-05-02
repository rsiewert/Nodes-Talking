package com.topaz.communications.servers;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.sun.security.sasl.ClientFactoryImpl;
import com.topaz.nodes.Device;

import javax.print.attribute.standard.Media;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by rbgodwin on 4/30/14.
 */
public class RestServer {

    final static String APP = "RestServer";

    private String ip;
    Client mClient = ClientBuilder.newClient();

    public RestServer(String ip) {
        this.ip = ip;
    }

    public String getJson(String endpoint, String jsonQuery) {

        Response response;
        String resource = "http://" + this.ip + endpoint;
        WebTarget target = mClient.target(resource);

        try {
            response = target.request(MediaType.APPLICATION_JSON)
                    .post(Entity.json(jsonQuery));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return response.readEntity(String.class);
    }
}

