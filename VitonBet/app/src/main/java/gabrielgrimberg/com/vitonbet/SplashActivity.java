/*

Name: HomeActivity

Description: Activity for the loading screen, also known as the splash screen.

*/

package gabrielgrimberg.com.vitonbet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity
{
    //Splash screen timer.
    private static int SPLASH_TIME_OUT = 3000;

    private ImageView ivLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Animation for the starting logo.
        ivLogo = (ImageView) findViewById(R.id.loadingVB);
        Animation logoAnim = AnimationUtils.loadAnimation(this,R.anim.loading_logo_anim);
        ivLogo.startAnimation(logoAnim);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Intent homeIntent = new Intent(SplashActivity.this, ConfirmationActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                finish();

            }
        },SPLASH_TIME_OUT);
    }
}