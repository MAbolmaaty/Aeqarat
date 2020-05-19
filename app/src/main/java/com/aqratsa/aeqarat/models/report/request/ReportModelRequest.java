package com.aqratsa.aeqarat.models.report.request;

import com.google.gson.annotations.SerializedName;

public class ReportModelRequest {

    private String message;
    @SerializedName("user_id")
    private String userId;
    @SerializedName("akar_id")
    private String realEstateId;
    @SerializedName("lang")
    private String locale;
    @SerializedName("type")
    private String status;

    public ReportModelRequest(String message, String userId, String realEstateId, String locale, String status) {
        this.message = message;
        this.userId = userId;
        this.realEstateId = realEstateId;
        this.locale = locale;
        this.status = status;
    }
}
