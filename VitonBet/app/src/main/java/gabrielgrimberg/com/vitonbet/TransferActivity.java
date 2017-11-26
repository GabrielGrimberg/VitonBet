package gabrielgrimberg.com.vitonbet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class TransferActivity extends AppCompatActivity
{
    private EditText xEmailField;
    private EditText xCashAmount;

    //Button to register.
    private Button xSendCash;

    //Top nav vars.
    private DrawerLayout xDrawerLayout;
    private ActionBarDrawerToggle xToggle;
    private FirebaseAuth xAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

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

                    case R.id.nav_sendcash:
                        Intent sendActivity = new Intent(getApplicationContext(), TransferActivity.class);
                        sendActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(sendActivity);

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


        xSendCash = (Button) findViewById(R.id.sendBtn);

        //When the button is clicked go to register method.
        xSendCash.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                sendingCash();
            }
        });
    }

    private void sendingCash()
    {
        xEmailField = (EditText) findViewById(R.id.inputemailf);
        xCashAmount = (EditText) findViewById(R.id.inputamountf);
        Helper.transferCash(xEmailField.getText().toString(), Integer.parseInt(xCashAmount.getText().toString()));

        Toast.makeText(TransferActivity.this,
                "Your funds have been transferred only if the email matches.",
                Toast.LENGTH_LONG).show();
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
