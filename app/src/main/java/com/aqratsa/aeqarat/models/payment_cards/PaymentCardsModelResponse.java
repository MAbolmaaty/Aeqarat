package com.aqratsa.aeqarat.models.payment_cards;

import com.google.gson.annotations.SerializedName;

public class PaymentCardsModelResponse {

    @SerializedName("data")
    private PaymentCard[] mPaymentCards;

    @SerializedName("key")
    private String mKey;

    public PaymentCard[] getPaymentCards ()
    {
        return mPaymentCards;
    }

    public String getKey ()
    {
        return mKey;
    }
}
