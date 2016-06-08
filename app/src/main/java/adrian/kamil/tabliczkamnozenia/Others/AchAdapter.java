package adrian.kamil.tabliczkamnozenia.Others;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import adrian.kamil.tabliczkamnozenia.R;

/**
 * Created by Piter on 07/06/2016.
 */
public class AchAdapter extends ArrayAdapter
{

    public static final int ACTIVE_COLOR = Color.GREEN;
    public static final int INACTIVE_COLOR = Color.RED;

    public AchAdapter(Context context, Achievement[] list) {
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Achievement ach = (Achievement) getItem(position);
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ach_adapter, parent, false);
        }
        TextView listItem = (TextView) convertView.findViewById(R.id.ach_listItem);
        listItem.setText(ach.getName());
        if(ach.isLocked())
        {
            listItem.setTextColor(INACTIVE_COLOR);
        }
        else
        {
            listItem.setTextColor(ACTIVE_COLOR);
        }
        return convertView;
    }
}
