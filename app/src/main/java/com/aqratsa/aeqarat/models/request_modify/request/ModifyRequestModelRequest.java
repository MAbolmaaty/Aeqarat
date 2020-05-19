package com.aqratsa.aeqarat.models.request_modify.request;

import com.google.gson.annotations.SerializedName;

public class ModifyRequestModelRequest {

    @SerializedName("access_date")
    private String startDate;
    @SerializedName("duration")
    private String duration;
    @SerializedName("pay_way")
    private String payment;
    @SerializedName("order_id")
    private String requestId;
    @SerializedName("lang")
    private String locale;

    public ModifyRequestModelRequest(String startDate, String duration, String payment, String requestId, String locale) {
        this.startDate = startDate;
        this.duration = duration;
        this.payment = payment;
        this.requestId = requestId;
        this.locale = locale;
    }
}
