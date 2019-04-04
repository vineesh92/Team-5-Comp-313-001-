package com.restaurant.manasa.restauranthub.webservices;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class LoginModel {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private LoginModelData data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LoginModelData getData() {
        return data;
    }

    public void setData(LoginModelData data) {
        this.data = data;
    }
}
