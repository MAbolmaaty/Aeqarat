package com.aqratsa.aeqarat.models.real_estate_categories;

import com.google.gson.annotations.SerializedName;

public class RealEstateCategoriesModelResponse {

    @SerializedName("data")
    private Category[] mCategories;

    private String key;

    public Category[] getCategories ()
    {
        return mCategories;
    }

    public String getKey ()
    {
        return key;
    }
}
