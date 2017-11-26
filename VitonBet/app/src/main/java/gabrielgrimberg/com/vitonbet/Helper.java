/*
Name: Helper

Description: - Piece of Code to Set the Balance for Current User.

Last updated: 22nd of November.
 */

package gabrielgrimberg.com.vitonbet;

import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Helper
{
    //Method that adds a balance field to the user.
    public static void SetBalance(final AppCompatActivity a)
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
        {
            DatabaseReference db = FirebaseDatabase.getInstance().getReference()
                    .child("Users").child(user.getUid().toString()).child("balance");


            db.addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    TextView bal = (TextView)a.findViewById(R.id.balance);
                    bal.setText("Balance: €" + Integer.toString((int)(long)dataSnapshot.getValue()));
                }

                @Override
                public void onCancelled(DatabaseError databaseError)
                {
                    // ...
                }
            });
        }
    }

    //Method that finds the username for the user.
    public static void SetUsername(final AppCompatActivity a)
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
        {
            DatabaseReference db = FirebaseDatabase.getInstance().getReference()
                    .child("Users").child(user.getUid().toString()).child("username");


            db.addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    TextView bal = (TextView)a.findViewById(R.id.username);
                    bal.setText("Welcome: " + dataSnapshot.getValue());
                }

                @Override
                public void onCancelled(DatabaseError databaseError)
                {
                    // ...
                }
            });
        }
    }

    //Method that finds the dob for the user.
    public static void SetDOB(final AppCompatActivity a)
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
        {
            DatabaseReference db = FirebaseDatabase.getInstance().getReference()
                    .child("Users").child(user.getUid().toString()).child("dob");


            db.addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    TextView bal = (TextView)a.findViewById(R.id.dobfield);
                    bal.setText("Date of Birth: " + dataSnapshot.getValue());
                }

                @Override
                public void onCancelled(DatabaseError databaseError)
                {
                    // ...
                }
            });
        }
    }

    //Method that finds the username for the user.
    public static void SetEmail(final AppCompatActivity a)
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
        {
            DatabaseReference db = FirebaseDatabase.getInstance().getReference()
                    .child("Users").child(user.getUid().toString()).child("email");


            db.addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    TextView bal = (TextView)a.findViewById(R.id.emailfield);
                    bal.setText("Your Email: " + dataSnapshot.getValue());
                }

                @Override
                public void onCancelled(DatabaseError databaseError)
                {
                    // ...
                }
            });
        }
    }

    //Method to change the balance for the user.
    public static void AddBalance(final AppCompatActivity a, final int amount)
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
        {
            final DatabaseReference db = FirebaseDatabase.getInstance().getReference()
                    .child("Users").child(user.getUid().toString()).child("balance");

            db.addListenerForSingleValueEvent(new ValueEventListener()
            {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    TextView bal = (TextView)a.findViewById(R.id.balance);
                    bal.setText("Balance: " + Integer.toString((int)(long)dataSnapshot.getValue() + amount));
                    db.setValue((int)(long)dataSnapshot.getValue() + amount);
                }

                @Override
                public void onCancelled(DatabaseError databaseError)
                {
                    // ...
                }
            });
        }
    }

    public static void transferCash(final String email, final int amount) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference myBalDb = FirebaseDatabase.getInstance().getReference()
                .child("Users").child(user.getUid().toString()).child("balance");


        myBalDb.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                final int myBal = (int)(long)dataSnapshot.getValue();
                if (myBal > amount) {
                    DatabaseReference users = FirebaseDatabase.getInstance().getReference()
                            .child("Users");
                    users.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot user : dataSnapshot.getChildren()) {
                                if (email.equals(user.child("email").getValue())) {
                                    DatabaseReference bal = FirebaseDatabase.getInstance().getReference()
                                            .child("Users").child(user.getKey()).child("balance");
                                    bal.setValue((int)(long)user.child("balance").getValue()
                                                + amount);

                                    myBalDb.setValue(myBal - amount);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) { }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });


    }

}
