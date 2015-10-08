package wolverine.example.com.btp_farmer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
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
import java.util.ArrayList;
import java.util.List;

import app.AppController;
import wolverine.example.com.btp_farmer.adapter.CustomListAdapter;
import wolverine.example.com.btp_farmer.model.QuestionGetSet;


public class see_answers extends Activity {
    TextView question,answertxt;
    Button refresh;
    String Querry,Number,Date,answer,disAnswer;
    public static final String ans="answer";
    private ProgressDialog pDialog;
    private List<QuestionGetSet> movieList = new ArrayList<QuestionGetSet>();
    private ListView listView;
    private CustomListAdapter adapter;
    private static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_see_answers);
        question = (TextView)findViewById(R.id.tag_query);

        listView = (ListView) findViewById(R.id.seeAnswer);
        adapter = new CustomListAdapter(this, movieList);
        listView.setAdapter(adapter);

        Intent ansquerry =getIntent();
        Bundle data = ansquerry.getExtras();
        Querry = data.getString("Querry");
        Log.d("Querry========",Querry);
        question.setText(Querry);

        pDialog = new ProgressDialog(see_answers.this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        new AsyncTask<Void,Void,Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    allanswertoquey();
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
    private void allanswertoquey() throws UnsupportedEncodingException {
        // Creating volley request obj
        String new_querry = URLEncoder.encode(Querry, "utf-8");
        final JsonArrayRequest quesReq = new JsonArrayRequest("http://192.168.43.17/farmer_portal/allanswerstoquery.php/?query="+new_querry,
                new Response.Listener<JSONArray>() {
                    @Override

                    public void onResponse(JSONArray response) {
                        Log.d("Response:====", response.toString());
                        if (response.length()==0){
                            new AlertDialog.Builder(see_answers.this)
                                    .setMessage("No Answer(s) yet")
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent i = new Intent(see_answers.this, Navigationfarmer.class);
                                            startActivity(i);
                                            finish();

                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                        }
                        else {

                            // Parsing json
                            for (int i = 0; i < response.length(); i++) {
                                try {

                                    JSONObject obj = response.getJSONObject(i);
                                    QuestionGetSet movie = new QuestionGetSet();
                                    movie.setQues(obj.getString("ans"));
                                    movie.setNumber(obj.getString("farmerName"));
                                    // adding movie to movies array
                                    movieList.add(movie);

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
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Log.d("Error:------",error.getMessage());

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                QuestionGetSet selectedques = movieList.get(position);
                String ans= selectedques.getQues();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(quesReq);
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
        getMenuInflater().inflate(R.menu.menu_see_answers, menu);
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
