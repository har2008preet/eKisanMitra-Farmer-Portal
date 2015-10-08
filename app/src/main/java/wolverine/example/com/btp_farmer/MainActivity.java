package wolverine.example.com.btp_farmer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import app.AppController;

import static wolverine.example.com.btp_farmer.CommonUtilities.SENDER_ID;


public class MainActivity extends ActionBarActivity {
    private ProgressDialog pDialog;
    private SessionManager session;

    EditText phone;
    private Database db;
    Button Sign;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Phone = "phoneKey";
    SharedPreferences sharedpreferences;
    Activity context = this;
    private GCMClientManager pushClientManager;
    String firstName,lastName,state,district,pincode,address,mobile;
    public static final String fName="First_Name";
    public static final String lName="Last_Name";
    public static final String ste="State";
    public static final String drict="District";
    public static final String pcode="PinCode";
    public static final String adr="Address";
    public static final String nbr="Mobile";

    private static final String TAG = farmerRegis.class.getSimpleName();
    String regId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(MainActivity.this, Navigationfarmer.class);
            startActivity(intent);
            finish();
        }

        db = new Database(getApplicationContext());
        Sign = (Button)findViewById(R.id.btnSingIn);
        Sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = (EditText) findViewById(R.id.phoneno);
                final String number = phone.getText().toString();
                if (number.length() != 10) {
                    Toast.makeText(getApplicationContext(), "Enter 10 digit registered phone number", Toast.LENGTH_SHORT).show();
                } else {
                    new AlertDialog.Builder(MainActivity.this)
                            .setMessage("Confirm your number:+91-" + number)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    SharedPreferences.Editor editor = getSharedPreferences("Number",
                                            Context.MODE_PRIVATE).edit();
                                    editor.putString(Phone, number);
                                    editor.commit();
                                    login(number);

                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        });
    }

    private void login(final String number) {
        String tag_string_req = "req_login";
        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                "http://192.168.43.17/farmer_portal/login.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();


                JSONObject jObj = null;
                try {
                    jObj = new JSONObject(response);
                    int success = jObj.getInt("success");


                    if (success == 1) {
                        // user successfully logged in
                        // Create login session


                        //Toast.makeText(getApplicationContext(),jObj.getString("District"),Toast.LENGTH_LONG).show();
                        // Launch main activity
                        farmer_info(number);

                        pushClientManager = new GCMClientManager(MainActivity.this, SENDER_ID);

                        pushClientManager.registerIfNeeded(new GCMClientManager.RegistrationCompletedHandler() {

                            @Override
                            public void onSuccess(String registrationId, boolean isNewRegistration) {

                                regId=registrationId;

                            }

                            @Override
                            public void onFailure(String ex) {
                                super.onFailure(ex);
                                Toast.makeText(getApplicationContext(),"Some error occured",Toast.LENGTH_LONG).show();
                            }

                        });
                        if(jObj.getString("regId").matches("null")||jObj.getString("regId")!=regId)
                        {

                            final String PhoneNumber=jObj.getString("PhoneNumber");

                            UpdateUser(PhoneNumber, regId);


                        }
                        else {
                            session.setLogin(true);
                            Intent intent = new Intent(MainActivity.this,
                                    Navigationfarmer.class);
                            startActivity(intent);

                        //Toast.makeText(getApplicationContext(),"success ", Toast.LENGTH_LONG).show();
                        //      finish();
                    }} else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("message");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),
                            "Some error occured", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Check your internet Connection", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("PhoneNumber",number);
                return checkParams(params);

            }
            private Map<String, String> checkParams(Map<String, String> map){
                Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String> pairs = (Map.Entry<String, String>)it.next();
                    if(pairs.getValue()==null){
                        map.put(pairs.getKey(), "");
                    }
                }
                return map;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void farmer_info(final String phonenumber) {
        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Submitting...");
        pDialog.setCancelable(false);
        pDialog.show();
        // String url = "http://192.168.43.17/register_scien/register.php;
        JsonObjectRequest jreq = new JsonObjectRequest(Request.Method.POST, "http://192.168.43.17/farmer_portal/farmer_info.php?phonenumber="+phonenumber, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int success = response.getInt("success");

                            if (success == 1) {
                                JSONArray ja = response.getJSONArray("info");

                                for (int i = 0; i < ja.length(); i++) {

                                    JSONObject jObj1 = ja.getJSONObject(i);
                                    firstName = jObj1.getString("firstName");
                                    lastName = jObj1.getString("lastName");
                                    state = jObj1.getString("state");
                                    district = jObj1.getString("district");
                                    pincode = jObj1.getString("pincode");
                                    address = jObj1.getString("address");
                                    mobile = jObj1.getString("mobile");
                                    SharedPreferences.Editor editor = getSharedPreferences("Info",
                                            Context.MODE_PRIVATE).edit();
                                    editor.putString(fName, firstName);
                                    editor.putString(lName, lastName);
                                    editor.putString(ste,state);
                                    editor.putString(drict, district);
                                    editor.putString(pcode, pincode);
                                    editor.putString(adr, address);
                                    editor.putString(nbr, mobile);
                                    editor.commit();

                                   // Toast.makeText(MainActivity.this,firstName,Toast.LENGTH_SHORT).show();

                                } // for loop ends



                                pDialog.dismiss();

                            } // if ends

                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),"Some error occured",Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Some error occured",Toast.LENGTH_SHORT).show();
                pDialog.dismiss();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jreq);
    }

    private void UpdateUser(final String phoneNumber,final String regId) {
        StringRequest SpecialReq = new StringRequest(Request.Method.POST,"http://192.168.43.17/farmer_portal/login_update.php",
                new Response.Listener<String>()
                {

                    @Override
                    public void onResponse(String response) {


                        JSONObject jObj1 = null;
                        try {
                            jObj1 = new JSONObject(response);
                            int success = jObj1.getInt("success");


                            if (success == 1) {
                                // user successfully logged in
                                // Create login session
                                session.setLogin(true);

                                // Launch main activity
                                Intent intent = new Intent(MainActivity.this,
                                        Navigationfarmer.class);
                                startActivity(intent);
                                //Toast.makeText(getApplicationContext(),"success ", Toast.LENGTH_LONG).show();
                                finish();
                            } else {
                                // Error in login. Get the error message
                                String errorMsg = jObj1.getString("message");
                                //Toast.makeText(getApplicationContext(),
                                        //errorMsg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            //Toast.makeText(getApplicationContext(),
                                    //"errorException ", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        hideDialog();
                        Toast.makeText(getApplicationContext(),"Some error occured", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("PhoneNumber",phoneNumber );
                params.put("regId", regId);

                return params;
            }
        };



        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(SpecialReq);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
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

    public void register(View view) {
        Intent i = new Intent(getApplicationContext(),farmerRegis.class);
        startActivity(i);
    }
}
