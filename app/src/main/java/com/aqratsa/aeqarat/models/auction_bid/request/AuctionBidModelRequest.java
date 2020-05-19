package com.aqratsa.aeqarat.models.auction_bid.request;

import com.google.gson.annotations.SerializedName;

public class AuctionBidModelRequest {

    @SerializedName("akar_id")
    private String realEstateId;
    @SerializedName("user_id")
    private String userId;
    @SerializedName("price")
    private String amount;
    @SerializedName("lang")
    private String locale;

    public AuctionBidModelRequest(String realEstateId, String userId, String amount, String locale) {
        this.realEstateId = realEstateId;
        this.userId = userId;
        this.amount = amount;
        this.locale = locale;
    }
}
