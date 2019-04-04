package com.restaurant.manasa.restauranthub.webservices;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MenuImage implements Parcelable {
    @SerializedName("menu_image")
    @Expose
    private String menuImage;

    private MenuImage(Parcel in) {
        menuImage = in.readString();
    }

    public static final Creator<MenuImage> CREATOR = new Creator<MenuImage>() {
        @Override
        public MenuImage createFromParcel(Parcel in) {
            return new MenuImage(in);
        }

        @Override
        public MenuImage[] newArray(int size) {
            return new MenuImage[size];
        }
    };

    public String getMenuImage() {
        return menuImage;
    }

    public void setMenuImage(String menuImage) {
        this.menuImage = menuImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(menuImage);
    }
}
