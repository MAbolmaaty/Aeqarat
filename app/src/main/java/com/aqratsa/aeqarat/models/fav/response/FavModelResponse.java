package com.aqratsa.aeqarat.models.fav.response;

public class FavModelResponse {

    private String message;

    private String key;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getKey ()
    {
        return key;
    }

    public void setKey (String key)
    {
        this.key = key;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", key = "+key+"]";
    }
}
