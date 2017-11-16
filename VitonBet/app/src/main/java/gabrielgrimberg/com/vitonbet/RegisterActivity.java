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

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity
{
    private EditText xNameField;
    private EditText xEmailField;
    private EditText xPasswordField;

    private Button xRegisterBtn;

    private FirebaseAuth xAuth;
    private DatabaseReference xDatabase;

    private ProgressDialog xProgress;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        xAuth = FirebaseAuth.getInstance();

        xDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        xProgress = new ProgressDialog(this);

        xNameField = (EditText) findViewById(R.id.nameField);
        xEmailField = (EditText) findViewById(R.id.emailField);
        xPasswordField = (EditText) findViewById(R.id.passwordField);
        xRegisterBtn = (Button) findViewById(R.id.registerBtn);

        xRegisterBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startRegister();

            }
        });
    }

    private void startRegister()
    {
        final String name = xNameField.getText().toString().trim();
        String email = xEmailField.getText().toString().trim();
        String password = xPasswordField.getText().toString().trim();

        //Check if user is submitting a field empty.
        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password))
        {
            xProgress.setMessage("Signing Up...");
            xProgress.show();

            xAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                   if(task.isSuccessful())
                   {
                       String user_id = xAuth.getCurrentUser().getUid();

                       DatabaseReference current_user_db = xDatabase.child(user_id);

                       current_user_db.child("name").setValue(name);

                       xProgress.dismiss();

                       Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                       mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                       startActivity(mainIntent);
                   }
                }
            });
        }
    }
}
