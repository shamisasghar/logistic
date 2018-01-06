package com.logistics.hypernym.logistic.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Metis on 29-Dec-17.
 */

public class User{

    @SerializedName("response")
    @Expose
    private Response response;
    @SerializedName("status")
    @Expose
    private Boolean status;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}