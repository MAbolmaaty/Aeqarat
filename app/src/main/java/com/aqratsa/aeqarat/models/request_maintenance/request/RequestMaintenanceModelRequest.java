package com.aqratsa.aeqarat.models.request_maintenance.request;

import com.google.gson.annotations.SerializedName;

public class RequestMaintenanceModelRequest {

    @SerializedName("akar_id")
    private String mRealEstateId;
    @SerializedName("user_id")
    private String mUserId;
    @SerializedName("maintenance_type")
    private String mType;
    @SerializedName("describtion")
    private String mDescription;
    @SerializedName("lang")
    private String mLocale;

    public RequestMaintenanceModelRequest(String realEstateId, String userId, String type, String description, String locale) {
        mRealEstateId = realEstateId;
        mUserId = userId;
        mType = type;
        mDescription = description;
        mLocale = locale;
    }
}
