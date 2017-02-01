
package mypackage.loginregistration;

        import android.content.SharedPreferences;
        import android.graphics.drawable.ColorDrawable;
        import android.graphics.drawable.ColorDrawable;
        import android.net.Uri;
        import android.os.Bundle;
        import android.view.KeyEvent;
        import android.view.View;
        import android.view.inputmethod.InputMethodManager;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.AutoCompleteTextView;
        import android.widget.Toast;
        import android.widget.AdapterView.OnItemClickListener;

        import com.google.android.gms.appindexing.Action;
        import com.google.android.gms.appindexing.AppIndex;
        import com.google.android.gms.appindexing.Thing;
        import com.google.android.gms.common.api.GoogleApiClient;

        import java.util.ArrayList;
        import java.util.HashSet;
        import java.util.List;
        import java.util.Set;

        import butterknife.ButterKnife;

        import static android.R.id.input;
        import static android.R.id.list;
        import static android.view.View.INVISIBLE;

public class HomeScreen1 extends BaseActivty implements OnItemClickListener, AdapterView.OnItemSelectedListener {


    // Initialize variables

    AutoCompleteTextView textView = null;
    private ArrayAdapter<String> adapter;
    public static final String PREFS_NAME = "PingBusPrefs";
    public static final String PREFS_SEARCH_HISTORY = "SearchHistory";
    private SharedPreferences settings;
    private Set<String> history;
    //These values show in autocomplete
    String item[] = {
            "Paracetamol", "Disprin", "Asprin", "Azithromycin",
            "Allegra", "Crocin", "Combiflame", "Benadryl",
            "Vomikind", "VicksAction-500", "Pudinhara"
    };


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.activity_homescreen);
        settings = getSharedPreferences(PREFS_NAME, 0);
        history = settings.getStringSet(PREFS_SEARCH_HISTORY, new HashSet<String>());

        setAutoCompleteSource();
    }
    // Initialize AutoCompleteTextView values

    // Get AutoCompleteTextView reference from xml
       /* textView = (AutoCompleteTextView) findViewById(R.id.medicines);
        //Create adapter
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, item);

        textView.setThreshold(1);
        textView.setDropDownBackgroundResource(R.drawable.autocomplete_dropdown);
        // textView.setDropDownBackgroundDrawable(new ColorDrawable(context.getResources().getColor("@null")));
        //Set adapter to AutoCompleteTextView
        textView.setAdapter(adapter);
        textView.setOnItemSelectedListener(this);
        textView.setOnItemClickListener(this);*/
//---}
//--
    private void setAutoCompleteSource()
    {
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


        textView.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    addSearchInput(textView.getText().toString());
                    return true;
                }
                return false;
            }
        });}
    //--
    private void addSearchInput(String input)
    {
        if (!history.contains(input))
        {
            history.add(input);
            setAutoCompleteSource();
        }
    }
    //--
    private void savePrefs()
    {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putStringSet(PREFS_SEARCH_HISTORY, history);

        editor.commit();
    }





    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
                               long arg3) {
        // TODO Auto-generated method stub

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
        //  Toast.makeText(getBaseContext(), "Position:" + arg2 + " Month:" + arg0.getItemAtPosition(arg2),
        //      Toast.LENGTH_LONG).show();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
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
        super.onStart();


    }

    @Override
    public void onStop() {
        super.onStop();
        savePrefs();

    }
}