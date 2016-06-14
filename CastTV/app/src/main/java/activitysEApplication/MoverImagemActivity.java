package activitysEApplication;


import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.example.admin.casttv.R;
import com.google.android.libraries.cast.companionlibrary.cast.VideoCastManager;
import com.google.android.libraries.cast.companionlibrary.cast.exceptions.NoConnectionException;
import com.google.android.libraries.cast.companionlibrary.cast.exceptions.TransientNetworkDisconnectionException;

import org.json.JSONException;
import org.json.JSONObject;

import utils.CalculosUtils;
import utils.ImagemUtils;


/**
 * Created by admin on 23/12/2015.
 */
public class MoverImagemActivity extends AppCompatActivity {
    private static final  String TAG = "Daniel";
    private String tipoMidia;
    private String texto;
    private boolean imagemClicada = false;
    private ImageView objetoImageView;
    private DisplayMetrics metrics;
    private float widthTela,heightTela;
    private AlertDialog alerta;
    private JSONObject resposta;
    private JSONObject elemento;
    private float x,y;
    private String pathImg;
    private  Bundle extras;
    private VideoCastManager videoCastManager;
    private String mensagem = "";
    private String urlFinal = "";
    private int widthRealImg, heightRealImg;
    private LinearLayout mLayout;
    private LayoutParams dimensoesImageView;
    private CastApplication castApplication;
    private ImagemUtils imagemUtils;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_moverimagem);
        videoCastManager = VideoCastManager.getInstance();
        castApplication = CastApplication.getInstance();
        imagemUtils = new ImagemUtils();
        extras = getIntent().getExtras();
        tipoMidia = extras.getString("tipo");
        mLayout = (LinearLayout)findViewById(R.id.teste);
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        widthTela = metrics.widthPixels;
        heightTela = metrics.heightPixels;
        resposta = new JSONObject();
        objetoImageView = new ImageView(this);

        if(tipoMidia.equals("imagem")){
            pathImg = extras.getString("path");
            urlFinal = extras.getString("urlFinal");
            Bitmap bitmap = BitmapFactory.decodeFile(pathImg);
            widthRealImg = bitmap.getWidth();
            heightRealImg = bitmap.getHeight();
            dimensoesImageView = new LayoutParams(((int)(widthRealImg * 0.1)),((int)(heightRealImg * 0.1)));
            objetoImageView.setLayoutParams(dimensoesImageView);
            objetoImageView.setImageBitmap(bitmap);
            objetoImageView.setVisibility(View.VISIBLE);
            mLayout.addView(objetoImageView);
            objetoImageView.setX(((widthTela / 2) - (float)(widthRealImg * 0.05)));
            objetoImageView.setY(((heightTela/2) - (float)(heightRealImg * 0.05)));

        }
        else {

            texto = extras.getString("texto");
            dimensoesImageView = new LayoutParams(((int)(castApplication.getWidthTelaSmartphone()* 0.1)),
                    ((int)(castApplication.getHeightTelaSmartphone() * 0.1)));

            objetoImageView.setLayoutParams(dimensoesImageView);
            objetoImageView.setBackgroundColor(getResources().getColor(R.color.yellow));
            objetoImageView.setVisibility(View.VISIBLE);
            mLayout.addView(objetoImageView);
            objetoImageView.setX(((widthTela / 2) - (objetoImageView.getWidth()/ 2)));
            objetoImageView.setY(((heightTela / 2 ) - (objetoImageView.getHeight()/2)));
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:
                imagemClicada = isImagemClicada(event.getX(),event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                if (imagemClicada){
                    if(event.getX() >= (widthTela - objetoImageView.getWidth())){
                        x = (widthTela - objetoImageView.getWidth());
                    }
                    else if(event.getY() >= (heightTela - objetoImageView.getHeight())){
                        y = (heightTela - objetoImageView.getHeight());
                    }
                    else if(event.getX() < 0.0){
                        x = 0.0f;
                    }
                    else if(event.getY() < 0.0){
                        y = 0.0f;
                    }
                    else {
                        x = event.getX();
                        y = event.getY();
                    }

                    objetoImageView.setX(x);
                    objetoImageView.setY(y);




                }
                break;
            case MotionEvent.ACTION_UP:

                    mostrar_alerta();
                    Log.d(TAG, "Valor de X ao soltar a imagem = " + x);
                   /*if (imagemClicada){
                       mostrar_alerta();
                   }*/


                break;
        }
        return true;
    }

    private void mostrar_alerta(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Envio para o Chromecast");
        builder.setMessage("Enviar a imagem para o chromecast?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {



                try {

                    elemento = new JSONObject();
                    if(tipoMidia.equals("imagem")){
                        elemento.put("tipoMidia","img");
                        elemento.put("id",castApplication.getContador());
                        elemento.put("url",urlFinal);
                        elemento.put("widthImagem", String.valueOf(objetoImageView.getWidth()));
                        elemento.put("heightImagem", String.valueOf(objetoImageView.getHeight()));
                        elemento.put("posicao",CalculosUtils.calcularPosicao(objetoImageView.getX(),
                                objetoImageView.getY(),castApplication));
                        elemento.put("widthRealImg",String.valueOf(widthRealImg));
                        elemento.put("heightRealImg",String.valueOf(heightRealImg));
                    }
                    else {
                        elemento.put("tipoMidia","comentario");
                        elemento.put("id",castApplication.getContador());
                        elemento.put("texto",texto);
                        elemento.put("posicao",CalculosUtils.calcularPosicao(objetoImageView.getX(),
                                objetoImageView.getY(),castApplication));
                        elemento.put("widthImagem", String.valueOf(objetoImageView.getWidth()));
                        elemento.put("heightImagem", String.valueOf(objetoImageView.getHeight()));

                    }


                    resposta.put("tipo","addElemento");
                    resposta.put("elemento",elemento);
                    mensagem = resposta.toString();
                    Log.d(TAG,"Valor da mensagem = " + mensagem);
                    Log.d(TAG,"Valor da variavel resposta = " + resposta.toString());
                    videoCastManager.sendDataMessage(mensagem);
                    onBackPressed();

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (TransientNetworkDisconnectionException e) {
                    e.printStackTrace();
                } catch (NoConnectionException e) {
                    e.printStackTrace();
                }
            }
        });

        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                onBackPressed();
            }
        });

        alerta = builder.create();
        alerta.show();
    }

    /*@Override
    public void onBackPressed() {
        Intent it = new Intent();
        it.putExtra("moverImg", resposta.toString());
        setResult(3,it);
        super.onBackPressed();
    }*/

    private boolean isImagemClicada(float x, float y) {



        if ((x >= objetoImageView.getX() && x <= (objetoImageView.getX() + objetoImageView.getWidth()))
                && (y >= objetoImageView.getY() && y <= (objetoImageView.getY() + objetoImageView.getHeight()))) {

            return true;
        }
        return false;
    }


}
