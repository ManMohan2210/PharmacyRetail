package mypackage.loginregistration;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;
import java.util.ArrayList;
import android.view.Menu;

import android.view.MenuInflater;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by satveer on 28-01-2017.
 */

public class MainActivity extends AppCompatActivity {

    ArrayList<DataModel> dataModels;
    ListView listView;
    private static CustomAdapter adapter;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitymain);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.list);

        dataModels = new ArrayList<>();

        dataModels.add(new DataModel("Apple Pie", "Android 1.0", "1", "September 23, 2008"));
        dataModels.add(new DataModel("Banana Bread", "Android 1.1", "2", "February 9, 2009"));
        dataModels.add(new DataModel("Cupcake", "Android 1.5", "3", "April 27, 2009"));
        dataModels.add(new DataModel("Donut", "Android 1.6", "4", "September 15, 2009"));
        dataModels.add(new DataModel("Eclair", "Android 2.0", "5", "October 26, 2009"));
        dataModels.add(new DataModel("Froyo", "Android 2.2", "8", "May 20, 2010"));
        dataModels.add(new DataModel("Gingerbread", "Android 2.3", "9", "December 6, 2010"));
        dataModels.add(new DataModel("Honeycomb", "Android 3.0", "11", "February 22, 2011"));
        dataModels.add(new DataModel("Ice Cream Sandwich", "Android 4.0", "14", "October 18, 2011"));
        dataModels.add(new DataModel("Jelly Bean", "Android 4.2", "16", "July 9, 2012"));
        dataModels.add(new DataModel("Kitkat", "Android 4.4", "19", "October 31, 2013"));
        dataModels.add(new DataModel("Lollipop", "Android 5.0", "21", "November 12, 2014"));
        dataModels.add(new DataModel("Marshmallow", "Android 6.0", "23", "October 5, 2015"));

        adapter = new CustomAdapter(dataModels, getApplicationContext());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DataModel dataModel = dataModels.get(position);

                Snackbar.make(view, dataModel.getName() + "\n" + dataModel.getType() + " API: " + dataModel.getVersion_number(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onStop() {
        super.onStop();


    }
}