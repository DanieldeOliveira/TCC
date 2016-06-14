package activitysEApplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.example.admin.casttv.R;
import com.google.android.libraries.cast.companionlibrary.cast.VideoCastManager;

public class ComentarioActivity extends AppCompatActivity {


    private Button btn_enviarComentario;
    private Button btn_limparComentario;
    private EditText edt_comentario;
    private VideoCastManager videoCastManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btn_enviarComentario = (Button)findViewById(R.id.btn_enviarComentario);
        btn_limparComentario = (Button)findViewById(R.id.btn_limparComentario);
        edt_comentario = (EditText)findViewById(R.id.editTextComentario);
        videoCastManager = VideoCastManager.getInstance();


        btn_enviarComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comentario = edt_comentario.getText().toString();
                Intent intent = new Intent(ComentarioActivity.this,MoverImagemActivity.class);
                intent.putExtra("tipo","comentario");
                intent.putExtra("texto",comentario);
                startActivity(intent);
                finish();

            }
        });

        btn_limparComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_comentario.setText("");
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        videoCastManager.addMediaRouterButton(menu,R.id.media_route_menu_item);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoCastManager = VideoCastManager.getInstance();
        videoCastManager.incrementUiCounter();
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoCastManager.decrementUiCounter();
    }

}
