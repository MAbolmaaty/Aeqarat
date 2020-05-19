package com.aqratsa.aeqarat.models.payment_card_add.request;

import com.google.gson.annotations.SerializedName;

public class PaymentCardAddModelRequest {

    @SerializedName("card_num")
    private String mCardNumber;
    @SerializedName("expire_date")
    private String mExpireDate;
    @SerializedName("user_id")
    private String mUserId;
    @SerializedName("type")
    private String mType;
    @SerializedName("lang")
    private String mLocale;
    @SerializedName("csc_num")
    private String mCVVV;
    @SerializedName("status")
    private String mStatus;

    public PaymentCardAddModelRequest(String cardNumber, String expireDate, String userId,
                                      String type, String locale, String cvv, String status) {
        mCardNumber = cardNumber;
        mExpireDate = expireDate;
        mUserId = userId;
        mType = type;
        mLocale = locale;
        mCVVV = cvv;
        mStatus = status;
    }
}
