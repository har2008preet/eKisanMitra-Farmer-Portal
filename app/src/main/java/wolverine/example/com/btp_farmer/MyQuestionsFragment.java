package wolverine.example.com.btp_farmer;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class MyQuestionsFragment extends Fragment {

    /**
     * Returns a new instance of this fragment for the given section number.
     */
    public static MyQuestionsFragment newInstance() {
        MyQuestionsFragment fragment = new MyQuestionsFragment();
        return fragment;
    }

    public MyQuestionsFragment () {
    }

    String farmerNumber;
    private ProgressDialog pDialog;
    private List<QuestionGetSet> movieList = new ArrayList<QuestionGetSet>();
    private ListView listView;
    private CustomListAdapter adapter;
    private static final String TAG = MainActivity.class.getSimpleName();
    private ArrayList<String> answer = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_questions, container,
                false);


        listView = (ListView) rootView.findViewById(R.id.list_ques);
        adapter = new CustomListAdapter(getActivity(), movieList);
        listView.setAdapter(adapter);

        SharedPreferences preferences = this.getActivity().getSharedPreferences("Info", Context.MODE_PRIVATE);
        farmerNumber=preferences.getString("Mobile",null);

        pDialog = new ProgressDialog(getActivity());
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        new AsyncTask<Void,Void,Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                archievequestions();
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                pDialog.dismiss();
            }
        }.execute();
        return rootView;
    }
    private void archievequestions(){
        // Creating volley request obj
        JsonArrayRequest quesReq = new JsonArrayRequest("http://192.168.43.17/farmer_portal/farmersquestions.php/?farmerNumber="+farmerNumber,
                new Response.Listener<JSONArray>() {
                    @Override

                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                QuestionGetSet movie = new QuestionGetSet();
                                movie.setQues(obj.getString("ques"));
                                movie.setDate(obj.getString("askDate"));
                                movie.setNumber(obj.getString("farmerName"));

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
                Log.d("Error:------",error.getMessage());

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                QuestionGetSet selectedques = movieList.get(position);
                String Querry = selectedques.getQues();
                String number = selectedques.getNumber();
                String date = selectedques.getDate();
                Intent ansScreen = new Intent(getActivity(),see_answers.class);
                ansScreen.putExtra("Querry", Querry);
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
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((Navigationfarmer) activity).onSectionAttached(3);
    }
}