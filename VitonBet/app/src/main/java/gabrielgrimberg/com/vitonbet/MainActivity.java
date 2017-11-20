/*
Application Name:   VitonBet
Application Status: In Development for Stable Release.
Version:            v0.2

Name: MainActivity

Description: - The heart of the app.
             - Main Activity to display events.
             - If users is not logged in activity loads up with the Login Activity.

TODO 2. List Events (Where user can click and it will load another activity to place bet).
TODO 3. UI Improvements.

Last updated: 18th of November.
 */

package gabrielgrimberg.com.vitonbet;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.Toolbar;

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

    //Bottom Navigator. (Frags).
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener()
    {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            switch (item.getItemId())
            {
                case R.id.navigation_home:

                    //Frag code.
                    setTitle("HomeMenu");
                    HomeMenu fragmentMenu = new HomeMenu();
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content, fragmentMenu, "FragmentName");
                    fragmentTransaction.commit();

                    return true;

                case R.id.navigation_events:

                    //Frag code.
                    setTitle("EventsMenu");
                    EventsMenu fragmenteSports = new EventsMenu();
                    FragmentTransaction fragmentTransactionSports = getFragmentManager().beginTransaction();
                    fragmentTransactionSports.replace(R.id.content, fragmenteSports, "FragmentName");
                    fragmentTransactionSports.commit();

                    return true;

                case R.id.navigation_casino:

                    //Frag code.
                    setTitle("CasinoMenu");
                    CasinoMenu fragmentCasino = new CasinoMenu();
                    FragmentTransaction fragmentTransactionCasino = getFragmentManager().beginTransaction();
                    fragmentTransactionCasino.replace(R.id.content, fragmentCasino, "FragmentName");
                    fragmentTransactionCasino.commit();

                    return true;

                case R.id.navigation_cashout:

                    //Frag code.
                    setTitle("CashoutMenu");
                    CashoutMenu fragmentCashout = new CashoutMenu();
                    FragmentTransaction fragmentTransactionCashout = getFragmentManager().beginTransaction();
                    fragmentTransactionCashout.replace(R.id.content, fragmentCashout, "FragmentName");
                    fragmentTransactionCashout.commit();

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Show the HomeMenu at first when logging in.
        setTitle("HomeMenu");
        HomeMenu fragmentMenu = new HomeMenu();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, fragmentMenu, "FragmentName");
        fragmentTransaction.commit();

        xLogoutBtn = (Button) findViewById(R.id.signoutField);
        xCasinoDemo = (Button) findViewById(R.id.enterCasinoDemo);

        xAuth = FirebaseAuth.getInstance();

        /* Top nav. */
        xDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        xToggle = new ActionBarDrawerToggle(this, xDrawerLayout, R.string.open, R.string.close);

        xDrawerLayout.addDrawerListener(xToggle);
        xToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        //When the logout button is clicked. For testing at the moment.
        xLogoutBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                logout();

            }
        });

        //When the casino button is clicked. For testing at the moment.
        xCasinoDemo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent casinoIntent = new Intent(MainActivity.this, CasinoActivity.class);
                startActivity(casinoIntent);

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
