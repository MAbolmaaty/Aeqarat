package com.aqratsa.aeqarat.models.payment_card_add.response;

import com.google.gson.annotations.SerializedName;

public class PaymentCardAddModelResponse {

    @SerializedName("data")
    private PaymentCard mPaymentCard;

    @SerializedName("message")
    private String mMessage;

    @SerializedName("key")
    private String mKey;

    public PaymentCard getPaymentCard ()
    {
        return mPaymentCard;
    }

    public String getMessage ()
    {
        return mMessage;
    }

    public String getKey ()
    {
        return mKey;
    }
}
