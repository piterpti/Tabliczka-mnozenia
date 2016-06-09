package layout;


import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import adrian.kamil.tabliczkamnozenia.Activity;
import adrian.kamil.tabliczkamnozenia.Others.AchAdapter;
import adrian.kamil.tabliczkamnozenia.Others.Achievement;
import adrian.kamil.tabliczkamnozenia.Others.GameProgress;
import adrian.kamil.tabliczkamnozenia.Others.Task;
import adrian.kamil.tabliczkamnozenia.R;
import java.util.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class GameEnd extends Fragment {

    private Button backMenuButton;
    private TextView resultTextView;
    private LinearLayout achievementsLayout;
    private ListView achievementListView;
    private ArrayList<Achievement> unlockedAchievements;


    public GameEnd() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_end, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        resultTextView = (TextView) view.findViewById(R.id.end_resultTextView);
        backMenuButton = (Button) view.findViewById(R.id.end_backToMenu);
        achievementsLayout = (LinearLayout) view.findViewById(R.id.end_achievementLayout);
        achievementListView = (ListView) view.findViewById(R.id.end_unlockedAchievements);
        backMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackToMenu();
            }
        });

        int correctAnswers = Activity.GAME_PROGRESS.getCorrectAnswers();
        int questionCount = Activity.GAME_PROGRESS.getLevel().getCount();
        float percentAnswers = ((float) correctAnswers / (float) questionCount) * 100f;
        String result = String.format("%.2f", percentAnswers) + "%";
        result += " (" + correctAnswers + "/" + questionCount + ")";
        resultTextView.setText(result);
        CheckAchievements((int) percentAnswers);
        SaveMistakes();
    }

    private void SaveMistakes() {
        String superMemo = "";
        Arrays.sort(Activity.GAME_TASKS, new Comparator<Task>() {
            @Override
            public int compare(Task lhs, Task rhs) {
                return Integer.valueOf(lhs.getId()).compareTo(Integer.valueOf(rhs.getId()));
            }
        });
        for(Task t : Activity.GAME_TASKS) {
            superMemo += t.getMistakes() + ",";
        }
        PreferenceManager.getDefaultSharedPreferences(Activity.CONTEXT).edit().putString(Activity.SUPER_MEMO_SHARED, superMemo).commit();
    }

    private void CheckAchievements(int percentAnswers) {
        if(unlockedAchievements == null)
        {
            unlockedAchievements = Activity.GAME_PROGRESS.getUnlockedAchievements(percentAnswers);
        }
        if(unlockedAchievements.size() > 0)
        {
            achievementsLayout.setVisibility(View.VISIBLE);
            Achievement[] array = new Achievement[unlockedAchievements.size()];
            array = unlockedAchievements.toArray(array);
            AchAdapter achAdapter = new AchAdapter(Activity.CONTEXT, array);
            achievementListView.setAdapter(achAdapter);
        }
    }

    public void BackToMenu()
    {
        Menu menu = new Menu();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, menu, Activity.MENU_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
