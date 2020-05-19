package com.aqratsa.aeqarat.models.privacy_policy;

import com.google.gson.annotations.SerializedName;

public class PrivacyPolicyModelResponse {

    @SerializedName("message")
    private PrivacyPolicies privacyPolicies;

    private String key;

    public PrivacyPolicies getPrivacyPolicies ()
    {
        return privacyPolicies;
    }

    public void setPrivacyPolicies (PrivacyPolicies privacyPolicies)
    {
        this.privacyPolicies = privacyPolicies;
    }

    public String getKey ()
    {
        return key;
    }

    public void setKey (String key)
    {
        this.key = key;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [privacyPolicies = "+privacyPolicies+", key = "+key+"]";
    }
}
