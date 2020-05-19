package com.aqratsa.aeqarat.models.favorites;

import com.google.gson.annotations.SerializedName;

public class RealEstate {

    private String akar_id;

    private String updated_at;

    private String user_id;

    private String created_at;

    private String id;

    @SerializedName("bulid")
    private Details details;

    public String getAkar_id ()
    {
        return akar_id;
    }

    public String getUpdated_at ()
    {
        return updated_at;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public String getCreated_at ()
    {
        return created_at;
    }

    public String getId ()
    {
        return id;
    }

    public Details getDetails ()
    {
        return details;
    }

}
