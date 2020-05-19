package com.aqratsa.aeqarat.models.real_estate;

import com.google.gson.annotations.SerializedName;

public class RealEstate {

    @SerializedName("max_offe")
    private String maxBet;

    private String kitchen_num;

    private String price_of_one_meter;

    private String garden_space;

    private String water_meter_num;

    private String full_address;

    private String negotiable;

    private String documented;

    private String taqbel;

    private String id;

    private String describtion;

    private Gallery[] gallery;

    private String longitude;

    private String area;

    @SerializedName("hall_num")
    private String halls;

    @SerializedName("offers")
    private Bid[] mBids;

    private String auction_date;

    private String floor_num;

    private String active;

    private String cate_id;

    private String price_for_12month;

    @SerializedName("electrical_public")
    private String mElectricityStatus;

    private String apartment_space;

    private String total_amount;

    private String build_num;

    private String neighborhood;

    @SerializedName("street_space")
    private String streetWidth;

    private String status;

    private String latitude;

    private String year_of_building;

    @SerializedName("document")
    private Document[] mDocuments;

    private String link;

    private String electrical_meter_num;

    private String created_at;

    private String title;

    @SerializedName("shop_num")
    private String shops;

    private String rooms_num;

    private String auction;

    private String updated_at;

    private String map_link;

    private String stare;

    @SerializedName("commission")
    private String mCommission;

    @SerializedName("earnings")
    private String mEarnings;

    @SerializedName("revenues")
    private String mRevenues;

    @SerializedName("expenses")
    private String mExpenses;

    private String is_user;

    private String day_rent;

    private String cate;

    @SerializedName("comments")
    private Comment[] mComments;

    @SerializedName("water_public")
    private String mWaterStatus;

    private String price_for_3month;

    private String waqf;

    private String photo;

    private String price_for_6month;

    private String apartments_num;

    private String rent_date;

    private String special;

    private String bath_num;

    private Ownership ownership;

    private String service;

    private String width;

    private String hight;

    @SerializedName("desk_num")
    private String mOffices;

    private String build_image;

    private String amount_insurance;

    private String price_for_month;

    public String getMaxBet ()
    {
        return maxBet;
    }

    public String getKitchen_num ()
    {
        return kitchen_num;
    }

    public String getPrice_of_one_meter ()
    {
        return price_of_one_meter;
    }

    public String getGarden_space ()
    {
        return garden_space;
    }

    public String getWater_meter_num ()
    {
        return water_meter_num;
    }

    public String getFull_address ()
    {
        return full_address;
    }

    public String getNegotiable ()
    {
        return negotiable;
    }

    public String getDocumented ()
    {
        return documented;
    }

    public String getTaqbel ()
    {
        return taqbel;
    }

    public String getId ()
    {
        return id;
    }

    public String getDescribtion ()
    {
        return describtion;
    }

    public Gallery[] getGallery ()
    {
        return gallery;
    }

    public String getLongitude ()
    {
        return longitude;
    }

    public String getArea ()
    {
        return area;
    }

    public String getHalls ()
    {
        return halls;
    }

    public Bid[] getBids()
    {
        return mBids;
    }

    public String getAuction_date ()
    {
        return auction_date;
    }

    public String getFloor_num ()
    {
        return floor_num;
    }

    public String getActive ()
    {
        return active;
    }

    public String getCate_id ()
    {
        return cate_id;
    }

    public String getPrice_for_12month ()
    {
        return price_for_12month;
    }

    public String getElectricityStatus()
    {
        return mElectricityStatus;
    }

    public String getApartment_space ()
    {
        return apartment_space;
    }

    public String getTotal_amount ()
    {
        return total_amount;
    }

    public String getBuild_num ()
    {
        return build_num;
    }

    public String getNeighborhood ()
    {
        return neighborhood;
    }

    public String getStreetWidth ()
    {
        return streetWidth;
    }

    public String getStatus ()
    {
        return status;
    }

    public String getLatitude ()
    {
        return latitude;
    }

    public String getYear_of_building ()
    {
        return year_of_building;
    }

    public Document[] getDocument ()
    {
        return mDocuments;
    }

    public String getLink ()
    {
        return link;
    }

    public String getElectrical_meter_num ()
    {
        return electrical_meter_num;
    }

    public String getCreated_at ()
    {
        return created_at;
    }

    public String getTitle ()
    {
        return title;
    }

    public String getShops ()
    {
        return shops;
    }

    public String getRooms_num ()
    {
        return rooms_num;
    }

    public String getAuction ()
    {
        return auction;
    }

    public String getUpdated_at ()
    {
        return updated_at;
    }

    public String getMap_link ()
    {
        return map_link;
    }

    public String getStare ()
    {
        return stare;
    }

    public String getCommission ()
    {
        return mCommission;
    }

    public String getIs_user ()
    {
        return is_user;
    }

    public String getDay_rent ()
    {
        return day_rent;
    }

    public String getCate ()
    {
        return cate;
    }

    public Comment[] getComments ()
    {
        return mComments;
    }

    public String getWaterStatus ()
    {
        return mWaterStatus;
    }

    public String getPrice_for_3month ()
    {
        return price_for_3month;
    }

    public String getWaqf ()
    {
        return waqf;
    }

    public String getPhoto ()
    {
        return photo;
    }

    public String getPrice_for_6month ()
    {
        return price_for_6month;
    }

    public String getApartments_num ()
    {
        return apartments_num;
    }

    public String getRent_date ()
    {
        return rent_date;
    }

    public String getSpecial ()
    {
        return special;
    }

    public String getBath_num ()
    {
        return bath_num;
    }

    public Ownership getOwnership ()
    {
        return ownership;
    }

    public String getService ()
    {
        return service;
    }

    public String getWidth ()
    {
        return width;
    }

    public String getHight ()
    {
        return hight;
    }

    public String getOffices ()
    {
        return mOffices;
    }

    public String getBuild_image ()
    {
        return build_image;
    }

    public String getAmount_insurance ()
    {
        return amount_insurance;
    }

    public String getPrice_for_month ()
    {
        return price_for_month;
    }

    public String getEarnings() {
        return mEarnings;
    }

    public String getRevenues() {
        return mRevenues;
    }

    public String getExpenses() {
        return mExpenses;
    }
}
