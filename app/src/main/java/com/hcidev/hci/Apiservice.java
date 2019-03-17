package com.hcidev.hci;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Apiservice {

    @GET("notes")
    Call<ArrayList<Notes>> getNotes();

    @FormUrlEncoded
    @POST("users")
    Call<Usersprofile> regUsers(@Field("username") String user_username,
                                @Field("email")String user_email,
                                @Field("password")String user_password);





    Call<Notes> uploadNote(@Field("title") String notes_title,
                           @Field("content")String note_content);





}
