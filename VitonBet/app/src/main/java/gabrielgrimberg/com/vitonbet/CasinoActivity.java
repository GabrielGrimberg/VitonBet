package gabrielgrimberg.com.vitonbet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class CasinoActivity extends AppCompatActivity
{
    Button btn; //Button to spin.
    TextView tvOutcome; //Displays the results.
    ImageView ivWheel; //Image for the wheel.

    Random random; //For RNG.

    int degree = 0;
    int oldDegree = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_casino);

        btn = (Button) findViewById(R.id.spinWheel);
        tvOutcome = (TextView) findViewById(R.id.tvScore);
        ivWheel = (ImageView) findViewById(R.id.imageView);

        random = new Random();

        //When button is clicked.
        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                oldDegree = degree % 360; //Wheel is 360 degrees.

                degree = random.nextInt(3600) + 720;

                RotateAnimation rotateAnimation = new RotateAnimation(oldDegree, degree,
                        RotateAnimation.RELATIVE_TO_SELF,
                        0.5f,
                        RotateAnimation.RELATIVE_TO_SELF,
                        0.5f);

                rotateAnimation.setDuration(3600);
                rotateAnimation.setFillAfter(true);
                rotateAnimation.setInterpolator(new DecelerateInterpolator());
                rotateAnimation.setAnimationListener(new Animation.AnimationListener()
                {
                    @Override
                    public void onAnimationStart(Animation animation)
                    {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation)
                    {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation)
                    {

                    }
                });


            }
        });

    }
}
