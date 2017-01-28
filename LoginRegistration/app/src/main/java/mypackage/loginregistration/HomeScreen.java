
package mypackage.loginregistration;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.Thing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.R.attr.button;
import static android.R.id.list;

public class HomeScreen extends BaseActivty implements OnItemClickListener, AdapterView.OnItemSelectedListener {
    Map<String, Integer> mapIndex;
    ListView itemList;

    // Initialize variables

    AutoCompleteTextView textView = null;
    private ArrayAdapter<String> adapter;

    //These values show in autocomplete
    String item[] = {
            "Allegra", "Asprin", "Azithromycin", "Combiflame ", "Crocin","Disprin", "Paracetamol",  "Pudinhara",
            "VicksAction-500", " Vomikind", "Benadryl"
    };


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        setContentView(R.layout.activity_homescreen);


        // Initialize AutoCompleteTextView values

        // Get AutoCompleteTextView reference from xml
        textView = (AutoCompleteTextView) findViewById(R.id.medicines);
        //Create adapter
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, item);

        textView.setThreshold(1);
        textView.setDropDownBackgroundResource(R.drawable.autocomplete_dropdown);
        // textView.setDropDownBackgroundDrawable(new ColorDrawable(context.getResources().getColor("@null")));
        //Set adapter to AutoCompleteTextView
        textView.setAdapter(adapter);
        textView.setOnItemSelectedListener(this);
        textView.setOnItemClickListener(this);
        textView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                LinearLayout indexLayout = (LinearLayout) findViewById(R.id.side_index);
              //  indexLayout.setVisibility(View.VISIBLE);
            }
        });


//--
        getIndexList(item);

        displayIndex();

    }
//--


    private void getIndexList(String[] item) {
        mapIndex = new LinkedHashMap<String, Integer>();
        for (int i = 0; i < item.length; i++) {
            String value = item[i];
            String index = value.substring(0, 1);

            if (mapIndex.get(index) == null)
                mapIndex.put(index, i);
        }
    }

    private void displayIndex() {
        LinearLayout indexLayout = (LinearLayout) findViewById(R.id.side_index);

        TextView textView;
        List<String> indexList = new ArrayList<String>(mapIndex.keySet());
        Set<String> hashsetList;
        for (String index : indexList) {
            textView = (TextView) getLayoutInflater().inflate(
                    R.layout.side_index_item, null);
            textView.setText(index);
            //textView.setOnClickListener(this);
            indexLayout.addView(textView);
            Collections.sort(indexList, new Comparator<String>()
            {
                @Override
                public int compare(String text1, String text2)
                {
                    return text1.compareToIgnoreCase(text2);
                }
            });
            hashsetList = new HashSet<String>(indexList);
        }

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

        //listOfRecentSearched.add((String) arg0.getItemAtPosition(arg2));
         // Toast.makeText(getBaseContext(), "Position:" + arg2 + " medicine:" + arg0.getItemAtPosition(arg2),
            //  Toast.LENGTH_LONG).show();
/*

                Intent intent = new Intent(HomeScreen.this, Paracetamol.class);
                Bundle bundle = new Bundle();
            bundle.putString("medicine_name", arg0.getItemAtPosition(arg2).toString());
                bundle.putLong("_id", arg3);
                intent.putExtras(bundle);
                startActivity(intent);
*/

         Intent intent;
      String  selectedValue= (String) arg0.getItemAtPosition(arg2);

       switch (selectedValue) {
           case "Paracetamol":
                intent = new Intent(HomeScreen.this, Paracetamol.class);
                startActivity(intent);
              break;
            case "Crocin":
                 intent = new Intent(HomeScreen.this, Crocin.class);
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
