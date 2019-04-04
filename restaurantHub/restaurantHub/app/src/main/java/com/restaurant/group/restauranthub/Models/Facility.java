package com.restaurant.manasa.restauranthub.webservices;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Facility implements Parcelable {
    @SerializedName("facility")
    @Expose
    private String facility;

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.facility);
    }

    public Facility() {
    }

    Facility(Parcel in) {
        this.facility = in.readString();
    }

    public static final Parcelable.Creator<Facility> CREATOR = new Parcelable.Creator<Facility>() {
        @Override
        public Facility createFromParcel(Parcel source) {
            return new Facility(source);
        }

        @Override
        public Facility[] newArray(int size) {
            return new Facility[size];
        }
    };
}
