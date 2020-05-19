package com.aqratsa.aeqarat.models.payment_card_add.response;

public class PaymentCard {

    private String country;

    private String created_at;

    private String type;

    private String card_num;

    private String expire_date;

    private String full_name;

    private String updated_at;

    private String user_id;

    private String bank_num;

    private String bank_name;

    private String id;

    private String csc_num;

    private String status;

    public String getCountry ()
    {
        return country;
    }

    public String getCreated_at ()
    {
        return created_at;
    }

    public String getType ()
    {
        return type;
    }

    public String getCard_num ()
    {
        return card_num;
    }

    public String getExpire_date ()
    {
        return expire_date;
    }

    public String getFull_name ()
    {
        return full_name;
    }

    public String getUpdated_at ()
    {
        return updated_at;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public String getBank_num ()
    {
        return bank_num;
    }

    public String getBank_name ()
    {
        return bank_name;
    }

    public String getId ()
    {
        return id;
    }

    public String getCsc_num ()
    {
        return csc_num;
    }

    public String getStatus ()
    {
        return status;
    }
}
