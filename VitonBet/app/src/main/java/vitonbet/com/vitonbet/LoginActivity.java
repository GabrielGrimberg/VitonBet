package vitonbet.com.vitonbet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity
{
    private Button btnSignIn;
    private EditText emailInput;
    private EditText passwordInput;
    private TextView tvSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = (EditText) findViewById(R.id.emailEntry);
        passwordInput = (EditText) findViewById(R.id.passwordEntry);
        btnSignIn = (Button) findViewById(R.id.btnLogin);
        tvSignUp = (TextView) findViewById(R.id.signUp);

    }
}
