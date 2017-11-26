/***************************************************************************************************************
Application Name:   VitonBet
Application Status: Stable Assignment Release
Version:            v1.0

About:  - VitonBet is a betting application for Android phones with version 7.1.1.
        - Users can create events and bet on other events.
        - Users can go into the casino and test their luck with the roulette wheel.
        - If the user runs out of funds, watch ads to gain some funds.
        - Does not use real money.
        - Users can view their details in the home page.
        - Transferring cash to other users.
        - Age verification for VitonBet.
        - The basics of logging in and registering for the application with error handling.
        - Firebase used over SQLite.
        - Updates, Deletes, Selects and Inserts are all performed in VitonBet.
        - VitonBet meets the standard set out by the lecturer for this assignment with bonus features.
        - Refer to the Design Document to view the standards.
        - VitonBet followed the Design Plan set out by the group with a few changes.

Name: MainActivity

Description: - The heart of the app.
             - Main Activity to display events.
             - If users is not logged in activity loads up with the Login Activity.
 ***************************************************************************************************************/

package gabrielgrimberg.com.vitonbet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity
{
    private FirebaseAuth xAuth;
    private FirebaseAuth.AuthStateListener xAuthListner;

    private Toolbar xToolbar;

    //ImageViews
    private ImageView ivCasino;
    private ImageView ivEvents;
    private ImageView ivEarnCash;

    //Top nav vars.
    private DrawerLayout xDrawerLayout;
    private ActionBarDrawerToggle xToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        xAuth = FirebaseAuth.getInstance();

        ivCasino = (ImageView) findViewById(R.id.ivcasino);
        ivEvents = (ImageView) findViewById(R.id.ivsports);
        ivEarnCash = (ImageView) findViewById(R.id.ivcash);

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
                        logout();

                        return true;
                }
                return true;
            }
        });

        /* Top Nav Overlay */
        //xToolbar = (Toolbar) findViewById(R.id.topnav_overlay);
        //setSupportActionBar(xToolbar);

        //Checking if user has logged in.
        xAuthListner = new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                if(firebaseAuth.getCurrentUser() == null)

                {
                    Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);

                }
            }
        };

        ivCasino.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view)
            {
                Intent xcasinoActivity = new Intent(MainActivity.this, CasinoActivity.class);
                xcasinoActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(xcasinoActivity);
            }

        });

        ivEvents.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view)
            {
                Intent xeventsActivity = new Intent(MainActivity.this, EventsActivity.class);
                xeventsActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(xeventsActivity);
            }

        });

        ivEarnCash.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view)
            {
                Intent xadActivity = new Intent(MainActivity.this, AdActivity.class);
                xadActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(xadActivity);
            }

        });

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

    @Override
    protected void onStart()
    {
        super.onStart();

        xAuth.addAuthStateListener(xAuthListner);
    }

    //Logging out.
    private void logout()
    {
        xAuth.signOut();
    }
}