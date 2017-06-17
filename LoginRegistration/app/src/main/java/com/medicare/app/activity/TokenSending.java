package com.medicare.app.activity;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.medicare.app.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TokenSending extends BaseActivty implements View.OnClickListener {
    private BroadcastReceiver broadcastReceiver;
    private EditText edtToken;
//    private Button btnToken;
    private ProgressDialog progressDialog;
   // private static  final String URL_STORE_TOKEN = "http://192.168.201.2/mediCare/sendSinglePush.php";

    private Button buttonSendPush;
    private Button buttonRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token_sending);
        edtToken = (EditText)findViewById(R.id.edt_token_email);
//        btnToken= (Button)findViewById(R.id.btn_token);
        //adding listener to view
//        btnToken.setOnClickListener(this);

        //getting views from xml
       buttonRegister = (Button) findViewById(R.id.buttonRegister);
        buttonSendPush = (Button) findViewById(R.id.buttonSendNotification);

        //adding listener to view
        buttonRegister.setOnClickListener(this);
        buttonSendPush.setOnClickListener(this);
    }

    //storing token to mysql server
    private void sendTokenToServer() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        final String token = SharedPrefManager.getInstance(this).getToken();
        final String email = edtToken.getText().toString();

        if (token == null) {
            progressDialog.dismiss();
            Toast.makeText(this, "Token not generated", Toast.LENGTH_LONG).show();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, EndPoints.URL_REGISTER_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            Toast.makeText(TokenSending.this, obj.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(TokenSending.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("token", token);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View view) {
        if (view == buttonRegister) {
            sendTokenToServer();
        }

        //starting send notification activity
        if(view == buttonSendPush){
            startActivity(new Intent(this, ActivitySendPushNotification.class));
        }
    }
}
/*

public class TokenSending extends BaseActivty {
    private BroadcastReceiver broadcastReceiver;
    private EditText edtToken;
    private Button btnToken;
    private static  final String URL_STORE_TOKEN = "192.168.201.2/mediCare/StoreFCMToken.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token_sending);
        edtToken = (EditText)findViewById(R.id.edt_token_email);
        btnToken= (Button)findViewById(R.id.btn_token);

        btnToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendTokenToServer();
            }
        });


        broadcastReceiver= new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {


            }
        };

        if (SharedPrefManager.getInstance(this).getToken()!= null)
        {

            Log.d("my firebaseToken",SharedPrefManager.getInstance(this).getToken());}
        registerReceiver(broadcastReceiver, new IntentFilter(MyFirebaseInstanceIDService.TOKEN_BROADCAST));}


    public void sendTokenToServer(){
        final String email = edtToken.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
        showToast("enter value");}else{
            if(SharedPrefManager.getInstance(this).getToken()!= null){
                StringRequest stringRequest= new StringRequest(Request.Method.POST,URL_STORE_TOKEN,
                        new Response.Listener<String>(){
                            public void onResponse(String response){
                                try {
                                    JSONObject obj = new JSONObject(response);
                                    showToast(obj.getString("message"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener()
                {
                    public void onErrorResponse(VolleyError error){
showToast(error.getMessage());
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("token", SharedPrefManager.getInstance(getApplicationContext()).getToken());
                        params.put("email",email);
                        return params;
                    }
                };
                RequestQueue requestQueue= Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);
            }else{
            showToast("Token not generted");}
        }
    }
}
*/
