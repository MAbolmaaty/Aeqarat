package com.aqratsa.aeqarat.models.real_estates_owned;

import com.google.gson.annotations.SerializedName;

public class RealEstatesOwned {

    @SerializedName("akar_id")
    private String mRealEstateId;

    private String tenant_id;

    private String tenant_old_id;

    @SerializedName("Building")
    private RealEstateOwned mRealEstateOwned;

    private String updated_at;

    private String owner_id;

    private String responsible_id;

    private String created_at;

    private String id;

    private String order_num;

    public String getRealEstateId ()
    {
        return mRealEstateId;
    }

    public String getTenant_id ()
    {
        return tenant_id;
    }

    public String getTenant_old_id ()
    {
        return tenant_old_id;
    }

    public RealEstateOwned getRealEstateOwned ()
    {
        return mRealEstateOwned;
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

    public String getOrder_num ()
    {
        return order_num;
    }
}
