package com.restaurant.manasa.restauranthub.webservices;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class RestaurentTableDineoutBaseModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("result")
    @Expose
    private List<RestaurentTableDineoutResultModel> result = null;

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

    public List<RestaurentTableDineoutResultModel> getResult() {
        return result;
    }

    public void setResult(List<RestaurentTableDineoutResultModel> result) {
        this.result = result;
    }

}
