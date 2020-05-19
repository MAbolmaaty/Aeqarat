package com.aqratsa.aeqarat.models.real_estate_requests_user;

import com.google.gson.annotations.SerializedName;

public class Request {

    private String akar_id;

    private String cate;

    private String contract_sale;

    private String amount;

    private String recovery_way;

    private String access_date;

    @SerializedName("departure_date")
    private String mDateExit;

    private String maintenance_type;

    private String photo;

    private String created_at;

    private String attendees_date;

    private String type;

    private String pay_way;

    private String duration;

    private String contract_rent;

    private String updated_at;

    private String user_id;

    private String request_date;

    private String report;

    private String id;

    private String amount_insurance;

    private User user;

    private String describtion;

    private String status;

    public String getAkar_id ()
    {
        return akar_id;
    }

    public String getCate ()
    {
        return cate;
    }

    public String getContract_sale ()
    {
        return contract_sale;
    }

    public String getAmount ()
    {
        return amount;
    }

    public String getRecovery_way ()
    {
        return recovery_way;
    }

    public String getAccess_date ()
    {
        return access_date;
    }

    public String getDateExit ()
    {
        return mDateExit;
    }

    public String getMaintenance_type ()
    {
        return maintenance_type;
    }

    public String getPhoto ()
    {
        return photo;
    }

    public String getCreated_at ()
    {
        return created_at;
    }

    public String getAttendees_date ()
    {
        return attendees_date;
    }

    public String getType ()
    {
        return type;
    }

    public String getPay_way ()
    {
        return pay_way;
    }

    public String getDuration ()
    {
        return duration;
    }

    public String getContract_rent ()
    {
        return contract_rent;
    }

    public String getUpdated_at ()
    {
        return updated_at;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public String getRequest_date ()
    {
        return request_date;
    }

    public String getReport ()
    {
        return report;
    }

    public String getId ()
    {
        return id;
    }

    public String getAmount_insurance ()
    {
        return amount_insurance;
    }

    public User getUser ()
    {
        return user;
    }

    public String getDescribtion ()
    {
        return describtion;
    }

    public String getStatus ()
    {
        return status;
    }
}
