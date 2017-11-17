/*
Application Name:   VitonBet
Application Status: In Development for Stable Release.
Version:            v0.1

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

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity
{
    private FirebaseAuth xAuth;
    private FirebaseAuth.AuthStateListener xAuthListner;

    private Button xLogoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
