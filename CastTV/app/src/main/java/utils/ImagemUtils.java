package utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import org.json.JSONException;
import org.json.JSONObject;

import activitys.CastApplication;

/**
 * Created by admin on 13/06/2016.
 */
public class ImagemUtils {

    private CastApplication castApplication;
    private JSONObject dimensoes;


    /* Classe responsável pela implementação da lógica mídia relativa a parte de imagem */
    public static String getUrlImagem(Uri uri, Context ctx){

        Context context = ctx;
        Uri contentUri = uri;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = null;
        String path = null;

        try{
            if(Build.VERSION.SDK_INT > 19){
                String wholeID = DocumentsContract.getDocumentId(contentUri);
                // Split at colon, use second item in the array
                String id = wholeID.split(":")[1];
                // where id is equal to
                String sel = MediaStore.Images.Media._ID + "=?";


                cursor = context.getContentResolver().query(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        projection, sel, new String[] { id }, null);
            }
            else{
                cursor = context.getContentResolver().query(contentUri,
                        projection, null, null, null);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            int column_index = cursor
                    .getColumnIndex(MediaStore.Images.Media.DATA);

            cursor.moveToFirst();
            path = cursor.getString(column_index).toString();
            cursor.close();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return path;

    }



}
