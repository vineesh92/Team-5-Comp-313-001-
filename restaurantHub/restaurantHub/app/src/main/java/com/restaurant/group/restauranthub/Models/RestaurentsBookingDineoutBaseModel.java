package com.restaurant.manasa.restauranthub.webservices;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class RestaurentsBookingDineoutBaseModel {
    @Expose
    private String status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("result")
    @Expose
    private RestaurentsBookingDineoutResultModel result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public RestaurentsBookingDineoutResultModel getResult() {
        return result;
    }

    public void setResult(RestaurentsBookingDineoutResultModel result) {
        this.result = result;
    }
}
