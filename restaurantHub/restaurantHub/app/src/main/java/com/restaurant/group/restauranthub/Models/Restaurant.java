package com.restaurant.manasa.restauranthub.webservices;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;



public class Restaurant {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("latitude")
    @Expose
    private Integer latitude;
    @SerializedName("longitude")
    @Expose
    private Integer longitude;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("photos_url")
    @Expose
    private String photosUrl;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("rating_votes")
    @Expose
    private String ratingVotes;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("average_cost_for_two")
    @Expose
    private String averageCostForTwo;
    @SerializedName("locality")
    @Expose
    private String locality;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("cuisines")
    @Expose
    private String cuisines;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("start_from")
    @Expose
    private String startFrom;
    @SerializedName("featured_image")
    @Expose
    private List<FeaturedImage> featuredImage = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLatitude() {
        return latitude;
    }

    public void setLatitude(Integer latitude) {
        this.latitude = latitude;
    }

    public Integer getLongitude() {
        return longitude;
    }

    public void setLongitude(Integer longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotosUrl() {
        return photosUrl;
    }

    public void setPhotosUrl(String photosUrl) {
        this.photosUrl = photosUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRatingVotes() {
        return ratingVotes;
    }

    public void setRatingVotes(String ratingVotes) {
        this.ratingVotes = ratingVotes;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAverageCostForTwo() {
        return averageCostForTwo;
    }

    public void setAverageCostForTwo(String averageCostForTwo) {
        this.averageCostForTwo = averageCostForTwo;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCuisines() {
        return cuisines;
    }

    public void setCuisines(String cuisines) {
        this.cuisines = cuisines;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getStartFrom() {
        return startFrom;
    }

    public void setStartFrom(String startFrom) {
        this.startFrom = startFrom;
    }

    public List<FeaturedImage> getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(List<FeaturedImage> featuredImage) {
        this.featuredImage = featuredImage;
    }
}
