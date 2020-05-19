package com.aqratsa.aeqarat.models.real_estates_rented;

import com.google.gson.annotations.SerializedName;

public class RentedRealEstatesModelResponse {

    @SerializedName("data")
    private RentedRealEstates[] rentedRealEstates;

    private String key;

    public RentedRealEstates[] getRentedRealEstates ()
    {
        return rentedRealEstates;
    }

    public void setRentedRealEstates (RentedRealEstates[] rentedRealEstates)
    {
        this.rentedRealEstates = rentedRealEstates;
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
        return "ClassPojo [data = "+rentedRealEstates+", key = "+key+"]";
    }
}
