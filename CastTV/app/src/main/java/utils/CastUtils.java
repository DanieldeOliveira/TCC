package utils;

import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.libraries.cast.companionlibrary.cast.VideoCastManager;
import com.google.android.libraries.cast.companionlibrary.cast.exceptions.NoConnectionException;
import com.google.android.libraries.cast.companionlibrary.cast.exceptions.TransientNetworkDisconnectionException;

/**
 * Created by admin on 13/06/2016.
 */
public class CastUtils {

    /* Variavel para trabalhar com a bibloteca CastCompanionLibrary */
    private VideoCastManager videoCastManager;



    /* Singleton */

    public CastUtils(){

        videoCastManager = VideoCastManager.getInstance();
    }


    public void startVideo(String url,int duracao,String nome){

        MediaMetadata mediaMetadata = new MediaMetadata(MediaMetadata.MEDIA_TYPE_MOVIE);
        mediaMetadata.putString(MediaMetadata.KEY_TITLE, nome);

        MediaInfo mediaInfo = new MediaInfo.Builder(url)
                .setContentType("video/")
                .setStreamDuration(duracao)
                .setStreamType(MediaInfo.STREAM_TYPE_BUFFERED)
                .setMetadata(mediaMetadata)
                .build();

        try {
            videoCastManager.loadMedia(mediaInfo, true, 0);


        } catch (TransientNetworkDisconnectionException e) {
            e.printStackTrace();
        } catch (NoConnectionException e) {
            e.printStackTrace();

        }
    }


}
