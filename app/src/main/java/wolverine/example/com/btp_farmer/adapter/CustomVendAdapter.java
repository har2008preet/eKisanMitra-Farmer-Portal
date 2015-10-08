package wolverine.example.com.btp_farmer.adapter;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import wolverine.example.com.btp_farmer.Product;
import wolverine.example.com.btp_farmer.R;


public class CustomVendAdapter extends ArrayAdapter<Product> {


    int groupid;
    ArrayList<Product> records;
    Context context;



    public CustomVendAdapter(Context context, int vg, int id, ArrayList<Product>
            records) {

        super(context, vg, id, records);
        this.context = context;
        groupid = vg;
        this.records = records;
    }



    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(groupid, parent, false);
        TextView textName = (TextView) itemView.findViewById(R.id.vend_name);
        textName.setText(records.get(position).getpName());
        TextView textPrice = (TextView) itemView.findViewById(R.id.vend_state);
        textPrice.setText(records.get(position).getuState());
        return itemView;

    }

}