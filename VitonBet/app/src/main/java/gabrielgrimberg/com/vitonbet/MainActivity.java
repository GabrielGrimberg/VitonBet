/*
Application Name:   VitonBet
Application Status: In Development for Stable Release.
Version:            v0.2

Name: MainActivity

Description: - The heart of the app.
             - Main Activity to display events.
             - If users is not logged in activity loads up with the Login Activity.

TODO 1. Navigator (Bottom Navigator)
TODO 2. List Events (Where user can click and it will load another activity to place bet).
TODO 3. UI Improvements.

Last updated: 17th of November.
 */

package gabrielgrimberg.com.vitonbet;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity
{
    private FirebaseAuth xAuth;
    private FirebaseAuth.AuthStateListener xAuthListner;

    private Button xLogoutBtn;


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
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content, fragmentMenu, "FragmentName");
                    fragmentTransaction.commit();

                    return true;
                case R.id.navigation_dashboard:

                    //Frag code.
                    setTitle("EventsMenu");
                    EventsMenu fragmenteSports = new EventsMenu();
                    FragmentTransaction fragmentTransactionSports = getSupportFragmentManager().beginTransaction();
                    fragmentTransactionSports.replace(R.id.content, fragmenteSports, "FragmentName");
                    fragmentTransactionSports.commit();

                    return true;

                case R.id.navigation_notifications:

                    //Frag code.
                    setTitle("CasinoMenu");
                    CasinoMenu fragmentCasino = new CasinoMenu();
                    FragmentTransaction fragmentTransactionCasino = getSupportFragmentManager().beginTransaction();
                    fragmentTransactionCasino.replace(R.id.content, fragmentCasino, "FragmentName");
                    fragmentTransactionCasino.commit();

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

        xLogoutBtn = (Button) findViewById(R.id.signoutField);

        xAuth = FirebaseAuth.getInstance();

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

        xLogoutBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                logout();

            }
        });
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
