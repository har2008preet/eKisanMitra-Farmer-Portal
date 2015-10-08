package wolverine.example.com.btp_farmer;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import app.AppController;


public class TagDescription extends ActionBarActivity {

    ListView list;
    TagCustomAdapter adapter;
    public  TagDescription CustomListView = null;
    public ArrayList<TagModel> CustomListViewValuesArr = new ArrayList<TagModel>();
    String question,PhoneNumber;
    ListView lv;
    Context context;
    private Database db;
    private SharedPreference sharedPreference;

    private ProgressDialog pDialog;
    private static String url= "http://192.168.43.17/qestion_insert/question_insert.php";
    int success;

    ArrayList prgmName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_description);

        Intent i =getIntent();
        Bundle data = i.getExtras();
        question = data.getString("question");
        SharedPreferences prefs = getSharedPreferences("Number", MODE_PRIVATE);
        final String PhoneNumber = prefs.getString("phoneKey", null);
        //Toast.makeText(getApplicationContext(),PhoneNumber,Toast.LENGTH_SHORT).show();

        ArrayList<TagModel> searchResults = GetSearchResults();

        final ListView lv = (ListView) findViewById(R.id.list_tags);
        lv.setAdapter(new TagCustomAdapter(this, searchResults));


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                Object o = lv.getItemAtPosition(position);
                final TagModel fullObject = (TagModel) o;
                Toast.makeText(getApplicationContext(), "You have chosen: " + " " + fullObject.getTag(), Toast.LENGTH_SHORT).show();
                new AlertDialog.Builder(TagDescription.this)
                        .setMessage("Are you sure?" )
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                sendQuestion(question, fullObject.getTag(),PhoneNumber);


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
        });


    }

    private void sendQuestion(final String question, final String tag, final String phoneNumber) {
        //Toast.makeText(getApplicationContext(),question+" "+tag+" "+phoneNumber,Toast.LENGTH_LONG).show();
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("contacting server ...");
        pDialog.setCancelable(false);
        pDialog.show();
        // String url = "http://192.168.43.17/register_scien/register.php;

        StringRequest SpecialReq = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>()
                {

                    @Override
                    public void onResponse(String response) {
                        hidePDialog();

                        Toast.makeText(getApplicationContext(),"You have successfully submitted the question", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getApplicationContext(),MightBeAnsweres.class);
                        i.putExtra("question",question);
                        startActivity(i);
                        finish();

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        hidePDialog();
                        Toast.makeText(getApplicationContext(),"failure", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("question", question);
                params.put("number", phoneNumber);
                params.put("Tag", tag);

                return params;
            }
        };



        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(SpecialReq);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tag_description, menu);
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

    private ArrayList<TagModel> GetSearchResults(){
        ArrayList<TagModel> results = new ArrayList<TagModel>();

        /*TagModel sr = new TagModel();
        sr.setTag("I am not Sure");
        sr.setDescription("Choose this if you are not sure of which catagory your question is.");
        results.add(sr);*/

        TagModel sr = new TagModel();
        sr = new TagModel();
        sr.setTag("Agronomy");
        sr.setDescription("Agronomy is the science and technology of producing and using plants for food, fuel, fibre, and land reclamation.");
        results.add(sr);

        sr = new TagModel();
        sr.setTag("Agricultural Economics");
        sr.setDescription("Aricultural economics is the study of allocation, distribution, and utilization of the resources used, along with the commodities produced, by farming.");
        results.add(sr);

        sr = new TagModel();
        sr.setTag("Entomology");
        sr.setDescription("Entomology is the study of insects and their relationship to humans, the environment, and other organisms.");
        results.add(sr);

        sr = new TagModel();
        sr.setTag("Agricultural Engineering");
        sr.setDescription("Agricultural engineering is the engineering discipline that applies engineering science and technology to agricultural production and processing.");
        results.add(sr);

        sr = new TagModel();
        sr.setTag("Agriculture Extension");
        sr.setDescription("Agricultural extension is a general term meaning the application of scientific research and new knowledge to agricultural practices through farmer education.");
        results.add(sr);

        sr = new TagModel();
        sr.setTag("Sericulture");
        sr.setDescription("Sericulture is the cultivation of silk through rearing of silkworm.");
        results.add(sr);

        sr = new TagModel();
        sr.setTag("Apiculture");
        sr.setDescription("Apiculture is the maintenance of honey bee colonies, commonly in hives, by humans.");
        results.add(sr);

        sr = new TagModel();
        sr.setTag("Biotechnology");
        sr.setDescription("Biotechnology is a broad discipline in which biological processes, organisms, cells or cellular components are exploited to develop new technologies.");
        results.add(sr);

        sr = new TagModel();
        sr.setTag("Crop physiology");
        sr.setDescription("Crop physiology is a subdiscipline of botany concerned with the functioning, or physiology, of plants.");
        results.add(sr);

        sr = new TagModel();
        sr.setTag("Horticulture");
        sr.setDescription("Horticulture is the branch of agriculture that deals with the art, science, technology, and business of vegetable garden plant growing.");
        results.add(sr);
        return results;
    }
}
