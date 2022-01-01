package com.webzon.utils;

import com.webzon.Model.GetCategoryModel;
import com.webzon.Model.GetSliderModel;
import com.webzon.Model.LoginModel;
import com.webzon.Model.OtpModel;
import com.webzon.Model.categoryListModel;
import com.webzon.Model.categoryShopListModel;
import com.webzon.Model.createCategoryModel;
import com.webzon.Model.createProductModel;
import com.webzon.Model.homePageDataModel;
import com.webzon.Model.resendOTPModel;
import com.webzon.Model.unitListModel;
import com.webzon.Model.updateBussinessModel;
import com.webzon.Model.verifyUserModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;


public interface ApiInterface {
    @GET
    Call<GetSliderModel> getSlider(@Url String getSlider);

    @FormUrlEncoded
    @POST
  //  Call<OtpModel> getotp(@Url String singleblog, @PartMap HashMap<String, RequestBody> map);
    Call<OtpModel> getotp(@Url String getotp, @Field("country_code") String actionId, @Field("phone") String offerCode);

    @FormUrlEncoded
    @POST
    Call<OtpModel> getwhatsapp_otp(@Url String getotp, @Field("country_code") String actionId, @Field("phone") String phone, @Field("users_id") String id);

    @GET
    Call<GetCategoryModel> getCategory(@Url String getSlider);

    @FormUrlEncoded
    @POST
    Call<LoginModel> getlogin(@Url String getlogin, @Field("phone_number") String actionId, @Field("role") String phone, @Field("device_id") String otp, @Field("device_token") String token, @Field("device_type") String type);

  /*  @FormUrlEncoded
    @POST
    Call<LoginModel> getlogin1(@Url String getotp, @Field("country_code") String actionId, @Field("phone") String phone, @Field("otp") String otp, @Field("device_token") String token);
*/
    @FormUrlEncoded
    @POST
    Call<verifyUserModel> getverifyUser(@Url String getotp, @Field("user_id") String actionId, @Field("code") String phone, @Field("device_id") String id, @Field("device_token") String token, @Field("device_type") String type);

    @FormUrlEncoded
    @POST
    Call<resendOTPModel> getresendOTP(@Url String getotp, @Field("user_id") String actionId, @Field("device_id") String id, @Field("device_token") String token, @Field("device_type") String type);

    @FormUrlEncoded
    @POST
    Call<categoryListModel> getcategoryList(@Url String getotp, @Field("user_id") String actionId, @Field("type") String type, @Field("device_id") String id, @Field("device_token") String token, @Field("device_type") String Dtype);

    @FormUrlEncoded
    @POST
    Call<updateBussinessModel> getupdateBussiness(@Url String getotp, @Field("user_id") String actionId, @Field("name") String name, @Field("category_id") String c_id, @Field("device_id") String d_id, @Field("device_token") String token, @Field("device_type") String Dtype, @Field("address") String address, @Field("lat") String lat, @Field("lng") String lng, @Field("photo") String photo);

    @FormUrlEncoded
    @POST
    Call<homePageDataModel> gethomePageData(@Url String getotp, @Field("user_id") String actionId, @Field("device_id") String d_id, @Field("device_token") String token, @Field("device_type") String Dtype, @Field("filter") String filter, @Field("start_date") String start_date, @Field("end_date") String end_date);

    @FormUrlEncoded
    @POST
    Call<createProductModel> getcreateProduct(@Url String getotp, @Field("user_id") String user_id, @Field("title") String title, @Field("unit_name") String unit_name, @Field("unit_id") String unit_id, @Field("cat_id") String cat_id, @Field("color") String color, @Field("variant") String variant, @Field("type") String type, @Field("price") String price, @Field("discount") String discount, @Field("description") String description, @Field("device_id") String device_id, @Field("device_token") String device_token, @Field("device_type") String device_type, @Field("shop_id") String shop_id, @Field("photo") String photo);

    @FormUrlEncoded
    @POST
    Call<createCategoryModel> getcreateCategory(@Url String getotp, @Field("user_id") String user_id, @Field("title") String title,@Field("type") String type , @Field("device_id") String device_id, @Field("device_token") String device_token, @Field("device_type") String device_type, @Field("photo") String photo);

    @FormUrlEncoded
    @POST
    Call<categoryShopListModel> getcategoryList1(@Url String getotp, @Field("user_id") String actionId, @Field("type") String type, @Field("device_id") String id, @Field("device_token") String token, @Field("device_type") String Dtype);

    @FormUrlEncoded
    @POST
    Call<productListModel> getproductList(@Url String getotp, @Field("user_id") String actionId, @Field("device_id") String id, @Field("type") String type, @Field("device_token") String token, @Field("device_type") String Dtype, @Field("filter") String filter, @Field("start_date") String start_date, @Field("end_date") String end_date);

    @FormUrlEncoded
    @POST
    Call<unitListModel> getunitList(@Url String getotp, @Field("user_id") String actionId, @Field("device_id") String id, @Field("device_token") String token, @Field("device_type") String Dtype);

    @FormUrlEncoded
    @POST
    Call<unitListModel.Data.Datum> getunitList1(@Url String getotp, @Field("user_id") String actionId, @Field("device_id") String id, @Field("device_token") String token, @Field("device_type") String Dtype, @Field("page") String page);


    @FormUrlEncoded
    @POST
    Call<createProductModel> geteditProduct(@Url String getotp, @Field("user_id") String user_id, @Field("title") String title, @Field("unit_name") String unit_name, @Field("unit_id") String unit_id, @Field("cat_id") String cat_id, @Field("color") String color, @Field("variant") String variant, @Field("type") String type, @Field("price") String price, @Field("discount") String discount, @Field("description") String description, @Field("device_id") String device_id, @Field("device_token") String device_token, @Field("device_type") String device_type, @Field("shop_id") String shop_id, @Field("photo") String photo, @Field("product_id") String id);


}
