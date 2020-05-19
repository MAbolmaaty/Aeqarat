package com.aqratsa.aeqarat.models.info_user;

public class RentedRealEstates {

    private String akar_id;

    private String tenant_id;

    private String tenant_old_id;

    private String updated_at;

    private String owner_id;

    private String responsible_id;

    private String created_at;

    private String id;

    public String getAkar_id ()
    {
        return akar_id;
    }

    public void setAkar_id (String akar_id)
    {
        this.akar_id = akar_id;
    }

    public String getTenant_id ()
    {
        return tenant_id;
    }

    public void setTenant_id (String tenant_id)
    {
        this.tenant_id = tenant_id;
    }

    public String getTenant_old_id ()
    {
        return tenant_old_id;
    }

    public void setTenant_old_id (String tenant_old_id)
    {
        this.tenant_old_id = tenant_old_id;
    }

    public String getUpdated_at ()
    {
        return updated_at;
    }

    public void setUpdated_at (String updated_at)
    {
        this.updated_at = updated_at;
    }

    public String getOwner_id ()
    {
        return owner_id;
    }

    public void setOwner_id (String owner_id)
    {
        this.owner_id = owner_id;
    }

    public String getResponsible_id ()
    {
        return responsible_id;
    }

    public void setResponsible_id (String responsible_id)
    {
        this.responsible_id = responsible_id;
    }

    public String getCreated_at ()
    {
        return created_at;
    }

    public void setCreated_at (String created_at)
    {
        this.created_at = created_at;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [akar_id = "+akar_id+", tenant_id = "+tenant_id+", tenant_old_id = "+tenant_old_id+", updated_at = "+updated_at+", owner_id = "+owner_id+", responsible_id = "+responsible_id+", created_at = "+created_at+", id = "+id+"]";
    }
}
