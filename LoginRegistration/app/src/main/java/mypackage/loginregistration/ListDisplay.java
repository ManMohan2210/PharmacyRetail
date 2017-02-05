package mypackage.loginregistration;


    import android.os.Bundle;
    import android.app.Activity;
    import android.view.Menu;
    import android.widget.ArrayAdapter;
    import android.widget.ImageView;
    import android.widget.ListView;
    import android.widget.TextView;

public class ListDisplay extends BaseActivty {

        // Array of strings...
       /* String[] cityArray = {"New Delhi","Gurgaon","Pune","Mumbai",
                "Bangalore","Kolkata","Chennai"};*/
        private TextView label;
    private TextView txtViewForgot;
    private TextView txtViewAutodetect;
    private ImageView imgViewArrow;
    private ImageView imgViewDirection;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            txtViewForgot =(TextView)findViewById(R.id.tv_forgot_pwd);
            txtViewAutodetect=(TextView)findViewById(R.id.tv_autodetect);
           imgViewArrow =(ImageView)findViewById(R.id.img_location);
             imgViewDirection=(ImageView)findViewById(R.id.img_direction);
            label=(TextView)findViewById(R.id.label);
            ArrayAdapter adapter = new ArrayAdapter<String>(this,
                    R.layout.activity_listview, cityArray);

            ListView listView = (ListView) findViewById(R.id.city_list);
            listView.setAdapter(adapter);
        }
    }

