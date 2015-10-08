package wolverine.example.com.btp_farmer.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import wolverine.example.com.btp_farmer.R;
import wolverine.example.com.btp_farmer.model.QuestionGetSet;

public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<QuestionGetSet> quesItems;
 
 
    public CustomListAdapter(Activity activity, List<QuestionGetSet> quesItems) {
        this.activity = activity;
        this.quesItems = quesItems;
    }
 
    @Override
    public int getCount() {
        return quesItems.size();
    }
 
    @Override
    public Object getItem(int location) {
        return quesItems.get(location);
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
 
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.custom_list_layout_home, null);
 
        TextView question = (TextView) convertView.findViewById(R.id.txt_questions);
        TextView date = (TextView) convertView.findViewById(R.id.txt_date);
        TextView number = (TextView) convertView.findViewById(R.id.txt_asked_by);
 
        // getting movie data for the row
        QuestionGetSet m = quesItems.get(position);
          
        // title
        question.setText(m.getQues());
         
        // rating
        date.setText(m.getDate());
         
         
        // release year
        number.setText(m.getNumber());
 
        return convertView;
    }
 
}


