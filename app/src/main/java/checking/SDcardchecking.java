package checking;

import android.os.Environment;

public class SDcardchecking {

    public static boolean isSdmount(){
        if(Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED
        )){
            return true;
        }
        return  false;
    }
}
