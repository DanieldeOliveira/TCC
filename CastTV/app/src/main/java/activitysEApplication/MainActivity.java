package activitysEApplication;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.admin.casttv.R;
import com.google.android.libraries.cast.companionlibrary.cast.BaseCastManager;
import com.google.android.libraries.cast.companionlibrary.cast.VideoCastManager;
import com.google.android.libraries.cast.companionlibrary.cast.callbacks.VideoCastConsumer;
import com.google.android.libraries.cast.companionlibrary.cast.callbacks.VideoCastConsumerImpl;
import com.google.android.libraries.cast.companionlibrary.widgets.IntroductoryOverlay;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import utils.CastUtils;
import utils.ImagemUtils;
import utils.ServerUtils;
import utils.VideoUtils;


public class MainActivity extends AppCompatActivity {



    /* Tags para identificação do tipo de mídia deve ser exibida na abretura do Galery */

    public static final int IMAGEM_INTERNA = 1;
    public static final int VIDEO_INTERNO = 2;


    private static String TAG = "LogsCastTV";


    private boolean mIsHoneyCombOrAbove = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    private IntroductoryOverlay mOverlay;

    /* Variavél que permite acessar as variáveis globais da aplicaçao */
    private CastApplication castApplication;


    /* Variáveis da biblioteca CastCompanionLibrary */
    private VideoCastManager videoCastManager;
    private VideoCastConsumer videoCastConsumer;

    /* Variavel responsável pela comunicação com o Chromecast */
    private CastUtils castUtils;


    /* Botões */
    private ImageButton btn_addVideo;
    private ImageButton btn_addImagem;
    private ImageButton btn_addComentario;
    private ImageButton btn_listaMidiasEnviadas;

    /* variavel para acessar o menu da activity */
    private MenuItem mediaRouteMenuItem;


    /* Variaveis por armazenar as mensagens trocadas entre o Chromecast e o smartphone */
    private JSONObject mensagem;
    private JSONObject mensagemRecebida;

    private Intent intent;

    /* Variaveis para a manipulação dos elementos de mídias */
    private File file;
    Uri uriSelecionada = null;
    String path = "";
    String urlFinal = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"Entrou no onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Configuração do Toolbar */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /* Verifica se o smartphone tem o Google Play Services instalado */
        BaseCastManager.checkGooglePlayServices(this);


        castApplication = CastApplication.getInstance();

        btn_addVideo = (ImageButton)findViewById(R.id.Ibtn_addVideo);
        btn_addImagem = (ImageButton)findViewById(R.id.Ibtn_addImg);
        btn_addComentario = (ImageButton)findViewById(R.id.Ibtn_addComentario);
        btn_listaMidiasEnviadas = (ImageButton)findViewById(R.id.Ibtn_elementosEnviados);

        castUtils = new CastUtils();

        /*Configuração de "escuta" dos eventos de comunicação entre o Chromecast e smartphone */
        videoCastManager = VideoCastManager.getInstance();
        videoCastConsumer = new VideoCastConsumerImpl(){


            @Override
            public void onCastAvailabilityChanged(boolean castPresent) {

                if(castPresent && mIsHoneyCombOrAbove){

                    showOverlay();
                }
            }

            @Override
            public void onDataMessageReceived(String message) {

                try {

                    mensagemRecebida = new JSONObject(message);

                    if (mensagemRecebida.get("tipo").equals("dimensaoTela")){
                        castApplication.setWidthTV((int)mensagemRecebida.get("width"));
                        castApplication.setHeightTV((int)mensagemRecebida.get("height"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        videoCastManager.addVideoCastConsumer(videoCastConsumer);


        /* Eventos de clique dos botões */

        btn_addVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(videoCastManager.isConnected()){
                    intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("video/*");
                    startActivityForResult(Intent.createChooser(intent, "Selecione o video"), VIDEO_INTERNO);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Por favor conecte-se ao ChormeCast",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_addImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(videoCastManager.isConnected()){

                    intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Selecione a foto"), IMAGEM_INTERNA);
                }

                else {
                    Toast.makeText(getApplicationContext(),"Por favor conecte-se ao ChormeCast",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_addComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(videoCastManager.isConnected()){

                    intent = new Intent(MainActivity.this,ComentarioActivity.class);
                    startActivity(intent);
                }

                else{
                    Toast.makeText(getApplicationContext(),"Por favor conecte-se ao ChormeCast",Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        Log.d(TAG,"Entrou no onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mediaRouteMenuItem = videoCastManager.addMediaRouterButton(menu,R.id.media_route_menu_item);
        return true;
    }

    @Override
    protected void onResume() {
        Log.d(TAG,"Entrou no onResume");
        videoCastManager = VideoCastManager.getInstance();
        videoCastManager.incrementUiCounter();
        super.onResume();
    }

    @Override
    protected void onPause() {
        videoCastManager.decrementUiCounter();
        Log.d(TAG,"Entrou no onPause");
        super.onPause();
    }

    @Override
    protected void onRestart() {
        Log.d(TAG,"Entrou no onRestart");
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG,"Entrou no onDestroy");
        super.onDestroy();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK){

            if (requestCode == VIDEO_INTERNO){

                uriSelecionada = data.getData();
                path = VideoUtils.getPathVideo(uriSelecionada,getApplicationContext());
                file = new File(path);
                urlFinal = ServerUtils.enderecoServidor(getApplicationContext()) + path.toString()
                        + "?tipo=vdo";

                castUtils.startVideo(urlFinal, VideoUtils.getDuracaoVideo(getApplicationContext(),path),file.getName());

            }

            if(requestCode == IMAGEM_INTERNA){

                uriSelecionada = data.getData();
                path = ImagemUtils.getUrlImagem(uriSelecionada,getApplicationContext());
                urlFinal = ServerUtils.enderecoServidor(getApplicationContext()) + path.toString() +
                        "?tipo=img";

                intent = new Intent(MainActivity.this,MoverImagemActivity.class);
                intent.putExtra("tipo","imagem");
                intent.putExtra("path",path);
                intent.putExtra("urlFinal",urlFinal);

                startActivity(intent);

            }
        }
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void showOverlay() {

        if (mOverlay != null){
            mOverlay.remove();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (mediaRouteMenuItem.isVisible()){
                    mOverlay = new IntroductoryOverlay.Builder(MainActivity.this)
                            .setMenuItem(mediaRouteMenuItem)
                            .setSingleTime()
                            .setTitleText("Conecte-se ao Chromecast")
                            .setOnDismissed(new IntroductoryOverlay.OnOverlayDismissedListener() {
                                @Override
                                public void onOverlayDismissed() {
                                    mOverlay = null;
                                }
                            })
                            .build();
                    mOverlay.show();
                }
            }
        },1000);
    }
}
