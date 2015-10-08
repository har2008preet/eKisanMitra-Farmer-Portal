package wolverine.example.com.btp_farmer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
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

import java.util.ArrayList;
import java.util.List;

import app.AppController;
import wolverine.example.com.btp_farmer.adapter.CustomListAdapter;
import wolverine.example.com.btp_farmer.model.QuestionGetSet;


public class MightBeAnsweres extends ActionBarActivity {

    String question;
    private ProgressDialog pDialog;
    private List<QuestionGetSet> movieList = new ArrayList<QuestionGetSet>();
    private ListView listView;
    private CustomListAdapter adapter;
    private static final String TAG = MainActivity.class.getSimpleName();
    ArrayList<String> answer = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_might_be_answeres);

        Intent i =getIntent();
        Bundle data = i.getExtras();
        question = data.getString("question");
        questionSuggestions(question);
    }

    private void questionSuggestions(final String question) {

        listView = (ListView) findViewById(R.id.list_previous);
        adapter = new CustomListAdapter(MightBeAnsweres.this, movieList);
        listView.setAdapter(adapter);

        /*pDialog = new ProgressDialog(getApplicationContext());
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();*/

        // Creating volley request obj
        JsonArrayRequest quesReq = new JsonArrayRequest("http://192.168.43.17/php_tests/similarty_check1.php?Search="+question,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        //hidePDialog();

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                QuestionGetSet movie = new QuestionGetSet();
                                movie.setQues(obj.getString("question"));
                                movie.setDate(obj.getString("date_time"));
                                movie.setNumber(obj.getString("Number"));
                                answer.add(obj.getString("answer"));
                                // adding movie to movies array
                                movieList.add(movie);

                            } catch (JSONException e) {
                                e.printStackTrace();
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
                //hidePDialog();

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                QuestionGetSet selectedques = movieList.get(position);
                String Querry = selectedques.getQues();
                String number = selectedques.getNumber();
                String date = selectedques.getDate();
                Intent ansScreen = new Intent(getApplicationContext(),see_answers.class);
                ansScreen.putExtra("Querry", Querry);
                ansScreen.putExtra("answer",answer.get(position));
                startActivity(ansScreen);
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(quesReq);
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
        getMenuInflater().inflate(R.menu.menu_might_be_answeres, menu);
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
