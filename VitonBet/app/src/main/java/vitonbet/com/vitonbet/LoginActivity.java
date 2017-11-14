package vitonbet.com.vitonbet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{
    private Button btnSignIn;
    private EditText emailInput;
    private EditText passwordInput;
    private TextView tvSignUp;

    private FirebaseAuth firebaseAuth;

    //private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        //Track if user is logged in or not
        if(firebaseAuth.getCurrentUser() != null)
        {
            //Profile activity.
            finish(); //Before starting another activity finish another activity.
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }

        //progressDialog = new ProgressDialog(this);

        emailInput = (EditText) findViewById(R.id.emailEntry);
        passwordInput = (EditText) findViewById(R.id.passwordEntry);
        btnSignIn = (Button) findViewById(R.id.btnLogin);
        tvSignUp = (TextView) findViewById(R.id.signUp);

        btnSignIn.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);

    }

    private void UserLogin()
    {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if(TextUtils.isEmpty(email))
        {
            //Email is empty.
            Toast.makeText(this, "Enter in a valid email.", Toast.LENGTH_SHORT).show();

            //Stop exe further
            return;
        }

        if(TextUtils.isEmpty(password))
        {
            //Password is empty.
            Toast.makeText(this, "Enter in a valid password.", Toast.LENGTH_SHORT).show();

            //Stop exe further
            return;
        }

        //TODO
        //If all is okay, continue on to main screen. (Change activity.)
        //progressDialog.setMessage("Logging In, Please Wait...");
        //progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        //progressDialog.dismiss();

                        if(task.isSuccessful())
                        {
                            //Start Profile activity.
                            finish(); //Before starting another activity finish another activity.
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        }
                    }
                });


    }

    @Override
    public void onClick(View view)
    {
        if(view == btnSignIn)
        {
            UserLogin();
        }

        if(view == tvSignUp)
        {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }

    }
}
