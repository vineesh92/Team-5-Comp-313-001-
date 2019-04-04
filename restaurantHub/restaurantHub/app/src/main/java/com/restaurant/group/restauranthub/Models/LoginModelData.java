package com.restaurant.manasa.restauranthub.webservices;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginModelData {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("is_otp_verified")
    @Expose
    private String isOtpVerified;
    @SerializedName("auth_token")
    @Expose
    private String authToken;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIsOtpVerified() {
        return isOtpVerified;
    }

    public void setIsOtpVerified(String isOtpVerified) {
        this.isOtpVerified = isOtpVerified;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
