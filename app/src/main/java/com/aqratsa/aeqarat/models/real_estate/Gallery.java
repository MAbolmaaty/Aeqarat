package com.aqratsa.aeqarat.models.real_estate;

public class Gallery {

    private String akar_id;

    private String updated_at;

    private String photo;

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

    public String getUpdated_at ()
    {
        return updated_at;
    }

    public void setUpdated_at (String updated_at)
    {
        this.updated_at = updated_at;
    }

    public String getPhoto ()
    {
        return photo;
    }

    public void setPhoto (String photo)
    {
        this.photo = photo;
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
        return "ClassPojo [akar_id = "+akar_id+", updated_at = "+updated_at+", photo = "+photo+", created_at = "+created_at+", id = "+id+"]";
    }
}
