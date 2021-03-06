package com.hcidev.hci;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import recyclercontroller.Profileadapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class MynoteFragment extends Fragment {

    RecyclerView recyclerView;
    Profileadapter profileadapter;
    ImageView imageViewtype;
    TextView textViews;
    Button downloadb;
    private static final String BASE_URL = "http://192.168.1.132/hcirestapi/api/Nhandler/";



    public MynoteFragment(){

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootview =  inflater.inflate(R.layout.mynote_content,container,false);


        recyclerView = (RecyclerView) rootview.findViewById(R.id.myRecylerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(profileadapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Apiservice apiservice = retrofit.create(Apiservice.class);

        Call<ArrayList<Notes>> call = apiservice.getNotes();

        call.enqueue(new Callback<ArrayList<Notes>>() {
            @Override
            public void onResponse(Call<ArrayList<Notes>> call, Response<ArrayList<Notes>> response) {
                profileadapter = new Profileadapter(response.body());
                recyclerView.setAdapter(profileadapter);

            }

            @Override
            public void onFailure(Call<ArrayList<Notes>> call, Throwable t) {

            }
        });


        return rootview;



    }

}
