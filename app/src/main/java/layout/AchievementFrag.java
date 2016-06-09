package layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import adrian.kamil.tabliczkamnozenia.Activity;
import adrian.kamil.tabliczkamnozenia.Others.AchAdapter;
import adrian.kamil.tabliczkamnozenia.Others.Achievement;
import adrian.kamil.tabliczkamnozenia.R;


public class AchievementFrag extends Fragment {

    private ListView achListView;
    private AchAdapter achAdapter;

    public AchievementFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_achievement, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        achListView = (ListView) view.findViewById(R.id.ach_listView);
        achAdapter = new AchAdapter(Activity.CONTEXT, Activity.ACHIEVEMENTS);
        achListView.setAdapter(achAdapter);
    }
}
