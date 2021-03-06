package com.appsinventiv.littlegarden.Utils;


import com.appsinventiv.littlegarden.NetworkResponses.AddToCartResponse;
import com.appsinventiv.littlegarden.NetworkResponses.CategoryResponse;
import com.appsinventiv.littlegarden.NetworkResponses.ChangePasswordResponse;
import com.appsinventiv.littlegarden.NetworkResponses.ConfirmBookingResponse;
import com.appsinventiv.littlegarden.NetworkResponses.LoginResponse;
import com.appsinventiv.littlegarden.NetworkResponses.ListOfOrdersResponse;
import com.appsinventiv.littlegarden.NetworkResponses.MakeReservationResponse;
import com.appsinventiv.littlegarden.NetworkResponses.PlaceOrderResponse;
import com.appsinventiv.littlegarden.NetworkResponses.ProductResponse;
import com.appsinventiv.littlegarden.NetworkResponses.RemoveMenuResponse;
import com.appsinventiv.littlegarden.NetworkResponses.SaveAddressResponse;
import com.appsinventiv.littlegarden.NetworkResponses.SaveUserResponse;
import com.appsinventiv.littlegarden.NetworkResponses.SignupResponse;
import com.appsinventiv.littlegarden.NetworkResponses.UploadProfilePictureReponse;
import com.appsinventiv.littlegarden.NetworkResponses.UserDetailsResponse;
import com.appsinventiv.littlegarden.NetworkResponses.ViewOrderResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface UserClient {


    @POST("api/login")
    @FormUrlEncoded
    Call<LoginResponse> loginUser(
            @Field("email") String email,
            @Field("password") String password

    );

    @POST("api/signup")
    @FormUrlEncoded
    Call<SignupResponse> signUp(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("c_password") String cpassword

    );


    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("api/details")
    Call<UserDetailsResponse> userDetails(
            @Header("Authorization") String auth

    );

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("api/category")
    Call<CategoryResponse> getCategories(
            @Header("Authorization") String auth

    );

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("api/products")
    Call<ProductResponse> getProducts(
            @Header("Authorization") String auth

    );

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("api/orders/all")
    Call<ListOfOrdersResponse> getOrders(
            @Header("Authorization") String auth

    );

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @POST("api/orders/{id}")
    Call<ViewOrderResponse> getOrdersDetails(
            @Header("Authorization") String auth,
            @Path(value = "id", encoded = true) String userId

    );


    @Headers({"Content-Type: application/x-www-form-urlencoded", "Accept:application/json"})
    @POST("api/savegeneral")
    @FormUrlEncoded
    Call<SaveUserResponse> saveGeneral(
            @Header("Authorization") String auth,
            @Field("name") String name,
            @Field("cell1") String cell1,
            @Field("cell2") String cell2,
            @Field("cell3") String cell3


    );

    @Headers({"Content-Type: application/x-www-form-urlencoded", "Accept:application/json"})
    @POST("api/savepassword")
    @FormUrlEncoded
    Call<ChangePasswordResponse> changePassword(
            @Header("Authorization") String auth,
            @Field("password") String password,
            @Field("password_confirmation") String confirmPassword


    );

    @Headers({"Content-Type: application/x-www-form-urlencoded", "Accept:application/json"})
    @POST("api/saveaddress")
    @FormUrlEncoded
    Call<SaveAddressResponse> saveAddress(
            @Header("Authorization") String auth,
            @Field("address1") String address1,
            @Field("address2") String address2,
            @Field("address3") String address3


    );

    @Headers({"Accept:application/json"})
    @POST("api/saveimage")
    @Multipart
    Call<UploadProfilePictureReponse> uploadPicture(
            @Header("Authorization") String auth,
            @Part MultipartBody.Part file, @Part("profile") RequestBody name

    );


    @Headers({"Content-Type: application/x-www-form-urlencoded", "Accept:application/json"})
    @POST("api/reservations")
    @FormUrlEncoded
    Call<MakeReservationResponse> bookTable(
            @Header("Authorization") String auth,
            @Field("date") String date,
            @Field("time") String time,
            @Field("timeto") String timeTo,
            @Field("persons") String persons


    );

    @Headers({"Content-Type: application/x-www-form-urlencoded", "Accept:application/json"})
    @POST("api/confirm")
    Call<ConfirmBookingResponse> confirmBooking(
            @Header("Authorization") String auth


    );


    @Headers({"Content-Type: application/x-www-form-urlencoded", "Accept:application/json"})
    @POST("api/confirm")
    Call<ConfirmBookingResponse> addMenuToCart(
            @Header("Authorization") String auth,
            @Field("id") String menuId

    );

    @Headers({"Content-Type: application/x-www-form-urlencoded", "Accept:application/json"})
    @POST("api/addtable")
    @FormUrlEncoded
    Call<ConfirmBookingResponse> chooseTable(
            @Header("Authorization") String auth,
            @Field("id") String tableId,
            @Field("date") String date,
            @Field("time") String time,
            @Field("persons") String persons

    );


    @Headers({"Content-Type: application/x-www-form-urlencoded", "Accept:application/json"})
    @POST("api/cart")
    @FormUrlEncoded
    Call<ConfirmBookingResponse> confirmBooking(
            @Header("Authorization") String auth,
            @Field("id") String menuId


    );

    @Headers({"Content-Type: application/x-www-form-urlencoded", "Accept:application/json"})
    @POST("api/cart")
    @FormUrlEncoded
    Call<AddToCartResponse> addToCart(
            @Header("Authorization") String auth,
            @Field("id") String id,
            @Field("eid") String eid

    );

    @Headers({"Content-Type: application/x-www-form-urlencoded", "Accept:application/json"})
    @POST("api/addExtraToCart")
    @FormUrlEncoded
    Call<AddToCartResponse> addExtraToCart(
            @Header("Authorization") String auth,
            @Field("id") String id,
            @Field("eid") String eid

    );



    @Headers({"Content-Type: application/x-www-form-urlencoded", "Accept:application/json"})
    @POST("api/removeFromCart")
    @FormUrlEncoded
    Call<AddToCartResponse> removeFromCart(
            @Header("Authorization") String auth,
            @Field("id") String id

    );

    @Headers({"Content-Type: application/x-www-form-urlencoded", "Accept:application/json"})
    @POST("api/removemenu")
    @FormUrlEncoded
    Call<RemoveMenuResponse> removeMenu(
            @Header("Authorization") String auth,
            @Field("id") String id

    );

    @Headers({"Content-Type: application/x-www-form-urlencoded", "Accept:application/json"})
    @POST("api/removetable")
    @FormUrlEncoded
    Call<RemoveMenuResponse> removeTable(
            @Header("Authorization") String auth,
            @Field("id") String id

    );

    //    @Headers({"Content-Type: application/x-www-form-urlencoded", "Accept:application/json"})
//    @POST("api/placeorder{data}")
//    Call<PlaceOrderResponse> placeOrder(
//            @Header("Authorization") String auth,
//            @Path(value = "data", encoded = true) String data,
//            @Url String apiname
//
//
//    );
    @Headers({"Content-Type: application/x-www-form-urlencoded", "Accept:application/json"})
    @POST()
    Call<PlaceOrderResponse> placeOrder(
            @Header("Authorization") String auth,
            @Url String apiname


    );

    @POST("/braintreeCheckout.php")
    @FormUrlEncoded
    Call<ResponseBody> braintreeCheckout(
            @Field("payment_method_nonce") String nonce

    );

    @GET("/braintreetoken.php")
    Call<ResponseBody> geTokken();

}
