package com.restaurant.manasa.restauranthub.webservices;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ForgotPassWordOtpModelData {

    @SerializedName("is_otp_send")
    @Expose
    private String isOtpSend;

    public String getIsOtpSend() {
        return isOtpSend;
    }

    public void setIsOtpSend(String isOtpSend) {
        this.isOtpSend = isOtpSend;
    }
}
