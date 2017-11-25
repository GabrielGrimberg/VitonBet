/*
Name: CasinoActivity

Description: - Casino Mode.
             - Spinning the wheel to win or lose.
             - If lands on green: 4x amount
             - If you pick red or green and it lands on that then x2 amount.
             - Else lose.

Last updated: 22nd of November.
 */
package gabrielgrimberg.com.vitonbet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Random;

public class CasinoActivity extends AppCompatActivity
{
    public int[] numberMapping = {0, 29, 16, 1, 20, 20, 15, 4, 27, 28, 5, 14, 21, 2,
                                    17, 8, 23, 23, 10, 19, 26, 11, 18, 7, 22, 13, 32,
                                    25, 12, 9, 9, 30, 15, 24, 3, 6};

    public enum COLOR {RED, BLACK, GREEN};
    public COLOR color = COLOR.GREEN;
    Button btn; //Button to spin.
    TextView tvOutcome; //Displays the results.
    ImageView ivWheel; //Image for the wheel.
    boolean spinning = false;

    Random random; //For RNG.

    int degree = 0;
    int oldDegree = 0;
    public int betAmount = 0;

    //Top nav vars.
    private DrawerLayout xDrawerLayout;
    private ActionBarDrawerToggle xToggle;
    private FirebaseAuth xAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_casino);

        xAuth = FirebaseAuth.getInstance();

        /* Top nav. */
        xDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        xToggle = new ActionBarDrawerToggle(this, xDrawerLayout, R.string.open, R.string.close);

        xDrawerLayout.addDrawerListener(xToggle);
        xToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView xNavigationView = (NavigationView) findViewById(R.id.top_nav_id);

        /* Activity Change when Item from Top Navigator is Clicked */
        xNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem)
            {
                switch (menuItem.getItemId())
                {
                    case R.id.nav_account:
                        Intent accountActivity = new Intent(getApplicationContext(), AccountActivity.class);
                        accountActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(accountActivity);

                        return true;

                    case R.id.nav_home:
                        Intent homeActivity = new Intent(getApplicationContext(), MainActivity.class);
                        homeActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(homeActivity);

                        return true;

                    case R.id.nav_events:
                        Intent eventsActivity = new Intent(getApplicationContext(), EventsActivity.class);
                        eventsActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(eventsActivity);

                        return true;

                    case R.id.nav_casino:
                        Intent casinoActivity = new Intent(getApplicationContext(), CasinoActivity.class);
                        casinoActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(casinoActivity);

                        return true;

                    case R.id.nav_ad:
                        Intent adActivity = new Intent(getApplicationContext(), AdActivity.class);
                        adActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(adActivity);

                        return true;

                    case R.id.nav_logout:
                        xAuth.signOut();

                        return true;
                }
                return true;
            }
        });


        Helper.SetBalance(this);

        btn = (Button) findViewById(R.id.spinWheel);
        tvOutcome = (TextView) findViewById(R.id.tvScore);
        ivWheel = (ImageView) findViewById(R.id.imageView);

        random = new Random();
        final CasinoActivity mine = this;

        //When button is clicked.
        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (spinning)
                {
                    return;
                }
                TextView tv = findViewById(R.id.betAmount);
                String amount = tv.getText().toString();
                mine.betAmount = Integer.parseInt(amount);
                if (mine.betAmount > Integer.parseInt(((TextView)findViewById(R.id.balance)).getText().toString().replace("€", "").replace("Balance: ", ""))) {
                    Toast.makeText(CasinoActivity.this, "You don't have enough balance.",
                            Toast.LENGTH_LONG).show();

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
                        if (numberMapping[(degree-360) / 10] % 2 == 0)
                        {
                            if (mine.color == COLOR.RED)
                            {
                                //pay 2x
                                t.setText("WON €" + Integer.toString(mine.betAmount*2));
                                Helper.AddBalance(mine, mine.betAmount*2);
                            }
                            else
                            {
                                t.setText("LOST €" + Integer.toString(mine.betAmount));
                                Helper.AddBalance(mine, -mine.betAmount);
                            }
                        }

                        if (numberMapping[(degree-360) / 10] == 0)
                        {
                            if (mine.color == COLOR.GREEN)
                            {
                                t.setText("WON €" + Integer.toString(mine.betAmount*8));
                                Helper.AddBalance(mine, mine.betAmount*8);
                            }
                            else
                            {
                                t.setText("LOST €" + Integer.toString(mine.betAmount));
                                Helper.AddBalance(mine, -mine.betAmount);
                            }
                        }

                        if (numberMapping[(degree-360) / 10] % 2 == 1)
                        {
                            if (mine.color == COLOR.BLACK)
                            {
                                t.setText("WON €" + Integer.toString(mine.betAmount*2));
                                Helper.AddBalance(mine, mine.betAmount*2);
                            }
                            else
                            {
                                t.setText("LOST €" + Integer.toString(mine.betAmount));
                                Helper.AddBalance(mine, -mine.betAmount);
                            }
                        }

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation)
                    {

                    }
                });

                ivWheel.startAnimation(rotateAnimation);


            }
        });


        final ToggleButton green = findViewById(R.id.green);
        final ToggleButton black = findViewById(R.id.black);
        final ToggleButton red = findViewById(R.id.red);

        green.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    black.setChecked(false);
                    red.setChecked(false);
                    mine.color = COLOR.GREEN;

                    ImageView iv = findViewById(R.id.arrow);
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) iv.getLayoutParams();
                    params.addRule(RelativeLayout.BELOW, R.id.green);
                    params.addRule(RelativeLayout.ALIGN_START, R.id.green);
                    iv.setLayoutParams(params);
                }

            }
        });
        black.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    green.setChecked(false);
                    red.setChecked(false);
                    mine.color = COLOR.BLACK;

                    ImageView iv = findViewById(R.id.arrow);
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) iv.getLayoutParams();
                    params.addRule(RelativeLayout.BELOW, R.id.black);
                    params.addRule(RelativeLayout.ALIGN_START, R.id.black);
                    iv.setLayoutParams(params);
                }

            }
        });
        red.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                {
                    green.setChecked(false);
                    black.setChecked(false);
                    mine.color = COLOR.RED;

                    ImageView iv = findViewById(R.id.arrow);
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) iv.getLayoutParams();
                    params.addRule(RelativeLayout.BELOW, R.id.red);
                    params.addRule(RelativeLayout.ALIGN_START, R.id.red);
                    iv.setLayoutParams(params);
                }

            }
        });


        green.setSelected(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(xToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
