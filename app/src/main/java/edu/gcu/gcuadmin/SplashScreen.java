package edu.gcu.gcuadmin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import edu.gcu.gcuadmin.login.LoginActivity;

public class SplashScreen extends AppCompatActivity {

    Animation animAlpha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        final ImageView iv = (ImageView) findViewById(R.id.splash_logo);
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.raw
                .gcu_logo);

        iv.setImageBitmap(bmp);

        final TextView tv = (TextView) findViewById(R.id.splash_text);
        animAlpha = new AlphaAnimation(0f, 1f);
        animAlpha.setFillAfter(true);
        animAlpha.setDuration(1500);
        animAlpha.setRepeatMode(Animation.INFINITE);
        iv.startAnimation(animAlpha);
        tv.startAnimation(animAlpha);
        iv.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
                finish();
            }
        }, 2000);

    }
}
