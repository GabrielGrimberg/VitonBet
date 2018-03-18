/***
 * Name: AccountActivity
 * Description: Activity where the user their details.
 * Note: Details displayed: Name, DOB, Email and Balance.
 */

package gabrielgrimberg.com.vitonbet;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class AccountActivity extends AppCompatActivity {

    // Top nav vars.
    private DrawerLayout xDrawerLayout;
    private ActionBarDrawerToggle xToggle;
    private FirebaseAuth xAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // Access the DB to display correct information for a specific user.
        Helper.SetBalance(this);
        Helper.SetUsername(this);
        Helper.SetDOB(this);
        Helper.SetEmail(this);

        // Check if user is logged in.
        xAuth = FirebaseAuth.getInstance();

        /* Top nav. */
        xDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        xToggle = new ActionBarDrawerToggle(this, xDrawerLayout, R.string.open, R.string.close);

        xDrawerLayout.addDrawerListener(xToggle);
        xToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView xNavigationView = (NavigationView) findViewById(R.id.top_nav_id);

        /* Activity Change when Item from Top Navigator is Clicked */
        xNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId()) {

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
    }

    // Method that allows the navigator to open.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(xToggle.onOptionsItemSelected(item)) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
