package mypackage.loginregistration;


    import android.os.Bundle;
    import android.app.Activity;
    import android.view.Menu;
    import android.widget.ArrayAdapter;
    import android.widget.ListView;
    import android.widget.TextView;

public class ListDisplay extends BaseActivty {

        // Array of strings...
        String[] cityArray = {"New Delhi","Gurgaon","Pune","Mumbai",
                "Bangalore","Kolkata","Chennai"};
        private TextView label;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            label=(TextView)findViewById(R.id.label);
            ArrayAdapter adapter = new ArrayAdapter<String>(this,
                    R.layout.activity_listview, cityArray);

            ListView listView = (ListView) findViewById(R.id.city_list);
            listView.setAdapter(adapter);
        }
    }

