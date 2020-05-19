package com.aqratsa.aeqarat.models.real_estates.request;

public class RealEstatesModelRequest {

    private String sale;
    private String rent;
    private String auction;

    public RealEstatesModelRequest(String sale, String rent, String auction) {
        this.sale = sale;
        this.rent = rent;
        this.auction = auction;
    }
}
