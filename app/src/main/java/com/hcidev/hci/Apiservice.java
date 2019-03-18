package com.hcidev.hci;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Apiservice {

    @GET("notes")
    Call<ArrayList<Notes>> getNotes();

    @FormUrlEncoded
    @POST("users")
    Call<Usersprofile> regUsers(@Field("username") String user_username,
                                @Field("email")String user_email,
                                @Field("password")String user_password);

    @Multipart
    @POST("notes")
    Call<Notes> uploadNote(@Part MultipartBody.Part note_link,
                           @Part("title") RequestBody note_title,
                           @Part("content") RequestBody note_content);





}
