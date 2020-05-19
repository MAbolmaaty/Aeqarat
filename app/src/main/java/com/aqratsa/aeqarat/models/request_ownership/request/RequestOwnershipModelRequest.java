package com.aqratsa.aeqarat.models.request_ownership.request;

import com.google.gson.annotations.SerializedName;

public class RequestOwnershipModelRequest {

    @SerializedName("akar_id")
    private String realEstateId;
    @SerializedName("user_id")
    private String userId;
    @SerializedName("access_date")
    private String startDate;
    @SerializedName("pay_way")
    private String payment;
    @SerializedName("lang")
    private String locale;

    public RequestOwnershipModelRequest(String realEstateId, String userId, String startDate, String payment, String locale) {
        this.realEstateId = realEstateId;
        this.userId = userId;
        this.startDate = startDate;
        this.payment = payment;
        this.locale = locale;
    }
}
