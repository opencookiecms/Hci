package com.hcidev.hci;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import filepath.Filepath;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;

public class AddnoteFragment extends Fragment {

    private static final String BASE_URL = "http://192.168.1.132/hcirestapi/api/Nhandler/";

    private SharedPreferences sharedPreferences;
    Retrofit retrofit;
    Apiservice apiservice;
    String mediapath;

    EditText noteTitle;
    EditText noteContent;
    Button buttonupload;
    Button pickbutton;
    TextView textpaths;
    Context context;

    private int PICK_PDF_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;


    //Uri to store the image uri




    public AddnoteFragment(){

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView  = inflater.inflate(R.layout.addnote_content,container,false);

        requestStoragePermission();

        noteTitle = (EditText) rootView.findViewById(R.id.noteTitleid);
        noteContent = (EditText)rootView.findViewById(R.id.noteContentid);
        buttonupload = (Button) rootView.findViewById(R.id.uploadBut);
        pickbutton = (Button) rootView.findViewById(R.id.findFile);
        textpaths = (TextView) rootView.findViewById(R.id.textfilepath) ;

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();



        buttonupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mediapath == null){
                    Toast.makeText(getContext(),"Please select file first ", Toast.LENGTH_LONG).show();
                }
                else {
                    uploadMultipart();
                }


                //Toast.makeText(getContext(),"this is "+filePath, Toast.LENGTH_LONG).show();
                //Fileholder = Filepath.getPath(getActivity(), uri);

                //File note_link = new File(Fileholder);

                  //String path = Filepath.getPath(context, uri);
                  //Toast.makeText(getContext(),"fef"+path, Toast.LENGTH_LONG).show();


            }
        });


        pickbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showFileChooser();


            }
        });
        return  rootView;

    }

    public void uploadMultipart() {

       File note_link = new File(mediapath);
       Toast.makeText(getContext(),"test"+note_link, Toast.LENGTH_LONG).show();
       String note_title = noteTitle.getText().toString();
       String note_content = noteContent.getText().toString();

        apiservice = retrofit.create(Apiservice.class);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), note_link);
        MultipartBody.Part body = MultipartBody.Part.createFormData("note_link", note_link.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("title"), note_title);
        RequestBody content = RequestBody.create(MediaType.parse("content"),note_content);
        Call<Notes> call = apiservice.uploadNote(body,filename,content);
        call.enqueue(new Callback<Notes>() {
            @Override
            public void onResponse(Call<Notes> call, Response<Notes> response) {
                if (response.isSuccessful()) {

                    Notes notes = response.body();
                    Toast.makeText(getActivity(),"File successful uploaded",Toast.LENGTH_LONG).show();

                }else {
                    Toast.makeText(getActivity(),"No such file thing to upload or the files is to big",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Notes> call, Throwable t) {

            }
        });



    }


    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();

        intent.setType("*/*");

        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_PDF_REQUEST);

        //textpaths.setText(uri.getPath());

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri fileuri = data.getData();
            mediapath = Filepath.getPath(getActivity(),fileuri);

            textpaths.setText(mediapath);


        }
    }


    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {

        }

        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(getContext(), "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(getContext(), "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    public String getRealPathFromURI(Uri contentUri) {

        String[] projection = {MediaStore.Files.FileColumns.DATA};
        CursorLoader loader = new CursorLoader(getContext(),contentUri,projection,null,null,null);
        Cursor cursor = loader.loadInBackground();
        int index_col = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA);
        cursor.moveToFirst();
        String pathss = cursor.getString(index_col);
        cursor.close();
        return  pathss;
    }




}
