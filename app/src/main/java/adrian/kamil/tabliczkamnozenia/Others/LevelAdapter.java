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
public class LevelAdapter extends ArrayAdapter{

    public static final int ACTIVE_COLOR = Color.GREEN;
    public static final int INACTIVE_COLOR = Color.RED;

    public LevelAdapter(Context context, Level[] list) {
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Level lvl = (Level) getItem(position);
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.level_adapter, parent, false);
        }
        TextView listItem = (TextView) convertView.findViewById(R.id.level_listItem);
        String textToDisp = lvl.getName() + "\n" + lvl.getCount() + " pytań | " + lvl.getTime() + " sekund na odpowiedź";
        listItem.setText(textToDisp);
        if(lvl.isLocked())
            listItem.setTextColor(INACTIVE_COLOR);
        else
            listItem.setTextColor(ACTIVE_COLOR);
        return convertView;
    }
}
