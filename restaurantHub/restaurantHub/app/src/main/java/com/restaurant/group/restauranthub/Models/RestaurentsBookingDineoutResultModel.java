package com.restaurant.manasa.restauranthub.webservices;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class RestaurentsBookingDineoutResultModel {
    @SerializedName("booking_id")
    @Expose
    private Integer bookingId;
    @SerializedName("booking_key")
    @Expose
    private String bookingKey;
    @SerializedName("diner_name")
    @Expose
    private String dinerName;
    @SerializedName("dining_date_time")
    @Expose
    private String diningDateTime;
    @SerializedName("male")
    @Expose
    private String male;
    @SerializedName("female")
    @Expose
    private Integer female;
    @SerializedName("restaurant_name")
    @Expose
    private String restaurantName;
    @SerializedName("restaurant_address")
    @Expose
    private String restaurantAddress;
    @SerializedName("offer_text")
    @Expose
    private String offerText;

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookingKey() {
        return bookingKey;
    }

    public void setBookingKey(String bookingKey) {
        this.bookingKey = bookingKey;
    }

    public String getDinerName() {
        return dinerName;
    }

    public void setDinerName(String dinerName) {
        this.dinerName = dinerName;
    }

    public String getDiningDateTime() {
        return diningDateTime;
    }

    public void setDiningDateTime(String diningDateTime) {
        this.diningDateTime = diningDateTime;
    }

    public String getMale() {
        return male;
    }

    public void setMale(String male) {
        this.male = male;
    }

    public Integer getFemale() {
        return female;
    }

    public void setFemale(Integer female) {
        this.female = female;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public void setRestaurantAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }

    public String getOfferText() {
        return offerText;
    }

    public void setOfferText(String offerText) {
        this.offerText = offerText;
    }

}
