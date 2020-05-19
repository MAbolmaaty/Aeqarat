package com.aqratsa.aeqarat.models.payment_card_default;

import com.google.gson.annotations.SerializedName;

public class PaymentCardDefaultModelResponse {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("key")
    private String mKey;

    public String getMessage() {
        return mMessage;
    }

    public String getKey() {
        return mKey;
    }
}
