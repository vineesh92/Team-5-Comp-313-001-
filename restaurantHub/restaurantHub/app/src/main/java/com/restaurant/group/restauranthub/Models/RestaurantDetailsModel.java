package com.restaurant.manasa.restauranthub.webservices;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class RestaurantDetailsModel {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("result")
    @Expose
    private RestaurantDetailsModelResult result;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public RestaurantDetailsModelResult getResult() {
        return result;
    }

    public void setResult(RestaurantDetailsModelResult result) {
        this.result = result;
    }
}
