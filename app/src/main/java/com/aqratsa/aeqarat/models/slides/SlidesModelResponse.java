package com.aqratsa.aeqarat.models.slides;

import com.google.gson.annotations.SerializedName;

public class SlidesModelResponse {

    @SerializedName("data")
    private Slide[] slide;

    private String key;

    public Slide[] getSlide ()
    {
        return slide;
    }

    public void setSlide (Slide[] slide)
    {
        this.slide = slide;
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
        return "ClassPojo [slide = "+slide+", key = "+key+"]";
    }
}
