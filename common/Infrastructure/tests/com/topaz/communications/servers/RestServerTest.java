package com.topaz.communications.servers;

import com.google.gson.Gson;
import org.junit.Test;
import org.junit.Assert;


/**
 * Created by rbgodwin on 4/30/14.
 */
public class RestServerTest {

    final static String REST_SERVER = "localhost:3000";
    final static String TEST_JSON = "{\"best\":\"message\"}";
    final static String[] TEST_JSON_STRINGS = {"test1", "test2", "test3"};


    @Test
    public void testGetJson() throws Exception {

        String response;
        RestServer testServer = new RestServer(this.REST_SERVER);
        Gson gson = new Gson();

        response = testServer.getJson("/json-mirror",gson.toJson(this.TEST_JSON_STRINGS));

        Assert.assertArrayEquals(this.TEST_JSON_STRINGS, gson.fromJson(response, String[].class));
    }
}
