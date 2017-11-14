package vitonbet.com.vitonbet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener
{
    private FirebaseAuth firebaseAuth;

    private TextView tvEmail;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        //If not logged in.
        if(firebaseAuth.getCurrentUser() == null)
        {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        tvEmail = (TextView) findViewById(R.id.tvUserEmail);
        btnLogout = (Button) findViewById(R.id.logOutBtn);

        tvEmail.setText("Welcome " + user.getEmail());
        btnLogout.setOnClickListener(this);
    }


    @Override
    public void onClick(View view)
    {
        if(view == btnLogout)
        {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
