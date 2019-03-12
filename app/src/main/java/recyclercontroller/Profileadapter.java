package recyclercontroller;

import android.media.Image;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcidev.hci.Notes;
import com.hcidev.hci.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class Profileadapter extends RecyclerView.Adapter<Profileadapter.ProfileHolder> {


    ArrayList<Notes> noteadapter;
    public Profileadapter(ArrayList<Notes> noteadapter) {
        this.noteadapter = noteadapter;
    }

    public class ProfileHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView content;
        TextView typetext;
        ImageView imageView;

        public  ProfileHolder(View profileview){
            super(profileview);

            title = profileview.findViewById(R.id.textTitle);
            content = profileview.findViewById(R.id.textContent);
            typetext = profileview.findViewById(R.id.textType);
            imageView = profileview.findViewById(R.id.imgTypes);

        }
    }


    @NonNull
    @Override
    public Profileadapter.ProfileHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.profilenote_card,viewGroup,false);
        return new ProfileHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Profileadapter.ProfileHolder profileHolder, int i) {
        Notes notes = noteadapter.get(i);

        profileHolder.title.setText(notes.getNote_title());
        profileHolder.content.setText(notes.getNote_content());
       // profileHolder.typetext.setText(notes.getNote_type());
        //profileHolder.imageView.setImageResource(R.drawable.ic_docx);

       if(notes.getNote_type() == 1){
            profileHolder.imageView.setImageResource(R.drawable.ic_docx);
        }
        else if(notes.getNote_type() == 2) {
           profileHolder.imageView.setImageResource(R.drawable.ic_pdf64);
        }
        else{
            profileHolder.imageView.setImageResource(R.drawable.ic_menu_camera);
       }

    }

    @Override
    public int getItemCount() {
        return noteadapter.size();
    }


}
