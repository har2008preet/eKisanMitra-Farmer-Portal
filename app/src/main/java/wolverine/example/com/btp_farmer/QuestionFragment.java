package wolverine.example.com.btp_farmer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.AppController;
import wolverine.example.com.btp_farmer.adapter.CustomListAdapter;
import wolverine.example.com.btp_farmer.model.QuestionGetSet;

/**
 * Created by Wolverine on 23-06-2015.
 */
public class QuestionFragment extends Fragment{

    EditText query;
    Button sendQuerry;
    TextView previoustxt;
    private Database db;
    String number,searchques,ques;
    private SharedPreference sharedPreference;

    private ProgressDialog pDialog;
    private List<QuestionGetSet> movieList = new ArrayList<QuestionGetSet>();
    private ListView listView;
    private CustomListAdapter adapter;
    private static final String TAG = MainActivity.class.getSimpleName();
    ArrayList<String> answer = new ArrayList<String>();

    String firstname,phonenumber,lastWord;
    ArrayList<String> RField = new ArrayList<String>();
    ArrayList<Integer> j= new ArrayList<Integer>();

    public static final String Phone = "phoneKey";

    public static QuestionFragment newInstance() {
        QuestionFragment fragment = new QuestionFragment();
        return fragment;
    }

    public QuestionFragment () {
    }
    int[] temp = {0};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_sendquerry, container,
                false);
        query = (EditText)rootView.findViewById(R.id.query);
        sendQuerry = (Button)rootView.findViewById(R.id.sendQuerry);
        previoustxt = (TextView)rootView.findViewById(R.id.txt_previous);
        SharedPreferences preferences = this.getActivity().getSharedPreferences
                ("Info", Context.MODE_PRIVATE);
        firstname = preferences.getString("First_Name",null);
        phonenumber = preferences.getString("Mobile",null);

        sendQuerry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(query.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(),"Please Enter your Question", Toast.LENGTH_SHORT).show();
                }
                else {
                    RField.clear();
                    searchques = query.getText().toString();
                    String[] parts = searchques.split(" ");
                    /*String lastWord = parts[parts.length - 1];
                    lastWord = lastWord.trim();*/
                    //Toast.makeText(getActivity(), lastWord, Toast.LENGTH_SHORT).show();
                    for(int i=0;i<parts.length;i++) {
                        String lastWord = parts[i];
                        lastWord = lastWord.trim();
                        if (lastWord.matches("Breed") || lastWord.matches("breed") || lastWord.matches("BREED") || lastWord.matches("Breeding") || lastWord.matches("breeding") || lastWord.matches("BREEDING")) {
                            RField.add("Breed");
                        }
                        if (lastWord.matches("Soil") || lastWord.matches("soil") || lastWord.matches("SOIL") || lastWord.matches("Soiling") || lastWord.matches("soiling") || lastWord.matches("SOILING")) {
                            RField.add("Soil");
                        }
                        if (lastWord.matches("Harvest") || lastWord.matches("harvest") || lastWord.matches("HARVEST")
                                || lastWord.matches("Harvesting") || lastWord.matches("harvesting") || lastWord.matches("HARVESTING")) {
                            RField.add("HARVEST");
                        }
                        if (lastWord.matches("Crop") || lastWord.matches("crop") || lastWord.matches("CROP") || lastWord.matches("Croping") || lastWord.matches("croping") || lastWord.matches("CROPING")) {
                            RField.add("Crop");
                        }
                        if (lastWord.matches("Machinery") || lastWord.matches("machinery") || lastWord.matches("MACHINERY")
                                || lastWord.matches("Machine") || lastWord.matches("machine") || lastWord.matches("MACHINE")
                                || lastWord.matches("Machines") || lastWord.matches("machines") || lastWord.matches("MACHINES")) {
                            RField.add("Machinery");
                        }
                        if (lastWord.matches("Pesticide") || lastWord.matches("pesticide") || lastWord.matches("PESTICIDE")
                                || lastWord.matches("Pesticides") || lastWord.matches("pesticides") || lastWord.matches("PESTICIDES")
                                || lastWord.matches("DDT") || lastWord.matches("ddt")
                                || lastWord.matches("Insecticide") || lastWord.matches("insecticide") || lastWord.matches("INSECTICIDE")
                                || lastWord.matches("Insecticides") || lastWord.matches("insecticides") || lastWord.matches("INSECTICIDES")) {
                            RField.add("Pesticide");
                        }
                        if (lastWord.matches("Cultivation") || lastWord.matches("cultivation") || lastWord.matches("CULTIVATION")
                                || lastWord.matches("Farming") || lastWord.matches("farming") || lastWord.matches("FARMING")
                                || lastWord.matches("Planting") || lastWord.matches("planting") || lastWord.matches("PLANTING")) {
                            RField.add("Cultivation");
                        }
                        if (lastWord.matches("Irrigation") || lastWord.matches("irrigation") || lastWord.matches("IRRIGATION")) {
                            RField.add("Irrigation");
                        }
                        if (lastWord.matches("Compost") || lastWord.matches("compost") || lastWord.matches("COMPOST")
                                || lastWord.matches("Fertilizer") || lastWord.matches("fertilizer") || lastWord.matches("FERTILIZER")
                                || lastWord.matches("Fertilizers") || lastWord.matches("fertilizers") || lastWord.matches("FERTILIZERS")
                                || lastWord.matches("Fertiliser") || lastWord.matches("fertiliser") || lastWord.matches("FERTILISER")
                                || lastWord.matches("Fertiliser") || lastWord.matches("fertiliser") || lastWord.matches("FERTILISERS")) {
                            RField.add("Compost");
                        }
                        if (lastWord.matches("Climate") || lastWord.matches("climate") || lastWord.matches("CLIMATE")
                                || lastWord.matches("Temperature") || lastWord.matches("temperature") || lastWord.matches("TEMPERATURE")
                                || lastWord.matches("Weather") || lastWord.matches("weather") || lastWord.matches("WEATHER")) {
                            RField.add("Climate");
                        }
                        if (lastWord.matches("Food") || lastWord.matches("food") || lastWord.matches("FOOD")) {
                            RField.add("Food");
                        }
                        if (lastWord.matches("Agriculture") || lastWord.matches("agriculture") || lastWord.matches("AGRICULTURE")) {
                            RField.add("Agri");
                        }
                        if (lastWord.matches("Rain") || lastWord.matches("rain") || lastWord.matches("RAIN")) {
                            RField.add("Rain");
                        }
                    }
                    if(RField.isEmpty()){
                        RField.add("Breed");
                        RField.add("Soil");
                        RField.add("Harvest");
                        RField.add("Crop");
                        RField.add("Machinery");
                        RField.add("Pesticide");
                        RField.add("Cultivation");
                        RField.add("Irrigation");
                        RField.add("Compost");
                        RField.add("Climate");
                        RField.add("Food");
                        RField.add("Agri");
                        RField.add("Rain");
                    }
                    //Log.w("sendQuestion,RFiel======", String.valueOf(RField));

                    final String finalquery=query.getText().toString();


                    new AsyncTask<Void, Void, Void>(){
                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                            pDialog = new ProgressDialog(getActivity());
                            pDialog.setMessage("Submitting question ...");
                            pDialog.setCancelable(false);
                            pDialog.show();
                        }

                        @Override
                        protected Void doInBackground(Void... params) {
                            try {

                                for(int i=0;i<RField.size();i++){

                                    j.add(sendQuestion(firstname, phonenumber, finalquery, RField.get(i)));
                                }


                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return null;
                        }
                        @Override
                        protected void onPostExecute(Void result)
                        {
                            pDialog.dismiss();
                        }
                    }.execute();
                    if(temp[0]==0){
                        Toast.makeText(getActivity(),"Some error occured ", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getActivity(), "You have successfully submitted the question ", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        return rootView;
    }

    private int sendQuestion(final String firstname, final String phoneNumber, final String question, final String rField) throws IOException {
        //Toast.makeText(getApplicationContext(),question+" "+tag+" "+phoneNumber,Toast.LENGTH_LONG).show();
        /*pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Submitting question ...");
        pDialog.setCancelable(false);
        pDialog.show();*/
         temp = new int[]{0};
        ques = URLEncoder.encode(question, "utf-8");
        StringRequest SpecialReq = new StringRequest(Request.Method.GET,"http://192.168.43.17/farmer_portal/question_insert.php/?farmerName="+firstname+"&rField="+rField+"&question="+ques+"&number="+phoneNumber,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        //hidePDialog();
                        Log.d("url============", "http://192.168.43.17/farmer_portal/question_insert.php/?farmerName=" + firstname + "&rField=" + rField + "&question=" + ques + "&number=" + phoneNumber);
                        temp[0] = 1;


                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        //hidePDialog();
                        Toast.makeText(getActivity(), "Some error occured", Toast.LENGTH_SHORT).show();
                        temp[0] = 0;
                    }
                });
        SpecialReq.setRetryPolicy(
                new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                        0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));



        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(SpecialReq);
        return temp[0];

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
        ((Navigationfarmer) activity).onSectionAttached(1);
    }
}
