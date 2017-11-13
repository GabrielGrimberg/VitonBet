package vitonbet.com.vitonbet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private Button btnSubmit;
    private EditText emailField;
    private EditText passField;
    private TextView signInLink;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSubmit = (Button) findViewById(R.id.btnRegister);

        emailField = (EditText) findViewById(R.id.emailEntry);
        passField = (EditText) findViewById(R.id.passwordEntry);

        signInLink = (TextView) findViewById(R.id.signIn);

        btnSubmit.setOnClickListener(this);
        signInLink.setOnClickListener(this);
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
            //TODO
            //Open Login Activity.
        }
    }
}
