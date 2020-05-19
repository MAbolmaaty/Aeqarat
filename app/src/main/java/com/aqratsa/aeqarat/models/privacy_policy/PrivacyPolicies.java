package com.aqratsa.aeqarat.models.privacy_policy;

import com.google.gson.annotations.SerializedName;

public class PrivacyPolicies {

    @SerializedName("titles0")
    private PropertyRights[] propertyRights;

    @SerializedName("titles1")
    private TermsOfUse[] termsOfUses;

    public PropertyRights[] getPropertyRights ()
    {
        return propertyRights;
    }

    public void setPropertyRights (PropertyRights[] propertyRights)
    {
        this.propertyRights = propertyRights;
    }

    public TermsOfUse[] getTermsOfUses ()
    {
        return termsOfUses;
    }

    public void setTermsOfUses (TermsOfUse[] termsOfUses)
    {
        this.termsOfUses = termsOfUses;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [propertyRights = "+propertyRights+", termsOfUse = "+termsOfUses+"]";
    }
}
