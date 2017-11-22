package gabrielgrimberg.com.vitonbet;

/**
 * Created by Ecoste on 11/22/2017.
 */

import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Helper {
    public static void SetBalance(final AppCompatActivity a) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            DatabaseReference db = FirebaseDatabase.getInstance().getReference()
                    .child("Users").child(user.getUid().toString()).child("balance");


            db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    TextView bal = (TextView)a.findViewById(R.id.balance);
                    bal.setText("Balance: " + Integer.toString((int)(long)dataSnapshot.getValue()));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // ...
                }
            });
        }
    }
}
