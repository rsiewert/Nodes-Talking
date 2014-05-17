package com.topaz.communications.apis;

import java.util.HashMap;

/**
 * Created by rbgodwin on 4/24/14.
 */
public class Api {

    // Name of the method
    String name = new String();

    //Description of the method
    String description = new String();

    //Parameters passed into the method
    private HashMap<String,Object> parameters = new HashMap<String,Object>();

    // Return value of the method
    private String returns = new String("void");

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getReturns() {
        return returns;
    }

    public void setReturns(String returns) {
        this.returns = returns;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addParameter(String name, Object value)
    {
        this.parameters.put(name, value);
    }

    public Object getParameter(String name)
    {
        return this.parameters.get(name);
    }



}
