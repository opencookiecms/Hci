package com.hcidev.hci;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterFragment extends Fragment {

    private static final String BASE_URL = "http://192.168.1.132/hcirestapi/api/Nhandler/";
    private SharedPreferences sharedPreferences;
    Retrofit retrofit;
    Apiservice apiservice;

    EditText regname;
    EditText regusename;
    EditText regpassword;
    Button buttonreg;

    public RegisterFragment(){

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.register_content,container,false);

        regname = (EditText) rootView.findViewById(R.id.editName);
        regusename = (EditText) rootView.findViewById(R.id.editEmail);
        regpassword = (EditText) rootView.findViewById(R.id.editPassword);
        buttonreg  = (Button) rootView.findViewById(R.id.buttonReg);

                retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        buttonreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        return rootView;

    }

    private void registerUser(){
        apiservice = retrofit.create(Apiservice.class);

        apiservice.regUsers(
                regname.getText().toString(),
                regusename.getText().toString(),
                regpassword.getText().toString())
                .enqueue(new Callback<Usersprofile>() {
                    @Override
                    public void onResponse(Call<Usersprofile> call, Response<Usersprofile> response) {
                        if(response.isSuccessful()){
                            Usersprofile usersprofile = response.body();
                            Toast.makeText(getActivity(),"Your accout was susccessful create :" + response.code(),Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(getActivity(),"Fail error code " + response.code(),Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Usersprofile> call, Throwable t) {

                    }
                });
    }
}

