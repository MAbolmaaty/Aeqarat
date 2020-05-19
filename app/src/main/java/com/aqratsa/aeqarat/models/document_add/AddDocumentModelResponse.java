package com.aqratsa.aeqarat.models.document_add;

import com.google.gson.annotations.SerializedName;

public class AddDocumentModelResponse {

    @SerializedName("data")
    private Document mDocument;

    private String message;

    private String key;

    public Document getDocument()
    {
        return mDocument;
    }

    public void setDocument(Document document)
    {
        this.mDocument = document;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
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
        return "ClassPojo [mDocument = "+ mDocument +", message = "+message+", key = "+key+"]";
    }
}
