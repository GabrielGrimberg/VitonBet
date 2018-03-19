/***
 * Name: RegisterActivity
 *
 * Description: - Activity for a user to Register.
 *              - Register with: Username, Email, Password, Date of Birth and Phone Number.
 *              - UI Enhanced.
 *              - Error Checking - Password must match and Email must be valid.
 *              - DOB Field improved.
 **/

package gabrielgrimberg.com.vitonbet;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    // Register fields.
    private EditText xNameField;
    private EditText xEmailField;
    private EditText xPasswordField;
    private EditText xRePasswordField;
    private EditText xDOBField;
    private EditText xPhoneField;

    // Button to register.
    private Button xRegisterBtn;

    // Firebase connection vars.
    private FirebaseAuth xAuth;
    private DatabaseReference xDatabase;

    // Progress bar.
    private ProgressDialog xProgress;

    // The calendar box to pick your DOB.
    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        xAuth = FirebaseAuth.getInstance(); //Get instance of current user.

        // Adding to db when user registers.
        xDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        xProgress = new ProgressDialog(this);

        xNameField = (EditText) findViewById(R.id.nameField);
        xEmailField = (EditText) findViewById(R.id.emailField);
        xPasswordField = (EditText) findViewById(R.id.passwordField);
        xRePasswordField = (EditText) findViewById(R.id.repasswordField);
        xDOBField = (EditText) findViewById(R.id.dobField);
        xPhoneField = (EditText) findViewById(R.id.phoneField);

        xRegisterBtn = (Button) findViewById(R.id.registerBtn);

        // When the button is clicked go to register method.
        xRegisterBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                startRegister();
            }
        });

        // When the DOB field is click show the calendar.
        xDOBField.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                datePickerDialog.show();
            }
        });

        // Set up Calendar view for the datePickerDialog.
        Calendar calendar = Calendar.getInstance();

        // Make the date view pop up.
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                // Select the date and dismiss the date picker dialog.
                xDOBField.setText(dayOfMonth+"/"+monthOfYear+"/"+year);
                datePickerDialog.dismiss();
            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
    }

    // Method to validate registration and check if input is correct and suitable.
    private void startRegister() {

        final String name = xNameField.getText().toString().trim();
        final String dob = xDOBField.getText().toString().trim();
        final String phone = xPhoneField.getText().toString().trim();

        final String email = xEmailField.getText().toString().trim();
        String password = xPasswordField.getText().toString().trim();
        String repassword = xRePasswordField.getText().toString().trim();

        // Check if user is submitting a field empty.
        if(!TextUtils.isEmpty(name) &&
                !TextUtils.isEmpty(email) &&
                !TextUtils.isEmpty(password) &&
                !TextUtils.isEmpty(repassword) &&
                !TextUtils.isEmpty(dob) &&
                !TextUtils.isEmpty(phone) &&
                password.equals(repassword) &&
                isEmailValid(email)) {

            xProgress.setMessage("Signing Up...");
            xProgress.show();

            // If input is good and nothing wrong then add it to th database and show up the home page.
            xAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                   if(task.isSuccessful()) {

                       String user_id = xAuth.getCurrentUser().getUid();

                       DatabaseReference current_user_db = xDatabase.child(user_id);

                       current_user_db.child("username").setValue(name);
                       current_user_db.child("email").setValue(email);
                       current_user_db.child("dob").setValue(dob);
                       current_user_db.child("phone").setValue(phone);
                       current_user_db.child("balance").setValue(1000);

                       xProgress.dismiss();

                       Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                       mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                       startActivity(mainIntent);
                   }
                }
            });

        } else { //If an input was wrong display an error message.

            Toast.makeText(RegisterActivity.this,
                    "Please make sure that all fields are filled out and that the password matches when you re-entered it. " +
                            "The email must also be valid and number must have 10 digits.",
                    Toast.LENGTH_LONG).show();
        }
    }

    /* Reference from : https://stackoverflow.com/questions/6119722/how-to-check-edittexts-text-is-email-address-or-not */
    // Method to check if email is legit
    public static boolean isEmailValid(String email) {

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
    /* End of Reference */
}
