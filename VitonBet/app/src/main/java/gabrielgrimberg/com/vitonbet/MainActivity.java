/*
Application Name:   VitonBet
Application Status: In Development for Stable Release.
Version:            v0.3

Name: MainActivity

Description: - The heart of the app.
             - Main Activity to display events.
             - If users is not logged in activity loads up with the Login Activity.

TODO 2. List Events (Where user can click and it will load another activity to place bet).
TODO 3. UI Improvements.

Last updated: 20th of November.
 */

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
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity
{
    private FirebaseAuth xAuth;
    private FirebaseAuth.AuthStateListener xAuthListner;

    private Button xLogoutBtn; //Logout button.
    private Button xCasinoDemo; //Temp Button to Enter Casino.
    private Toolbar xToolbar;

    //Top nav vars.
    private DrawerLayout xDrawerLayout;
    private ActionBarDrawerToggle xToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        xLogoutBtn = (Button) findViewById(R.id.signoutField);
        xCasinoDemo = (Button) findViewById(R.id.enterCasinoDemo);

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
                    case(R.id.nav_account):
                        Intent accountActivity = new Intent(getApplicationContext(), AccountActivity.class);
                        startActivity(accountActivity);

                    case(R.id.nav_home):
                        Intent homeActivity = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(homeActivity);

                    case(R.id.nav_events):
                        Intent eventsActivity = new Intent(getApplicationContext(), EventsActivity.class);
                        startActivity(eventsActivity);

                    case(R.id.nav_casino):
                        Intent casinoActivity = new Intent(getApplicationContext(), CasinoActivity.class);
                        startActivity(casinoActivity);

                    case(R.id.nav_cashout):
                        Intent cashoutActivity = new Intent(getApplicationContext(), CashoutActivity.class);
                        startActivity(cashoutActivity);

                    case(R.id.nav_logout):
                        logout();

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
