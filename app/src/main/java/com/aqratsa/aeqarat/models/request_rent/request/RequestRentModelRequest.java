package com.aqratsa.aeqarat.models.request_rent.request;

import com.google.gson.annotations.SerializedName;

public class RequestRentModelRequest {

    @SerializedName("akar_id")
    private String realEstateId;
    @SerializedName("user_id")
    private String userId;
    @SerializedName("access_date")
    private String startDate;
    @SerializedName("duration")
    private String duration;
    @SerializedName("pay_way")
    private String payment;
    @SerializedName("lang")
    private String locale;

    public RequestRentModelRequest(String realEstateId, String userId, String startDate, String duration, String payment, String locale) {
        this.realEstateId = realEstateId;
        this.userId = userId;
        this.startDate = startDate;
        this.duration = duration;
        this.payment = payment;
        this.locale = locale;
    }
}
