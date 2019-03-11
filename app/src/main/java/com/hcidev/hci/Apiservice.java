package com.hcidev.hci;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Apiservice {

    @GET("notes")
    Call<List<Notes>> getNotes();
}
