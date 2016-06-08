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


public class AchievementFrag extends Fragment  implements AdapterView.OnItemLongClickListener{

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
        achListView.setOnItemLongClickListener(this);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Achievement a = (Achievement) parent.getItemAtPosition(position);
        a.setLocked(false);
        Activity.UNLOCKED_ACHIEVEMENTS[Activity.GAME_PROGRESS.getLevel().getId() - 1] = position + 1;
        achAdapter.notifyDataSetChanged();
        String ach = "";
        for(int i : Activity.UNLOCKED_ACHIEVEMENTS) {
            ach += i + ",";
        }
        PreferenceManager.getDefaultSharedPreferences(Activity.CONTEXT).edit().putString(Activity.ACHIEVEMENT_KEY, ach).commit();
        return false;
    }
}
