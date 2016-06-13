package activitys;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.admin.casttv.R;
import com.google.android.libraries.cast.companionlibrary.widgets.IntroductoryOverlay;

public class MainActivity extends AppCompatActivity {


    private static String TAG = "mensagemParaOSender";
    private boolean mIsHoneyCombOrAbove = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    private IntroductoryOverlay mOverlay;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
}
