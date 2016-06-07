package adrian.kamil.tabliczkamnozenia.Others;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import adrian.kamil.tabliczkamnozenia.R;


/**
 * Created by Piter on 07/06/2016.
 */
public class LevelAdapter extends ArrayAdapter{

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

        return convertView;
    }
}
