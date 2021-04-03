package wahyuapps.ngimel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private SharedPreferences mPref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mPref = getSharedPreferences(MainActivity.TAG_NGIMEL, Context.MODE_PRIVATE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Boolean saved;
                saved = mPref.getBoolean(MainActivity.TAG_LAUNCH_MAIN, false);
                if (saved == true) {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Intent i2 = new Intent(SplashActivity.this, BiodataActivity.class);
                    startActivity(i2);
                    finish();
                }
            }
        }, 1000);
    }
}
