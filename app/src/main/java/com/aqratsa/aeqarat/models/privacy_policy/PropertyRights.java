package com.aqratsa.aeqarat.models.privacy_policy;

public class PropertyRights {

    private String id;

    private String title;

    private String describtion;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getDescribtion ()
    {
        return describtion;
    }

    public void setDescribtion (String describtion)
    {
        this.describtion = describtion;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", title = "+title+", describtion = "+describtion+"]";
    }
}
