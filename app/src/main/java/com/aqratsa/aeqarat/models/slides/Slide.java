package com.aqratsa.aeqarat.models.slides;

public class Slide {

    private String image;

    private String title1;

    private String created_at;

    private String title2;

    private String title3;

    private String title;

    private int local_image;

    private String link1;

    private String updated_at;

    private String link3;

    private String id;

    private String link2;

    private String description;

    public Slide(String title, int local_image, String description) {
        this.title = title;
        this.local_image = local_image;
        this.description = description;
    }

    public Slide(String image) {
        this.image = image;
    }

    public String getImage ()
    {
        return image;
    }

    public void setImage (String image)
    {
        this.image = image;
    }

    public String getTitle1 ()
    {
        return title1;
    }

    public void setTitle1 (String title1)
    {
        this.title1 = title1;
    }

    public String getCreated_at ()
    {
        return created_at;
    }

    public void setCreated_at (String created_at)
    {
        this.created_at = created_at;
    }

    public String getTitle2 ()
    {
        return title2;
    }

    public void setTitle2 (String title2)
    {
        this.title2 = title2;
    }

    public String getTitle3 ()
    {
        return title3;
    }

    public void setTitle3 (String title3)
    {
        this.title3 = title3;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public int getLocal_image ()
    {
        return local_image;
    }

    public void setLocal_image (int local_image)
    {
        this.local_image = local_image;
    }

    public String getLink1 ()
    {
        return link1;
    }

    public void setLink1 (String link1)
    {
        this.link1 = link1;
    }

    public String getUpdated_at ()
    {
        return updated_at;
    }

    public void setUpdated_at (String updated_at)
    {
        this.updated_at = updated_at;
    }

    public String getLink3 ()
    {
        return link3;
    }

    public void setLink3 (String link3)
    {
        this.link3 = link3;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getLink2 ()
    {
        return link2;
    }

    public void setLink2 (String link2)
    {
        this.link2 = link2;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [image = "+image+", title1 = "+title1+", created_at = "+created_at+", title2 = "+title2+", title3 = "+title3+", title = "+title+", local_image = "+local_image+", link1 = "+link1+", updated_at = "+updated_at+", link3 = "+link3+", id = "+id+", link2 = "+link2+", description = "+ description +"]";
    }
}
