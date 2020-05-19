package com.aqratsa.aeqarat.models.real_estate;

import com.google.gson.annotations.SerializedName;

public class Ownership {

    private String akar_id;

    private String tenant_id;

    private String tenant_old_id;

    private String updated_at;

    private String owner_id;

    private String responsible_id;

    private String created_at;

    private String id;

    @SerializedName("user")
    private Owner owner;

    public String getAkar_id ()
    {
        return akar_id;
    }

    public String getTenant_id ()
    {
        return tenant_id;
    }

    public String getTenant_old_id ()
    {
        return tenant_old_id;
    }

    public String getUpdated_at ()
    {
        return updated_at;
    }

    public String getOwner_id ()
    {
        return owner_id;
    }

    public String getResponsible_id ()
    {
        return responsible_id;
    }

    public String getCreated_at ()
    {
        return created_at;
    }

    public String getId ()
    {
        return id;
    }

    public Owner getOwner() {
        return owner;
    }
}
