/***
 * Name: TransferActivity
 * Description: Activity that allows users to transfer funds to each other.
 **/

package gabrielgrimberg.com.vitonbet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class TransferActivity extends AppCompatActivity {

    private EditText xEmailField;
    private EditText xCashAmount;

    // Button to register.
    private Button xSendCash;
    private Button xReturnBtn;

    // Top nav vars.
    private DrawerLayout xDrawerLayout;
    private ActionBarDrawerToggle xToggle;
    private FirebaseAuth xAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        xSendCash = (Button) findViewById(R.id.sendBtn);
        xReturnBtn = (Button) findViewById(R.id.sendBtn2);

        // When the button is clicked go to register method.
        xSendCash.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                sendingCash();
            }
        });

        xReturnBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent homeActivity = new Intent(getApplicationContext(), MainActivity.class);
                homeActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeActivity);
            }
        });
    }

    // Method that sends the funds over.
    private void sendingCash() {

        xEmailField = (EditText) findViewById(R.id.inputemailf);
        xCashAmount = (EditText) findViewById(R.id.inputamountf);

        // Calling the transferCash method from the Helper class.
        // The Helper class as stated is the Database control class.
        Helper.transferCash(xEmailField.getText().toString(), Integer.parseInt(xCashAmount.getText().toString()));

        Toast.makeText(TransferActivity.this,
                "If the email matches, the funds will be transferred to that account.",
                Toast.LENGTH_LONG).show();
    }

}