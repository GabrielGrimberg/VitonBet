package gabrielgrimberg.com.vitonbet;


import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class EventsActivity extends AppCompatActivity implements EnterEvent.OnFragmentInteractionListener
{
    String[] teams = {"Na'Vi", "Virtus Dno", "Chelsea FC", "ManUnited"};
    ArrayList<String> listItems=new ArrayList<String>();
    ArrayAdapter<String> adapter;

    public void onFragmentInteraction(Uri u) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        findViewById(R.id.enterEvent).setVisibility(View.GONE);

        adapter=new CusAdapter(this,
                android.R.layout.simple_list_item_1,
                listItems);
        final ListView lv = findViewById(R.id.list);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String s = (String)adapterView.getItemAtPosition(i);
                Log.d("mytag", s);
            }
        });

        Random random = new Random();
        for(int i = 0; i < 10; i++) {
            //addItems(teams[random.nextInt(4)] + " VS. " + teams[random.nextInt(4)]);
        }

        Button addNewBtn = findViewById(R.id.addNew);
        addNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.enterEvent).setVisibility(View.VISIBLE);
                findViewById(R.id.list).setVisibility(View.GONE);
                findViewById(R.id.addNew).setVisibility(View.GONE);
            }
        });

        Button submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FirebaseAuth xAuth = FirebaseAuth.getInstance();
                DatabaseReference xDatabase = FirebaseDatabase.getInstance().getReference().child("Events");

                EditText title = findViewById(R.id.title);
                EditText description = findViewById(R.id.description);
                EditText note = findViewById(R.id.note);
                xDatabase.child(title.getText().toString()).child("description").setValue(description.getText().toString());
                xDatabase.child(title.getText().toString()).child("note").setValue(note.getText().toString());

                findViewById(R.id.enterEvent).setVisibility(View.GONE);
                findViewById(R.id.list).setVisibility(View.VISIBLE);
                findViewById(R.id.addNew).setVisibility(View.VISIBLE);

                Toast.makeText(EventsActivity.this, "Your submission will be reviwed by an admin.",
                        Toast.LENGTH_LONG).show();
            }
        });

        DatabaseReference db = FirebaseDatabase.getInstance().getReference()
                .child("Events");
        db.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for (DataSnapshot event : dataSnapshot.getChildren()) {
                    addItems(event.getKey());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                // ...
            }
        });
    }

    public void addItems(String s) {
        listItems.add(s);
        adapter.notifyDataSetChanged();
    }

    class CusAdapter extends ArrayAdapter<String> implements View.OnClickListener {
        CusAdapter(Context c, int x, ArrayList<String> s) {
            super(c, x, s);
        }

        @Override
        public void onClick(View v) {
            int position=(Integer) v.getTag();
            Object object= getItem(position);
            String teams = (String)object;
            Log.d("mytag", teams);
        }
    }
}
