/***
 * Name: LoginActivity
 * Description: - Activity for a user to Login.
 *              - Login with Email and Password.
 *              - UI Enhanced.
 *
 **/

package gabrielgrimberg.com.vitonbet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText xLoginEmailField;
    private EditText xLoginPasswordField;

    private Button xLoginBtn;
    private Button xRegisterBtn;

    // Access the login.
    private FirebaseAuth  xAuth;

    private ProgressDialog xProgress;

    private DatabaseReference xDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        xAuth = FirebaseAuth.getInstance();

        // Check inside the Users table.
        xDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        xProgress = new ProgressDialog(this);

        xLoginEmailField = (EditText) findViewById(R.id.emailLoginField);
        xLoginPasswordField = (EditText) findViewById(R.id.loginPWField);

        xLoginBtn = (Button) findViewById(R.id.loginBtn);
        xRegisterBtn = (Button) findViewById(R.id.registerLBtn);

        xRegisterBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent loginRIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                loginRIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(loginRIntent);

            }
        });

        xLoginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                checkLogin();

            }
        });
    }

    private void checkLogin() {

        String email = xLoginEmailField.getText().toString().trim();
        String password = xLoginPasswordField.getText().toString().trim();

        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

            xProgress.setMessage("Logging In...");
            xProgress.show();

            xAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()) {

                        checkUserExist();

                    } else {

                        xProgress.dismiss();

                        Toast.makeText(LoginActivity.this,
                                "Either Password or Email is wrong.",
                                Toast.LENGTH_LONG).show();

                    }

                }
            });

        } else {

            // This is the error checking if you leave some fields blank.
            xProgress.dismiss();

            Toast.makeText(LoginActivity.this,
                    "Why did you leave some fields blank?",
                    Toast.LENGTH_LONG).show();

        }
    }

    // Check if user is in Database.
    private void checkUserExist() {

        final String user_id = xAuth.getCurrentUser().getUid();

        // Check if the user exists in the database or not.
        xDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // If it has the user_id.
                if(dataSnapshot.hasChild(user_id)) {

                    xProgress.dismiss();

                    Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(mainIntent);

                } else {

                    // This is the error checking if the user doesn't have an account.
                    xProgress.dismiss();

                    Toast.makeText(LoginActivity.this,
                            "Not in Database, please register.",
                            Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

    }
}