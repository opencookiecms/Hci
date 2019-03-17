package com.hcidev.hci;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddnoteFragment extends Fragment {

    private static final String BASE_URL = "http://192.168.1.132/hcirestapi/api/Dhandler/";
    private SharedPreferences sharedPreferences;
    Retrofit retrofit;
    Apiservice apiservice;

    EditText noteTitle;
    EditText noteContent;
    Button buttonupload;

    public AddnoteFragment(){

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView  = inflater.inflate(R.layout.addnote_content,container,false);

        noteTitle = (EditText) rootView.findViewById(R.id.noteTitleid);
        noteContent = (EditText)rootView.findViewById(R.id.noteContentid);
        buttonupload = (Button) rootView.findViewById(R.id.uploadBut);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        buttonupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uploadnote();
            }
        });



        return  rootView;

    }

    private void Uploadnote(){

        apiservice = retrofit.create(Apiservice.class);


        apiservice.uploadNote(
                noteTitle.getText().toString(),
                noteContent.getText().toString())
                .enqueue(new Callback<Notes>() {
                    @Override
                    public void onResponse(Call<Notes> call, Response<Notes> response) {

                    }

                    @Override
                    public void onFailure(Call<Notes> call, Throwable t) {

                    }
                });
    }
}
