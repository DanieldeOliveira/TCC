package activitysEApplication;

import android.app.Application;
import android.util.DisplayMetrics;

import com.example.admin.casttv.R;
import com.google.android.libraries.cast.companionlibrary.cast.CastConfiguration;
import com.google.android.libraries.cast.companionlibrary.cast.VideoCastManager;

import java.util.Locale;

import utils.ServerUtils;


public class CastApplication extends Application {



    private static CastApplication instance;

    /* Namespace para comunicação com o Chromecast */
    private String nameSpaceSenderToChromeCast = String.valueOf(R.string.nameSpaceComunicaoChromeCast);


    /* Variavéis para armazenar o tamanho das telas da TV e do Smartphone. */
    private int widthTV;
    private int heightTV;
    private int widthTelaSmartphone;
    private int heightTelaSmartphone;
    String applicationId;
    /* Responsável por captar o tamanho da tela do smartphone */
    private DisplayMetrics metrics;


    /* Variável de configuração da biblioteca CastCompanionLibrary */
    private CastConfiguration options;


    /* Contador utilizado como ID nos elementos de mídia */
    private int contador;


    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        contador = 0;

        metrics = getResources().getDisplayMetrics();

        widthTelaSmartphone = metrics.heightPixels;
        heightTelaSmartphone = metrics.widthPixels;

        applicationId =  getString(R.string.app_id);

        options = new CastConfiguration.Builder(applicationId)
                .enableAutoReconnect()
                .enableDebug()
                .enableLockScreen()
                .enableNotification()
                .enableWifiReconnection()
                .setCastControllerImmersive(true)
                .addNamespace(getString(R.string.nameSpaceComunicaoChromeCast))
                .setLaunchOptions(false, Locale.getDefault())
                .setNextPrevVisibilityPolicy(CastConfiguration.NEXT_PREV_VISIBILITY_POLICY_DISABLED)
                .addNotificationAction(CastConfiguration.NOTIFICATION_ACTION_REWIND,true)
                .addNotificationAction(CastConfiguration.NOTIFICATION_ACTION_PLAY_PAUSE,true)
                .addNotificationAction(CastConfiguration.NOTIFICATION_ACTION_DISCONNECT,true)
                .setForwardStep(10)
                .build();


        VideoCastManager.initialize(this,options);
        ServerUtils.initialize();

    }


    public static CastApplication getInstance() {
        return instance;
    }

    public int getWidthTV() {
        return widthTV;
    }

    public void setWidthTV(int widthTV) {
        this.widthTV = widthTV;
    }

    public int getHeightTV() {
        return heightTV;
    }

    public void setHeightTV(int heightTV) {
        this.heightTV = heightTV;
    }

    public int getContador() {
        return contador++;
    }

    public int getWidthTelaSmartphone() {
        return widthTelaSmartphone;
    }


    public int getHeightTelaSmartphone() {
        return heightTelaSmartphone;
    }

    public void setHeightTelaSmartphone(int heightTelaSmartphone) {
        this.heightTelaSmartphone = heightTelaSmartphone;
    }
}
