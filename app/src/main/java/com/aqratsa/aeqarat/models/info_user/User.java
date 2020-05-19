package com.aqratsa.aeqarat.models.info_user;

import com.google.gson.annotations.SerializedName;

public class User {

    private String birthday;

    private String reason;

    private String area_work;

    private String code;

    private String email_num;

    private String document;

    private String created_at;

    private String type;

    @SerializedName("rent")
    private RentedRealEstates[] rentedRealEstates;

    private String updated_at;

    private String rate;

    private String commission;

    private String id;

    private String email;

    private String phone_code;

    private String area;

    //private String[] owner;

    private String address;

    private String star;

    @SerializedName("documentations")
    private Documents[] mDocuments;

    private String photo;

    private String email_verified_at;

    private String block_by;

    private String revenues;

    private String earnings;

    private String phone;

    private String name;

    private String neighborhood;

    private String status;

    private String expenses;

    public String getBirthday ()
    {
        return birthday;
    }

    public void setBirthday (String birthday)
    {
        this.birthday = birthday;
    }

    public String getReason ()
    {
        return reason;
    }

    public void setReason (String reason)
    {
        this.reason = reason;
    }

    public String getArea_work ()
    {
        return area_work;
    }

    public void setArea_work (String area_work)
    {
        this.area_work = area_work;
    }

    public String getCode ()
    {
        return code;
    }

    public void setCode (String code)
    {
        this.code = code;
    }

    public String getEmail_num ()
    {
        return email_num;
    }

    public void setEmail_num (String email_num)
    {
        this.email_num = email_num;
    }

    public String getDocument ()
    {
        return document;
    }

    public void setDocument (String document)
    {
        this.document = document;
    }

    public String getCreated_at ()
    {
        return created_at;
    }

    public void setCreated_at (String created_at)
    {
        this.created_at = created_at;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public RentedRealEstates[] getRentedRealEstates ()
    {
        return rentedRealEstates;
    }

    public void setRentedRealEstates (RentedRealEstates[] rentedRealEstates)
    {
        this.rentedRealEstates = rentedRealEstates;
    }

    public String getUpdated_at ()
    {
        return updated_at;
    }

    public void setUpdated_at (String updated_at)
    {
        this.updated_at = updated_at;
    }

    public String getRate ()
    {
        return rate;
    }

    public void setRate (String rate)
    {
        this.rate = rate;
    }

    public String getCommission ()
    {
        return commission;
    }

    public void setCommission (String commission)
    {
        this.commission = commission;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getPhone_code ()
    {
        return phone_code;
    }

    public void setPhone_code (String phone_code)
    {
        this.phone_code = phone_code;
    }

    public String getArea ()
    {
        return area;
    }

    public void setArea (String area)
    {
        this.area = area;
    }

    /*public String[] getOwner ()
    {
        return owner;
    }

    public void setOwner (String[] owner)
    {
        this.owner = owner;
    }
*/
    public String getAddress ()
    {
        return address;
    }

    public void setAddress (String address)
    {
        this.address = address;
    }

    public String getStar ()
    {
        return star;
    }

    public void setStar (String star)
    {
        this.star = star;
    }

    public Documents[] getDocuments()
    {
        return mDocuments;
    }

    public void setDocuments(Documents[] documents)
    {
        this.mDocuments = documents;
    }

    public String getPhoto ()
    {
        return photo;
    }

    public void setPhoto (String photo)
    {
        this.photo = photo;
    }

    public String getEmail_verified_at ()
    {
        return email_verified_at;
    }

    public void setEmail_verified_at (String email_verified_at)
    {
        this.email_verified_at = email_verified_at;
    }

    public String getBlock_by ()
    {
        return block_by;
    }

    public void setBlock_by (String block_by)
    {
        this.block_by = block_by;
    }

    public String getRevenues ()
    {
        return revenues;
    }

    public void setRevenues (String revenues)
    {
        this.revenues = revenues;
    }

    public String getEarnings ()
    {
        return earnings;
    }

    public void setEarnings (String earnings)
    {
        this.earnings = earnings;
    }

    public String getPhone ()
    {
        return phone;
    }

    public void setPhone (String phone)
    {
        this.phone = phone;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getNeighborhood ()
    {
        return neighborhood;
    }

    public void setNeighborhood (String neighborhood)
    {
        this.neighborhood = neighborhood;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getExpenses ()
    {
        return expenses;
    }

    public void setExpenses (String expenses)
    {
        this.expenses = expenses;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [birthday = "+birthday+", reason = "+reason+", area_work = "+area_work+", code = "+code+", email_num = "+email_num+", document = "+document+", created_at = "+created_at+", type = "+type+", rentedRealEstates = "+rentedRealEstates+", updated_at = "+updated_at+", rate = "+rate+", commission = "+commission+", id = "+id+", email = "+email+", phone_code = "+phone_code+", area = "+area+", address = "+address+", star = "+star+", mDocuments = "+ mDocuments +", photo = "+photo+", email_verified_at = "+email_verified_at+", block_by = "+block_by+", revenues = "+revenues+", earnings = "+earnings+", phone = "+phone+", name = "+name+", neighborhood = "+neighborhood+", status = "+status+", expenses = "+expenses+"]";
    }
}
