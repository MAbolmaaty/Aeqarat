package com.aqratsa.aeqarat.models.payment_card_delete;

import com.google.gson.annotations.SerializedName;

public class PaymentCardDeleteModelResponse {

    @SerializedName("mesaage")
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
