package com.hcidev.hci;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Apiservice {

    @GET("notes")
    Call<ArrayList<Notes>> getNotes();
}
