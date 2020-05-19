package com.aqratsa.aeqarat.models.auction_join;

import com.google.gson.annotations.SerializedName;

public class AuctionJoinModelResponse {

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
