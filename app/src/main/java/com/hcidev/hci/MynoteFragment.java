package com.hcidev.hci;

import android.os.Bundle;
import android.support.annotation.Nullable;
import java.util.List;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MynoteFragment extends Fragment {

    private TextView viewResult;

    public MynoteFragment(){

    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.mynote_content,container,false);

        viewResult = (TextView) view.findViewById(R.id.TextViewOnTextResult);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2/hcirestapi/api/Nhandler/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Apiservice apiservice = retrofit.create(Apiservice.class);

        Call<List<Notes>> call = apiservice.getNotes();

        call.enqueue(new Callback<List<Notes>>() {
            @Override
            public void onResponse(Call<List<Notes>> call, Response<List<Notes>> response) {

                if(!response.isSuccessful()){
                    viewResult.setText("Code" + response.code());
                    return;
                }

                List<Notes> notes = response.body();

                for(Notes notess : notes){

                    String content ="notes";
                    content += "id: " + notess.getNote_id()+ "\n";
                    content += "Type: "  + notess.getNote_title() + "\n";

                    viewResult.append(content);

                }


            }

            @Override
            public void onFailure(Call<List<Notes>> call, Throwable t) {

                viewResult.setText(t.getMessage());

            }
        });

        return view;

    }
}
