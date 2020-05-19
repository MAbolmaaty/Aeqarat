package com.aqratsa.aeqarat.models.real_estate;

public class Comment {

    private String akar_id;

    private String updated_at;

    private String user_id;

    private String rate;

    private String created_at;

    private String photo;

    private String comment;

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

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    public String getRate ()
    {
        return rate;
    }

    public void setRate (String rate)
    {
        this.rate = rate;
    }

    public String getCreated_at ()
    {
        return created_at;
    }

    public void setCreated_at (String created_at)
    {
        this.created_at = created_at;
    }

    public String getPhoto ()
    {
        return photo;
    }

    public void setPhoto (String photo)
    {
        this.photo = photo;
    }

    public String getComment ()
    {
        return comment;
    }

    public void setComment (String comment)
    {
        this.comment = comment;
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
        return "ClassPojo [akar_id = "+akar_id+", updated_at = "+updated_at+", user_id = "+user_id+", rate = "+rate+", created_at = "+created_at+", photo = "+photo+", comment = "+comment+", id = "+id+"]";
    }
}
