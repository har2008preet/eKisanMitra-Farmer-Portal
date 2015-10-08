package wolverine.example.com.btp_farmer;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Wolverine on 08-07-2015.
 */

public class TagCustomAdapter extends BaseAdapter {
    private static ArrayList<TagModel> searchArrayList;

    private LayoutInflater mInflater;

    public TagCustomAdapter(Context context, ArrayList<TagModel> results) {
        searchArrayList = results;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return searchArrayList.size();
    }

    public Object getItem(int position) {
        return searchArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.custom_list_tags, null);
            holder = new ViewHolder();
            holder.txtTag = (TextView) convertView.findViewById(R.id.tag_txt);
            holder.txtDesc= (TextView) convertView
                    .findViewById(R.id.desc_txt);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtTag.setText(searchArrayList.get(position).getTag());
        holder.txtDesc.setText(searchArrayList.get(position)
                .getDescription());

        return convertView;
    }

    static class ViewHolder {
        TextView txtTag;
        TextView txtDesc;
    }
}