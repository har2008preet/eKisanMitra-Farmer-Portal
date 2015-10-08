package wolverine.example.com.btp_farmer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import app.AppController;

public class Vendor_Fertilizer_datail extends AppCompatActivity {
    TextView DealerName,DealerId,DealerType,DealarMobile,Village,Address1,Address2,License,IssueDate,ExpiryDate,WebSite;
    String Name,State,Tag;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor__fertilizer_datail);

        Intent i = getIntent();
        Bundle data = i.getExtras();
        State = data.getString("State");
        Name=data.getString("Name");
        Tag = data.getString("Tag");

        setTitle(Name);
        DealerName = (TextView)findViewById(R.id.idTextVendor);
        DealerId = (TextView)findViewById(R.id.dealer_id);
        DealerType = (TextView)findViewById(R.id.dealer_Type);
        DealarMobile = (TextView)findViewById(R.id.dealer_Mobile);
        Village = (TextView)findViewById(R.id.dealer_Village);
        Address1 = (TextView)findViewById(R.id.dealer_addressline1);
        Address2 = (TextView)findViewById(R.id.dealer_addressline2);
        License = (TextView)findViewById(R.id.dealer_License);
        IssueDate = (TextView)findViewById(R.id.license_issue_date);
        ExpiryDate = (TextView)findViewById(R.id.license_expiry_date);
        WebSite = (TextView)findViewById(R.id.dealer_Web);

        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        new AsyncTask<Void,Void,Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    vendorDetail();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                pDialog.dismiss();
            }
        }.execute();
    }

    private void vendorDetail() throws UnsupportedEncodingException {
        JsonArrayRequest quesReq = null;
        if(Tag.matches("Fertilizer")) {
            String state = URLEncoder.encode(State, "utf-8");
            String name = URLEncoder.encode(Name, "utf-8");
            quesReq = new JsonArrayRequest("http://192.168.43.17/farmer_portal/vendordetail.php/?name=" + name + "&state=" + state+ "&tag=" + Tag,
                    new Response.Listener<JSONArray>() {
                        @Override

                        public void onResponse(JSONArray response) {
                            Log.d("response=========", response.toString());

                            // Parsing json
                            for (int i = 0; i < 1; i++) {
                                try {

                                    JSONObject obj = response.getJSONObject(i);
                                    DealerName.setText(obj.getString("Dealer Name"));
                                    DealerId.setText(obj.getString("Dealer Id"));
                                    DealerType.setText(obj.getString("Dealer Type"));
                                    DealarMobile.setText(obj.getString("Mobile"));
                                    Village.setText(obj.getString("Village Name"));
                                    Address1.setText(obj.getString("Dealer Address Line 1"));
                                    Address2.setText(obj.getString("Dealer Address Line 2"));
                                    License.setText(obj.getString("Dealer License"));
                                    IssueDate.setText(obj.getString("License Issue Date"));
                                    ExpiryDate.setText(obj.getString("License Expiry Date"));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("Error=========", "Error: " + error.getMessage());
                    Log.d("Error:------", error.getMessage());

                }
            });
        }
        else{
            String state = URLEncoder.encode(State, "utf-8");
            String name = URLEncoder.encode(Name, "utf-8");
            quesReq = new JsonArrayRequest("http://192.168.43.17/farmer_portal/vendordetail.php/?name=" + name + "&state=" + state+ "&tag=" + Tag,
                    new Response.Listener<JSONArray>() {
                        @Override

                        public void onResponse(JSONArray response) {
                            Log.d("response=========", response.toString());

                            // Parsing json
                            for (int i = 0; i < 1; i++) {
                                try {

                                    JSONObject obj = response.getJSONObject(i);
                                    DealerName.setText(obj.getString("dealerName"));
                                    DealerId.setText(obj.getString("dealerId"));
                                    DealerType.setText(obj.getString("dealerType"));
                                    DealarMobile.setText(obj.getString("mobile"));
                                    Village.setText(obj.getString("taluk"));
                                    Address1.setText(obj.getString("address"));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("Error=========", "Error: " + error.getMessage());
                    Log.d("Error:------", error.getMessage());

                }
            });
        }

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(quesReq);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vendor__fertilizer_datail, menu);
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
}
