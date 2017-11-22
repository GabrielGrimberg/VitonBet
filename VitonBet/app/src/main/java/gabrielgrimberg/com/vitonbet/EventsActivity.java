package gabrielgrimberg.com.vitonbet;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Random;

public class EventsActivity extends AppCompatActivity
{
    String[] teams = {"Na'Vi", "Virtus Dno", "Chelsea FC", "ManUnited"};
    ArrayList<String> listItems=new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

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
            addItems(teams[random.nextInt(4)] + " VS. " + teams[random.nextInt(4)]);
        }
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
