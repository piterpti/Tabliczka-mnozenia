package layout;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import adrian.kamil.tabliczkamnozenia.Activity;
import adrian.kamil.tabliczkamnozenia.Others.Level;
import adrian.kamil.tabliczkamnozenia.Others.LevelAdapter;
import adrian.kamil.tabliczkamnozenia.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class LevelFrag extends Fragment {

    private ListView levelsListView;
    private Button resetAchievementButton;

    public LevelFrag() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_level, container, false);
        for(Level level : Activity.LEVELS) {
            if(level.equals(Activity.GAME_PROGRESS.getLevel())) {
                level.setLocked(false);
            } else {
                level.setLocked(true);
            }
        }
        resetAchievementButton = (Button) view.findViewById(R.id.level_resetAchievements);
        levelsListView = (ListView) view.findViewById(R.id.level_levelList);
        LevelAdapter levelAdapter = new LevelAdapter(Activity.CONTEXT , Activity.LEVELS);
        levelsListView.setAdapter(levelAdapter);
        levelsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Activity.GAME_PROGRESS.setLevel((Level) levelsListView.getItemAtPosition(position));
                Activity.GAME_PROGRESS.getLevel().setLocked(false);
                SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(Activity.DIFFICULT_LEVEL_KEY, Activity.GAME_PROGRESS.getLevel().getId());
                editor.commit();
                BackToMenu();
            }
        });
        resetAchievementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ach = "0,0,0,0,0";
                PreferenceManager.getDefaultSharedPreferences(Activity.CONTEXT).edit().putString(Activity.ACHIEVEMENT_KEY, ach).commit();
                Toast.makeText(Activity.CONTEXT, "Zresetowano osiągnięcia", Toast.LENGTH_LONG).show();
                BackToMenu();
            }
        });
        return view;
    }

    private void BackToMenu() {
        Menu main_menu_fragment = new Menu();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, main_menu_fragment, Activity.MENU_TAG);
        transaction.commit();
    }
}
