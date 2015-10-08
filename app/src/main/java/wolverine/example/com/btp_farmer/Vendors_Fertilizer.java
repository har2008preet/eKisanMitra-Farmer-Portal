package wolverine.example.com.btp_farmer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import app.AppController;
import wolverine.example.com.btp_farmer.adapter.CustomVendAdapter;
import wolverine.example.com.btp_farmer.model.QuestionGetSet;

public class Vendors_Fertilizer extends AppCompatActivity{
    Activity context;
    String state,district,Tag;
    ListView listView;
    ProgressDialog pDialog;
    ArrayList<Product> records;

    CustomVendAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendors__fertilizer);
        Intent i = getIntent();
        Bundle data = i.getExtras();
        state = data.getString("State");
        district=data.getString("District");
        Tag= data.getString("Tag");

        context=this;
        records=new ArrayList<Product>();
        listView = (ListView)findViewById(R.id.lv_vendor_fert);
        adapter=new CustomVendAdapter(context, R.layout.vendor_custom_list,R.id.vend_name,
                records);

        listView.setAdapter(adapter);
        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        new AsyncTask<Void,Void,Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    vendorList();
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

    private void vendorList() throws UnsupportedEncodingException {
        // Creating volley request obj
        JsonArrayRequest quesReq = null;
        if(Tag.matches("Fertilizer")) {
            final String District = URLEncoder.encode(district, "utf-8");
            quesReq = new JsonArrayRequest("http://192.168.43.17/farmer_portal/vendorsearchfertilizer.php?district=" + District + "&tag=" + Tag,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            if (response.isNull(1)){
                                new AlertDialog.Builder(Vendors_Fertilizer.this)
                                        .setMessage("No Dealer in "+ district +" found")
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent i = new Intent(Vendors_Fertilizer.this,StateDistrictForVendor.class);
                                                i.putExtra("Tag",Tag);
                                                startActivity(i);
                                                finish();

                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
                            }
                            else {

                                Log.d("response=========", response.toString());

                                // Parsing json
                                for (int i = 0; i < response.length(); i++) {
                                    try {

                                        JSONObject obj = response.getJSONObject(i);
                                        Product vendor = new Product();
                                        vendor.setpName(obj.getString("Dealer Name"));
                                        vendor.setuState(obj.getString("State Name"));

                                        // adding movie to movies array
                                        records.add(vendor);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }

                            // notifying list adapter about data changes
                            // so that it renders the list view with updated data
                            adapter.notifyDataSetChanged();
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
            final String District = URLEncoder.encode(district, "utf-8");
            quesReq = new JsonArrayRequest("http://192.168.43.17/farmer_portal/vendorsearchfertilizer.php?district=" + District + "&tag=" + Tag,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                            if (response.isNull(1)){
                                new AlertDialog.Builder(Vendors_Fertilizer.this)
                                        .setMessage("No Dealer in "+ district +" found")
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent i = new Intent(Vendors_Fertilizer.this,StateDistrictForVendor.class);
                                                i.putExtra("Tag", Tag);
                                                startActivity(i);
                                                finish();

                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
                            }
                            else {

                                Log.d("response=========", response.toString());

                                // Parsing json
                                for (int i = 0; i < response.length(); i++) {
                                    try {

                                        JSONObject obj = response.getJSONObject(i);
                                        Product vendor = new Product();
                                        vendor.setpName(obj.getString("Dealer Name"));
                                        vendor.setuState(obj.getString("State Name"));

                                        // adding movie to movies array
                                        records.add(vendor);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }
                            // notifying list adapter about data changes
                            // so that it renders the list view with updated data
                            adapter.notifyDataSetChanged();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("Error=========", "Error: " + error.getMessage());
                    Log.d("Error:------", error.getMessage());

                }
            });
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product selecteddealer = records.get(position);
                String dealerName = selecteddealer.getpName();
                String dealerState = selecteddealer.getuState();
                Intent detailScreen = new Intent(Vendors_Fertilizer.this,Vendor_Fertilizer_datail.class);
                detailScreen.putExtra("Name",dealerName);
                detailScreen.putExtra("State", dealerState);
                detailScreen.putExtra("Tag",Tag);
                startActivity(detailScreen);
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(quesReq);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vendors__fertilizer, menu);
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
