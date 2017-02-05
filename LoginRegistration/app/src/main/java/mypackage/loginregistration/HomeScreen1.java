
package mypackage.loginregistration;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.Thing;

import java.util.Map;

public class HomeScreen1 extends BaseActivty implements OnItemClickListener, AdapterView.OnItemSelectedListener {
    Map<String, Integer> mapIndex;
    ListView itemList;

    // Initialize variables

    AutoCompleteTextView textView = null;
    private ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        setContentView(R.layout.activity_homescreen);


        // Initialize AutoCompleteTextView values

        // Get AutoCompleteTextView reference from xml
        textView = (AutoCompleteTextView) findViewById(R.id.atv_medicines_search);
        //Create adapter
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, item);

        textView.setThreshold(1);
//        textView.setDropDownBackgroundResource(R.drawable.autocomplete_dropdown);
        // textView.setDropDownBackgroundDrawable(new ColorDrawable(context.getResources().getColor("@null")));
        //Set adapter to AutoCompleteTextView
        textView.setAdapter(adapter);
        textView.setOnItemSelectedListener(this);
        textView.setOnItemClickListener(this);
    }



    public void onClick(View view) {
        TextView selectedIndex = (TextView) view;
        itemList.setSelection(mapIndex.get(selectedIndex.getText()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }//---

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
                               long arg3) {


    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

        InputMethodManager imm = (InputMethodManager) getSystemService(
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub

        Intent intent;
        String  selectedValue= (String) arg0.getItemAtPosition(arg2);

        switch (selectedValue) {
            case "Paracetamol":
                intent = new Intent(HomeScreen1.this, Paracetamol.class);
                startActivity(intent);
                break;
            case "Crocin":
                intent = new Intent(HomeScreen1.this, Crocin.class);
                startActivity(intent);
                break;
        }
    }


    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("HomeScreen Page") // TODO: Define a title for the content shown.
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
        super.onStart();// ATTENTION: This was auto-generated to implement the App Indexing API.
// See https://g.co/AppIndexing/AndroidStudio for more information.


    }

    @Override
    public void onStop() {
        super.onStop();
    }


    public Action getIndexApiAction0() {
        Thing object = new Thing.Builder()
                .setName("HomeScreen Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }
}
