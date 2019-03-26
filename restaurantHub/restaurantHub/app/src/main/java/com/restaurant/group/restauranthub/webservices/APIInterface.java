package com.restaurant.group.restauranthub.webservices;

import com.restaurant.group.restauranthub.AppController;
import com.restaurant.group.restauranthub.models.ForgotPassWordOtpModel;
import com.restaurant.group.restauranthub.models.LoginModel;
import com.restaurant.group.restauranthub.models.MyAccountModel;
import com.restaurant.group.restauranthub.models.MyBookingsResponseModel;
import com.restaurant.group.restauranthub.models.OtpModel;
import com.restaurant.group.restauranthub.models.ResetPasswordModel;
import com.restaurant.group.restauranthub.models.RestaurantDetailsModel;
import com.restaurant.group.restauranthub.models.RestaurantModel;
import com.restaurant.group.restauranthub.models.RestaurentTableDineoutBaseModel;
import com.restaurant.group.restauranthub.models.SuccessModel;

import javax.xml.transform.Result;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

import static com.restaurant.group.restauranthub.AppController.END_POINT_FORGOT_PASSWORD;
import static com.restaurant.group.restauranthub.AppController.END_POINT_MY_ACCOUNT;
import static com.restaurant.group.restauranthub.AppController.END_POINT_MY_BOOKINGS;
import static com.restaurant.group.restauranthub.AppController.END_POINT_REGISTER;
import static com.restaurant.group.restauranthub.AppController.END_POINT_RESET_PASSWORD;
import static com.restaurant.group.restauranthub.AppController.END_POINT_RESTAURANTS_DETAILS;
import static com.restaurant.group.restauranthub.AppController.END_POINT_RESTAURANTS_GET_AVAILABLE_TIME;
import static com.restaurant.group.restauranthub.AppController.END_POINT_UPDATE_MY_PROFILE;



@SuppressWarnings("unused")
public interface APIInterface {

    @GET(AppController.END_POINT_RESTAURANTS_LIST)
    Call<RestaurantModel> getRestaurant(@Query("token") String token);

    @GET(END_POINT_RESTAURANTS_DETAILS)
    Call<RestaurantDetailsModel> getRestaurantDetails(@Query("token") String token);

    @GET(AppController.END_POINT_LOGIN)
    Call<LoginModel> login(@Query("email") String mEmail,
                           @Query("password") String mPassword);

    @GET(END_POINT_REGISTER)
    Call<LoginModel> register(@Query("email") String mEmail,
                              @Query("password") String mPassword,
                              @Query("name") String mName,
                              @Query("phone_number") String mPhonenumber);

    @GET(END_POINT_FORGOT_PASSWORD)
    Call<ForgotPassWordOtpModel> getForgotPassWord(@Query("email") String mEmail);

    @GET(END_POINT_RESET_PASSWORD)
    Call<ResetPasswordModel> resetPassword(@Query("token") String mToken,
                                           @Query("password") String mPassword);

    @GET("5b863d8e34000029048b546e")
    Call<OtpModel> sentOtp();

    @GET(END_POINT_MY_ACCOUNT)
    Call<MyAccountModel> myAccount(@Query("token") String token);

    @GET(END_POINT_UPDATE_MY_PROFILE)
    Call<SuccessModel> updateMyProfile(@Query("token") String token,
                                       @Query("email") String mEmail,
                                       @Query("name") String mName,
                                       @Query("phone_number") String mPhonenumber);

    @GET(END_POINT_RESTAURANTS_GET_AVAILABLE_TIME)
    Call<RestaurentTableDineoutBaseModel> getTableTime(@Query("restaurant_id") String restaurantId,
                                                       @Query("service") String serviceName,
                                                       @Query("date") String date,
                                                       @Query("token") String token);

    @Multipart
    @POST("upload.php")
    Call<Result> uploadImage(@Part MultipartBody.Part file);

    @GET(END_POINT_MY_BOOKINGS)
    Call<MyBookingsResponseModel> getMyBookings();
}