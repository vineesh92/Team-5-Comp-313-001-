package com.restaurant.manasa.restauranthub.webservices;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MyBookingsItem implements Serializable{

    @SerializedName("booking_id")
    private String bookingId;

    @SerializedName("date")
    private String date;

    @SerializedName("image")
    private String image;

    @SerializedName("time")
    private String time;

    @SerializedName("people")
    private String people;

    @SerializedName("restaurant_name")
    private String restaurantName;

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getPeople() {
        return people;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    @Override
    public String toString() {
        return
                "ResultItem{" +
                        "booking_id = '" + bookingId + '\'' +
                        ",date = '" + date + '\'' +
                        ",image = '" + image + '\'' +
                        ",time = '" + time + '\'' +
                        ",people = '" + people + '\'' +
                        ",restaurant_name = '" + restaurantName + '\'' +
                        "}";
    }
}