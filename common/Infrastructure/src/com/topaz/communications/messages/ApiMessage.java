package com.topaz.communications.messages;

import com.topaz.communications.apis.Api;

public class ApiMessage extends Message {

    public final static String REPLY_ROUTE = "replyRoute";

    protected Api api;

    public ApiMessage(Api api) {
        this.api = api;
    }

    public Api getApi() {
        return api;
    }

    public void setApi(Api api) {
        this.api = api;
    }
}
