package com.restaurant.manasa.restauranthub.webservices;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ForgotPassWordOtpModel {


    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private ForgotPassWordOtpModelData data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ForgotPassWordOtpModelData getData() {
        return data;
    }

    public void setData(ForgotPassWordOtpModelData data) {
        this.data = data;
    }

}
