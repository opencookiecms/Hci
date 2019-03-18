package recyclercontroller;


import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hcidev.hci.Notes;
import com.hcidev.hci.R;
import java.util.ArrayList;

public class Profileadapter extends RecyclerView.Adapter<Profileadapter.ProfileHolder>{

    private static final int MY_PERMISSIONS_REQUEST_STORAGE = 1000;
    private static final String TAG =Profileadapter.class.getSimpleName();
    //private String  urlpath;
    DownloadManager downloadManager;


    ArrayList<Notes> noteadapter;
    public Profileadapter(ArrayList<Notes> noteadapter) {
        this.noteadapter = noteadapter;
    }

    @NonNull
    @Override
    public Profileadapter.ProfileHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.profilenote_card,viewGroup,false);
        return new ProfileHolder(view);


    }

    @Override
    public void onBindViewHolder(final Profileadapter.ProfileHolder profileHolder, int i) {

        final Notes notes = noteadapter.get(i);

        profileHolder.title.setText(notes.getNote_title());
        profileHolder.content.setText(notes.getNote_content());
        String  docx = ".docx";
        String pdf = ".pdf";
        String pptx = ".pptx";
        String doc = ".doc";


       if(notes.getNote_type().equals(docx)){
            profileHolder.imageView.setImageResource(R.drawable.ic_docx32);
        }
        else if(notes.getNote_type().equals(pdf)) {
           profileHolder.imageView.setImageResource(R.drawable.ic_pdf32);
        }
        else if(notes.getNote_type().equals(pptx)){
           profileHolder.imageView.setImageResource(R.drawable.ic_pptx);
       }else if(notes.getNote_type().equals(doc)){
            profileHolder.imageView.setImageResource(R.drawable.ic_doc);
       }
       else {
           profileHolder.imageView.setImageResource(R.drawable.ic_file);
       }

       profileHolder.buttondownload.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                String urlpath = notes.getNote_link();
                String noFile = "";
               //Toast.makeText(v.getContext(),"this is "+urlpath, Toast.LENGTH_LONG).show();
               //new DownloadFile().execute(urlpath);
               if(notes.getNote_link().equals(noFile)){
                   Toast.makeText(v.getContext(),"no such file to download", Toast.LENGTH_LONG).show();
               }
               else {
                   downloadManager =(DownloadManager) v.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                   Uri uri = Uri.parse("http://192.168.1.132/hcirestapi/assets/pdf/"+urlpath);
                   DownloadManager.Request request = new DownloadManager.Request(uri);
                   request.setTitle("Downloading " +urlpath);
                   request.setDescription("Download files");
                   request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                   downloadManager.enqueue(request);
                   Toast.makeText(v.getContext()," Downloading Files "+urlpath, Toast.LENGTH_LONG).show();
               }



           }
       });
    }

    public class ProfileHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView content;
        TextView typetext;
        ImageView imageView;
        Button buttondownload;

        public  ProfileHolder(View profileview){
            super(profileview);

            title = profileview.findViewById(R.id.textTitle);
            content = profileview.findViewById(R.id.textContent);
            typetext = profileview.findViewById(R.id.textType);
            imageView = profileview.findViewById(R.id.imgTypes);
            buttondownload = profileview.findViewById(R.id.dButton);

        }
    }

    @Override
    public int getItemCount() {
        return noteadapter.size();
    }

    public class DownloadFile extends AsyncTask<String, String, String>{

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
    }


}
