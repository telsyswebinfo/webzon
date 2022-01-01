package com.webzon.utils;

import android.media.MediaPlayer;
import android.os.Handler;

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
import com.webzon.Model.storeImageModel;
import com.webzon.Model.unitListModel;
import com.webzon.Model.updateBussinessModel;
import com.webzon.Model.verifyUserModel;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;


public class Consts {
    public static final String CITYDATA = "cityData";
    public static final String USERID = "user_id";
    public static final String LOGINAS = "login_as";
    public static final String USERADATA = "user_data";
    public static final String ISLOGIN = "is_login";
    public static final String LOGINTOKEN = "login";
    public static final String DEVICE_TOKEN = "device_token";
    public static final String NOTIFICATIONID = "notificationid";
    public static final String IS_NOTIFICATION_ON = "is_notification_on";
    public static int REQUEST_CAMERA = 0;
    public static int SELECT_FILE = 1;
    public static String FILE_PROVIDER = ".android.fileprovider";
    public static MediaPlayer mediaPlayer;
    public static final int PAYPAL_REQUEST_CODE = 123;
    public static final int PLAY_SERVICES_RESOLUTION_REQUEST = 1234;
    public static int NOCONETENT = 204;
    public static int STTAUSOK = 200;
    public static int BADREQUEST = 400;
    public static int Unauthorized = 403;
    public static int Forbidden = 401;
    public static int NotFound = 404;
    public static int InternalServerError = 405;
    public static int Conflict = 409;
    public static int UnprocessableEntityParametermissing = 422;
    public static Handler handler = new Handler();
    public static String a_id = "0";
    public static String b_id = "0";


    public static Call<GetSliderModel> getSlider() {
        return Utils.requestApiDefault(null).getSlider("getSlider");
    }

    public static Call<OtpModel> getotp(String c_code , String number) {
        /*HashMap<String, RequestBody> map = new HashMap();
        map.put("id", RequestBody.create(MediaType.parse("text/plain"), id));
        map.put("blog_id", RequestBody.create(MediaType.parse("text/plain"), blog_id));*/
        return Utils.requestApiDefault(null).getotp("otp",c_code,number);
    }
    public static Call<OtpModel> getwhatsapp_otp(String c_code , String number, String id) {
        return Utils.requestApiDefault(null).getwhatsapp_otp("whatsapp_otp",c_code,number,id);
    }

    public static Call<GetCategoryModel> getCategory() {
        return Utils.requestApiDefault(null).getCategory("getCategory");
    }

    public static Call<LoginModel> getlogin(String phone_number , String role, String device_id, String device_token, String device_type) {
        return Utils.requestApiDefault(null).getlogin("api/login",phone_number,role,device_id,device_token,device_type);
    }

    public static Call<verifyUserModel> getverifyUser(String phone_number , String code, String device_id, String device_token, String device_type) {
        return Utils.requestApiDefault(null).getverifyUser("api/verifyUser",phone_number,code,device_id,device_token,device_type);
    }

    public static Call<resendOTPModel> getresendOTP(String id, String device_id, String device_token, String device_type) {
        return Utils.requestApiDefault(null).getresendOTP("api/resendOTP",id,device_id,device_token,device_type);
    }

    public static Call<categoryListModel> getcategoryList(String user_id, String type,String device_id, String device_token, String device_type) {
        return Utils.requestApiDefault(null).getcategoryList("api/categoryList",user_id,type,device_id,device_token,device_type);
    }

    public static Call<updateBussinessModel> getupdateBussiness(String user_id, String name, String category_id, String device_id, String device_token,String device_type, String address, String lat, String lng,String photo) {
        return Utils.requestApiDefault(null).getupdateBussiness("api/updateBussiness",user_id,name,category_id,device_id,device_token,device_type,address,lat,lng,photo);
    }

    public static Call<homePageDataModel> gethomePageData(String user_id, String device_id, String device_token, String device_type, String filter, String start_date, String end_date) {
        return Utils.requestApiDefault(null).gethomePageData("api/homePageData",user_id,device_id,device_token,device_type,filter,start_date,end_date);
    }

    public static Call<createProductModel> getcreateProduct(String user_id, String title, String unit_name, String unit_id,String cat_id,String color,String variant,String type,String price,String discount,String description,String device_id,String device_token,String device_type,String shop_id ,String photo) {
        return Utils.requestApiDefault(null).getcreateProduct("api/createProduct",user_id,title,unit_name,unit_id,cat_id,color,variant,type,price,discount,description,device_id,device_token,device_type,shop_id,photo);
    }

    public static Call<createCategoryModel> getcreateCategory(String user_id, String title, String type, String device_id, String device_token, String device_type, String photo) {
        return Utils.requestApiDefault(null).getcreateCategory("api/createCategory",user_id,title,type,device_id,device_token,device_type,photo);
    }

    public static Call<categoryShopListModel> getcategoryList1(String user_id, String type, String device_id, String device_token, String device_type) {
        return Utils.requestApiDefault(null).getcategoryList1("api/categoryList",user_id,type,device_id,device_token,device_type);
    }

    public static Call<productListModel> getproductList(String user_id, String device_id, String type, String device_token, String device_type, String filter,String start_date, String end_date) {
        return Utils.requestApiDefault(null).getproductList("api/productList",user_id,device_id,type,device_token,device_type,filter,start_date,end_date);
    }

    public static Call<unitListModel> getunitList(String user_id, String device_id, String device_token, String device_type) {
        return Utils.requestApiDefault(null).getunitList("api/unitList",user_id,device_id,device_token,device_type);
    }

    public static Call<unitListModel.Data.Datum> getunitList1(String user_id, String device_id, String device_token, String device_type,String page) {
        return Utils.requestApiDefault(null).getunitList1("api/unitList",user_id,device_id,device_token,device_type,page);
    }

    public static Call<createProductModel> geteditProduct(String user_id, String title, String unit_name, String unit_id,String cat_id,String color,String variant,String type,String price,String discount,String description,String device_id,String device_token,String device_type,String shop_id ,String photo,String id) {
        return Utils.requestApiDefault(null).geteditProduct("api/editProduct",user_id,title,unit_name,unit_id,cat_id,color,variant,type,price,discount,description,device_id,device_token,device_type,shop_id,photo,id);
    }

}
