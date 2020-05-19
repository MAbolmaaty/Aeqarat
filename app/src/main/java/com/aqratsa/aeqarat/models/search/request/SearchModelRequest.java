package com.aqratsa.aeqarat.models.search.request;

import com.google.gson.annotations.SerializedName;

public class SearchModelRequest {

    @SerializedName("cate")
    private String mCategory;
    @SerializedName("service")
    private String mStatus;
    @SerializedName("price_from")
    private String mMinPrice;
    @SerializedName("price_to")
    private String mMaxPrice;
    @SerializedName("full_address")
    private String mAddress;
    @SerializedName("area")
    private String mRegion;
    @SerializedName("neighborhood")
    private String mDistrict;
    @SerializedName("year_of_building")
    private String mRealEstateAge;
    @SerializedName("space_from")
    private String mMinArea;
    @SerializedName("space_to")
    private String mMaxArea;
    private int mRegionItemPosition;
    private int mDistrictItemPosition;

    public SearchModelRequest(String category, String status, String minPrice, String maxPrice,
                              String address, String region, String district, String mRealEstateAge,
                              String minArea, String maxArea, int regionItemPosition,
                              int districtItemPosition) {
        this.mCategory = category;
        this.mStatus = status;
        this.mMinPrice = minPrice;
        this.mMaxPrice = maxPrice;
        this.mAddress = address;
        this.mRegion = region;
        this.mDistrict = district;
        this.mRealEstateAge = mRealEstateAge;
        this.mMinArea = minArea;
        this.mMaxArea = maxArea;
        mRegionItemPosition = regionItemPosition;
        mDistrictItemPosition = districtItemPosition;
    }

    public String getCategory() {
        return mCategory;
    }

    public String getStatus() {
        return mStatus;
    }

    public String getMinPrice() {
        return mMinPrice;
    }

    public String getMaxPrice() {
        return mMaxPrice;
    }

    public String getAddress() {
        return mAddress;
    }

    public String getRegion() {
        return mRegion;
    }

    public String getDistrict() {
        return mDistrict;
    }

    public String getRealEstateAge() {
        return mRealEstateAge;
    }

    public String getMinArea() {
        return mMinArea;
    }

    public String getMaxArea() {
        return mMaxArea;
    }

    public int getRegionItemPosition() {
        return mRegionItemPosition;
    }

    public int getDistrictItemPosition() {
        return mDistrictItemPosition;
    }
}
