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

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private Button btnSubmit;
    private EditText emailField;
    private EditText passField;
    private TextView signInLink;

    //private ProgressDialog progressDialog;

    //Firebase Auth Object
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        //Track if user is logged in or not
        if(firebaseAuth.getCurrentUser() != null)
        {
            //Profile activity.
            finish(); //Before starting another activity finish another activity.
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }

        //progressDialog = new ProgressDialog(this);

        btnSubmit = (Button) findViewById(R.id.btnRegister);
        emailField = (EditText) findViewById(R.id.emailEntry);
        passField = (EditText) findViewById(R.id.passwordEntry);
        signInLink = (TextView) findViewById(R.id.signIn);

        btnSubmit.setOnClickListener(this);
        signInLink.setOnClickListener(this);
    }

    private void UserRegister()
    {
        String email = emailField.getText().toString().trim();
        String password = passField.getText().toString().trim();

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
        //progressDialog.setMessage("Registering, Please Wait...");
        //progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful())
                        {
                            //Successfully registered and logged in.
                            //Go to main screen activity.
                            finish(); //Before starting another activity finish another activity.
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Failed to Register.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    @Override
    public void onClick(View view)
    {
        if(view == btnSubmit)
        {
            UserRegister();
        }

        if(view == signInLink)
        {
            //Open Login Activity.
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
