package com.aqratsa.aeqarat.models.unfavorite.request;

import com.google.gson.annotations.SerializedName;

public class UnFavoriteModelRequest {

    @SerializedName("akar_id")
    private String realEstateId;
    @SerializedName("user_id")
    private String userId;
    @SerializedName("lang")
    private String locale;

    public UnFavoriteModelRequest(String realEstateId, String userId, String locale) {
        this.realEstateId = realEstateId;
        this.userId = userId;
        this.locale = locale;
    }
}
