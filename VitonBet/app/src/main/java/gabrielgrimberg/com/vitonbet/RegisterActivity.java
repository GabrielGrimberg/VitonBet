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
    private EditText xRePasswordField;
    private EditText xDOBField;
    private EditText xPhoneField;

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
        xRePasswordField = (EditText) findViewById(R.id.repasswordField);
        xDOBField = (EditText) findViewById(R.id.dobField);
        xPhoneField = (EditText) findViewById(R.id.phoneField);

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
        final String dob = xDOBField.getText().toString().trim();
        final String phone = xPhoneField.getText().toString().trim();

        final String email = xEmailField.getText().toString().trim();
        String password = xPasswordField.getText().toString().trim();
        String repassword = xRePasswordField.getText().toString().trim();

        //Check if user is submitting a field empty.
        if(!TextUtils.isEmpty(name) &&
                !TextUtils.isEmpty(email) &&
                !TextUtils.isEmpty(password) &&
                !TextUtils.isEmpty(repassword) &&
                !TextUtils.isEmpty(dob) &&
                !TextUtils.isEmpty(phone))

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

                       current_user_db.child("username").setValue(name);
                       current_user_db.child("email").setValue(email);
                       current_user_db.child("dob").setValue(dob);
                       current_user_db.child("phone").setValue(phone);


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
