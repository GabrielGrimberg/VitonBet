package gabrielgrimberg.com.vitonbet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    public int[] numberMapping = {0, 29, 16, 1, 20, 20, 15, 4, 27, 28, 5, 14, 21, 2,
                                    17, 8, 23, 23, 10, 19, 26, 11, 18, 7, 22, 13, 32,
                                    25, 12, 9, 9, 30, 15, 24, 3, 6};

    Button btn; //Button to spin.
    TextView tvOutcome; //Displays the results.
    ImageView ivWheel; //Image for the wheel.
    boolean spinning = false;

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
                if (spinning) {
                    return;
                }
                oldDegree = degree % 360; //Wheel is 360 degrees.

                degree = (int)Math.round((random.nextInt(360) + 360)/10.0) * 10;

                RotateAnimation rotateAnimation = new RotateAnimation(oldDegree, degree,
                        RotateAnimation.RELATIVE_TO_SELF,
                        0.5f,
                        RotateAnimation.RELATIVE_TO_SELF,
                        0.5f);

                rotateAnimation.setDuration(random.nextInt(1600) + 2000);
                rotateAnimation.setFillAfter(true);
                rotateAnimation.setInterpolator(new DecelerateInterpolator());
                rotateAnimation.setAnimationListener(new Animation.AnimationListener()
                {
                    @Override
                    public void onAnimationStart(Animation animation)
                    {
                        spinning = true;
                    }

                    @Override
                    public void onAnimationEnd(Animation animation)
                    {
                        spinning = false;
                        TextView t = (TextView) findViewById(R.id.tvScore);
                        t.setText(Integer.toString(numberMapping[(degree-360) / 10]));
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation)
                    {

                    }
                });

                ivWheel.startAnimation(rotateAnimation);


            }
        });

    }
}
